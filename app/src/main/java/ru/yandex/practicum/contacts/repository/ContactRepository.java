package ru.yandex.practicum.contacts.repository;


import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.Context;
import android.provider.ContactsContract;
import android.provider.ContactsContract.RawContacts;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import ru.yandex.practicum.contacts.model.ContactDomain;
import ru.yandex.practicum.contacts.model.ContactSource;
import ru.yandex.practicum.contacts.utils.Constants;
import ru.yandex.practicum.contacts.utils.ContextUtils;

public class ContactRepository {

    private final Context context;

    public ContactRepository(Context context) {
        this.context = context;
    }

    public List<ContactDomain> getContacts() {
        if (ContextUtils.hasContactPermissions(context)) {
            return Collections.emptyList();
        }

        return Collections.emptyList();
    }




}
