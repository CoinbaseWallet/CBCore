package com.coinbase.wallet.core.util


import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.Observer
import io.reactivex.annotations.NonNull
import io.reactivex.annotations.Nullable
import io.reactivex.disposables.Disposable
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

/**
 * This class is taken from Jake Wharton's ReplayingShare library (https://github.com/JakeWharton/RxReplayingShare)
 * It has been adapted for Kotlin.  The reason we don't import this library directly is that it doesn't support
 * RxJava3 and we don't want to pin ourselves to RxJava2.
 *
 * A transformer which combines the `replay(1)`, `publish()`, and `refCount()`
 * operators.
 *
 *
 * Unlike traditional combinations of these operators, `ReplayingShare` caches the last emitted
 * value from the upstream observable or flowable *only* when one or more downstream subscribers
 * are connected. This allows expensive upstream sources to be shut down when no one is listening
 * while also replaying the last value seen by *any* subscriber to new ones.
 */
class ReplayingShare<T> private constructor(
    @param:Nullable @field:Nullable private val defaultValue: T?
) : ObservableTransformer<T, T>, FlowableTransformer<T, T> {

    override fun apply(upstream: Observable<T>): Observable<T> {
        val lastSeen = LastSeen(defaultValue)
        return LastSeenObservable(upstream.doOnEach(lastSeen).share(), lastSeen).hide()
    }

    override fun apply(upstream: Flowable<T>): Flowable<T> {
        val lastSeen = LastSeen(defaultValue)
        return LastSeenFlowable(upstream.doOnEach(lastSeen).share(), lastSeen).hide()
    }

    internal class LastSeen<T>(
        @param:Nullable @field:Nullable private val defaultValue: T
    ) : Observer<T>, Subscriber<T> {
        @Nullable
        @Volatile
        internal var value: T? = defaultValue

        override fun onNext(value: T) {
            this.value = value
        }

        override fun onError(e: Throwable) {
            value = defaultValue
        }

        override fun onComplete() {
            value = defaultValue
        }

        override fun onSubscribe(ignored: Subscription) {}
        override fun onSubscribe(ignored: Disposable) {}
    }

    internal class LastSeenObservable<T>(
        private val upstream: Observable<T>,
        private val lastSeen: LastSeen<T>
    ) : Observable<T>() {

        override fun subscribeActual(observer: Observer<in T>) {
            upstream.subscribe(LastSeenObserver(observer, lastSeen))
        }
    }

    internal class LastSeenObserver<T>(
        private val downstream: Observer<in T>,
        private val lastSeen: LastSeen<T>
    ) : Observer<T> {

        override fun onSubscribe(d: Disposable) {
            downstream.onSubscribe(d)

            val value = lastSeen.value
            if (value != null && !d.isDisposed) {
                downstream.onNext(value)
            }
        }

        override fun onNext(value: T) {
            downstream.onNext(value)
        }

        override fun onComplete() {
            downstream.onComplete()
        }

        override fun onError(e: Throwable) {
            downstream.onError(e)
        }
    }

    internal class LastSeenFlowable<T>(
        private val upstream: Flowable<T>,
        private val lastSeen: LastSeen<T>
    ) : Flowable<T>() {

        override fun subscribeActual(subscriber: Subscriber<in T>) {
            upstream.subscribe(LastSeenSubscriber(subscriber, lastSeen))
        }
    }

    internal class LastSeenSubscriber<T>(
        private val downstream: Subscriber<in T>,
        private val lastSeen: LastSeen<T>
    ) : Subscriber<T>, Subscription {
        private var subscription: Subscription? = null

        @Volatile
        private var cancelled: Boolean = false

        private var first = true

        override fun onSubscribe(subscription: Subscription) {
            this.subscription = subscription
            downstream.onSubscribe(this)
        }

        override fun request(amountInput: Long) {
            var amount = amountInput
            if (amount == 0L) return

            if (first) {
                first = false

                val value = lastSeen.value
                if (value != null && !cancelled) {
                    downstream.onNext(value)

                    if (amount != java.lang.Long.MAX_VALUE && --amount == 0L) {
                        return
                    }
                }
            }
            val subscription = this.subscription!!
            subscription.request(amount)
        }

        override fun cancel() {
            val subscription = this.subscription!!
            cancelled = true
            subscription.cancel()
        }

        override fun onNext(value: T) {
            downstream.onNext(value)
        }

        override fun onComplete() {
            downstream.onComplete()
        }

        override fun onError(t: Throwable) {
            downstream.onError(t)
        }
    }

    companion object {
        /**
         * Creates a `ReplayingShare` transformer with a default value or null which will be emitted downstream
         * on subscription if there is not any cached value yet.
         *
         * @param defaultValue the initial value delivered to new subscribers before any events are
         * cached.
         */
        @NonNull
        fun <T> create(defaultValue: T? = null): ReplayingShare<T> {
            return ReplayingShare(defaultValue)
        }
    }
}
