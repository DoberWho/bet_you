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
import com.kaimanden.betyou.tools.models.BetItem;
import com.kaimanden.betyou.tools.models.Room;

import java.util.List;

public class BetitemAdapter extends RecyclerView.Adapter<BetitemAdapter.ContactoHolder> {

    private static final int layout = R.layout.adapter_betitem_item;
    private final Activity act;
    private List<BetItem> items;

    public BetitemAdapter(Activity act, List<BetItem> items) {
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
        final BetItem item = items.get(position);

        holder.txtOwner.setText(item.getOwner());
        holder.txtTitle.setText(item.getTitle());
        holder.txtPrice.setText(""+item.getPrice()+"€");

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
        public TextView txtOwner, txtTitle, txtPrice;
        public LinearLayout linRoot;

        public ContactoHolder(View v) {
            super(v);
            txtOwner = v.findViewById(R.id.adapter_betitem_owner );
            txtTitle = v.findViewById(R.id.adapter_betitem_title);
            txtPrice = v.findViewById(R.id.adapter_betitem_price);
            linRoot = v.findViewById(R.id.adapter_room_root);
        }
    }



}