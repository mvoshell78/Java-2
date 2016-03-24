package com.example.mich.mvoshell_contentprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Mich on 3/22/16.
 */
public class DBOpenHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "cities.db";
    public static final int DATABASE_VERSION = 1;


    public static final String TABLE_NOTES = "cities";
    public static final String NOTE_ID = "_id";
    public static final String CITY_Text = "cityNameText";
    public static final String POPULATION_Text = "populationText";
    public static final String CLIMATE_Text = "climateText";


    public static final String[] ALL_COLUMNS = {NOTE_ID, CITY_Text, CLIMATE_Text, POPULATION_Text};

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NOTES + " (" +
                    NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CITY_Text + " TEXT, " +
                    CLIMATE_Text + " TEXT, " +
                    POPULATION_Text + " TEXT " +
                    ")";





    public DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_NOTES);
        onCreate(db);
    }
}
