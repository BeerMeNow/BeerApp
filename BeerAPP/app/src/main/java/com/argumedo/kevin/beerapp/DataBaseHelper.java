package com.argumedo.kevin.beerapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.List;

/**
 * Created by mark on 5/7/15.
 */
public class DataBaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;

    private String[] projection = {
            //beerId, name, description, abv, pic, styleId
            Contract.PhotoEntry._ID,
            Contract.PhotoEntry.BEERID,
            Contract.PhotoEntry.NAME,
            Contract.PhotoEntry.ABV,
            Contract.PhotoEntry.STYLE,
            Contract.PhotoEntry.TYPE,};

    private static final String DATABASE_CREATE =
            "CREATE TABLE " +
                    Contract.PhotoEntry.TABLE_NAME + " (" +
                    Contract.PhotoEntry._ID + " TEXT PRIMARY KEY, " +
                    Contract.PhotoEntry.BEERID + " TEXT NOT NULL, " +
                    Contract.PhotoEntry.NAME + " TEXT NOT NULL, " +
                    Contract.PhotoEntry.ABV + " TEXT NOT NULL, " +
                    Contract.PhotoEntry.STYLE + " TEXT NOT NULL," +
                    Contract.PhotoEntry.TYPE + " TEXT NOT NULL" +")";


    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Contract.PhotoEntry.TABLE_NAME;

    public DataBaseHelper(Context context) {
        super(context, Contract.DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(Constants.TAG, "Create table command: " + DATABASE_CREATE);
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
    }

    public void insertPhotoEntry(Beer photo) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
//    private String id,day,min,max,night,eve,morn,wtf,description;
        cv.put(Contract.PhotoEntry._ID, photo.getBeerId());
        cv.put(Contract.PhotoEntry.BEERID, photo.getBeerId());
        cv.put(Contract.PhotoEntry.NAME, photo.getName());
        cv.put(Contract.PhotoEntry.ABV, photo.getAbv());
        cv.put(Contract.PhotoEntry.STYLE, photo.getStyleId());
        cv.put(Contract.PhotoEntry.TYPE, photo.getType());

        db.insert(Contract.PhotoEntry.TABLE_NAME, null, cv);
    }

    public Cursor getAllRows() {
        SQLiteDatabase db = getReadableDatabase();
        return db.query(Contract.PhotoEntry.TABLE_NAME, projection, null, null, null, null, null);

//        Here's the method with arguments:
//        public Cursor query (String table, String[] columns, String selection, String[]
//        selectionArgs, String groupBy, String orderBy, String limit)

    }

    public Cursor getRowByID(String id) {
        SQLiteDatabase db = getReadableDatabase();
        String[] ids = {String.valueOf(id)};
        return db.query(Contract.PhotoEntry.TABLE_NAME, projection, Contract.PhotoEntry._ID + "==?", ids, null, null, null);
    }

    public void deleteRow(String id) {
        SQLiteDatabase db = getWritableDatabase();
        String[] ids = {String.valueOf(id)};
        db.delete(Contract.PhotoEntry.TABLE_NAME, Contract.PhotoEntry._ID + "==?", ids);
    }

    public void addRows(List<Beer> photos) {
        for (Beer photo : photos) {
            insertPhotoEntry(photo);
        }
    }

    public void addRow(Beer photo) {

        insertPhotoEntry(photo);
    }
    public void clearTable() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from " + Contract.PhotoEntry.TABLE_NAME);
    }

    public void dropTable() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(SQL_DELETE_ENTRIES);
    }


}
