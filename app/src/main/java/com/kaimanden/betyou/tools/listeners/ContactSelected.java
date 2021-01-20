package com.kaimanden.betyou.tools.listeners;

import com.tomash.androidcontacts.contactgetter.entity.ContactData;

import java.util.List;

public interface ContactSelected {
    void selected(List<ContactData> contactSelected);
}
