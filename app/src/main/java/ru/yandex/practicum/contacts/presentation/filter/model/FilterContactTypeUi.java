package ru.yandex.practicum.contacts.presentation.filter.model;

import androidx.annotation.NonNull;

public class FilterContactTypeUi {

    private final String contactType;
    private final boolean selected;

    public FilterContactTypeUi(@NonNull String contactType, boolean selected) {
        this.contactType = contactType;
        this.selected = selected;
    }

    public String getContactType() {
        return contactType;
    }

    public boolean isSelected() {
        return selected;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FilterContactTypeUi that = (FilterContactTypeUi) o;

        if (selected != that.selected) return false;
        return contactType.equals(that.contactType);
    }

    @Override
    public int hashCode() {
        int result = contactType.hashCode();
        result = 31 * result + (selected ? 1 : 0);
        return result;
    }
}
