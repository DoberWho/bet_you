package com.kaimanden.betyou.tools.models;

import com.google.firebase.firestore.DocumentSnapshot;
import com.kaimanden.betyou.tools.listeners.DbObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Room implements DbObject {
    private String id;
    private String last_msg_date;
    private String last_msg_txt;
    private String last_msg_author_id;
    private String last_msg_author_label;
    private List<String> users;
    private List<Mensaje> mensajes;

    @Override
    public Map toMap() {
        HashMap map = new HashMap();

        map.put("last_msg_date", this.last_msg_date);
        map.put("last_msg_txt", this.last_msg_txt);
        map.put("last_msg_author_id", this.last_msg_author_id);
        map.put("last_msg_author_label", this.last_msg_author_label);
        map.put("users", this.users);
        map.put("mensajes", this.mensajes);

        List<Map> msgs = new ArrayList<>();

        for (Mensaje msg : this.mensajes) {
            msgs.add( msg.toMap());
        }

        //map.put("mensajes", msgs);

        return map;
    }

    @Override
    public Object toObject(DocumentSnapshot doc) {
        Room room = new Room();

        room.id = doc.getString("id");
        room.last_msg_date = doc.getString("last_msg_date");
        room.last_msg_txt = doc.getString("last_msg_txt");
        room.last_msg_author_id = doc.getString("last_msg_author_id");
        room.last_msg_author_label = doc.getString("last_msg_author_label");
        room.users = (List<String>) doc.get("users");
        room.mensajes = (List<Mensaje>) doc.get("mensajes");

        return room;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLast_msg_date() {
        return last_msg_date;
    }

    public void setLast_msg_date(String last_msg_date) {
        this.last_msg_date = last_msg_date;
    }

    public String getLast_msg_txt() {
        return last_msg_txt;
    }

    public void setLast_msg_txt(String last_msg_txt) {
        this.last_msg_txt = last_msg_txt;
    }

    public String getLast_msg_author_id() {
        return last_msg_author_id;
    }

    public void setLast_msg_author_id(String last_msg_author_id) {
        this.last_msg_author_id = last_msg_author_id;
    }

    public String getLast_msg_author_label() {
        return last_msg_author_label;
    }

    public void setLast_msg_author_label(String last_msg_author_label) {
        this.last_msg_author_label = last_msg_author_label;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

    public List<Mensaje> getMensajes() {
        return mensajes;
    }

    public void setMensajes(List<Mensaje> mensajes) {
        this.mensajes = mensajes;
    }
}
