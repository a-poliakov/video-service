package ru.apolyakov.client_app.service;

import org.springframework.stereotype.Component;
import ru.apolyakov.client_app.model.Contact;

import java.util.Collections;
import java.util.List;

@Component
public class ContactService {
    public List<Contact> findAll() {
        return Collections.emptyList();
    }

    public void save(Contact contact) {

    }
}
