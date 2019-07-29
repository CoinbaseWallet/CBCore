// Copyright (c) 2017-2019 Coinbase Inc. See LICENSE

import BigInt
@testable import CBCore
import Foundation
import XCTest

class BigIntExtensionTests: XCTestCase {
    func testRegularNumericString() {
        let expected = "123423323"
        guard let bigInt = expected.asBigInt else {
            return XCTFail("Unable to parse bigint")
        }

        XCTAssertEqual(BigInt(expected), bigInt)

        let negativeExpected = "-123423323"
        guard let negativeBigInt = negativeExpected.asBigInt else {
            return XCTFail("Unable to parse bigint")
        }

        XCTAssertEqual(BigInt(negativeExpected), negativeBigInt)
    }

    func testNonNumericString() {
        let expected = "sdjhxhsnh"
        let bigInt = expected.asBigInt

        XCTAssertNil(bigInt)
    }

    func testNonNumericWithLeadingNumbericValuesString() {
        let expected = "123456sdjhxhsnh"
        let bigInt = expected.asBigInt

        let negativeExpected = "-123456sdjhxhsnh"
        let negativeBigInt = negativeExpected.asBigInt

        XCTAssertNil(bigInt)
        XCTAssertNil(negativeBigInt)
    }

    func testFractionalNumericScientificNotation() {
        let expected = "3300000000000000000"
        guard let bigInt = "3.3e18".asBigInt else {
            return XCTFail("Unable to parse bigint")
        }

        XCTAssertEqual(BigInt(expected), bigInt)

        let negativeExpected = "-3300000000000000000"
        guard let negativeBigInt = "-3.3e18".asBigInt else {
            return XCTFail("Unable to parse bigint")
        }

        XCTAssertEqual(BigInt(negativeExpected), negativeBigInt)
    }

    func testNonFractionalNumericScientificNotation() {
        let expected = "40000"
        guard let bigInt = "4e4".asBigInt else {
            return XCTFail("Unable to parse bigint")
        }

        XCTAssertEqual(BigInt(expected), bigInt)

        let negativeExpected = "-40000"
        guard let negativeBigInt = "-4e4".asBigInt else {
            return XCTFail("Unable to parse bigint")
        }

        XCTAssertEqual(BigInt(negativeExpected), negativeBigInt)
    }

    func testFractionalNumericScientificNotation2() {
        let expected = "42029392000000"
        guard let bigInt = "42029.392e9".asBigInt else {
            return XCTFail("Unable to parse bigint")
        }

        XCTAssertEqual(BigInt(expected), bigInt)

        let negativeExpected = "-42029392000000"
        guard let negativeBigInt = "-42029.392e9".asBigInt else {
            return XCTFail("Unable to parse bigint")
        }

        XCTAssertEqual(BigInt(negativeExpected), negativeBigInt)
    }

    func testFractionalNumericScientificNotationWithTrailingZeros() {
        let expected = "42029392000000"
        guard let bigInt = "42029.39200e9".asBigInt else {
            return XCTFail("Unable to parse bigint")
        }

        XCTAssertEqual(BigInt(expected), bigInt)

        let negativeExpected = "-42029392000000"
        guard let negativeBigInt = "-42029.39200e9".asBigInt else {
            return XCTFail("Unable to parse bigint")
        }

        XCTAssertEqual(BigInt(negativeExpected), negativeBigInt)
    }

    func testFractionalNumericScientificNotationWithTrailingZeros2() {
        let expected = "42029392000000"
        guard let bigInt = "42029.39200000000000000000000000000e9".asBigInt else {
            return XCTFail("Unable to parse bigint")
        }

        XCTAssertEqual(BigInt(expected), bigInt)

        let negativeExpected = "-42029392000000"
        guard let negativeBigInt = "-42029.39200000000000000000000000000e9".asBigInt else {
            return XCTFail("Unable to parse bigint")
        }

        XCTAssertEqual(BigInt(negativeExpected), negativeBigInt)
    }
}
