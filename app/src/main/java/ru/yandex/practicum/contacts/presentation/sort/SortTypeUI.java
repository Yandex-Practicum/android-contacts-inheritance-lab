package ru.yandex.practicum.contacts.presentation.sort;

import androidx.annotation.NonNull;

import ru.yandex.practicum.contacts.presentation.base.ContactsOrderTypeUi;

public class SortTypeUI extends ContactsOrderTypeUi {

    public SortTypeUI(@NonNull String type, boolean isSelected) {
        super(type, isSelected);
    }

    @Override
    public String createLogMessage() {
        return "Выбран тип сортировки: " + type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SortTypeUI that = (SortTypeUI) o;

        if (isSelected != that.isSelected) {
            return false;
        }
        return type.equals(that.type);
    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + (isSelected ? 1 : 0);
        return result;
    }
}
