package com.coinbase.wallet.core.extensions

import com.coinbase.wallet.core.util.Optional
import com.coinbase.wallet.core.util.ReplayingShare
import io.reactivex.Observable
import io.reactivex.Single

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

/**
 * Retry [Observable] on error if given closure returns true.
 *
 * @param maxAttempts Maximum number of times to attempt the sequence subscription.
 * @param shouldRetry Closure called to determine whether to continue retrying
 *
 * @return Next sequence in the stream or error is thrown once maxAttempts is reached or closure returns false.
 */
fun <T> Observable<T>.retryIfNeeded(maxAttempts: Int, shouldRetry: (Throwable) -> Boolean): Observable<T> = this
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

/**
 * Converts any Observable<T> to a Observable<Unit>
 */
fun <T> Observable<T>.asUnit(): Observable<Unit> {
    return this.map { Unit }
}

/**
 * A transformer which combines `replay(1)`, `publish()`, and `refCount()` operators.
 *
 * Unlike traditional combinations of these operators, `ReplayingShare` caches the last emitted
 * value from the upstream observable *only* when one or more downstream observers are connected.
 * This allows expensive upstream observables to be shut down when no one is observing while also
 * replaying the last value seen by *any* observer to new ones.
 *
 * @param defaultValue the initial value delivered to new subscribers before any events are cached.
 * A null value means there will be no initial emission.
 */
@JvmOverloads
fun <T> Observable<T>.replayingShare(defaultValue: T? = null): Observable<T> {
    return compose(ReplayingShare.create(defaultValue))
}
