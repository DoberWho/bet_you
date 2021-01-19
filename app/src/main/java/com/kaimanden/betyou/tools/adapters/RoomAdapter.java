package com.kaimanden.betyou.tools.adapters;

import android.app.Activity;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kaimanden.betyou.R;
import com.kaimanden.betyou.tools.interfaces.ContactSelected;
import com.kaimanden.betyou.tools.models.Room;
import com.tomash.androidcontacts.contactgetter.entity.ContactData;
import com.tomash.androidcontacts.contactgetter.entity.Email;

import java.util.ArrayList;
import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ContactoHolder> {

    private static final int layout = R.layout.adapter_room_item;
    private final Activity act;
    private List<Room> items;

    public RoomAdapter(Activity act, List<Room> items) {
        this.act = act;
        this.items = items;
    }


    @NonNull
    @Override
    public ContactoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);

        return new ContactoHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactoHolder holder, int position) {
        final Room item = items.get(position);

        holder.txtName.setText(item.getLast_msg_author_label());
        holder.txtMsg.setText(item.getLast_msg_txt());
        holder.txtDate.setText(item.getLast_msg_date());

        holder.linRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ContactoHolder extends RecyclerView.ViewHolder {
        public TextView txtName, txtMsg, txtDate;
        public LinearLayout linRoot;

        public ContactoHolder(View v) {
            super(v);
            txtName = v.findViewById(R.id.adapter_room_user);
            txtMsg = v.findViewById(R.id.adapter_room_msg);
            txtDate = v.findViewById(R.id.adapter_room_date);
            linRoot = v.findViewById(R.id.adapter_room_root);
        }
    }



}