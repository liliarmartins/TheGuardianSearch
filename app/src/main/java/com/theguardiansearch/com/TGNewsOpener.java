package com.theguardiansearch.com;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Class that extends SQLiteOpenHelper to handle a SQLite Database with one table called
 * ARTICLES. The table ARTICLES saves data about a News from The Guardian obtained using
 * Json.
 * @author Lilia Ramalho Martins
 * @version 1.0
 */
public class TGNewsOpener extends SQLiteOpenHelper {

    protected final static String DATABASE_NAME = "ArticlesDB";
    protected final static int VERSION_NUM = 1;
    public final static String TABLE_NAME = "ARTICLES";
    public final static String COL_DATE = "DATE";
    public final static String COL_TITLE = "TITLE";
    public final static String COL_URL = "URL";
    public final static String COL_SECTION = "SECTION";
    public final static String COL_WEB_ID = "WEB_ID";
    public final static String COL_ID = "_id";

    /**
     * Construction that receives the Context as parameter and calls the super Constructor.
     * @param ctx The Activity Context.
     */
    public TGNewsOpener(Context ctx)
    {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    /**
     * Overrides method onCreate from SQLiteOpenHelper and creates the table ARTICLES in
     * the SQLite Database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_DATE + " TEXT,"
                + COL_TITLE + " TEXT,"
                + COL_URL + " TEXT,"
                + COL_SECTION + " TEXT,"
                + COL_WEB_ID + " TEXT);");
    }

    /**
     * Overrides method onUpgrade from SQLiteOpenHelper and drops the table ARTICLES if a new
     * upgrated version of the Database occurs. Then onCreate is called to create an new empty
     * table ARTICLES.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop the old table:
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME);

        //Create the new table:
        onCreate(db);
    }

    /**
     * Overrides method onUpgrade from SQLiteOpenHelper and drops the table ARTICLES if a new
     * downgrated version of the Database occurs. Then onCreate is called to create an new empty
     * table ARTICLES.
     */
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop the old table:
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME);

        //Create the new table:
        onCreate(db);
    }
}