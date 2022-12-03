package com.example.listview.Services;

import android.database.Cursor;

import com.example.listview.Entities.Product;
import com.example.listview.R;

import java.util.ArrayList;

public class ProductService {
    public ArrayList<Product> cursorToArray(Cursor cursor){
        ArrayList<Product> list = new ArrayList<>();

        if(cursor.getCount() != 0){
            while(cursor.moveToNext()){
                Product product = new Product(
                        R.drawable.paleta_rosa,
                        cursor.getString(1),
                        cursor.getString(2),
                        Integer.parseInt(cursor.getString(3))
                );
                list.add(product);
            }
        }
        return list;
    }
}
