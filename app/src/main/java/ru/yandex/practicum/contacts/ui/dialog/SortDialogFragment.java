package ru.yandex.practicum.contacts.ui.dialog;

public class SortDialogFragment extends BaseBottomSheetDialogFragment<SortViewModel> {

    private SortDialogFragment() {
        super(SortViewModel.class);
    }

    public static SortDialogFragment newInstance() {
        return new SortDialogFragment();
    }
}
