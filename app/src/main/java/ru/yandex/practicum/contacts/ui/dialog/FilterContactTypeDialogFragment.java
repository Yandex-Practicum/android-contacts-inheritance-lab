package ru.yandex.practicum.contacts.ui.dialog;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import ru.yandex.practicum.contacts.R;
import ru.yandex.practicum.contacts.model.ContactType;
import ru.yandex.practicum.contacts.ui.adapter.FilterContactTypeAdapter;
import ru.yandex.practicum.contacts.ui.model.FilterContactTypeUi;

public class FilterContactTypeDialogFragment extends BaseBottomSheetDialogFragment<FilterContactTypeViewModel> {

    public static final String REQUEST_KEY = "REQUEST_KEY_FILTER";
    public static final String ARG_SELECTED_FILTER_CONTACT_TYPE = "ARG_SELECTED_FILTER_CONTACT_TYPE";

    private FilterContactTypeAdapter adapter;

    private FilterContactTypeDialogFragment() {
        super(FilterContactTypeViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iniViewModel();
        adapter = new FilterContactTypeAdapter(viewModel::onFilterTypeItemClick);
        binding.recycler.setAdapter(adapter);

        final DividerItemDecoration decoration = new DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL);
        decoration.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(requireActivity(), R.drawable.item_sort_type_decoration)));
        binding.recycler.addItemDecoration(decoration);

        viewModel.getFilterContactTypesLiveDate().observe(this, this::updateFilterContactTypes);
        viewModel.getUiStateLiveDate().observe(this, this::updateState);
    }

    private void iniViewModel() {
        final Set<ContactType> defaultFilterContactTypes = from(getArguments());
        viewModel.init(defaultFilterContactTypes);
    }

    private void updateFilterContactTypes(List<FilterContactTypeUi> filterTypes) {
        adapter.setItems(filterTypes);
    }

    private void updateState(FilterContactTypeViewModel.UiState state) {
        binding.applyButton.setEnabled(state.isApplyEnable);

        if (!state.newSelectedContactTypes.isEmpty()) {
            getParentFragmentManager().setFragmentResult(REQUEST_KEY, createBundle(state.newSelectedContactTypes));
            dismiss();
        }
    }

    public static FilterContactTypeDialogFragment newInstance(Set<ContactType> selectedContactTypes) {
        final FilterContactTypeDialogFragment fragment = new FilterContactTypeDialogFragment();
        fragment.setArguments(createBundle(selectedContactTypes));
        return fragment;
    }

    public static Set<ContactType> from(@Nullable Bundle bundle) {
        if (bundle == null) {
            return Collections.emptySet();
        }
        return (Set<ContactType>) bundle.getSerializable(ARG_SELECTED_FILTER_CONTACT_TYPE);
    }

    private static Bundle createBundle(Set<ContactType> contactTypes) {
        final Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_SELECTED_FILTER_CONTACT_TYPE, new HashSet<>(contactTypes));
        return bundle;
    }
}
