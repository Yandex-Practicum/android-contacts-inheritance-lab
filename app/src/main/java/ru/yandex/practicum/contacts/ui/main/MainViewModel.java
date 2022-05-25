package ru.yandex.practicum.contacts.ui.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
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

    private final State state = new State();
    private final UiState uiState = new UiState();

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

        final List<MergedContact> allContacts = contactMerger.getMergedContacts(contacts, sources);
        state.setAllContacts(allContacts);
        mapContactsAndUpdate();
    }

    public void search() {
        mapContactsAndUpdate();
    }

    public void onMenuClick(MenuClick click) {
        switch (click) {
            case SORT:
                uiState.actions.showSortTypeDialog.data = state.getSortType();
                break;
            case FILTER:
                uiState.actions.showFilterContactTypeDialog.data = new HashSet<>(state.getContactTypes());
                break;
            case SEARCH:
                uiState.searchVisibility = !uiState.searchVisibility;
                break;
        }
        updateUiState();
    }

    public void updateSortType(SortType sortType) {
        state.setSortType(sortType);
        mapContactsAndUpdate();
    }

    public void updateFilterContactTypes(Set<ContactType> filterContactTypes) {
        state.setContactTypes(filterContactTypes);
        mapContactsAndUpdate();
    }

    public void onBackPressed() {
        if (uiState.searchVisibility) {
            uiState.searchVisibility = false;
        } else {
            uiState.actions.finishActivity.data = true;
        }
        updateUiState();
    }

    public void updateSearchText(String query) {
        state.setQuery(query);
        uiState.resetSearchButtonVisibility = state.getQuery().length() != 0;
        updateUiState();
    }

    private void mapContactsAndUpdate() {
        final List<ContactUi> uiContacts = state.getAllContacts().stream()
                .filter(contact -> MergedContactUtils.contains(contact, state.getQuery()))
                .filter(contact -> MergedContactUtils.contains(contact, state.getContactTypes()))
                .sorted(createComparator(state.getSortType()))
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
        uiStateLiveDate.postValue(uiState.copy());
        uiState.actions.clear();
    }

    public static class UiState {

        public boolean searchVisibility = false;
        public boolean resetSearchButtonVisibility = false;

        public Actions actions = new Actions();
        public MenuBadges menuBadges = new MenuBadges();

        @NonNull
        public UiState copy() {
            final UiState copy = new UiState();
            copy.searchVisibility = searchVisibility;
            copy.resetSearchButtonVisibility = resetSearchButtonVisibility;
            copy.actions = actions.copy();
            copy.menuBadges = menuBadges.copy();
            return copy;
        }

        public static class Actions {
            public Action<Boolean> finishActivity = new Action<>(false);
            public Action<SortType> showSortTypeDialog = new Action<>(null);
            public Action<Set<ContactType>> showFilterContactTypeDialog = new Action<>(Collections.emptySet());

            @NonNull
            public Actions copy(){
                final Actions copy = new Actions();
                copy.finishActivity = new Action<>(finishActivity.data);
                copy.showSortTypeDialog = new Action<>(showSortTypeDialog.data);
                copy.showFilterContactTypeDialog = new Action<>(showFilterContactTypeDialog.data);
                return copy;
            }

            public void clear() {
                finishActivity.data = false;
                showSortTypeDialog.data = null;
                showFilterContactTypeDialog.data = Collections.emptySet();
            }
        }

        public static class Action<T> {
            @Nullable
            public T data;

            public Action(@Nullable T value) {
                this.data = value;
            }
        }

        public static class MenuBadges {

            @Nullable
            public MenuBadge sort = null;
            @Nullable
            public MenuBadge filter = null;
            @Nullable
            public MenuBadge search = null;

            public MenuBadges copy(){
                final MenuBadges copy = new MenuBadges();
                copy.sort = sort == null ? null : new MenuBadge(sort.value);
                copy.filter = filter == null ? null : new MenuBadge(filter.value);
                copy.search = search == null ? null : new MenuBadge(search.value);
                return copy;
            }
        }

        public static class MenuBadge {

            public int value;

            public MenuBadge(int value) {
                this.value = value;
            }
        }
    }
}