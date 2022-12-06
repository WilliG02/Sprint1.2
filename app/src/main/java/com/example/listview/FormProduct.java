package com.example.listview;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.display.DeviceProductInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.listview.DB.DbHelper;
import com.example.listview.Entities.Product;
import com.example.listview.Services.ProductService;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class FormProduct extends AppCompatActivity {

    private Button btnFormProduct, btnGetFormProduct, btnDeleteFormProduct;
    private EditText editNameFormProduct, editDescriptionFormProduct, editPriceFormProduct, editIdFormProduct;
    private ImageView imgFormProduct;
    private ActivityResultLauncher<String> content;
    private ProductService productService;

    private DbHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_product);

        btnGetFormProduct = (Button) findViewById(R.id.btnGetFormProduct);
        btnDeleteFormProduct = (Button) findViewById(R.id.btnDeleteFormProduct);
        btnFormProduct = (Button) findViewById(R.id.btnFormProduct);
        editNameFormProduct = (EditText) findViewById(R.id.editNameFormProduct);
        editDescriptionFormProduct = (EditText) findViewById(R.id.editDescriptionFormProduct);
        editPriceFormProduct = (EditText) findViewById(R.id.editPriceFormProduct);
        editIdFormProduct = (EditText) findViewById(R.id.editIdFormProduct);
        imgFormProduct = (ImageView) findViewById(R.id.imgFormProduct);

        try {
            dbHelper = new DbHelper(this);
            productService = new ProductService();
            content = registerForActivityResult(
                    new ActivityResultContracts.GetContent(),
                    new ActivityResultCallback<Uri>() {
                        @Override
                        public void onActivityResult(Uri result) {
                            try {
                                InputStream inputStream = getContentResolver().openInputStream(result);
                                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                                imgFormProduct.setImageBitmap(bitmap);
                            }catch (FileNotFoundException e){
                                Log.e("FileError", e.toString());
                            }
                        }
                    });
        }catch (Exception e){
            Log.e("DB", e.toString());
        }

        imgFormProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                content.launch("image/*");
            }
        });

        btnFormProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.insertProduct(
                        editNameFormProduct.getText().toString(),
                        editDescriptionFormProduct.getText().toString(),
                        editPriceFormProduct.getText().toString(),
                        productService.imageViewToBy(imgFormProduct));
                Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                startActivity(intent);
            }
        });

        btnGetFormProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = editIdFormProduct.getText().toString().trim();
                if(id.compareTo("") == 0){
                    Toast.makeText(getApplicationContext(),"Insert Id", Toast.LENGTH_SHORT).show();
                }else{
                    ArrayList<Product> productDB = productService.cursorToArray(dbHelper.getProductsById(id));
                    if(productDB.size() != 0){
                        Product product = productDB.get(0);
                        editNameFormProduct.setText(product.getName());
                        editDescriptionFormProduct.setText(product.getDescription());
                        editPriceFormProduct.setText(String.valueOf(product.getPrice()));
                        Bitmap bitmap = BitmapFactory.decodeByteArray(product.getImage(),0,product.getImage().length);
                        imgFormProduct.setImageBitmap(bitmap);
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Not Results", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnDeleteFormProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = editIdFormProduct.getText().toString().trim();
                dbHelper.deleteProductById(id);
            }
        });
    }
}