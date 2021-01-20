package com.kaimanden.betyou.main;

import android.content.Intent;
import android.os.Bundle;

import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.kaimanden.betyou.base.BaseFrg;
import com.kaimanden.betyou.R;
import com.kaimanden.betyou.tools.adapters.BetitemAdapter;
import com.kaimanden.betyou.tools.controllers.DbController;
import com.kaimanden.betyou.tools.listeners.DbBetitemListener;
import com.kaimanden.betyou.tools.models.BetItem;

import java.util.List;

public class HomeFragment extends BaseFrg {

    private RecyclerView container;
    private TabLayout tabLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frg_home, container, false);
        initViews(v);
        initListeners();
        updateData(true);
        return v;
    }

    private void initViews(View v) {
        container = v.findViewById(R.id.frg_home_rec);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        container.setLayoutManager(manager);

        tabLayout = v.findViewById(R.id.frg_home_tab);
    }

    private void initListeners() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
                if (pos == 0){
                    updateData(true);
                }else{
                    updateData(false);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void updateData(boolean owner) {
        showLoading(true);
        DbBetitemListener listener = new DbBetitemListener() {
            @Override
            public void isOk(List<BetItem> items) {
                showLoading(false);
                updateAdapter(items);
            }

            @Override
            public void isKo(String error) {
                showLoading(false);
                showError(error);
            }
        };
        DbController.init(getActivity()).getUserBetItems(owner, listener);
    }

    private void updateAdapter(List<BetItem> items) {
        if (items == null || items.isEmpty()){
            return;
        }

        BetitemAdapter adapter = new BetitemAdapter(getActivity(), items);
        container.setAdapter(adapter);
    }

}