package com.kaimanden.betyou.user;

import android.os.Bundle;
import android.widget.TextView;

import com.kaimanden.betyou.BaseAct;
import com.kaimanden.betyou.R;

public class UserActivity extends BaseAct {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_user);
    }
}