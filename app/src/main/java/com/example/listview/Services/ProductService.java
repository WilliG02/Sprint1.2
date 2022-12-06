package com.example.listview.Services;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import com.example.listview.Entities.Product;
import com.example.listview.R;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class ProductService {
    public ArrayList<Product> cursorToArray(Cursor cursor){
        ArrayList<Product> list = new ArrayList<>();

        if(cursor.getCount() != 0){
            while(cursor.moveToNext()){
                Product product = new Product(
                        cursor.getInt(0),
                        cursor.getBlob(4),
                        cursor.getString(1),
                        cursor.getString(2),
                        Integer.parseInt(cursor.getString(3))
                );
                list.add(product);
            }
        }
        return list;
    }

    public byte[] imageViewToBy (ImageView imageView){
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
}
