// Copyright (c) 2017-2020 Coinbase Inc. See LICENSE

import Foundation

extension BidirectionalCollection {
    /// Get element at given index. If index is out of bounds, return nil
    ///
    /// - Parameter index: Index of element to fetch
    ///
    /// - Returns: Element at given index
    public subscript(safe offset: Int) -> Element? {
        guard !isEmpty, let i = index(startIndex, offsetBy: offset, limitedBy: index(before: endIndex))
        else { return nil }
        return self[i]
    }

    /// Get Second entry
    public var second: Element? {
        return self[safe: 1]
    }

    /// Get third entry
    public var third: Element? {
        return self[safe: 2]
    }
}
