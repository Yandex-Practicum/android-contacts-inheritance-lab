package ru.yandex.practicum.contacts.tmp

import ru.yandex.practicum.contacts.model.ContactType
import ru.yandex.practicum.contacts.ui.model.Contact
import kotlin.random.Random

private val names = listOf("Иван", "Петр", "Сергей", "Алексей", "Павел")
private val surnames = listOf("Иванов", "Петров", "Сергеев", "Алексеев", "Павлов")

fun generate(): List<Contact> {
    return (1..30).map {
        Contact(
            "${names.random()} ${surnames.random()}",
            phone(),
            date(),
            "",
            types()
        )
    }
}

fun phone(): String {
    return if (Random.nextBoolean()) {
        ""
    } else {
        "+7 (${Random.nextInt(100, 999)}) ${Random.nextInt(100, 999)}-${Random.nextInt(10, 99)}-${Random.nextInt(10, 99)}"
    }
}

fun date(): String {
    return "${Random.nextInt(10, 28)}.${Random.nextInt(1, 12)}.${Random.nextInt(2000, 2022)}"
}

fun types(): List<ContactType> {
    return ContactType.values().toList()
        .filter { Random.nextInt(4) != 0 }
}