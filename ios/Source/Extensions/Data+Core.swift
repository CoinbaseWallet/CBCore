// Copyright (c) 2017-2019 Coinbase Inc. See LICENSE

import Foundation
import os.log

private let hexadecimalArray: [UInt8] = Array("0123456789abcdef".utf8)

extension Data {
    /// Convert to JSON dictionary if possible
    public var jsonDictionary: [String: Any]? {
        return jsonObject as? [String: Any]
    }

    /// Convert to JSON object if possible
    public var jsonObject: Any? {
        do {
            return try JSONSerialization.jsonObject(with: self, options: [])
        } catch {
            print("exception: \(error)")
            return nil
        }
    }

    /// Generate random `Data` based on given length
    ///
    /// - Parameter:
    ///     - numberOfBytes: Size of random `Data` object to generate
    ///
    /// - Returns: Randomized bytes with given size encapsulated in a `Data` object
    public static func randomBytes(_ numberOfBytes: Int) -> Data? {
        var randomBytes = [UInt8](repeating: 0, count: numberOfBytes)
        let status = SecRandomCopyBytes(kSecRandomDefault, randomBytes.count, &randomBytes)

        if status != errSecSuccess { return nil }

        return Data(randomBytes)
    }

    /// Convert to hex encoded string
    public func hexEncodedString(addPrefix: Bool = false) -> String {
        let emptyResult = addPrefix ? "0x" : ""

        if isEmpty {
            return emptyResult
        }

        let startIndex = addPrefix ? 2 : 0
        let length = count * 2 + startIndex
        var bytes = [UInt8](repeating: 0, count: length)

        if addPrefix {
            bytes[0] = 48 // 0
            bytes[1] = 120 // x
        }

        var i = startIndex
        for value in self {
            let left = hexadecimalArray[Int(value / 16)]
            let right = hexadecimalArray[Int(value % 16)]
            bytes[i] = left
            bytes[i + 1] = right
            i += 2
        }

        guard let result = String(bytes: bytes, encoding: .utf8) else {
            return emptyResult
        }

        return result
    }

    /// Get a subset data from the current data using the given range
    ///
    /// - Parameter range: Range of sub data
    ///
    /// - Returns: Sub-data
    public func subdata(in range: ClosedRange<Index>) -> Data {
        return subdata(in: range.lowerBound ..< range.upperBound + 1)
    }
}
