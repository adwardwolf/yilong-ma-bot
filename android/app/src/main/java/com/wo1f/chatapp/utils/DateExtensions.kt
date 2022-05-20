package com.wo1f.chatapp.utils

import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

fun LocalDate.toAppDate(): String {
    return this.format(DateTimeFormatter.ofPattern(Constants.appDateFormat))
}

fun LocalTime.toAppTime(): String {
    return this.format(DateTimeFormatter.ofPattern(Constants.appTimeFormat))
}
