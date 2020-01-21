// Copyright (c) 2017-2020 Coinbase Inc. See LICENSE

import Foundation

/// Helper class to allow for lazy execution and caching
public class Lazy<T> {
    private var value: T!
    private let block: () -> T

    public init(_ block: @escaping () -> T) {
        self.block = block
    }

    /// Get cached value. If not cached, execute block, cache the value and return the result
    public func get() -> T {
        if value == nil {
            value = block()
        }

        return value
    }
}
