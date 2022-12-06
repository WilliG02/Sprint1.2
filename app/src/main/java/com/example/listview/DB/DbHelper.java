package com.example.listview.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class DbHelper extends SQLiteOpenHelper {

    private SQLiteDatabase sqLiteDatabase;

    public DbHelper (Context context){
        super(context, "DBIceCream", null, 1);
        sqLiteDatabase = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE PRODUCTS("+
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                        "name VARCHAR,"+
                        "description VARCHAR,"+
                        "price VARCHAR,"+
                        "image BLOB"+    // MAPA DE BITS
                        ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS PRODUCTS");
    }

    // METODOS CRUDS

    // INSERT
    public void insertProduct(String name, String description, String price, byte[] image){
        String sql = "INSERT INTO PRODUCTS VALUES(null, ?, ?, ?, ?)";
        SQLiteStatement statement = sqLiteDatabase.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, name);
        statement.bindString(2, description);
        statement.bindString(3, price);
        statement.bindBlob(4, image);

        statement.executeInsert();
    }

    // GET

    public Cursor getProducts(){
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM PRODUCTS" , null) ;
        return cursor;
    }

    // Consult by Id
    public Cursor getProductsById(String id){
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM PRODUCTS WHERE id = "+id , null) ;
        return cursor;
    }

    public void deleteProductById(String id){
        sqLiteDatabase.execSQL("DELETE FROM PRODUCTS WHERE id = " + id);
    }

    // UPDATE
    public void updateProduct(String id, String name, String description, String price, byte[] image){
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("description", description);
        contentValues.put("price", price);
        contentValues.put("image", image);

        sqLiteDatabase.update("PRODUCTS", contentValues, "id = ?", new String[]{id});
    }

}
