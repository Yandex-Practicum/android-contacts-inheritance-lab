package ru.yandex.practicum.contacts.ui;

import ru.yandex.practicum.contacts.model.MergedContact;
import ru.yandex.practicum.contacts.ui.model.ContactUi;
import ru.yandex.practicum.contacts.utils.ContactUtils;
import ru.yandex.practicum.contacts.utils.MergedContactUtils;
import ru.yandex.practicum.contacts.utils.PhoneUtils;

public class ContactUiMapper {

    public ContactUi map(MergedContact contact) {
        return new ContactUi(
                contact.getFirstName() + " " + contact.getSurname(),
                PhoneUtils.format(contact.getPhone()),
                "22.04.1993",
                contact.getPhotoUri(),
                MergedContactUtils.getContactTypes(contact)
        );
    }
}
