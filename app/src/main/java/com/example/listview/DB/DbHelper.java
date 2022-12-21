package com.example.listview.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import com.example.listview.Entities.Product;

public class DbHelper extends SQLiteOpenHelper {
    private SQLiteDatabase sqLiteDatabase;

    public DbHelper(Context context){
        super(context, "IceCream.db", null, 1);
        sqLiteDatabase = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL( "CREATE TABLE PRODUCTS("+
                "id TEXT PRIMARY KEY,"+
                "name VARCHAR,"+
                "description TEXT,"+
                "price VARCHAR,"+
                "image TEXT"+
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS PRODUCTS");
    }

    //METODOS CRUD
    public void insertProduct(Product product){
        String sql = "INSERT INTO PRODUCTS VALUES(?, ?, ?, ?, ?)";
        SQLiteStatement statement = sqLiteDatabase.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, product.getId());
        statement.bindString(2, product.getName());
        statement.bindString(3, product.getDescription());
        statement.bindString(4, String.valueOf(product.getPrice()));
        statement.bindString(5, product.getImage());

        statement.executeInsert();
    }

    public Cursor getProducts(){
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM PRODUCTS", null);
        return  cursor;
    }

    public Cursor getProductById(String id){
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM PRODUCTS WHERE id = "+id, null);
        return  cursor;
    }

    public void deleteProductById(String id){
        sqLiteDatabase.execSQL("DELETE FROM PRODUCTS WHERE id = "+id);
    }

    public void updateProduct(String id, String name, String description, String price, byte[] image){
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("description", description);
        contentValues.put("price", price);
        contentValues.put("image", image);

        sqLiteDatabase.update("PRODUCTS", contentValues, "id = ?", new String[]{id});
    }
}