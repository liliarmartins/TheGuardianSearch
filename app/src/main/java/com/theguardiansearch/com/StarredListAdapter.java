package com.theguardiansearch.com;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * The adapter for the Listview showed in the StarredActivity.
 * @author Lilia Ramalho Martins
 * @version 1.0
 */
public class StarredListAdapter extends BaseAdapter {

    private Context context;
    private TGNewsOpener dbHelper;
    private SQLiteDatabase db;
    private List<TheGuardianArticle> elements = new ArrayList<>();
    private boolean isTablet;

    /**
     * Constructor with one parameters.
     * @param context The context of the StarredActivity where the ListView is shown.
     */
    public StarredListAdapter(Context context) {
        super();
        this.context = context;
    }

    /**
     * Get method for the List of TheGuardianArticle objects that are shown in the ListView.
     * @return List<TheGuardianArticle>.
     */
    public List<TheGuardianArticle> getElements() {
        return elements;
    }

    /**
     * Overrides method getCount from BaseAdapter
     * @return int The size of the list of TheGuardianArticle objects shown in the ListView.
     */
    @Override
    public int getCount() {
        return getElements().size();
    }

    /**
     * Overrides method getItem from BaseAdapter
     * @param position int with the position of the TheGuardianArticle object to be returned.
     * @return Object TheGuardianArticle object in the position passed as parameter.
     */
    @Override
    public Object getItem(int position) {
        return getElements().get(position);
    }

    /**
     * Overrides method getItemId from BaseAdapter. Since we not using a numeric id. We are
     * rather using the id String from the json. This method is not used. It only returns the
     * position from parameter.
     * @param position int.
     * @return long.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Overrides method getView from BaseAdapter. It inflates the layouts for each row in the
     * ListView. It also adds listener and implement using lambda functions the events when a
     * News title is clicked (and a fragment with the News details is called) and when the
     * full star is clicked (and a dialog asks with the user wants to remove the News from
     * the starred list).
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TheGuardianArticle itemArticle = (TheGuardianArticle) getItem(position);
        View newView;
        newView = inflater.inflate(R.layout.tg_starred_row_layout,parent,false);
        TextView articleTitleView = newView.findViewById(R.id.starredArticleTitle);
        articleTitleView.setText(itemArticle.getTitle());
        TextView articleDateView = newView.findViewById(R.id.starredArticleDate);
        articleDateView.setText(itemArticle.getDate());
        ImageButton starBtn = newView.findViewById(R.id.starredStarButton);
        starBtn.setImageResource(R.drawable.star_full);

        starBtn.setOnClickListener((click) -> {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(parent.getContext());
            alertDialogBuilder.setTitle(R.string.tg_remov_dialog_title);
            alertDialogBuilder.setMessage(itemArticle.getTitle());

            alertDialogBuilder.setPositiveButton(R.string.tg_remov_dialog_yes, (click2, arg) -> {
                dbHelper = new TGNewsOpener(context);
                db = dbHelper.getWritableDatabase();
                db.delete(TGNewsOpener.TABLE_NAME, TGNewsOpener.COL_WEB_ID + "= ?", new String[]{itemArticle.getId()});
                this.getElements().remove(position);
                this.notifyDataSetChanged();
            });

            alertDialogBuilder.setNegativeButton(R.string.tg_remov_dialog_cancel, (click3, arg) -> {
                alertDialogBuilder.create().dismiss();
            });
            alertDialogBuilder.create().show();
        });

        newView.setOnClickListener((click) -> {
            Bundle dataToPass = new Bundle();
            dataToPass.putString("SECTION", itemArticle.getSectionName());
            dataToPass.putString("DATE", itemArticle.getDate());
            dataToPass.putString("TITLE", itemArticle.getTitle());
            dataToPass.putString("URL", itemArticle.getUrl());


            AppCompatActivity mainActivity = (AppCompatActivity)context;
            FrameLayout detailFrameLayout = (FrameLayout)mainActivity.findViewById(R.id.DetailFrameLayout);

            if (detailFrameLayout == null)
                isTablet = false;
            else
                isTablet = true;

            if(isTablet) {
                TGNewsDetailsFragment dFragment = new TGNewsDetailsFragment();
                dFragment.setArguments(dataToPass); //pass it a bundle for information

                mainActivity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.DetailFrameLayout, dFragment) //Add the fragment in FrameLayout
                        .commit(); //actually load the fragment.

            }
            else //is phone
            {
                Intent nextActivity = new Intent(context, TGNewsEmptyFrameActivity.class);
                nextActivity.putExtras(dataToPass); //send data to next activity
                context.startActivity(nextActivity); //make the transition
            }
        });

        return newView;
    }
}
