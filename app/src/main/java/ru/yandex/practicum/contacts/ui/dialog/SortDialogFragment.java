package ru.yandex.practicum.contacts.ui.dialog;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import ru.yandex.practicum.contacts.ui.adapter.SortTypeAdapter;
import ru.yandex.practicum.contacts.ui.model.SortTypeUI;

public class SortDialogFragment extends BaseBottomSheetDialogFragment<SortViewModel> {

    private SortTypeAdapter adapter;

    private SortDialogFragment() {
        super(SortViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new SortTypeAdapter();
        binding.recycler.setAdapter(adapter);

        viewModel.getSortTypesLiveDate().observe(this, this::updateSortTypes);
    }

    private void updateSortTypes(List<SortTypeUI> sortTypes) {
        adapter.setItems(sortTypes);
    }

    public static SortDialogFragment newInstance() {
        return new SortDialogFragment();
    }
}
