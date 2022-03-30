package org.kremcheev.contactmanager.dao;

import org.kremcheev.contactmanager.domain.Contact;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface ContactDAO {

    public void addContact(Contact contact);

    public List<Contact> listContact();

    public void removeContact(Integer id);

}
