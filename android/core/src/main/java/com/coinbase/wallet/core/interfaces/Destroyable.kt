package com.coinbase.wallet.core.interfaces

import io.reactivex.Single

/**
 * Represents an object that can be destroyed
 */
interface Destroyable {
    /**
     * Called to destroy conformers of this interface
     */
    fun destroy(): Single<Boolean>
}
