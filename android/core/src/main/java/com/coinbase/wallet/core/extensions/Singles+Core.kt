package com.coinbase.wallet.core.extensions

import com.coinbase.wallet.core.models.Five
import com.coinbase.wallet.core.models.Four
import com.coinbase.wallet.core.models.Seven
import com.coinbase.wallet.core.models.Six
import com.coinbase.wallet.core.util.Optional
import io.reactivex.Single
import io.reactivex.rxkotlin.Singles

/**
 * Safe way of passing singles to be zipped or returns empty if supplied list is empty
 *
 * @param singles List of singles to zip together
 *
 * @return result or zip operator or empty list
 */
inline fun <reified T> Singles.zipOrEmpty(singles: List<Single<T>>): Single<List<T>> {
    if (singles.isEmpty()) {
        return Single.just(emptyList())
    }

    return Single.zip(singles) { if (it.isEmpty()) emptyList() else it.filterIsInstance<T>() }
}

/**
 * Helper function to return a null optional
 */
inline fun <reified T : Any> Singles.justNull(): Single<Optional<T>> = Single.just(Optional(null))

/**
 * Zip the 4 singles and return a Four tuple
 *
 * @param s1 first single to zip
 * @param s2 second single to zip
 * @param s3 third single to zip
 * @param s4 fourth single to zip
 *
 * @return a Single that emits a Four tuple
 */
fun <A, B, C, D> Singles.zip(s1: Single<A>, s2: Single<B>, s3: Single<C>, s4: Single<D>): Single<Four<A, B, C, D>> {
    return Singles.zip(s1, s2, s3, s4) { r1, r2, r3, r4 -> Four(r1, r2, r3, r4) }
}

/**
 * Zip the 5 singles and return a Four tuple
 *
 * @param s1 first single to zip
 * @param s2 second single to zip
 * @param s3 third single to zip
 * @param s4 fourth single to zip
 * @param s5 fifth single to zip
 *
 * @return a Single that emits a Five tuple
 */
fun <A, B, C, D, E> Singles.zip(
    s1: Single<A>,
    s2: Single<B>,
    s3: Single<C>,
    s4: Single<D>,
    s5: Single<E>
): Single<Five<A, B, C, D, E>> {
    return Singles.zip(s1, s2, s3, s4, s5) { r1, r2, r3, r4, r5 -> Five(r1, r2, r3, r4, r5) }
}

/**
 * Zip the 6 singles and return a Four tuple
 *
 * @param s1 first single to zip
 * @param s2 second single to zip
 * @param s3 third single to zip
 * @param s4 fourth single to zip
 * @param s5 fifth single to zip
 * @param s6 sixth single to zip
 *
 * @return a Single that emits a Six tuple
 */
fun <A, B, C, D, E, F> Singles.zip(
    s1: Single<A>,
    s2: Single<B>,
    s3: Single<C>,
    s4: Single<D>,
    s5: Single<E>,
    s6: Single<F>
): Single<Six<A, B, C, D, E, F>> {
    return Singles.zip(s1, s2, s3, s4, s5, s6) { r1, r2, r3, r4, r5, r6 -> Six(r1, r2, r3, r4, r5, r6) }
}

/**
 * Zip the 7 singles and return a Four tuple
 *
 * @param s1 first single to zip
 * @param s2 second single to zip
 * @param s3 third single to zip
 * @param s4 fourth single to zip
 * @param s5 fifth single to zip
 * @param s6 sixth single to zip
 * @param s7 seventh single to zip
 *
 * @return a Single that emits a Seven tuple
 */
fun <A, B, C, D, E, F, G> Singles.zip(
    s1: Single<A>,
    s2: Single<B>,
    s3: Single<C>,
    s4: Single<D>,
    s5: Single<E>,
    s6: Single<F>,
    s7: Single<G>
): Single<Seven<A, B, C, D, E, E, G>> {
    return Singles.zip(s1, s2, s3, s4, s5, s6, s7) { r1, r2, r3, r4, r5, r6, r7 -> Seven(r1, r2, r3, r4, r5, r5, r7) }
}
