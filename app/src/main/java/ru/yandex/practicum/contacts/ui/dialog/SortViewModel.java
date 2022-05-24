package ru.yandex.practicum.contacts.ui.dialog;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import ru.yandex.practicum.contacts.ui.main.SortType;
import ru.yandex.practicum.contacts.ui.model.SortTypeUI;

public class SortViewModel extends BaseBottomSheetViewModel {

    @Nullable
    private SortType selectedSortType = null;
    private final MutableLiveData<List<SortTypeUI>> sortTypesLiveDate = new MutableLiveData<>();

    @Override
    public void init() {
        updateSortTypes();
    }

    @Override
    public void onApplyClick() {

    }

    @Override
    public void onResetClick() {

    }

    public MutableLiveData<List<SortTypeUI>> getSortTypesLiveDate() {
        return sortTypesLiveDate;
    }

    private void updateSortTypes() {
        final SortType[] sortTypes = SortType.values();
        final List<SortTypeUI> sortTypesUi = Arrays.stream(sortTypes)
                .map(sortType -> new SortTypeUI(sortType, Objects.equals(sortType, selectedSortType)))
                .collect(Collectors.toList());
        sortTypesLiveDate.postValue(sortTypesUi);
    }
}
