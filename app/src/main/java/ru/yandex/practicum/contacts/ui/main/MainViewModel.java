package ru.yandex.practicum.contacts.ui.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import ru.yandex.practicum.contacts.model.Contact;
import ru.yandex.practicum.contacts.model.ContactSource;
import ru.yandex.practicum.contacts.model.ContactType;
import ru.yandex.practicum.contacts.model.MergedContact;
import ru.yandex.practicum.contacts.repository.ContactMerger;
import ru.yandex.practicum.contacts.repository.ContactRepository;
import ru.yandex.practicum.contacts.repository.ContactSourceRepository;
import ru.yandex.practicum.contacts.ui.ContactUiMapper;
import ru.yandex.practicum.contacts.ui.model.ContactUi;
import ru.yandex.practicum.contacts.utils.MergedContactUtils;

public class MainViewModel extends AndroidViewModel {

    private final ContactSourceRepository contactSourceRepository;
    private final ContactRepository contactRepository;
    private final ContactMerger contactMerger;
    private final ContactUiMapper uiMapper;

    private final MutableLiveData<List<ContactUi>> contactsLiveDate = new MutableLiveData<>();

    private List<MergedContact> mergedContacts = Collections.emptyList();

    public MainViewModel(@NonNull Application application) {
        super(application);
        contactSourceRepository = new ContactSourceRepository(application);
        contactRepository = new ContactRepository(application);
        contactMerger = new ContactMerger();
        uiMapper = new ContactUiMapper();
    }

    public LiveData<List<ContactUi>> getContactsLiveDate() {
        return contactsLiveDate;
    }

    public void init() {
        final Set<ContactSource> sources = contactSourceRepository.getAllContactSources();
        final List<String> sourceNames = sources.stream()
                .map(ContactSource::getName)
                .collect(Collectors.toList());
        final List<Contact> contacts = contactRepository.getContacts(sourceNames);

        mergedContacts = contactMerger.getMergedContacts(contacts, sources);
        mapContactsAndUpdate(mergedContacts);

    }

    public void search(String query) {
        final List<MergedContact> filteredContacts = mergedContacts.stream()
                .filter(contact -> MergedContactUtils.contains(contact, query))
                .collect(Collectors.toList());
        mapContactsAndUpdate(filteredContacts);
    }

    public void filter(Set<ContactType> types) {
        final List<MergedContact> filteredContacts = mergedContacts.stream()
                .filter(contact -> MergedContactUtils.contains(contact, types))
                .collect(Collectors.toList());
        mapContactsAndUpdate(filteredContacts);
    }

    private void mapContactsAndUpdate(List<MergedContact> mergedContacts) {
        final List<ContactUi> uiContacts = mergedContacts.stream()
                .map(uiMapper::map)
                .collect(Collectors.toList());
        contactsLiveDate.postValue(uiContacts);
    }
}