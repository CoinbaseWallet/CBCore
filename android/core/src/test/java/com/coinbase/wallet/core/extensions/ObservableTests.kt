package com.coinbase.wallet.core.extensions

import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject
import org.junit.Assert
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.atomic.AtomicInteger

class ObservableTests {
    @Test
    fun replaySharedCreatesOnce() {
        val createCount = AtomicInteger(0)
        val subject = PublishSubject.create<Unit>()

        val observable = subject
            .map { createCount.incrementAndGet() }
            .replayingShare()

        val countdownLatch = CountDownLatch(2)
        observable.subscribeBy {
            Assert.assertEquals(1, it)
            countdownLatch.countDown()
        }

        observable.subscribeBy {
            Assert.assertEquals(1, it)
            countdownLatch.countDown()
        }

        subject.onNext(Unit)

        countdownLatch.await()
    }

    @Test
    fun replaySharedCreatesAgainAfterUnsubscribe() {
        val createCount = AtomicInteger(0)
        val observable = Observable.just(1)
            .map { createCount.incrementAndGet() }
            .replayingShare()

        val countdownLatch = CountDownLatch(2)
        observable
            .doOnComplete { countdownLatch.countDown() }
            .subscribeBy {
                Assert.assertEquals(1, it)
                countdownLatch.countDown()
            }

        countdownLatch.await()

        val countdownLatch2 = CountDownLatch(1)

        observable.subscribeBy {
            Assert.assertEquals(2, it)
            countdownLatch2.countDown()
        }

        countdownLatch2.await()
    }
}
