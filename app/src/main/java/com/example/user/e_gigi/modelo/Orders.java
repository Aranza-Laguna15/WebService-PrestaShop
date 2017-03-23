package com.example.user.e_gigi.modelo;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

/**
 * Created by Aranza on 24/02/2017.
 */

public class Orders {
    /**
     * Pedidos
     *
     */
    private String idOrder;
    private String reference;
    private String total;
    private String payment;
    private String date;
    private String customer;
    private String state;
    private String country;

    public Orders(String idOrder, String reference, String total, String payment, String date, String customer, String state, String country) {
        this.idOrder = idOrder;
        this.reference = reference;
        this.total = total;
        this.payment = payment;
        this.date = date;
        this.customer = customer;
        this.state = state;
        this.country = country;
    }

    public String getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Orders(Cursor cursor) {
        idOrder = cursor.getString(cursor.getColumnIndex(Orders.OrdersEntry.ID));
        reference = cursor.getString(cursor.getColumnIndex(OrdersEntry.reference));
        total = cursor.getString(cursor.getColumnIndex(OrdersEntry.total));
        date = cursor.getString(cursor.getColumnIndex(OrdersEntry.date));
        payment = cursor.getString(cursor.getColumnIndex(OrdersEntry.payment));
        customer = cursor.getString(cursor.getColumnIndex(OrdersEntry.customer));
        state = cursor.getString(cursor.getColumnIndex(OrdersEntry.state));
        country = cursor.getString(cursor.getColumnIndex(OrdersEntry.country));
    }

    //Metodo para insertar datos a la DB
    public ContentValues toContentValues(){
        ContentValues values = new ContentValues();
        values.put(OrdersEntry.ID,idOrder);
        values.put(OrdersEntry.reference,reference);
        values.put(OrdersEntry.total,total);
        values.put(OrdersEntry.payment,payment);
        values.put(OrdersEntry.date,date);
        values.put(OrdersEntry.customer,customer);
        values.put(OrdersEntry.state,state);
        values.put(OrdersEntry.country,country);
        return values;
    }

    public static abstract class OrdersEntry implements BaseColumns {
        public static final String TABLE_ORDERS="pedidos";

        public static final String ID="id_order";
        public static final String reference="reference";
        public static final String total="total";
        public static final String date="date";
        public static final String payment="payment";
        public static final String customer="customer";
        public static final String state="state";
        public static final String country="country";
    }
}
