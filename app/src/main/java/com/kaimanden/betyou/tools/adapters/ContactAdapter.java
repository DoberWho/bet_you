package com.kaimanden.betyou.tools.adapters;

import android.app.Activity;
import android.graphics.Color;
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
import com.kaimanden.betyou.tools.models.Contact;
import com.tomash.androidcontacts.contactgetter.entity.ContactData;
import com.tomash.androidcontacts.contactgetter.entity.Email;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter  extends RecyclerView.Adapter<ContactAdapter.ContactoHolder> {

    private static final int layout = R.layout.adapter_contact_item;
    private final Activity act;
    private final ContactSelected listener;
    private List<ContactData> items;
    private List<ContactData> selected = new ArrayList<>();

    public ContactAdapter(Activity act, List<ContactData> items, ContactSelected listener) {
        this.act = act;
        this.items = items;
        this.cleanContacts();
        this.listener = listener;
    }

    private void cleanContacts() {
        if (this.items == null || this.items.isEmpty()){
            return;
        }
        List<ContactData> items = new ArrayList<>();
        for (ContactData contact : this.items) {
            if (contact.getNameData().getFullName().trim().isEmpty()) continue;
            if (contact.getEmailList().isEmpty()) continue;
            Email email = contact.getEmailList().get(0);
            if (email.getMainData().trim().isEmpty()) continue;
            items.add(contact);
        }
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
        final ContactData item = items.get(position);

        Uri photo = item.getPhotoUri();
        Glide.with(act).load(photo).into(holder.image);

        String name = item.getNameData().getFullName();
        holder.txtName.setText(name);

        Email email = item.getEmailList().get(0);
        holder.txtEmail.setText(email.getMainData());

        holder.linRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleContact(holder, item);
            }
        });

        checkSelected(holder, item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ContactoHolder extends RecyclerView.ViewHolder {
        public TextView txtName, txtEmail;
        public ImageView image;
        public LinearLayout linRoot;

        public ContactoHolder(View v) {
            super(v);
            txtName = v.findViewById(R.id.adapter_contact_item_name);
            txtEmail = v.findViewById(R.id.adapter_contact_item_email);
            linRoot = v.findViewById(R.id.adapter_contact_item_root);
            image = v.findViewById(R.id.adapter_contact_item_image);
        }
    }


    private void toggleContact(ContactoHolder holder, ContactData item) {

        if (this.selected.contains(item)){
            this.selected.remove(item);
        }else{
            this.selected.add(item);
        }
        checkSelected(holder, item);
    }

    private void checkSelected(ContactoHolder holder, ContactData item) {

        boolean checked = false;
        if (this.selected.contains(item)){
            checked = true;
        }

        holder.linRoot.setSelected(checked);
        holder.txtName.setSelected(checked);
        holder.txtEmail.setSelected(checked);

        if (this.listener != null){
            this.listener.selected(this.selected);
        }
    }

}