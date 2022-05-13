package ru.yandex.practicum.contacts.utils;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import ru.yandex.practicum.contacts.model.ContactType;
import ru.yandex.practicum.contacts.model.MergedContact;

public class MergedContactUtils {

    public static boolean contains(MergedContact contact, String query) {
        if (TextUtils.isEmpty(query)) {
            return true;
        }
        return contact.getFirstName().contains(query) ||
                contact.getMiddleName().contains(query) ||
                contact.getSurname().contains(query) ||
                contact.getNormalizedNumber().contains(query) ||
                contact.getPhone().contains(query) ||
                contact.getEmail().contains(query);
    }

    public static List<ContactType> getContactTypes(MergedContact contact) {
        final ArrayList<ContactType> allTypes = new ArrayList<>();
        if (!TextUtils.isEmpty(contact.getPhone())) {
            allTypes.add(ContactType.PHONE);
        }
        if (!TextUtils.isEmpty(contact.getEmail())) {
            allTypes.add(ContactType.EMAIL);
        }
        final List<ContactType> types = contact.getContactTypes().stream()
                .map(ContactTypeUtils::parse)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        allTypes.addAll(types);

        return Collections.unmodifiableList(allTypes);
    }
}
