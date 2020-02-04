// Copyright (c) 2017-2020 Coinbase Inc. See LICENSE

import XCTest

class BidirectionalCollectionTests: XCTestCase {
    func testSafeGet() {
        let array = ["hello", "world", "coinbase", "homebase"]

        XCTAssertEqual("hello", array[safe: 0])
        XCTAssertNil(array[safe: 1000])
        XCTAssertEqual("world", array.second)
        XCTAssertEqual("coinbase", array.third)
        XCTAssertEqual("homebase", array[safe: 3])
    }
}
