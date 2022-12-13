package com.example.listview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.listview.Adapters.ProductAdapter;
import com.example.listview.DB.DBFirebase;
import com.example.listview.DB.DbHelper;
import com.example.listview.Entities.Product;
import com.example.listview.Services.ProductService;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;


public class MainActivity2 extends AppCompatActivity {
    private DbHelper dbHelper;
    private DBFirebase dbFirebase;
    private ProductService productService;
    private ListView listViewProducts;
    private ProductAdapter productAdapter;
    private ArrayList<Product> arrayProductos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        arrayProductos = new ArrayList<>();
        listViewProducts = (ListView) findViewById(R.id.listViewProducts);
        try {
            dbHelper = new DbHelper(this);
            dbFirebase = new DBFirebase();
            productService = new ProductService();
            arrayProductos = productService.cursorToArray(dbHelper.getProducts());

        }catch (Exception e){
            Log.e("DB", e.toString());
        }

        productAdapter = new ProductAdapter(this, arrayProductos);
        listViewProducts.setAdapter(productAdapter);

        dbFirebase.getProducts(productAdapter, arrayProductos);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.actionAdd:
                Intent intent = new Intent(getApplicationContext(), FormProduct.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
