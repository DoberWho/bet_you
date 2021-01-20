package com.kaimanden.betyou.tools.models;

import com.google.firebase.firestore.DocumentSnapshot;
import com.kaimanden.betyou.tools.listeners.DbObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Mensaje implements Serializable, DbObject {
    private String id;
    private String author_id;
    private String author_label;
    private String author_img;
    private String mensaje;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(String author_id) {
        this.author_id = author_id;
    }

    public String getAuthor_label() {
        return author_label;
    }

    public void setAuthor_label(String author_label) {
        this.author_label = author_label;
    }

    public String getAuthor_img() {
        return author_img;
    }

    public void setAuthor_img(String author_img) {
        this.author_img = author_img;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    @Override
    public Map toMap() {
        HashMap map = new HashMap();

        map.put("author_id", this.author_id);
        map.put("author_label", this.author_label);
        map.put("author_img", this.author_img);
        map.put("mensaje", this.mensaje);

        return map;
    }

    @Override
    public Object toObject(DocumentSnapshot doc) {
        return null;
    }
}
