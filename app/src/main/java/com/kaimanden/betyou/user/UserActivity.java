package com.kaimanden.betyou.user;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.widget.TextView;

import com.kaimanden.betyou.base.BaseAct;
import com.kaimanden.betyou.R;

public class UserActivity extends BaseAct {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_user);
    }

    public void onReceive(Context context, Intent intent) {
        // TODO: Recibimos el intent de la aplicación externa.
    }
}