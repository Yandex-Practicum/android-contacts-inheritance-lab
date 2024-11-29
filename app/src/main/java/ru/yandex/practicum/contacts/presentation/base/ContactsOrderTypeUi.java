package ru.yandex.practicum.contacts.presentation.base;

import androidx.annotation.NonNull;

abstract public class ContactsOrderTypeUi {
    protected final String type;
    protected final boolean isSelected;
    public ContactsOrderTypeUi (@NonNull String type, boolean isSelected) {
        this.type = type;
        this.isSelected = isSelected;
    }

    public String getType() {
        return type;
    }

    public boolean getIsSelected() {
        return isSelected;
    }
    abstract public String createLogMessage();
}
