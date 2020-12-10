package com.kaimanden.betyou.main.betcreate;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kaimanden.betyou.R;
import com.kaimanden.betyou.base.BaseAct;
import com.kaimanden.betyou.tools.adapters.ContactAdapter;
import com.kaimanden.betyou.tools.models.Contact;

import java.util.ArrayList;
import java.util.HashSet;

public class BetCreateActivity extends BaseAct {

    ArrayList<Contact> contactList = new ArrayList<>();
    private static final int MY_PERMISSIONS_REQUEST_CODE = 12345;
    private static final String[] PROJECTION = new String[]{
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Email.DATA
    };

    public static final String BETITEM = "bet item to create";

    private ImageButton btnMinus, btnPlus;
    private EditText edtMoney;
    private RecyclerView content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_bet_create);
        initViews();
        initButtons();
        checkPermission();

    }

    private void initViews() {
        btnMinus = findViewById(R.id.act_bet_create_minus);
        btnPlus  = findViewById(R.id.act_bet_create_plus);
        edtMoney = findViewById(R.id.act_bet_create_money);
        content  = findViewById(R.id.act_bet_content);
    }

    private void initButtons() {
        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeMoney(-1);
            }
        });
        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeMoney(1);
            }
        });
    }

    private void changeMoney(int increase) {
        String strMoney = edtMoney.getText().toString().trim();
        if (strMoney.isEmpty()){
            strMoney = "0";
        }
        Integer value = Integer.valueOf(strMoney);
        if (value == null) {
            value = 0;
        }
        value +=increase;

        Integer minValue = getResources().getInteger(R.integer.min_value);
        Integer maxValue = getResources().getInteger(R.integer.max_value);

        if (value < minValue){
            value = minValue;
        }
        if (value > maxValue){
            value = maxValue;
        }
        edtMoney.setText(""+value);

    }

    private void getContactList() {
        ContentResolver cr = getContentResolver();

        Cursor cursor = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PROJECTION, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        if (cursor == null) {
            String msg = getString(R.string.error_cursor_data_null);
            showError(msg);
            return;
        }

        String msg = "Contactos Encontrados:"+cursor.getCount();
        showInfo(msg);

        HashSet<String> mobileNoSet = new HashSet<String>();
        try {
            final int nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
            final int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            final int emailIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA);

            String name, number, email;
            while (cursor.moveToNext()) {
                name = cursor.getString(nameIndex);
                number = cursor.getString(numberIndex);
                number = number.replace(" ", "");
                email = cursor.getString(emailIndex);

                if (!mobileNoSet.contains(number)) {
                    contactList.add(new Contact(name, number, email));
                    mobileNoSet.add(number);
                }
            }
        } finally {
            cursor.close();
            updateContactsData();
        }
    }

    private void updateContactsData() {

        showInfo("Creando Adapter: "+contactList.size());

        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        content.setLayoutManager(manager);
        ContactAdapter adapter = new ContactAdapter(this, contactList);
        content.setAdapter(adapter);

    }


    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS) || ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_CONTACTS)) {

                String msg = getString(R.string.error_permision_not_granted);
                showError(msg);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS},
                        MY_PERMISSIONS_REQUEST_CODE);
            }
        } else {
            // Permission has already been granted
            getContactList();
        }
    }

}