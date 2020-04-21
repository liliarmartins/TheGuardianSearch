package com.theguardiansearch.com;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

/**
 * A Fragment that shows the details about a News.
 * @author Lilia Ramalho Martins
 * @version 1.0
 */
public class TGNewsDetailsFragment extends Fragment {

    private TextView sectionTextView;
    private TextView dateTextView;
    private TextView titleTextView;
    private TextView urlTextView;
    private Bundle fromTheGuardianActivity;
    private AppCompatActivity parentActivity;

    /**
     * Empty default constructor.
     */
    public TGNewsDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Override the method onCreateView in the super class Fragment. It get the arguments
     * from TheGuardianActivity (or StarredActivity) with the details about a News and shows
     * them in the layout widgets. It also has a Hide button that finishes the Fragment.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View result = inflater.inflate(R.layout.activity_tg_news_details, container, false);

        sectionTextView = (TextView)result.findViewById(R.id.newsDetails_section);
        dateTextView = (TextView)result.findViewById(R.id.newsDetails_date);
        titleTextView = (TextView)result.findViewById(R.id.newsDetails_title);
        urlTextView = (TextView)result.findViewById(R.id.newsDetails_url);

        fromTheGuardianActivity = getArguments();
        String section = fromTheGuardianActivity.getString("SECTION");
        section = getString(R.string.tg_frag_section) + ": " + section;
        String date = fromTheGuardianActivity.getString("DATE");
        String title = fromTheGuardianActivity.getString("TITLE");
        String url = fromTheGuardianActivity.getString("URL");

        sectionTextView.setText(section);
        dateTextView.setText(date);
        titleTextView.setText(title);
        titleTextView.setTextColor(Color.rgb(255, 51, 51));
        urlTextView.setText(url);
        urlTextView.setLinkTextColor(Color.rgb(255, 102, 102));

        Button finishButton = (Button)result.findViewById(R.id.returnButton);
        finishButton.setOnClickListener( click -> {

            //Tell the parent activity to remove
            if (parentActivity.getSupportFragmentManager().findFragmentById(R.id.DetailFrameLayout2) != null) {
                parentActivity.finish();
            }
            else {
                parentActivity.getSupportFragmentManager().beginTransaction().remove(this).commit();
            }
        });

        return result;

    }

    /**
     * Override the method onAttach that get the Activity Context and assigns it to parentActivity
     * private field.
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        parentActivity = (AppCompatActivity)context;
    }


}
