package ru.yandex.practicum.contacts.presentation.filter.model;

import androidx.annotation.NonNull;

import ru.yandex.practicum.contacts.presentation.base.ContactsOrderTypeUi;

public class FilterContactTypeUi extends ContactsOrderTypeUi {

    public FilterContactTypeUi(@NonNull String type, boolean isSelected) {
        super(type, isSelected);
    }

    @Override
    public String createLogMessage() {

        return "Выбран фильтр: " + type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FilterContactTypeUi that = (FilterContactTypeUi) o;

        if (isSelected != that.isSelected) return false;
        return type.equals(that.type);
    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + (isSelected ? 1 : 0);
        return result;
    }
}
