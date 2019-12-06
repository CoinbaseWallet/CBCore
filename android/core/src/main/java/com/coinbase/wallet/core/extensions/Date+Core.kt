package com.coinbase.wallet.core.extensions

import java.util.Date
import java.util.concurrent.TimeUnit

/**
 * Get number of seconds elapsed between dates
 */
fun Date.secondsSince(date: Date): Long {
    val millis = time - date.time
    return TimeUnit.MILLISECONDS.toSeconds(millis)
}

/**
 * Get the time difference between this time and the passed time
 *
 * @param date The time to subtract from this time
 *
 * @return The time difference in milliseconds
 */
fun Date.timeIntervalSince(date: Date): Long {
    val diffInMillis = this.time - date.time
    return TimeUnit.MILLISECONDS.convert(diffInMillis, TimeUnit.MILLISECONDS)
}

/**
 * Get the time difference between this time and the current time
 */
val Date.timeIntervalSinceNow: Long get() = this.timeIntervalSince(Date())
