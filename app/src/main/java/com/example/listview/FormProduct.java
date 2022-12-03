package com.example.listview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.listview.DB.DbHelper;

import java.nio.charset.StandardCharsets;

public class FormProduct extends AppCompatActivity {

    private Button btnFormProduct;
    private EditText editNameProduct;
    private EditText editDescriptionProduct;
    private EditText editPriceProduct;
    private ImageView imgFormProduct;

    private DbHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_product);

        btnFormProduct = (Button) findViewById(R.id.btnFormProduct);
        editNameProduct = (EditText) findViewById(R.id.editNameProduct);
        editDescriptionProduct = (EditText) findViewById(R.id.editDescriptionProduct);
        editPriceProduct = (EditText) findViewById(R.id.editPriceProduct);
        imgFormProduct = (ImageView) findViewById(R.id.imgFormProduct);
        byte[] img = "".getBytes(StandardCharsets.UTF_8);
        try {
            dbHelper = new DbHelper(this);
        }catch (Exception e){
            Log.e("DB", e.toString());
        }
        btnFormProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.insertProduct(
                        editNameProduct.getText().toString(),
                        editDescriptionProduct.getText().toString(),
                        editPriceProduct.getText().toString(),
                        img);


                Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                startActivity(intent);
            }
        });


    }
}