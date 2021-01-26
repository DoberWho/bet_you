package com.kaimanden.betyou.main.betcreate;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kaimanden.betyou.R;
import com.kaimanden.betyou.base.BaseAct;
import com.kaimanden.betyou.tools.controllers.DbController;
import com.kaimanden.betyou.tools.adapters.ContactAdapter;
import com.kaimanden.betyou.tools.listeners.ContactSelected;
import com.kaimanden.betyou.tools.listeners.DbSaveListener;
import com.kaimanden.betyou.tools.models.BetItem;
import com.tomash.androidcontacts.contactgetter.entity.ContactData;
import com.tomash.androidcontacts.contactgetter.entity.Email;
import com.tomash.androidcontacts.contactgetter.main.contactsGetter.ContactsGetterBuilder;

import java.util.ArrayList;
import java.util.List;

public class BetCreateActivity extends BaseAct {

    private List<ContactData> filteredContacts = new ArrayList<>();
    private List<ContactData> contactList = new ArrayList<>();
    private List<ContactData> selected = new ArrayList<>();
    private static final int MY_PERMISSIONS_REQUEST_CODE = 12345;

    public static final String BETITEM = "bet item to create";

    private ImageButton btnMinus, btnPlus;
    private Button btnOk;
    private EditText edtMoney, edtFilter;
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
        edtFilter = findViewById(R.id.act_bet_create_filter_edt);
        content  = findViewById(R.id.act_bet_content);
        btnOk    = findViewById(R.id.act_bet_create_btn);

        edtMoney.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                boolean action = false;
                if (actionId == EditorInfo.IME_ACTION_NONE) {
                    hideKeyb();
                    action = true;
                }
                return action;
            }
        });

        edtFilter.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                boolean action = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    hideKeyb();
                    action = true;
                }
                return action;
            }
        });

        edtFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String term = s.toString();
                filterContacts(term);
                updateContactsData();
            }
        });
    }

    private void filterContacts(String term) {
        if (term == null || term.trim().isEmpty()){
            this.filteredContacts = this.contactList;
            return;
        }

        List<ContactData> filtered = new ArrayList<>();

        for(ContactData contact : this.contactList){
            String name = contact.getNameData().getFullName();
            if (name.contains(term)){
                filtered.add(contact);
                continue;
            }
            List<Email> emails = contact.getEmailList();
            if (emails.isEmpty()) continue;
            Email email = emails.get(0);
            if (email == null) continue;
            String strEmail = email.getMainData();
            if(strEmail.contains(term)){
                filtered.add(contact);
                continue;
            }
        }

        this.filteredContacts = filtered;
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode != MY_PERMISSIONS_REQUEST_CODE){
            return;
        }

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // permission was granted, yay!
            getContactList();
            return;
        }
        showError(content, getString(R.string.error_permision_not_granted));
    }

    private void getContactList() {
        contactList = new ContactsGetterBuilder(this)
                .allFields()
                .buildList();

        filterContacts(null);
        updateContactsData();
    }

    private void updateContactsData() {
        ContactSelected listener = new ContactSelected() {
            @Override
            public void selected(List<ContactData> contactSelected) {
                if (contactSelected == null || contactSelected.isEmpty()){
                    showError(content, getString(R.string.error_empty_list_required));
                    return;
                }
                selected = contactSelected;
            }
        };

        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        content.setLayoutManager(manager);
        ContactAdapter adapter = new ContactAdapter(this, filteredContacts, listener);
        content.setAdapter(adapter);

    }

    private void checkCreateBet() {
        if (this.selected == null || this.selected.isEmpty()){
            showError(content, getString(R.string.error_empty_list_required));
            return;
        }

        this.betItem.setSelected(this.selected);

        String price = this.edtMoney.getText().toString();
        if (price == null || price.trim().isEmpty()){
            price = getString(R.string.default_bet_price);
        }
        this.betItem.setPrice(price);
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