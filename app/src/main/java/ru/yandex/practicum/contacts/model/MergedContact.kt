package ru.yandex.practicum.contacts.model

data class MergedContact(
    val id: Int,
    val firstName: String,
    val middleName: String,
    val surname: String,
    val phone: String,
    val normalizedNumber: String,
    val email: String,
    val contactTypes: List<String>,
    val photoUri: String
)