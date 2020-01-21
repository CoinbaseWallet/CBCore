// Copyright (c) 2017-2020 Coinbase Inc. See LICENSE

import Foundation

/// Execute code once. Similiar to dispatch_once in GCD on Objective-C
public struct Once {
    private var didRun = false

    public init() {}

    /// Execute the block once. Subsequent calls to this function will be noop'ed
    public mutating func run(block: () -> Void) {
        if didRun {
            return
        }

        didRun = true
        block()
    }
}
