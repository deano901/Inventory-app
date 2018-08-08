package com.example.dean.inventory.data;

import android.content.ContentResolver;
import android.provider.BaseColumns;
import android.net.Uri;
public final class ItemContract {

    private ItemContract() {
    }
    public static final String CONTENT_AUTHORITY = "com.example.android.pets";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" +  CONTENT_AUTHORITY);
    public static String PATH_ITEMS = "inventory";

    public static final class ItemEntry implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI,PATH_ITEMS);
        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ITEMS;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ITEMS;

        // name of datebase
        public final static String TABLE_NAME = "inventory";
        //Unique ID  Type:Integer
        public final static String _ID = BaseColumns._ID;
        //product name    Type:String
        public final static String COLUMN__PRODUCT_NAME = "name";
        //product price         Type:Integer
        public final static String COLUMN_PRODUCT_PRICE = "price";
        //Quantity        Type:Integer
        public final static String COLUMN_PRODUCT_QUANTITY = "quantity";
        // Supplier Name    Type:String
        public final static String COLUMN_SUPPLIER_NAME = "supplier";
        // Supplier Number   Type:Integer
        public final static String COLUMN_SUPPLIER_NUMBER = "contact";


    }
}

