package com.theguardiansearch.com;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * The adapter for the Listview showed in TheGuardianActivity.
 * @author Lilia Ramalho Martins
 * @version 1.0
 */
public class TGArticlesListAdapter extends BaseAdapter {

    private Context context;
    private TGNewsOpener dbHelper;
    private SQLiteDatabase db;
    private Cursor results;
    private List<TheGuardianArticle> elements = new ArrayList<>();
    private boolean isTablet;

    /**
     * Constructor with one parameters.
     * @param context The context of the TheGuardianActivity where the ListView is shown.
     */
    public TGArticlesListAdapter(Context context) {
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
     * star near a News is clicked (and the StarButtonOnClickListener handles the event).
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TheGuardianArticle itemArticle = (TheGuardianArticle) getItem(position);
        View newView;
        newView = inflater.inflate(R.layout.tg_row_layout,parent,false);
        TextView articleTitleView = newView.findViewById(R.id.articleTitle);
        articleTitleView.setText(itemArticle.getTitle());
        TextView articleDateView = newView.findViewById(R.id.articleDate);
        articleDateView.setText(itemArticle.getDate());
        ImageButton starBtn = newView.findViewById(R.id.starButton);
        if (itemArticle.isStarred())
            starBtn.setImageResource(R.drawable.star_full);
        else {
            dbHelper = new TGNewsOpener(context);
            db = dbHelper.getWritableDatabase();
            String [] columns = {TGNewsOpener.COL_WEB_ID};
            results = db.query(false,
                    TGNewsOpener.TABLE_NAME,
                    columns,
                    TGNewsOpener.COL_WEB_ID + " like ?",
                    new String[] {itemArticle.getId()},
                    null, null, null, null);
            if (results.getCount() == 0)
                starBtn.setImageResource(R.drawable.star_empty);
            else
                starBtn.setImageResource(R.drawable.star_full);
        }
        starBtn.setOnClickListener( new StarButtonOnClickListener(itemArticle, context));

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


