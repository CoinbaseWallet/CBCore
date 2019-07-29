// Copyright (c) 2017-2019 Coinbase Inc. See LICENSE

import XCTest
@testable import CBCore

class AtomicInt32Tests: XCTestCase {
    func testSingleThreadedIncrements() {
        var atomic = AtomicInt32(0)
        let expectedTotal: Int32 = 6

        (0 ..< expectedTotal).forEach { _ in atomic.increment() }

        XCTAssertEqual(expectedTotal, atomic.get())
    }

    func testSingleThreadedIncrementAndGet() {
        var atomic = AtomicInt32(0)
        let expectedTotal: Int32 = 6

        (0 ..< expectedTotal).forEach { current in
            let val = atomic.incrementAndGet()
            XCTAssertEqual(current + 1, val)
        }

        XCTAssertEqual(expectedTotal, atomic.get())
    }

    func testMultiThreadedIncrements() {
        let testExpectation = expectation(description: .empty)
        let expectedTotal: Int32 = 1000
        let group = DispatchGroup()
        var atomic = AtomicInt32(0)

        (0 ..< expectedTotal).forEach { _ in
            group.enter()
            DispatchQueue.global(qos: .userInitiated).async {
                atomic.increment()
                group.leave()
            }
        }

        group.notify(queue: .main) {
            testExpectation.fulfill()
        }

        waitForExpectations(timeout: 6) { err in
            XCTAssertNil(err)
            XCTAssertEqual(expectedTotal, atomic.get())
        }
    }

    func testMultiThreadedIncrementAndGets() {
        let testExpectation = expectation(description: .empty)
        let expectedTotal: Int32 = 1000
        let group = DispatchGroup()
        var atomic = AtomicInt32(0)

        (0 ..< expectedTotal).forEach { _ in
            group.enter()
            DispatchQueue.global(qos: .userInitiated).async {
                _ = atomic.incrementAndGet()
                group.leave()
            }
        }

        group.notify(queue: .main) {
            testExpectation.fulfill()
        }

        waitForExpectations(timeout: 6) { err in
            XCTAssertNil(err)
            XCTAssertEqual(expectedTotal, atomic.get())
        }
    }
}
