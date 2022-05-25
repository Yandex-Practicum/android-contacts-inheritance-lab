package ru.yandex.practicum.contacts.ui.dialog;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import ru.yandex.practicum.contacts.model.ContactType;
import ru.yandex.practicum.contacts.ui.main.FilterContactType;
import ru.yandex.practicum.contacts.ui.model.FilterContactTypeUi;

public class FilterContactTypeViewModel extends BaseBottomSheetViewModel {

    private final UiState uiState = new UiState();
    private final MutableLiveData<List<FilterContactTypeUi>> filterContactTypesLiveDate = new MutableLiveData<>();
    private final MutableLiveData<UiState> uiStateLiveDate = new MutableLiveData<>();

    private Set<ContactType> defaultFilterContactTypes;
    private Set<ContactType> selectedFilterContactTypes;

    public void init(Set<ContactType> defaultFilterContactTypes) {
        this.defaultFilterContactTypes = new HashSet<>(defaultFilterContactTypes);
        this.selectedFilterContactTypes = new HashSet<>(defaultFilterContactTypes);
        updateFilterContactTypes();
        updateUiState();
    }

    public void onFilterTypeItemClick(FilterContactTypeUi filterContactType) {
        updateSelectedContactTypes(filterContactType.getContactType());
        updateFilterContactTypes();
        updateUiState();
    }

    @Override
    public void onApplyClick() {
        uiState.newSelectedContactTypes = selectedFilterContactTypes;
        updateUiState();
    }

    @Override
    public void onResetClick() {
        selectedFilterContactTypes = new HashSet<>(defaultFilterContactTypes);
        updateFilterContactTypes();
        updateUiState();
    }

    public MutableLiveData<List<FilterContactTypeUi>> getFilterContactTypesLiveDate() {
        return filterContactTypesLiveDate;
    }

    public MutableLiveData<UiState> getUiStateLiveDate() {
        return uiStateLiveDate;
    }

    private void updateFilterContactTypes() {
        final List<FilterContactTypeUi> filterContactTypesUi = new ArrayList<>();
        final boolean allSelected = selectedFilterContactTypes.size() == ContactType.values().length;
        filterContactTypesUi.add(new FilterContactTypeUi(FilterContactType.ALL, allSelected));
        final List<FilterContactTypeUi> collect = Arrays.stream(ContactType.values())
                .map(contactType -> new FilterContactTypeUi(toFilterContactType(contactType), selectedFilterContactTypes.contains(contactType)))
                .collect(Collectors.toList());
        filterContactTypesUi.addAll(collect);
        filterContactTypesLiveDate.postValue(filterContactTypesUi);
    }

    private void updateUiState() {
        uiState.isApplyEnable = !defaultFilterContactTypes.equals(selectedFilterContactTypes) && !selectedFilterContactTypes.isEmpty();
        uiStateLiveDate.postValue(uiState);
    }

    private void updateSelectedContactTypes(FilterContactType type) {
        if (type == FilterContactType.ALL) {
            if (selectedFilterContactTypes.size() == ContactType.values().length) {
                selectedFilterContactTypes.clear();
            } else {
                selectedFilterContactTypes.addAll(Arrays.asList(ContactType.values()));
            }
            return;
        }
        final ContactType contactType = toContactType(type);
        if (selectedFilterContactTypes.contains(contactType)) {
            selectedFilterContactTypes.remove(contactType);
        } else {
            selectedFilterContactTypes.add(contactType);
        }
    }

    private ContactType toContactType(FilterContactType type) {
        switch (type) {
            case TELEGRAM:
                return ContactType.TELEGRAM;
            case WHATS_APP:
                return ContactType.WHATS_APP;
            case VIBER:
                return ContactType.VIBER;
            case SIGNAL:
                return ContactType.SIGNAL;
            case THREEMA:
                return ContactType.THREEMA;
            case PHONE:
                return ContactType.PHONE;
            case EMAIL:
                return ContactType.EMAIL;
            default:
                throw new IllegalArgumentException("Not supported FilterContactType");
        }
    }

    private FilterContactType toFilterContactType(ContactType type) {
        switch (type) {
            case TELEGRAM:
                return FilterContactType.TELEGRAM;
            case WHATS_APP:
                return FilterContactType.WHATS_APP;
            case VIBER:
                return FilterContactType.VIBER;
            case SIGNAL:
                return FilterContactType.SIGNAL;
            case THREEMA:
                return FilterContactType.THREEMA;
            case PHONE:
                return FilterContactType.PHONE;
            case EMAIL:
                return FilterContactType.EMAIL;
            default:
                throw new IllegalArgumentException("Not supported ContactType");
        }
    }

    static class UiState {
        public boolean isApplyEnable = false;
        public Set<ContactType> newSelectedContactTypes = Collections.emptySet();
    }
}
