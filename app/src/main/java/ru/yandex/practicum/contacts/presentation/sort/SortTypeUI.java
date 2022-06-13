package ru.yandex.practicum.contacts.presentation.sort;

import androidx.annotation.NonNull;

import ru.yandex.practicum.contacts.presentation.sort.model.SortType;

public class SortTypeUI {

    private final String sortType;
    private final boolean selected;

    public SortTypeUI(@NonNull String sortType, boolean selected) {
        this.sortType = sortType;
        this.selected = selected;
    }

    public String getSortType() {
        return sortType;
    }

    public boolean isSelected() {
        return selected;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SortTypeUI that = (SortTypeUI) o;

        if (selected != that.selected) return false;
        return sortType == that.sortType;
    }

    @Override
    public int hashCode() {
        int result = sortType.hashCode();
        result = 31 * result + (selected ? 1 : 0);
        return result;
    }
}
