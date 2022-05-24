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
import ru.yandex.practicum.contacts.ui.main.SortType;
import ru.yandex.practicum.contacts.ui.model.SortTypeUI;

public class SortDialogFragment extends BaseBottomSheetDialogFragment<SortViewModel> {

    public static final String REQUEST_KEY = "REQUEST_KEY_SORT";
    public static final String ARG_SELECTED_SORT_TYPE = "ARG_SELECTED_SORT_TYPE";

    private SortTypeAdapter adapter;

    private SortDialogFragment() {
        super(SortViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iniViewModel();
        adapter = new SortTypeAdapter(viewModel::onSortTypeItemClick);
        binding.recycler.setAdapter(adapter);

        final DividerItemDecoration decoration = new DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL);
        decoration.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(requireActivity(), R.drawable.item_sort_type_decoration)));
        binding.recycler.addItemDecoration(decoration);

        viewModel.getSortTypesLiveDate().observe(this, this::updateSortTypes);
        viewModel.getUiStateLiveDate().observe(this, this::updateState);
    }

    private void iniViewModel() {
        final Bundle bundle = getArguments();
        final SortType defaultSortType = (SortType) (bundle != null ? bundle.getSerializable(ARG_SELECTED_SORT_TYPE) : SortType.BY_NAME);
        viewModel.init(defaultSortType);
    }

    private void updateSortTypes(List<SortTypeUI> sortTypes) {
        adapter.setItems(sortTypes);
    }

    private void updateState(SortViewModel.UiState state) {
        binding.applyButton.setEnabled(state.isApplyEnable);

        if (state.newSelectedSortType != null) {
            getParentFragmentManager().setFragmentResult(REQUEST_KEY, createBundle(state.newSelectedSortType));
            dismiss();
        }
    }

    public static SortDialogFragment newInstance(SortType selectedSortType) {
        final SortDialogFragment fragment = new SortDialogFragment();
        fragment.setArguments(createBundle(selectedSortType));
        return fragment;
    }

    private static Bundle createBundle(SortType sortType) {
        final Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_SELECTED_SORT_TYPE, sortType);
        return bundle;
    }
}
