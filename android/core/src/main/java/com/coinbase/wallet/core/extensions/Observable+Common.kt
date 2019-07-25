package com.coinbase.wallet.core.extensions

import com.coinbase.wallet.core.util.Optional
import io.reactivex.Observable
import io.reactivex.Single
import timber.log.Timber

/**
 * Take the first entry from an observable and return it as a single.
 *
 * @param T The observable type
 *
 * @return A [Single] element from the first emitted element of the observable.
 *         Emits an error downstream if no such element exists.
 */
fun <T> Observable<T>.takeSingle(): Single<T> {
    return this.take(1).singleOrError()
}

/**
 * Safe unwrap element. Note this will block the chain until a valid non-nil value is available.
 *
 * @param T The element to unwrap
 *
 * @return The raw value [T] that has been unwrapped from [Optional]
 */
inline fun <reified T : Any> Observable<Optional<T>>.unwrap(): Observable<T> = this
    .filter { it.toNullable() != null }
    .map { it.toNullable() }

// FIXME: hish - log to analytics?
/**
 * Log any caught exception triggered inside an [Observable]
 *
 * @return The original observable if no error happens
 */
fun <T> Observable<T>.logError(): Observable<T> =
    doOnError { Timber.e("wallets: error ${it.localizedMessage}") }
