package ru.yandex.practicum.contacts.ui.dialog;

import androidx.lifecycle.ViewModel;

public abstract class BaseBottomSheetViewModel extends ViewModel {

    abstract public void onApplyClick();

    abstract public void onResetClick();
}
