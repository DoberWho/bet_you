package com.kaimanden.betyou.auth;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.kaimanden.betyou.R;

public class RecoveryActivity extends AppCompatActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_recovery);

        mTextView = (TextView) findViewById(R.id.text);

    }
}