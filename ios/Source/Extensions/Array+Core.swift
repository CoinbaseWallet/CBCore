// Copyright (c) 2017-2019 Coinbase Inc. See LICENSE

import RxSwift

extension Array {
    /// Helper to determine whether array is not empty
    public var isNotEmpty: Bool {
        return !isEmpty
    }

    /// Split current array into an array of arrays based on given size
    ///
    /// - Parameters:
    ///     - size: Size of chunked array
    ///
    /// - Return: An array containing group of array
    public func chunked(into size: Int) -> [[Element]] {
        return stride(from: 0, to: count, by: size).map { Array(self[$0 ..< Swift.min($0 + size, count)]) }
    }

    /// Helper to zip array of Singles
    public func zip<T>() -> Single<[T]> where Element == Single<T> {
        return Single.zip(self)
    }

    /// Helper to zip array of Observables
    public func zip<T>() -> Observable<[T]> where Element == Observable<T> {
        return Observable.zip(self)
    }

    /// Helper to combine latest array of Observables
    public func combineLatest<T>() -> Observable<[T]> where Element == Observable<T> {
        return Observable.combineLatest(self)
    }
}

extension Array where Element: Hashable {
    /// Returns a uniqued array
    public var uniqued: [Element] {
        let set = Set<Element>(self)

        return [Element](set)
    }
}

extension Array: JSONSerializable where Element: Codable & JSONSerializable {}
