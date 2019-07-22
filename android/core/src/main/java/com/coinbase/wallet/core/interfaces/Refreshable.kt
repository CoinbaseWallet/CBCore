package com.coinbase.wallet.core.interfaces

import io.reactivex.Single

/**
 * Represents an object that can be refreshed by the RefreshManager
 */
interface Refreshable {
    /**
     * Called to refresh conformers of this interface
     *
     * @param isForced Indicates whether conformer of this interface should ignore all conditions and force a refresh
     * @return A single [Unit] indicating the refresh operation completed
     */
    fun refresh(isForced: Boolean): Single<Unit>
}
