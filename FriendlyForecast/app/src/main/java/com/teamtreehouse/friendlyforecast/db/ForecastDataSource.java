package com.teamtreehouse.friendlyforecast.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.teamtreehouse.friendlyforecast.services.Forecast;

/**
 * Created by benjakuben on 4/8/14.
 */
public class ForecastDataSource {

    private SQLiteDatabase mDatabase;       // The actual DB!
    private ForecastHelper mForecastHelper; // Helper class for creating and opening the DB
    private Context mContext;

    public ForecastDataSource(Context context) {
        mContext = context;
        mForecastHelper = new ForecastHelper(mContext);
    }

    /*
     * Open the db. Will create if it doesn't exist
     */
    public void open() throws SQLException {
        mDatabase = mForecastHelper.getWritableDatabase();
    }

    /*
     * We always need to close our db connections
     */
    public void close() {
        mDatabase.close();
    }

    /*
     * CRUD operations!
     */

    /*
     * INSERT
     */
    public void insertForecast(Forecast forecast) {
        mDatabase.beginTransaction();

        try {
            for (Forecast.HourData hour : forecast.hourly.data) {
                ContentValues values = new ContentValues();
                values.put(ForecastHelper.COLUMN_TEMPERATURE, hour.temperature);
                mDatabase.insert(ForecastHelper.TABLE_TEMPERATURES, null, values);
            }
            mDatabase.setTransactionSuccessful();
        }
        finally {
            mDatabase.endTransaction();
        }
    }

    /*
     * SELECT ALL
     */
    public Cursor selectAllTemperatures() {
        Cursor cursor = mDatabase.query(
                ForecastHelper.TABLE_TEMPERATURES, // table
                new String[] { ForecastHelper.COLUMN_TEMPERATURE }, // column names
                null, // where clause
                null, // where params
                null, // groupby
                null, // having
                null  // orderby
        );

        return cursor;
    }

    /*
     * SELECT with WHERE clause
     */
    public Cursor selectTempsGreaterThan(String minTemp) {
        String whereClause = ForecastHelper.COLUMN_TEMPERATURE + " > ?";

        Cursor cursor = mDatabase.query(
                ForecastHelper.TABLE_TEMPERATURES, // table
                new String[] { ForecastHelper.COLUMN_TEMPERATURE }, // column names
                whereClause, // where clause
                new String[] { minTemp }, // where params
                null, // groupby
                null, // having
                null  // orderby
        );

        return cursor;
    }

    /*
     * UPDATE
     */
    public int updateTemperature(double newTemp) {
        ContentValues values = new ContentValues();
        values.put(ForecastHelper.COLUMN_TEMPERATURE, newTemp);
        int rowsUpdated = mDatabase.update(
                ForecastHelper.TABLE_TEMPERATURES, // table
                values, // values
                null,   // where clause
                null    // where params
        );

        return rowsUpdated;
    }

    /*
     * DELETE
     */
    public void deleteAll() {
        mDatabase.delete(
            ForecastHelper.TABLE_TEMPERATURES, // table
            null, // where clause
            null  // where params
        );
    }
}
