package com.example.user.e_gigi.web;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.user.e_gigi.modelo.Orders;

/**
 * Created by Aranza on 23/03/2017.
 */

public class SQLiteOrders extends SQLiteOpenHelper{
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Pedidos.db";

    public SQLiteOrders(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION );
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Orders.OrdersEntry.TABLE_ORDERS + " ("
                + Orders.OrdersEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Orders.OrdersEntry.ID + " TEXT NOT NULL,"
                + Orders.OrdersEntry.reference + " TEXT NOT NULL,"
                + Orders.OrdersEntry.total + " TEXT NOT NULL,"
                + Orders.OrdersEntry.payment + " TEXT NOT NULL,"
                + Orders.OrdersEntry.date + " TEXT NOT NULL,"
                + Orders.OrdersEntry.customer + " TEXT NOT NULL,"
                + Orders.OrdersEntry.state + " TEXT NOT NULL,"
                + Orders.OrdersEntry.country + " TEXT NOT NULL,"
                + " UNIQUE (" + Orders.OrdersEntry.ID + "))");
    }

    public long saveOrders(Orders orders){
        SQLiteDatabase db = getWritableDatabase();
        return db.insert(
                Orders.OrdersEntry.TABLE_ORDERS,
                null,
                orders.toContentValues()
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try{
        db.execSQL("DROP TABLE IF EXISTS "+Orders.OrdersEntry.TABLE_ORDERS);
        }catch (SQLiteException e){
            Log.e("ERROR DROP TABLE: ", e.getLocalizedMessage());
        }
        onCreate(db);
    }

    public Cursor getAllOrders(){
        return getReadableDatabase()
                .query(
                        Orders.OrdersEntry.TABLE_ORDERS,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
    }
}
