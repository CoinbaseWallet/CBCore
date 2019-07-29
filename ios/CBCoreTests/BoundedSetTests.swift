// Copyright (c) 2017-2019 Coinbase Inc. See LICENSE

import XCTest
@testable import CBCore

class BoundedSetTests: XCTestCase {
    func testBoundedSetMaxSize() {
        let set = BoundedSet<String>(maxSize: 2)

        set.add("hish")
        set.add("johnny")
        set.add("andrew")

        XCTAssertEqual(2, set.count)
        XCTAssertTrue(set.has("johnny"))
        XCTAssertTrue(set.has("andrew"))
        XCTAssertFalse(set.has("hish"))
    }

    func testBoundedItemsTouched() {
        let set = BoundedSet<String>(maxSize: 2)

        set.add("hish")
        set.add("johnny")
        set.add("hish")
        set.add("andrew")

        XCTAssertEqual(2, set.count)
        XCTAssertTrue(set.has("andrew"))
        XCTAssertFalse(set.has("johnny"))
        XCTAssertTrue(set.has("hish"))
    }
}
