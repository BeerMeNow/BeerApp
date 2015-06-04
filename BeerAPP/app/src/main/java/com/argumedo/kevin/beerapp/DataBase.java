package com.argumedo.kevin.beerapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.List;

public class DataBase extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;

    private String[] projection = {
            Contract.favEntry.BEER_NAME,
            Contract.favEntry.BEER_ID,
            Contract.favEntry.BEER_DESC,
            Contract.favEntry.BEER_ABV,
    };


    private static final String DATABASE_CREATE =
            "CREATE TABLE " +
                    Contract.favEntry.TABLE_NAME + " (" +
                    Contract.favEntry._ID + " INTEGER PRIMARY KEY, " +
                    Contract.favEntry.BEER_NAME + " TEXT NOT NULL, " +
                    Contract.favEntry.BEER_ID + " TEXT NOT NULL, " +
                    Contract.favEntry.BEER_DESC + " TEXT NOT NULL, " +
                    Contract.favEntry.BEER_ABV + " TEXT NOT NULL " + ")";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Contract.favEntry.TABLE_NAME;

    public DataBase(Context context) {
        super(context, Contract.DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
    }

    public void insertBeerEntry(Beer beer) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(Contract.favEntry._ID, 0);
        cv.put(Contract.favEntry.BEER_NAME, beer.getName());
        cv.put(Contract.favEntry.BEER_ID, beer.getBeerId());
        cv.put(Contract.favEntry.BEER_DESC, beer.getDescription());
        cv.put(Contract.favEntry.BEER_ABV, beer.getAbv());
        db.insert(Contract.favEntry.TABLE_NAME, null, cv);
    }

    public Cursor getAllRows() {
        SQLiteDatabase db = getReadableDatabase();
        return db.query(Contract.favEntry.TABLE_NAME, projection, null, null, null, null, null);

//        Here's the method with arguments:
//        public Cursor query (String table, String[] columns, String selection, String[]
//        selectionArgs, String groupBy, String orderBy, String limit)

    }

    public Cursor getRowByID(long id) {
        SQLiteDatabase db = getReadableDatabase();
        String[] ids = {String.valueOf(id)};
        return db.query(Contract.favEntry.TABLE_NAME, projection, Contract.favEntry._ID + "==?", ids, null, null, null);
    }

    public void deleteRow(long id) {
        SQLiteDatabase db = getWritableDatabase();
        String[] ids = {String.valueOf(id)};
        db.delete(Contract.favEntry.TABLE_NAME, Contract.favEntry._ID + "==?", ids);
    }

    public void addRows(List<Beer> favorites) {
        for (Beer beer : favorites) {
            insertBeerEntry(beer);
        }
    }

    public void clearTable() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from " + Contract.favEntry.TABLE_NAME);
    }

    public void dropTable() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(SQL_DELETE_ENTRIES);
    }


}