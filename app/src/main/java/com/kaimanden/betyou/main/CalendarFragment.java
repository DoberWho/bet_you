package com.kaimanden.betyou.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseUser;
import com.kaimanden.betyou.R;
import com.kaimanden.betyou.base.BaseFrg;
import com.kaimanden.betyou.tools.controllers.DbController;
import com.kaimanden.betyou.tools.adapters.RoomAdapter;
import com.kaimanden.betyou.tools.listeners.ChatListener;
import com.kaimanden.betyou.tools.listeners.DbListener;
import com.kaimanden.betyou.tools.models.Mensaje;
import com.kaimanden.betyou.tools.models.Room;
import com.kaimanden.betyou.tools.models.UserProfile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CalendarFragment extends BaseFrg {

    private RecyclerView container;
    private FloatingActionButton btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frg_calendar, container, false);
        initViews(v);
        initButtons();
        updateData();
        return v;
    }

    private void initButtons() {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbController db = DbController.init(getActivity());
                DbListener listener = new DbListener() {
                    @Override
                    public void isOk(UserProfile profile) {
                        generateMensaje(db, profile);
                    }

                    @Override
                    public void isKo(String error) {
                        showError(error);
                    }
                };
                db.getUserProfile(listener);

            }
        });
    }

    private void generateMensaje(DbController db, UserProfile profile) {
        FirebaseUser user = db.getUser();
        Room room = new Room();
        room.setLast_msg_author_id(user.getUid());
        room.setLast_msg_author_label(profile.getName());

        Date date = new Date();
        room.setLast_msg_date(date.toString());

        List<Mensaje> mensajes = new ArrayList<>();
        Mensaje msg = new Mensaje();
        msg.setAuthor_id(user.getUid());
        msg.setAuthor_label(profile.getName());

        mensajes.add(msg);

        String msgTxt = "Hola Que Tal";
        msg.setMensaje(msgTxt);
        room.setMensajes(mensajes);
        room.setLast_msg_txt(msgTxt);

        List<String> users = new ArrayList<>();
        users.add(user.getUid());
        users.add("yUbIOnZ4QBU6YR4RatgQpARxLQv2");
        room.setUsers(users);

        db.saveRoom(room);
    }

    private void updateData() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        container.setLayoutManager(manager);

        ChatListener listener = new ChatListener() {
            @Override
            public void list(List<Room> rooms) {
                RoomAdapter adapter = new RoomAdapter(getActivity(), rooms);
                container.setAdapter(adapter);
            }

            @Override
            public void ko(String error) {
                showError(error);
            }
        };
        DbController.init(getActivity()).getChats(listener);

    }

    private void initViews(View v) {
        container = v.findViewById(R.id.frg_calendar_rec);
        btn = v.findViewById(R.id.frg_calendar_new_room);
    }
}