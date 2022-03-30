package org.kremcheev.contactmanager.dao;

import org.hibernate.SessionFactory;
import org.hibernate.impl.SessionFactoryImpl;
import org.kremcheev.contactmanager.domain.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ContactDAOImpl implements ContactDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void addContact(Contact contact) {
        sessionFactory.getCurrentSession().save(contact);
    }

    @Override
    public List<Contact> listContact() {
        return sessionFactory.getCurrentSession().createQuery("from Contact")
                .list();
    }

    @Override
    public void removeContact(Integer id) {
        Contact contact = (Contact) sessionFactory.getCurrentSession().load(Contact.class, id);
        if (null != contact){
            sessionFactory.getCurrentSession().delete(contact);
        }
    }
}
