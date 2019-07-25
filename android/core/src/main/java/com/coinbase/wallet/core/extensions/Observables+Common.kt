package com.coinbase.wallet.core.extensions

import io.reactivex.Emitter
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.Observables
import java.util.concurrent.Callable

/**
 * Safe way of zipping observables or returns empty if supplied list is empty
 *
 * @param observables List of observables to zip together
 *
 * @return result of zip operator or empty list
 */
inline fun <reified T> Observables.zipOrEmpty(observables: List<Observable<T>>): Observable<List<T>> {
    if (observables.isEmpty()) {
        return Observable.just(emptyList())
    }

    return Observable.zip(observables) {
        if (it.isEmpty()) emptyList() else it.filterIsInstance<T>()
    }
}

/**
 * Safe way of combining latest results from given observables or returns empty if supplied list is empty
 *
 * @param observables List of observables to combined together
 *
 * @return result of combineLatest operator or empty list
 */
inline fun <reified T> Observables.combineLatestOrEmpty(observables: List<Observable<T>>): Observable<List<T>> {
    if (observables.isEmpty()) {
        return Observable.just(emptyList())
    }

    return Observable.combineLatest(observables.toTypedArray()) {
        if (it.isEmpty()) emptyList() else it.filterIsInstance<T>()
    }
}

inline fun <T> Observables.sequential(
    initialState: T,
    crossinline nextValue: (T) -> T,
    crossinline endWhen: (T) -> Boolean
): Observable<T> {
    return Observable.generate(
        Callable<T> { initialState },
        BiFunction<T, Emitter<T>, T> { currentValue, emitter ->
            if (endWhen(currentValue)) {
                emitter.onComplete()
            } else {
                emitter.onNext(currentValue)
            }

            nextValue(currentValue)
        }
    )
}
