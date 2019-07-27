Pod::Spec.new do |s|
  s.name             = 'CBCore'
  s.version          = '0.1.0'
  s.summary          = 'Shared code for all Wallet modules'
  s.description      = 'Shared code for all Wallet modules. Developed by Coinbase Wallet team.'

  s.homepage         = 'https://github.com/CoinbaseWallet/CBCore'
  s.license          = { :type => "AGPL-3.0-only", :file => 'LICENSE' }
  s.author           = { 'Coinbase' => 'developer@toshi.org' }
  s.source           = { :git => 'https://github.com/CoinbaseWallet/CBCore.git', :tag => s.version.to_s }
  s.social_media_url = 'https://twitter.com/coinbase'

  s.ios.deployment_target = '11.0'
  s.swift_version = '4.2'
  s.source_files = 'ios/Source/**/*'

  s.dependency 'RxSwift', '~> 4.4.0'
  s.dependency 'BigInt',  '~> 3.1.0'
end
