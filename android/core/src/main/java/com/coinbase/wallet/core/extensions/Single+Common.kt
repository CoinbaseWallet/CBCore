package com.coinbase.wallet.core.extensions

import io.reactivex.Single
import timber.log.Timber
import java.util.concurrent.TimeUnit

/**
 * Log any caught exception triggered inside an `Single`
 */
// FIXME: hish - log to analytics?

fun <T> Single<T>.logError(): Single<T> = doOnError { Timber.e("walletapp: error ${it.localizedMessage}") }

/**
 * Converts any Single<T> to a Single<Unit>
 */
fun <T> Single<T>.asUnit(): Single<Unit> {
    return this.map { Unit }
}

/**
 * Calls retryWhen multiple times with a set delay. In doing so, Rx re-subscribes to the Single item
 * that was emitted during the execution of the chain. It does NOT recall the the method
 * multiple times.
 *
 * @param maxAttempts the number of attempts to make before failing
 * @param delay the time delay in {@see timeUnit} units
 * @param timeUnit the units of time to measure in
 *
 * @return Single<T> if successful
 */
fun <T> Single<T>.retryWithDelay(maxAttempts: Long, delay: Long, timeUnit: TimeUnit): Single<T> {
    return this.retryWhen { it.take(maxAttempts).delay(delay, timeUnit) }
}
