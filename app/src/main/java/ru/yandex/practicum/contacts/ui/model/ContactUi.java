package ru.yandex.practicum.contacts.ui.model;

import androidx.annotation.NonNull;

import java.util.List;

import ru.yandex.practicum.contacts.model.ContactType;

public class ContactUi {

    private final String name;
    private final String phone;
    private final String date;
    private final String photo;
    private final List<ContactType> types;

    public ContactUi(
            @NonNull String name,
            @NonNull String phone,
            @NonNull String date,
            @NonNull String photo,
            @NonNull List<ContactType> types
    ) {
        this.name = name;
        this.phone = phone;
        this.date = date;
        this.photo = photo;
        this.types = types;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getDate() {
        return date;
    }

    public String getPhoto() {
        return photo;
    }

    public List<ContactType> getTypes() {
        return types;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContactUi contact = (ContactUi) o;

        if (!name.equals(contact.name)) return false;
        if (!phone.equals(contact.phone)) return false;
        if (!date.equals(contact.date)) return false;
        if (!photo.equals(contact.photo)) return false;
        return types.equals(contact.types);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + phone.hashCode();
        result = 31 * result + date.hashCode();
        result = 31 * result + photo.hashCode();
        result = 31 * result + types.hashCode();
        return result;
    }
}
