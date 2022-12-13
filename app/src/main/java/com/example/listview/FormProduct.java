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
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.listview.DB.DBFirebase;
import com.example.listview.DB.DbHelper;
import com.example.listview.Entities.Product;
import com.example.listview.Services.ProductService;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class FormProduct extends AppCompatActivity {
    private Button btnFormProduct, btnGetFormProduct, btnDeleteFormProduct, btnUpdateFormProduct;
    private EditText editNameFormProduct, editDescriptionFormProduct, editPriceFormProduct, editIdFormProduct;
    private ImageView imgFormProduct;
    private DbHelper dbHelper;
    private DBFirebase dbFirebase;
    private ActivityResultLauncher<String> content;
    private ProductService productoService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_product);

        btnUpdateFormProduct = (Button) findViewById(R.id.btnUpdateFormProduct);
        btnGetFormProduct = (Button) findViewById(R.id.btnGetFormProduct);
        btnDeleteFormProduct = (Button) findViewById(R.id.btnDeleteFormProduct);
        btnFormProduct = (Button) findViewById(R.id.btnFormProduct);
        editNameFormProduct = (EditText) findViewById(R.id.editNameFormProduct);
        editDescriptionFormProduct = (EditText) findViewById(R.id.editDescriptionFormProduct);
        editPriceFormProduct = (EditText) findViewById(R.id.editPriceFormProduct);
        editIdFormProduct = (EditText) findViewById(R.id.editIdFormProduct);
        imgFormProduct = (ImageView) findViewById(R.id.imgFormProduct);

        editPriceFormProduct.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    Toast.makeText(getApplicationContext(), "Hello Enter", Toast.LENGTH_SHORT).show();
                    return true;
                }

                return false;
            }
        });

        try {
            dbHelper = new DbHelper(this);
            dbFirebase = new DBFirebase();
            productoService = new ProductService();
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

                Product product = new Product(
                        editNameFormProduct.getText().toString(),
                        editDescriptionFormProduct.getText().toString(),
                        Integer.parseInt(editPriceFormProduct.getText().toString()),
                        ""
                );
                dbHelper.insertProduct(product);
                //dbFirebase.insertProduct(producto);


                Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                startActivity(intent);
            }
        });

        btnGetFormProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = editIdFormProduct.getText().toString().trim();
                if(id.compareTo("") == 0){
                    Toast.makeText(getApplicationContext(),"Ingrese un ID",Toast.LENGTH_SHORT).show();
                }else{
                    ArrayList<Product> productDB = productoService.cursorToArray(dbHelper.getProductById(id));
                    if(productDB.size() != 0){
                        Product product = productDB.get(0);
                        editNameFormProduct.setText(product.getName());
                        editDescriptionFormProduct.setText(product.getDescription());
                        editPriceFormProduct.setText(String.valueOf(product.getPrice()));
                        //Bitmap bitmap = BitmapFactory.decodeByteArray(producto.getImage(), 0, producto.getImage().length);
                        //imgFormProduct.setImageBitmap(bitmap);
                    }else {
                        Toast.makeText(getApplicationContext(),"No existe",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnDeleteFormProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = editIdFormProduct.getText().toString().trim();
                dbHelper.deleteProductById(id);
                clean();
            }
        });

        btnUpdateFormProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = editIdFormProduct.getText().toString().trim();
                if(id.compareTo("")!=0){
                    dbHelper.updateProduct(
                            id,
                            editNameFormProduct.getText().toString(),
                            editDescriptionFormProduct.getText().toString(),
                            editPriceFormProduct.getText().toString(),
                            productoService.imageViewToByte(imgFormProduct)
                    );
                    clean();
                }
            }
        });
    }

    public void clean(){
        editNameFormProduct.setText("");
        editDescriptionFormProduct.setText("");
        editPriceFormProduct.setText("");
        imgFormProduct.setImageResource(R.drawable.ic_launcher_background);
    }
}