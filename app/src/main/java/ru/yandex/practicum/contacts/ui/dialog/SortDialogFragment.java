package ru.yandex.practicum.contacts.ui.dialog;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;

import java.util.List;
import java.util.Objects;

import ru.yandex.practicum.contacts.R;
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
        adapter = new SortTypeAdapter(viewModel::onSortTypeItemClick);
        binding.recycler.setAdapter(adapter);

        final DividerItemDecoration decoration = new DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL);
        decoration.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(requireActivity(), R.drawable.item_sort_type_decoration)));
        binding.recycler.addItemDecoration(decoration);

        viewModel.getSortTypesLiveDate().observe(this, this::updateSortTypes);
    }

    private void updateSortTypes(List<SortTypeUI> sortTypes) {
        adapter.setItems(sortTypes);
    }

    public static SortDialogFragment newInstance() {
        return new SortDialogFragment();
    }
}
