package com.example.user.e_gigi.modelo;

/**
 * Created by User on 24/02/2017.
 */

public class Comments {
    /**Comentarios**/
    private String idComment;
    private String comentarios;
    private String autorComents;
    private String fechaComents;
    private String autorEmailComments;

    public Comments(String idComment, String comentarios, String autorComents, String fechaComents, String autorEmailComments) {
        this.idComment = idComment;
        this.comentarios = comentarios;
        this.autorComents = autorComents;
        this.fechaComents = fechaComents;
        this.autorEmailComments = autorEmailComments;
    }

    public String getIdComment() {
        return idComment;
    }

    public void setIdComment(String idComment) {
        this.idComment = idComment;
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

    public String getAutorEmailComments() {
        return autorEmailComments;
    }

    public void setAutorEmailComments(String autorEmailComments) {
        this.autorEmailComments = autorEmailComments;
    }

    public boolean comparate(Comments comments){
        return this.comentarios.compareTo(comentarios)==0 &&
                this.idComment.compareTo(idComment)==0;
    }
}
