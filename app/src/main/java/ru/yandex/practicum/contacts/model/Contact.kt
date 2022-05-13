package ru.yandex.practicum.contacts.model

data class Contact(
    val id: Int,
    val prefix: String,
    val firstName: String,
    val middleName: String,
    val surname: String,
    val suffix: String,
    val photoUri: String,
    val phoneNumbers: List<PhoneNumber>,
    val emails: List<Email>,
    val addresses: List<Address>,
    val source: String,
    val starred: Int,
    val contactId: Int,
    val thumbnailUri: String
)
