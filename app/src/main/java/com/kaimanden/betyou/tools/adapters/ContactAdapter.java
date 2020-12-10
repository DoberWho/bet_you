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

import java.util.List;

public class ContactAdapter  extends RecyclerView.Adapter<ContactAdapter.ContactoHolder> {

    private static final int layout = R.layout.adapter_contact_item;
    private final Activity act;
    private List<Contact> items;

    public ContactAdapter(Activity act, List<Contact> items) {
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
        final Contact item = items.get(position);

        String name = item.getName();
        holder.txtName.setText(name);

        String email = item.getName();
        holder.txtEmail.setText(email);

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
}