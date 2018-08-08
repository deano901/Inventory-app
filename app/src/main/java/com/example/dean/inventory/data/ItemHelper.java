package com.example.dean.inventory.data;

import android.content.ClipData;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.dean.inventory.data.ItemContract.ItemEntry;

public class ItemHelper extends SQLiteOpenHelper{
    public static final String LOG_TAG = ItemHelper.class.getSimpleName();
    //Name of database file
    private static final String DATABASE_NAME = "inventory.db";
    //Database version
    private static final int DATABASE_VERSION = 1;

    public ItemHelper(Context context) {super(context, DATABASE_NAME,null,DATABASE_VERSION);}

    @Override
    public void onCreate(SQLiteDatabase db ){
        String SQL_CREATE_PRODUCT_TABLE = "CREATE TABLE " + ItemEntry.TABLE_NAME + " ("
                + ItemEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ItemEntry.COLUMN__PRODUCT_NAME + " TEXT NOT NULL, "
                + ItemEntry.COLUMN_PRODUCT_PRICE + " INTEGER  DEFAULT 0, "
                + ItemEntry.COLUMN_PRODUCT_QUANTITY + " INTEGER DEFAULT 0, "
                + ItemEntry.COLUMN_SUPPLIER_NAME + " TEXT, "
                + ItemEntry.COLUMN_SUPPLIER_NUMBER + " TEXT);";

        db.execSQL(SQL_CREATE_PRODUCT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldversion, int newVersion){

    }


}
