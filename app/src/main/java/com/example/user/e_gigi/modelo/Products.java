package com.example.user.e_gigi.modelo;

/**
 * Created by User on 15/02/2017.
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
 //   private String imagen;


    public Products(String idProduct, String titulo, String descripcion, String fecha, String categoria, String precio, String stock) {
        this.idProduct = idProduct;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
        this.fecha =fecha;
    }


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

    public boolean comparate(Products products){
      return this.descripcion.compareTo(descripcion)==0 &&
        this.idProduct.compareTo(idProduct)==0 && this.titulo.compareTo(titulo)==0;
    }
}
