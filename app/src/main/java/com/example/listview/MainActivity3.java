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
    private ProductService productService;
    private Button btnReturn;
    private ImageView imgDetail;
    private TextView tvNameDetail;
    private TextView tvDescriptionDetail;
    private TextView tvPriceDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        tvNameDetail = (TextView) findViewById(R.id.tvNameDetail);
        tvDescriptionDetail = (TextView) findViewById(R.id.tvDescriptionDetail);
        tvPriceDetail = (TextView) findViewById(R.id.tvPriceDetail);
        imgDetail = (ImageView) findViewById(R.id.imgDetail);
        btnReturn = (Button) findViewById(R.id.btnReturn);

        dbHelper = new DbHelper(this);
        productService = new ProductService();
        Intent intent = getIntent();
        String id = String.valueOf(intent.getIntExtra("id",0));
        ArrayList<Product> productDB = productService.cursorToArray(dbHelper.getProductsById(id));
        Product product = productDB.get(0);
        Bitmap bitmap = BitmapFactory.decodeByteArray(product.getImage(),0,product.getImage().length);
        imgDetail.setImageBitmap(bitmap);

        tvNameDetail.setText(intent.getStringExtra("name"));
        tvDescriptionDetail.setText(intent.getStringExtra("description"));
        tvPriceDetail.setText(String.valueOf(intent.getIntExtra("price",0)));


        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getApplicationContext(),MainActivity2.class);
                startActivity(intent1);
            }
        });
    }
}