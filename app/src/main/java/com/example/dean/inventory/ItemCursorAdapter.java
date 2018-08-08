package com.example.dean.inventory;


import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.dean.inventory.data.ItemContract.ItemEntry;


public class ItemCursorAdapter extends CursorAdapter{
    public ItemCursorAdapter(Context context, Cursor c){super(context,c,0);}
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent){
        return LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);
    }
    @Override
    public void bindView(View view, Context context, Cursor cursor){
        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        TextView priceTextView = (TextView) view.findViewById(R.id.price);
        TextView quantityTextView = (TextView) view.findViewById(R.id.quantity);
        int namecolumnIndex = cursor.getColumnIndex(ItemEntry.COLUMN__PRODUCT_NAME);
        int pricecolumnIndex = cursor.getColumnIndex(ItemEntry.COLUMN_PRODUCT_QUANTITY);
        int quantitycolumnIndex = cursor.getColumnIndex(ItemEntry.COLUMN_PRODUCT_QUANTITY);
        String itemname = cursor.getString(namecolumnIndex);
        String itemprice = cursor.getString(pricecolumnIndex);
        String itemquantity = cursor.getString(quantitycolumnIndex);

        nameTextView.setText(itemname);
        priceTextView.setText("£" + itemprice);
        quantityTextView.setText(itemquantity);

    }
}
