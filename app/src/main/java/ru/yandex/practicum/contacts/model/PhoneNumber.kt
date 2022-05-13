package ru.yandex.practicum.contacts.model

data class PhoneNumber(
    val value: String,
    val type: Int,
    val label: String,
    val normalizedNumber: String
)
