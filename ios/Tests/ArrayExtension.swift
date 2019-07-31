// Copyright (c) 2017-2019 Coinbase Inc. See LICENSE

import RxBlocking
import RxSwift
import XCTest

class ArrayExtension: XCTestCase {
    func testSingleZip() throws {
        let arrayOfSingles = [Single.just(1), Single.just(2), Single.just(3)]
        let zipped = try arrayOfSingles.zip().toBlocking(timeout: 6).single()

        XCTAssertEqual([1, 2, 3], zipped)
    }

    func testObservableZip() throws {
        let arrayOfObservables = [Observable.just(1), Observable.just(2), Observable.just(3)]
        let zipped = try arrayOfObservables.zip().toBlocking(timeout: 6).last()

        XCTAssertEqual([1, 2, 3], zipped)
    }
}
