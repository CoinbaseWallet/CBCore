// Copyright (c) 2017-2019 Coinbase Inc. See LICENSE

import XCTest
@testable import CBCore

class BoundedCacheTests: XCTestCase {
    func testBoundedCacheMaxSize() {
        let cache = BoundedCache<String, Int>(maxSize: 2)

        cache["hish"] = 1234

        XCTAssertEqual(1, cache.count)

        cache["andrew"] = 345

        XCTAssertEqual(2, cache.count)

        cache["johnny"] = 567

        XCTAssertEqual(2, cache.count)

        XCTAssertEqual(345, cache["andrew"])
        XCTAssertEqual(567, cache["johnny"])
        XCTAssertNil(cache["hish"])

        cache["james"] = 23444

        XCTAssertEqual(2, cache.count)

        XCTAssertNil(cache["hish"])
        XCTAssertNil(cache["andrew"])
        XCTAssertEqual(23444, cache["james"])
        XCTAssertEqual(567, cache["johnny"])
    }

    func testUpdatingCacheEntry() {
        let cache = BoundedCache<String, Int>(maxSize: 2)

        cache["hish"] = 1234

        XCTAssertEqual(1, cache.count)

        cache["andrew"] = 345

        XCTAssertEqual(2, cache.count)

        cache["hish"] = 9000

        cache["johnny"] = 567

        XCTAssertEqual(2, cache.count)

        XCTAssertEqual(9000, cache["hish"])
        XCTAssertEqual(567, cache["johnny"])
        XCTAssertNil(cache["andrew"])
    }

    func testDeletion() {
        let cache = BoundedCache<String, Int>(maxSize: 2)

        cache["hish"] = 1234

        XCTAssertEqual(1, cache.count)

        cache["hish"] = nil

        XCTAssertEqual(0, cache.count)
    }
}
