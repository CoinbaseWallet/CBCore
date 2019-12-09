// swift-tools-version:5.1
// The swift-tools-version declares the minimum version of Swift required to build this package.

import PackageDescription

let package = Package(
    name: "CBCore",
    products: [
        // Products define the executables and libraries produced by a package, and make them visible to other packages.
        .library(name: "CBCore", targets: ["CBCore"]),
    ],
    dependencies: [
        .package(url: "https://github.com/attaswift/BigInt.git", from: "3.1.0"),
        .package(url: "https://github.com/ReactiveX/RxSwift.git", from: "4.4.0")
    ],
    targets: [
        // Targets are the basic building blocks of a package. A target can define a module or a test suite.
        // Targets can depend on other targets in this package, and on products in packages which this package depends on.
        .target(name: "CBCore", dependencies: ["RxSwift", "BigInt"], path: "./ios/Source"),
        .testTarget(name: "CBCoreTests",dependencies: ["CBCore", "RxBlocking"], path: "./ios/Tests"),
    ]
)
