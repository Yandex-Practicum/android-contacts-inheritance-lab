package ru.yandex.practicum.contacts.ui;

import android.text.TextUtils;

import ru.yandex.practicum.contacts.model.MergedContact;
import ru.yandex.practicum.contacts.ui.model.ContactUi;
import ru.yandex.practicum.contacts.utils.model.MergedContactUtils;
import ru.yandex.practicum.contacts.utils.model.PhoneUtils;

public class ContactUiMapper {

    public ContactUi map(MergedContact contact) {
        String displayName = (contact.getFirstName() + " " + contact.getSurname()).trim();
        String phone = PhoneUtils.format(contact.getPhone());
        if (TextUtils.isEmpty(displayName)) {
            displayName = phone;
            phone = "";
        }
        return new ContactUi(
                displayName,
                phone,
                contact.getPhotoUri(),
                MergedContactUtils.getContactTypes(contact)
        );
    }
}
