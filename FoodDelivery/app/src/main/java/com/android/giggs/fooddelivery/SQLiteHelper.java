package com.android.giggs.fooddelivery;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by giggs on 5/27/2016.
 */
public class SQLiteHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "OrdersDatabase.db";
    public static final String TABLE_NAME = "ORDERS";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_ITEM_NAME = "ITEM_NAME";
    public static final String COLUMN_ITEM_PRICE = "ITEM_PRICE";
    public static final String COLUMN_ITEM_QUANTITY = "ITEM_QUANTITY";
    public static final String COLUMN_DELIVERY_DAY = "DELIVERY_DAY";

    private SQLiteDatabase database;

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " ( " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_ITEM_NAME + " VARCHAR, " + COLUMN_ITEM_PRICE + " REAL,"+ COLUMN_ITEM_QUANTITY + " INTEGER,"+ COLUMN_DELIVERY_DAY +" VARCHAR);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);
        onCreate(db);
    }

    public void insertRecord(OrderModel order){
        database = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ITEM_NAME, order.getITEM_NAME());
        contentValues.put(COLUMN_ITEM_PRICE, order.getITEM_PRICE());
        contentValues.put(COLUMN_ITEM_QUANTITY, order.getITEM_QUANTITY());
        contentValues.put(COLUMN_DELIVERY_DAY, order.getDELIVERY_DAY());
        database.insert(TABLE_NAME, null, contentValues);
        database.close();
    }
}
