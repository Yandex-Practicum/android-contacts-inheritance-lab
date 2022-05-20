package ru.yandex.practicum.contacts.ui.main;

import java.util.Collections;
import java.util.List;

import ru.yandex.practicum.contacts.model.MergedContact;

public class State {

    private List<MergedContact> allContacts = Collections.emptyList();
    private List<MergedContact> lastFilteredContacts = Collections.emptyList();

    public List<MergedContact> getAllContacts() {
        return allContacts;
    }

    public void setAllContacts(List<MergedContact> allContacts) {
        this.allContacts = allContacts;
    }

    public List<MergedContact> getLastFilteredContacts() {
        return lastFilteredContacts;
    }

    public void setLastFilteredContacts(List<MergedContact> lastFilteredContacts) {
        this.lastFilteredContacts = lastFilteredContacts;
    }

    public void clear() {
        allContacts = Collections.emptyList();
        lastFilteredContacts = Collections.emptyList();
    }
}
