/**
 * @author Adwardwo1f
 * @created May 27, 2022
 */

package com.wo1f.yilongma.utils

import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

/**
 * Format to (EEE, MMM dd yyyy) format
 */
fun LocalDate.toAppDate(): String {
    return this.format(DateTimeFormatter.ofPattern(Constants.appDateFormat))
}

/**
 * Format to (hh:mm a) format
 */
fun LocalTime.toAppTime(): String {
    return this.format(DateTimeFormatter.ofPattern(Constants.appTimeFormat))
}
