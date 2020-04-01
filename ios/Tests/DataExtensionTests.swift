// Copyright (c) 2017-2020 Coinbase Inc. See LICENSE

@testable import CBCore
import Foundation
import XCTest

class DataExtensionTests: XCTestCase {
    func testHexEncodedString() {
        let str = "acbdef0123456789abcdef0123456789"
        let data = Data(base64Encoded: str)!
        XCTAssertEqual(data.hexEncodedString(addPrefix: false), "69c6dd79fd35db7e39ebbf3d69b71d79fd35db7e39ebbf3d")
        XCTAssertEqual(data.hexEncodedString(addPrefix: true), "0x69c6dd79fd35db7e39ebbf3d69b71d79fd35db7e39ebbf3d")
    }

    func testHexEncodedStringWithEmptyData() {
        XCTAssertEqual(Data().hexEncodedString(addPrefix: false), "")
        XCTAssertEqual(Data().hexEncodedString(addPrefix: true), "0x")
    }
}
