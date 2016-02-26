package com.teamtreehouse.friendlyforecast.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by benjakuben on 4/8/14.
 */
public class ForecastHelper extends SQLiteOpenHelper {

    /*
     * Table and column information
     */
    public static final String TABLE_TEMPERATURES = "TEMPERATURES";
    public static final String COLUMN_ID = "_ID";
    public static final String COLUMN_TEMPERATURE = "TEMPERATURE";
    public static final String COLUMN_TIME = "TIME";

    /*
     * Database information
     */
    private static final String DB_NAME = "temperatures.db";
    private static final int DB_VERSION = 2; // Must increment to trigger an upgrade
    private static final String DB_CREATE =
            "CREATE TABLE " + TABLE_TEMPERATURES + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TEMPERATURE + " REAL, " +
                    COLUMN_TIME + " INTEGER)";

    private static final String DB_ALTER =
            "ALTER TABLE " + TABLE_TEMPERATURES + " ADD COLUMN " + COLUMN_TIME + " INTEGER";

    public ForecastHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_CREATE);
    }

    /*
     * This is triggered by incrementing DB_VERSION
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DB_ALTER);
    }
}
