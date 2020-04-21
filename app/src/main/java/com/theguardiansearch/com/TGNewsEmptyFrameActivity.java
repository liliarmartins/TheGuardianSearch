package com.theguardiansearch.com;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Activity that is started instead of TGNewsDetailsFragment when the application is running
 * in a phone and not in a tablet. Its only task is to start the fragment passing the bundle
 * with the News details.
 * @author Lilia Ramalho Martins
 * @version 1.0
 */
public class TGNewsEmptyFrameActivity extends AppCompatActivity {

    /**
     * Override the method onCreate in the super class AppCompatActivity. Its only task is to
     * start TGNewsDetailsFragment passing the bundle with the News details.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tg_empty_frame);

        Bundle dataToPass = getIntent().getExtras();

        TGNewsDetailsFragment dFragment = new TGNewsDetailsFragment();
        dFragment.setArguments( dataToPass ); //pass it a bundle for information
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.DetailFrameLayout2, dFragment) //Add the fragment in FrameLayout
                .commit(); //actually load the fragment.

    }
}
