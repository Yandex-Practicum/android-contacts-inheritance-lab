package ru.yandex.practicum.contacts.utils;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

import ru.yandex.practicum.contacts.R;
import ru.yandex.practicum.contacts.model.ContactType;

public class ContactTypeUtils {

    @DrawableRes
    public static int getIconRes(@NonNull ContactType type) {
        switch (type) {
            case TELEGRAM:
                return R.drawable.ic_type_telegram;
            case WHATS_APP:
                return R.drawable.ic_type_whatsapp;
            case VIBER:
                return R.drawable.ic_type_viber;
            case SIGNAL:
                return R.drawable.ic_type_signal;
            case THREEMA:
                return R.drawable.ic_type_threema;
            case PHONE:
                return R.drawable.ic_type_phone;
            case EMAIL:
                return R.drawable.ic_type_email;
            default:
                throw new IllegalArgumentException("Not supported type of ContactType");
        }
    }
}
