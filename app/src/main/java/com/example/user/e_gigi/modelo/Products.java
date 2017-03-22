package com.example.user.e_gigi.modelo;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

/**
 * Created by Aranza on 15/02/2017.
 */

public class Products {

    //Producto
    private String idProduct;
    private String titulo; //name
    private String descripcion;
    private String fecha;
    private String categoria;
    private String precio;
    private String stock;
    private String imagen;


    public Products(String idProduct, String titulo, String descripcion, String fecha, String categoria, String precio, String stock, String imagen) {
        this.idProduct = idProduct;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
        this.fecha =fecha;
        this.imagen=imagen;
    }
//Obtener lista de productos desde el Cursor
    public Products(Cursor cursor){
        idProduct= cursor.getString(cursor.getColumnIndex(ProductsEntry.ID));
        titulo=cursor.getString(cursor.getColumnIndex(ProductsEntry.titulo));
        descripcion=cursor.getString(cursor.getColumnIndex(ProductsEntry.descripcion));
        categoria=cursor.getString(cursor.getColumnIndex(ProductsEntry.categoria));
        precio=cursor.getString(cursor.getColumnIndex(ProductsEntry.precio));
        stock=cursor.getString(cursor.getColumnIndex(ProductsEntry.stock));
        fecha=cursor.getString(cursor.getColumnIndex(ProductsEntry.fecha));
        imagen=cursor.getString(cursor.getColumnIndex(ProductsEntry.imagen));
    }

//Metodo para insertar datos a la DB
    public ContentValues toContentValues(){
        ContentValues values = new ContentValues();
        values.put(ProductsEntry.ID,idProduct);
        values.put(ProductsEntry.titulo,titulo);
        values.put(ProductsEntry.descripcion,descripcion);
        values.put(ProductsEntry.categoria,categoria);
        values.put(ProductsEntry.precio,precio);
        values.put(ProductsEntry.stock,stock);
        values.put(ProductsEntry.fecha,fecha);
        values.put(ProductsEntry.imagen,imagen);
        return values;
    }

//Getters y Setters
    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public boolean comparate(Products products){
      return this.descripcion.compareTo(descripcion)==0 &&
        this.idProduct.compareTo(idProduct)==0 && this.titulo.compareTo(titulo)==0;
    }
//Variables para uso global
    public static abstract class ProductsEntry implements BaseColumns{
        public static final String TABLE_NAME="productos";

        public static final String ID="id_producto";
        public static final String titulo="titulo";
        public static final String descripcion="descripcion";
        public static final String fecha="fecha";
        public static final String categoria="categoria";
        public static final String precio="precio";
        public static final String stock="stock";
        public static final String imagen="imagen";
    }
}
