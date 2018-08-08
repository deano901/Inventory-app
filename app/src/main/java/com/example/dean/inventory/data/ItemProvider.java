package com.example.dean.inventory.data;
import android.content.ClipData;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.example.dean.inventory.data.ItemContract.ItemEntry;

public class ItemProvider extends ContentProvider{
    public static final String LOG_TAG = ItemProvider.class.getSimpleName();

    private ItemHelper mDbHelper;

    private static final int ITEMS = 100;
    private static final int ITEM_ID = 101;
    private static final UriMatcher aUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        aUriMatcher.addURI(ItemContract.CONTENT_AUTHORITY,ItemContract.PATH_ITEMS,ITEMS);
        aUriMatcher.addURI(ItemContract.CONTENT_AUTHORITY,ItemContract.PATH_ITEMS + "/#",ITEM_ID);
    }
    /**
     * Initialize the provider and the database helper object.
     */
    @Override
    public boolean onCreate() {
        mDbHelper = new ItemHelper(getContext());

        return true;
    }

    /**
     * Perform the query for the given URI. Use the given projection, selection, selection arguments, and sort order.
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        Cursor cursor;
        int match = aUriMatcher.match(uri);
        switch(match){
            case ITEMS:
                cursor = database.query(ItemEntry.TABLE_NAME, projection, selection,selectionArgs, null, null, sortOrder);
                break;
            case ITEM_ID:
                selection = ItemEntry._ID + "=?";
                selectionArgs = new String[]{ String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(ItemEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot Query unknown URI " + uri);

        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    /**
     * Insert new data into the provider with the given ContentValues.
     */
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = aUriMatcher.match(uri);
        switch (match){
            case ITEMS:
                return insertItem(uri,contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);

        }

    }

    private Uri insertItem(Uri uri, ContentValues values){
        String name = values.getAsString(ItemEntry.COLUMN__PRODUCT_NAME);
        if(name == null){
            throw new IllegalArgumentException("Item requires name");
        }
        Integer price = values.getAsInteger(ItemEntry.COLUMN_PRODUCT_PRICE);
        if(price != null || price < 0 ){
            throw new IllegalArgumentException("Item requires valid price");
        }
        Integer quantity = values.getAsInteger(ItemEntry.COLUMN_PRODUCT_QUANTITY);
        if(quantity != null || quantity < 0){
            throw new IllegalArgumentException("Item requires valid quantity");
        }

         // no need to check supplier info null is accepted.

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        long id = database.insert(ItemEntry.TABLE_NAME,null,values);
        if(id == -1 ){
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return ContentUris.withAppendedId(uri,id);

    }
        /**
     * Updates the data at the given selection and selection arguments, with the new ContentValues.
     */
    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        final int match = aUriMatcher.match(uri);
        switch(match){
            case ITEMS:
                return updateItem(uri,contentValues,selection,selectionArgs);
            case ITEM_ID:
                selection = ItemEntry._ID + "=?";
                selectionArgs = new String[]{ String.valueOf(ContentUris.parseId(uri)) };
                return updateItem(uri,contentValues,selection,selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    private int updateItem(Uri uri, ContentValues values, String selection,String[] selectionArgs){
        if(values.containsKey(ItemEntry.COLUMN__PRODUCT_NAME)){
            String name = values.getAsString(ItemEntry.COLUMN__PRODUCT_NAME);
            if(name == null){
                throw new IllegalArgumentException("Item requires a name");
            }
        }
        if(values.containsKey(ItemEntry.COLUMN_PRODUCT_PRICE)){
            Integer price = values.getAsInteger(ItemEntry.COLUMN_PRODUCT_PRICE);
            if(price != null || price < 0){
                throw new IllegalArgumentException("Item requires a price");
            }
        }
        if(values.containsKey(ItemEntry.COLUMN_PRODUCT_QUANTITY)){
            Integer quantity = values.getAsInteger(ItemEntry.COLUMN_PRODUCT_QUANTITY);
            if (quantity != null || quantity <0){
                throw new IllegalArgumentException("Item requires a quantity");
            }
        }
        if(values.size() == 0 ){
            return 0;
        }

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        int rowUpdated = database.update(ItemEntry.TABLE_NAME, values,selection,selectionArgs);
        if(rowUpdated != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowUpdated;
    }

    /**
     * Delete the data at the given selection and selection arguments.
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        int rowDeleted;
        final int match = aUriMatcher.match(uri);
        switch (match){
            case ITEMS:
                rowDeleted = database.delete(ItemEntry.TABLE_NAME,selection,selectionArgs);
                break;
            case ITEM_ID:
                selection = ItemEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowDeleted = database.delete(ItemEntry.TABLE_NAME,selection,selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
        if(rowDeleted != 0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return rowDeleted;
    }

    /**
     * Returns the MIME type of data for the content URI.
     */
    @Override
    public String getType(Uri uri) {
        final int match = aUriMatcher.match(uri);
        switch (match){
            case ITEMS:
                return ItemEntry.CONTENT_LIST_TYPE;
            case ITEM_ID:
                return ItemEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri +" with match" + match);
        }
    }
}
