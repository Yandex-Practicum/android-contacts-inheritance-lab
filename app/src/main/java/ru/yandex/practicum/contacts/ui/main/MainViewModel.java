package ru.yandex.practicum.contacts.ui.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.Collections;
import java.util.Comparator;
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
import ru.yandex.practicum.contacts.utils.model.MergedContactUtils;

public class MainViewModel extends AndroidViewModel {

    private final ContactSourceRepository contactSourceRepository;
    private final ContactRepository contactRepository;
    private final ContactMerger contactMerger;
    private final ContactUiMapper uiMapper;

    private final MutableLiveData<List<ContactUi>> contactsLiveDate = new MutableLiveData<>();

    private List<MergedContact> allContacts = Collections.emptyList();
    private List<MergedContact> lastFilteredContacts = Collections.emptyList();

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

        allContacts = contactMerger.getMergedContacts(contacts, sources);
        mapContactsAndUpdate(allContacts);

    }

    public void search(String query) {
        lastFilteredContacts = allContacts.stream()
                .filter(contact -> MergedContactUtils.contains(contact, query))
                .collect(Collectors.toList());
        mapContactsAndUpdate(lastFilteredContacts);
    }

    public void filter(Set<ContactType> types) {
        lastFilteredContacts = allContacts.stream()
                .filter(contact -> MergedContactUtils.contains(contact, types))
                .collect(Collectors.toList());
        mapContactsAndUpdate(lastFilteredContacts);
    }

    public void sort(SortType type) {
        final List<MergedContact> sortedContacts = lastFilteredContacts.stream()
                .sorted(createComparator(type))
                .collect(Collectors.toList());
        mapContactsAndUpdate(sortedContacts);
    }

    public void clear() {
        lastFilteredContacts = allContacts;
        mapContactsAndUpdate(lastFilteredContacts);
    }

    private void mapContactsAndUpdate(List<MergedContact> mergedContacts) {
        final List<ContactUi> uiContacts = mergedContacts.stream()
                .map(uiMapper::map)
                .collect(Collectors.toList());
        contactsLiveDate.postValue(uiContacts);
    }

    private Comparator<MergedContact> createComparator(SortType type) {
        switch (type) {
            case BY_NAME:
                return Comparator.comparing(MergedContact::getFirstName);
            case BY_NAME_REVERSED:
                return Comparator.comparing(MergedContact::getFirstName).reversed();
            case BY_SURNAME:
                return Comparator.comparing(MergedContact::getSurname);
            case BY_SURNAME_REVERSED:
                return Comparator.comparing(MergedContact::getSurname).reversed();
            default:
                throw new IllegalArgumentException("Not supported SortType");
        }
    }

    enum SortType {
        BY_NAME,
        BY_NAME_REVERSED,
        BY_SURNAME,
        BY_SURNAME_REVERSED
    }
}