package com.theguardiansearch.com;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Activity that show the list of saved news.
 * @author Lilia Ramalho Martins
 * @version 1.0
 */
public class StarredActivity extends AppCompatActivity {

    private ListView listView;
    private TGNewsOpener dbHelper;
    private SQLiteDatabase db;
    private Cursor results;

    /**
     * Override the method onCreate in the super class AppCompatActivity. It creates a
     * StarredListAdapter to load the ListView with the saved News in database.
     * @param savedInstanceState not used Bundle.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tg_activity_starred);

        listView = (ListView)findViewById(R.id.list_starred);
        StarredListAdapter myStarredListAdapter = new StarredListAdapter(this);
        dbHelper = new TGNewsOpener(this);
        db = dbHelper.getWritableDatabase();
        String [] columns = {TGNewsOpener.COL_ID,
                TGNewsOpener.COL_DATE,
                TGNewsOpener.COL_TITLE,
                TGNewsOpener.COL_URL,
                TGNewsOpener.COL_SECTION,
                TGNewsOpener.COL_WEB_ID};
        results = db.query(false, TGNewsOpener.TABLE_NAME, columns, null, null, null, null, null, null);
        int dateColIndex = results.getColumnIndex(TGNewsOpener.COL_DATE);
        int titleColIndex = results.getColumnIndex(TGNewsOpener.COL_TITLE);
        int urlColIndex = results.getColumnIndex(TGNewsOpener.COL_URL);
        int sectionColIndex = results.getColumnIndex(TGNewsOpener.COL_SECTION);
        int webIdColIndex = results.getColumnIndex(TGNewsOpener.COL_WEB_ID);

        if (results.getCount() > 0) {
            results.moveToFirst();
            do {
                String date = results.getString(dateColIndex);
                String title = results.getString(titleColIndex);
                String url = results.getString(urlColIndex);
                String section = results.getString(sectionColIndex);
                String webID = results.getString(webIdColIndex);

                myStarredListAdapter.getElements().add(new TheGuardianArticle(date, title, url, section, webID));

            } while(results.moveToNext());
        }

        listView.setAdapter(myStarredListAdapter);

    }
}
