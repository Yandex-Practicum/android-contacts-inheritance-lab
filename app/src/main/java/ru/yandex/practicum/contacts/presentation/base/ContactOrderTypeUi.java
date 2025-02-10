package ru.yandex.practicum.contacts.presentation.base;

import androidx.annotation.NonNull;

public abstract class ContactOrderTypeUi {
    protected final String type;
    protected final boolean isSelected;
    public ContactOrderTypeUi(@NonNull String type, boolean isSelected) {
        this.type = type;
        this.isSelected = isSelected;
    }
    public String getType() {
        return type;
    }
    public boolean isSelected() {
        return isSelected;
    }
    public abstract String createLogMessage();
}
