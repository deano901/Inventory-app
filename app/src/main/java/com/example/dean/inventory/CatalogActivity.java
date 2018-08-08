package com.example.dean.inventory;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;


import com.example.dean.inventory.data.ItemContract.ItemEntry;
import com.example.dean.inventory.data.ItemHelper;



public class CatalogActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    public static final int ITEM_LOADER = 0;

    ItemCursorAdapter mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
            startActivity(intent);}
        });

        ListView petListView = (ListView) findViewById(R.id.list);

        View emptyView = findViewById(R.id.empty_view);
        petListView.setAdapter(mCursorAdapter);

        petListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                Uri currentItemUri = ContentUris.withAppendedId(ItemEntry.CONTENT_URI,id);
                intent.setData(currentItemUri);
                startActivity(intent);
            }
        });
    }

    private void  insertitem(){
        ContentValues values = new ContentValues();
        values.put(ItemEntry.COLUMN__PRODUCT_NAME, "AI-Python");
        values.put(ItemEntry.COLUMN_PRODUCT_PRICE, 219);
        values.put(ItemEntry.COLUMN_PRODUCT_QUANTITY, 1000);
        values.put(ItemEntry.COLUMN_SUPPLIER_NAME, "Udactity");
        values.put(ItemEntry.COLUMN_SUPPLIER_NUMBER,"555-555-555" );

        Uri newUri = getContentResolver().insert(ItemEntry.CONTENT_URI, values);

    }

    private void deleteAllPets(){
        int rowsDeleted = getContentResolver().delete(ItemEntry.CONTENT_URI,null,null);
        Log.v("CatalogActivity", rowsDeleted + " row deleted from inventory");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_insert_dummy_data:
                insertitem();
                return true;
            case R.id.action_delete_all_entries:
                deleteAllPets();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle){
        String[] projection = {
                ItemEntry._ID,
                ItemEntry.COLUMN__PRODUCT_NAME,
                ItemEntry.COLUMN_PRODUCT_PRICE,
                ItemEntry.COLUMN_PRODUCT_QUANTITY};
        return new CursorLoader(this,
                ItemEntry.CONTENT_URI,
                projection,null,null,null);
        }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data){
        mCursorAdapter.swapCursor(data);
    }
    @Override
    public void onLoaderReset(Loader<Cursor> loader){
        mCursorAdapter.swapCursor(null);
    }
}