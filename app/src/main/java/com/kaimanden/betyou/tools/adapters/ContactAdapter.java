package com.kaimanden.betyou.tools.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kaimanden.betyou.R;
import com.kaimanden.betyou.tools.models.Contact;
import com.tomash.androidcontacts.contactgetter.entity.ContactData;
import com.tomash.androidcontacts.contactgetter.entity.Email;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter  extends RecyclerView.Adapter<ContactAdapter.ContactoHolder> {

    private static final int layout = R.layout.adapter_contact_item;
    private final Activity act;
    private List<ContactData> items;
    private List<ContactData> selected = new ArrayList<>();

    public ContactAdapter(Activity act, List<ContactData> items) {
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
        final ContactData item = items.get(position);

        //String name = item.getNickName();
        holder.txtName.setText("NAME");

        //Email email = item.getEmailList().get(0);
        holder.txtEmail.setText("EMAIL");


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
        public LinearLayout linRoot;

        public ContactoHolder(View v) {
            super(v);
            txtName = v.findViewById(R.id.adapter_contact_item_name);
            txtEmail = v.findViewById(R.id.adapter_contact_item_email);
            linRoot = v.findViewById(R.id.adapter_contact_item_root);
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
    }

}