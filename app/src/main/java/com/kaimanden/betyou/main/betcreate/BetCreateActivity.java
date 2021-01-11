package com.kaimanden.betyou.main.betcreate;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.kaimanden.betyou.tools.DbController;
import com.kaimanden.betyou.tools.adapters.ContactAdapter;
import com.kaimanden.betyou.tools.interfaces.ContactSelected;
import com.kaimanden.betyou.tools.interfaces.DbSaveListener;
import com.kaimanden.betyou.tools.models.BetItem;
import com.kaimanden.betyou.tools.models.Contact;
import com.tomash.androidcontacts.contactgetter.entity.ContactData;
import com.tomash.androidcontacts.contactgetter.main.contactsGetter.ContactsGetterBuilder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Stream;

public class BetCreateActivity extends BaseAct {

    private List<ContactData> contactList = new ArrayList<>();
    private List<ContactData> selected = new ArrayList<>();
    private static final int MY_PERMISSIONS_REQUEST_CODE = 12345;

    public static final String BETITEM = "bet item to create";

    private ImageButton btnMinus, btnPlus;
    private Button btnOk;
    private EditText edtMoney;
    private RecyclerView content;
    private BetItem betItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_bet_create);
        getBetitem();
        initViews();
        initButtons();
        checkPermission();

    }

    private void getBetitem() {
        Bundle extras = getIntent().getExtras();
        if (extras == null) return;
        BetItem item = (BetItem) extras.getSerializable(BETITEM);
        if (item == null) return;
        this.betItem = item;
    }

    private void initViews() {
        btnMinus = findViewById(R.id.act_bet_create_minus);
        btnPlus  = findViewById(R.id.act_bet_create_plus);
        edtMoney = findViewById(R.id.act_bet_create_money);
        content  = findViewById(R.id.act_bet_content);
        btnOk    = findViewById(R.id.act_bet_create_btn);
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

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCreateBet();
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

    private void checkPermission() {

        int granted = PackageManager.PERMISSION_GRANTED;
        String read = Manifest.permission.READ_CONTACTS;
        String write = Manifest.permission.WRITE_CONTACTS;

        boolean checkR = ( ContextCompat.checkSelfPermission(this, read) == granted );
        boolean checkW = ( ContextCompat.checkSelfPermission(this, write) == granted );

        if (!checkR){
            boolean checkDR = ActivityCompat.shouldShowRequestPermissionRationale(this, read);
            if (!checkDR){
                ActivityCompat.requestPermissions(this, new String[]{read}, MY_PERMISSIONS_REQUEST_CODE);
                return;
            }
        }

        // Permission has already been granted
        getContactList();

    }

    private void getContactList() {
        contactList = new ContactsGetterBuilder(this)
                .allFields()
                .buildList();
        updateContactsData();
    }

    private void updateContactsData() {
        ContactSelected listener = new ContactSelected() {
            @Override
            public void selected(List<ContactData> contactSelected) {
                if (contactSelected == null || contactSelected.isEmpty()){
                    return;
                }
                selected = contactSelected;
            }
        };

        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        content.setLayoutManager(manager);
        ContactAdapter adapter = new ContactAdapter(this, contactList, listener);
        content.setAdapter(adapter);

    }

    private void checkCreateBet() {
        if (this.selected == null || this.selected.isEmpty()){
            showError(content, getString(R.string.error_empty_list_required));
            return;
        }

        this.betItem.setSelected(this.selected);
        this.betItem.setPrice(this.edtMoney.getText().toString());
        DbController.init(this).saveBetItem(this.betItem, new DbSaveListener() {
            @Override
            public void saveOk() {
                showInfo(content, getString(R.string.save_betitem_ok));
            }

            @Override
            public void saveKO(String reason) {
                showError(content, reason);
            }
        });
    }

}