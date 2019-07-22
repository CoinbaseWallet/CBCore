package com.coinbase.wallet.core.interfaces

import io.reactivex.Single

/**
 * Represents an object that can be started
 */
interface Startable {
    /**
     * Called to start conformers of this interface
     */
    fun start(): Single<Unit>
}
