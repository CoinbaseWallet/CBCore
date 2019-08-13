package com.coinbase.wallet.core.util

import com.coinbase.wallet.core.extensions.retryIfNeeded
import io.reactivex.Single
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import org.junit.Assert
import org.junit.Test
import java.util.concurrent.CountDownLatch

class SingleExtensionTests {
    @Test
    fun retryIfNeededSucceed() {
        var attempt = 0
        val expectedResult = true
        val single = Single.create<Boolean> {
            when (attempt) {
                0, 1 -> it.onError(TestException2)
                else -> it.onSuccess(true)
            }

            attempt++
        }

        val latch = CountDownLatch(1)
        var result = false

        Schedulers.io().scheduleDirect {
            single.retryIfNeeded(3) { it is TestException2 }
                .subscribeBy(
                    onSuccess = {
                        result = it
                        latch.countDown()
                    },
                    onError = { latch.countDown() }
                )
        }

        latch.await()

        Assert.assertEquals(expectedResult, result)
        Assert.assertEquals(3, attempt)
    }

    @Test
    fun retryIfNeededExceedMaxAttempts() {
        var attempt = 0
        val single = Single.create<Boolean> {
            when (attempt) {
                0, 1, 2 -> it.onError(TestException2)
                else -> it.onSuccess(true)
            }

            attempt++
        }

        val latch = CountDownLatch(1)

        Schedulers.io().scheduleDirect {
            single.retryIfNeeded(3) { it is TestException2 }
                .subscribeBy(
                    onSuccess = {
                        Assert.fail("stream should not succeed")
                        latch.countDown()
                    },
                    onError = { latch.countDown() }
                )
        }

        latch.await()

        Assert.assertEquals(3, attempt)
    }

    @Test
    fun retryIfNeededDoNotRetry() {
        var attempt = 0
        val single = Single.create<Boolean> {
            when (attempt) {
                0 -> it.onError(TestException2)
                1 -> it.onError(TestException1)
                else -> it.onSuccess(true)
            }

            attempt++
        }

        val latch = CountDownLatch(1)

        Schedulers.io().scheduleDirect {
            single.retryIfNeeded(3) { it is TestException2 }
                .subscribeBy(
                    onSuccess = {
                        Assert.fail("stream should not succeed")
                        latch.countDown()
                    },
                    onError = { latch.countDown() }
                )
        }

        latch.await()

        Assert.assertEquals(2, attempt)
    }
}
