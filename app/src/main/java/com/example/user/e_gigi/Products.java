package com.example.user.e_gigi;

/**
 * Created by User on 15/02/2017.
 */

public class Products {

    //Producto
    private String idProduct;
    private String titulo;
    private String descripcion;
    private String numPedidos;
    private String fecha;
    private String categoria;
    private String precio;
    private String stock;
 //   private String imagen;

    /**Comentarios**/
    private String comentarios;
    private String autorComents;
    private String fechaComents;

    public Products(String idProduct, String titulo, String descripcion, String numPedidos, String fecha, String categoria, String precio, String stock, String comentarios, String autorComents, String fechaComents) {
        this.idProduct = idProduct;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.numPedidos = numPedidos;
        this.fecha = fecha;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
        this.comentarios = comentarios;
        this.autorComents = autorComents;
        this.fechaComents = fechaComents;
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

    public String getNumPedidos() {
        return numPedidos;
    }

    public void setNumPedidos(String numPedidos) {
        this.numPedidos = numPedidos;
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

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public String getAutorComents() {
        return autorComents;
    }

    public void setAutorComents(String autorComents) {
        this.autorComents = autorComents;
    }

    public String getFechaComents() {
        return fechaComents;
    }

    public void setFechaComents(String fechaComents) {
        this.fechaComents = fechaComents;
    }

    public boolean comparate(Products products){
      return this.descripcion.compareTo(descripcion)==0 &&
        this.idProduct.compareTo(idProduct)==0 && this.titulo.compareTo(titulo)==0;
    }
}
