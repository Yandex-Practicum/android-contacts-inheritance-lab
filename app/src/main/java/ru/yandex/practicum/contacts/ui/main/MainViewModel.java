package ru.yandex.practicum.contacts.ui.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

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
import ru.yandex.practicum.contacts.utils.java.ThreadUtils;
import ru.yandex.practicum.contacts.utils.model.MergedContactUtils;

public class MainViewModel extends AndroidViewModel {

    private final ContactSourceRepository contactSourceRepository;
    private final ContactRepository contactRepository;
    private final ContactMerger contactMerger;
    private final ContactUiMapper uiMapper;

    private final MutableLiveData<List<ContactUi>> contactsLiveDate = new MutableLiveData<>();
    private final MutableLiveData<UiState> uiStateLiveDate = new MutableLiveData<>();

    private State state = new State();

    private UiState uiState = new UiState();

    public MainViewModel(@NonNull Application application) {
        super(application);
        contactSourceRepository = new ContactSourceRepository(application);
        contactRepository = new ContactRepository(application);
        contactMerger = new ContactMerger();
        uiMapper = new ContactUiMapper();
        ThreadUtils.runAsync(this::initLoading);
    }

    public LiveData<List<ContactUi>> getContactsLiveDate() {
        return contactsLiveDate;
    }

    public MutableLiveData<UiState> getUiStateLiveDate() {
        return uiStateLiveDate;
    }

    public void initLoading() {
        final Set<ContactSource> sources = contactSourceRepository.getAllContactSources();
        final List<String> sourceNames = sources.stream()
                .map(ContactSource::getName)
                .collect(Collectors.toList());
        final List<Contact> contacts = contactRepository.getContacts(sourceNames);

        //final List<Contact> list = contacts.stream().sorted(Comparator.comparing(Contact::getId)).collect(Collectors.toList());

        final List<MergedContact> allContacts = contactMerger.getMergedContacts(contacts, sources);
        state.setAllContacts(allContacts);
        mapContactsAndUpdate(state.getAllContacts());
    }

    public void search(String query) {
        final List<MergedContact> lastFilteredContacts = state.getAllContacts().stream()
                .filter(contact -> MergedContactUtils.contains(contact, query))
                .collect(Collectors.toList());
        state.setLastFilteredContacts(lastFilteredContacts);
        mapContactsAndUpdate(state.getLastFilteredContacts());
    }

    public void filter(Set<ContactType> types) {
        final List<MergedContact> lastFilteredContacts = state.getAllContacts().stream()
                .filter(contact -> MergedContactUtils.contains(contact, types))
                .collect(Collectors.toList());
        state.setLastFilteredContacts(lastFilteredContacts);
        mapContactsAndUpdate(state.getLastFilteredContacts());
    }

    public void sort(SortType type) {
        final List<MergedContact> sortedContacts = state.getLastFilteredContacts().stream()
                .sorted(createComparator(type))
                .collect(Collectors.toList());
        mapContactsAndUpdate(sortedContacts);
    }

    public void onMenuClick(MenuClick click) {
        switch (click) {
            case SORT:
                break;
            case FILTER:
                break;
            case SEARCH:
                uiState.searchVisibility = !uiState.searchVisibility;
                updateUiState();
                break;
        }
    }

    public void onBackPressed() {
        if (uiState.searchVisibility) {
            uiState.searchVisibility = false;
        } else {
            uiState.finishing = true;
        }
        updateUiState();
    }

    public void clear() {
        state.clear();
        mapContactsAndUpdate(state.getLastFilteredContacts());
    }

    public void updateSearchText(String query) {
        uiState.resetSearchButtonVisibility = query.length() != 0;
        updateUiState();
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

    private void updateUiState() {
        uiStateLiveDate.setValue(uiState);
    }

    enum SortType {
        BY_NAME,
        BY_NAME_REVERSED,
        BY_SURNAME,
        BY_SURNAME_REVERSED
    }
}