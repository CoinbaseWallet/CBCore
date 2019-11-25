package com.coinbase.wallet.core.extensions

import io.reactivex.Single
import timber.log.Timber
import java.util.concurrent.TimeUnit

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

/**
 * Retry [Single] on error if given closure returns true.
 *
 * @param maxAttempts Maximum number of times to attempt the sequence subscription.
 * @param shouldRetry Closure called to determine whether to continue retrying
 *
 * @return Next sequence in the stream or error is thrown once maxAttempts is reached or closure returns false.
 */
fun <T> Single<T>.retryIfNeeded(maxAttempts: Int, shouldRetry: (Throwable) -> Boolean): Single<T> = this
    .retryWhen { errors ->
        var count = 0

        errors
            .map { Pair(count, it) }
            .map { (attempt, error) ->
                count++
                if (maxAttempts == attempt + 1 || !shouldRetry(error)) {
                    throw error
                }

                Unit
            }
    }
