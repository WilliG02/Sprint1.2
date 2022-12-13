package com.example.listview;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.listview.DB.DbHelper;
import com.example.listview.Entities.Product;
import com.example.listview.Services.ProductService;

import java.util.ArrayList;

public class MainActivity3 extends AppCompatActivity {
    private DbHelper dbHelper;
    private ProductService productoService;
    private Button btnReturn;
    private ImageView imgDetail;
    private TextView tvNameDetail, tvDescriptionDetail, tvPriceDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        btnReturn = (Button) findViewById(R.id.btnReturn);
        imgDetail = (ImageView) findViewById(R.id.imgDetail);
        tvNameDetail = (TextView) findViewById(R.id.tvNameDetail);
        tvDescriptionDetail = (TextView) findViewById(R.id.tvDescriptionDetail);
        tvPriceDetail = (TextView) findViewById(R.id.tvPriceDetail);

        dbHelper = new DbHelper(this);
        productoService = new ProductService();

        Intent intent = getIntent();
        String id = String.valueOf(intent.getIntExtra("id",0));
        ArrayList<Product> productDB = productoService.cursorToArray(dbHelper.getProductById(id));
        Product product = productDB.get(0);

        //Bitmap bitmap = BitmapFactory.decodeByteArray(producto.getImage(), 0, producto.getImage().length);
        //imgDetail.setImageBitmap(bitmap);


        tvNameDetail.setText(intent.getStringExtra("name"));
        tvDescriptionDetail.setText(intent.getStringExtra("description"));
        tvPriceDetail.setText(String.valueOf(intent.getIntExtra("price", 0)));


        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(getApplicationContext(), MainActivity2.class);
                startActivity(intent2);
            }
        });
    }
}