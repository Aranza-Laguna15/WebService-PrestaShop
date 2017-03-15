package com.example.user.e_gigi.web;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.user.e_gigi.modelo.Products;

/**
 * Created by Aranza on 10/03/2017.
 * Clase de conexion a SQLite
 */

public class SQLiteDB extends SQLiteOpenHelper{
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Productos.db";

    public SQLiteDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+ Products.ProductsEntry.TABLE_NAME + " ("
        + Products.ProductsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
        + Products.ProductsEntry.ID + " TEXT NOT NULL,"
        + Products.ProductsEntry.titulo + " TEXT NOT NULL,"
        + Products.ProductsEntry.descripcion + " TEXT NOT NULL,"
        + Products.ProductsEntry.fecha + " TEXT NOT NULL,"
        + Products.ProductsEntry.categoria + " TEXT NOT NULL,"
        + Products.ProductsEntry.precio + " TEXT NOT NULL,"
        + Products.ProductsEntry.stock + " TEXT NOT NULL,"
                + " UNIQUE (" + Products.ProductsEntry.ID + "))");

    }

    public long saveProducts(Products products){
        SQLiteDatabase db = getWritableDatabase();
        return db.insert(
                Products.ProductsEntry.TABLE_NAME,
                null,
                products.toContentValues()
        );
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try{
            db.execSQL("DROP TABLE IF EXISTS "+Products.ProductsEntry.TABLE_NAME);
        }catch (SQLiteException e){
            Log.e("ERROR DROP TABLE: ", e.getLocalizedMessage());
        }
        onCreate(db);
    }

    public Cursor getAllProducts(){
        return getReadableDatabase()
                .query(
                        Products.ProductsEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
    }

    public Cursor getProductById(String id) {
        Cursor c = getReadableDatabase().query(
                Products.ProductsEntry.TABLE_NAME,
                null,
                Products.ProductsEntry.ID + " LIKE ?",
                new String[]{id},
                null,
                null,
                null);
        return c;
    }
}
