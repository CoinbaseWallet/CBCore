//
//  StringExtensionTests.swift
//  Tests
//
//  Created by Andrew Gold on 10/25/19.
//  Copyright © 2019 Coinbase Inc. All rights reserved.
//

import XCTest

class StringExtensionTests: XCTestCase {
    func testMatches() {
        let string = "this is a string"

        var matches = string.matches(regex: "(\\w)+")
        XCTAssertEqual(matches.count, 4)

        matches = string.matches(regex: "(is)+")
        XCTAssertEqual(matches.count, 2)

        matches = string.matches(regex: "(string)+")
        XCTAssertEqual(matches.count, 1)

        matches = string.matches(regex: "(thisi)+")
        XCTAssertTrue(matches.isEmpty)

        matches = string.matches(regex: "(rthis)+")
        XCTAssertTrue(matches.isEmpty)

        matches = string.matches(regex: "(this is a string and more)+")
        XCTAssertTrue(matches.isEmpty)
    }
}

