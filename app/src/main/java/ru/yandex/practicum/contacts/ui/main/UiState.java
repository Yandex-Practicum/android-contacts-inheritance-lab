package ru.yandex.practicum.contacts.ui.main;

import androidx.annotation.Nullable;

public class UiState {

    public boolean searchVisibility = false;
    public boolean finishing = false;
    public boolean resetSearchButtonVisibility = false;

    @Nullable
    public MenuBadge sortBadge = null;
    @Nullable
    public MenuBadge filterBadge = null;
    @Nullable
    public MenuBadge searchBadge = null;

    public static class MenuBadge {
        public int value = 0;
    }
}
