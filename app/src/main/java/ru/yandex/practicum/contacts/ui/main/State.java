package ru.yandex.practicum.contacts.ui.main;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ru.yandex.practicum.contacts.model.ContactType;
import ru.yandex.practicum.contacts.model.MergedContact;

public class State {

    private List<MergedContact> allContacts = Collections.emptyList();
    private SortType sortType = SortType.BY_NAME;
    private Set<ContactType> contactTypes = new HashSet<>(Arrays.asList(ContactType.values()));
    private String query = "";

    public List<MergedContact> getAllContacts() {
        return allContacts;
    }

    public void setAllContacts(List<MergedContact> allContacts) {
        this.allContacts = allContacts;
    }

    public SortType getSortType() {
        return sortType;
    }

    public void setSortType(SortType sortType) {
        this.sortType = sortType;
    }

    public Set<ContactType> getContactTypes() {
        return contactTypes;
    }

    public void setContactTypes(Set<ContactType> contactTypes) {
        this.contactTypes = contactTypes;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
