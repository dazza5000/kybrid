{
  mode: 'production',
  resolve: {
    modules: [
      '/Users/fivestars.user/source/fivestars/cordova-alternative-pattern/build/js/packages/CordovaAlternativePattern-bluetoothSerialJs/kotlin-dce',
      'node_modules'
    ]
  },
  plugins: [],
  module: {
    rules: [
      {
        test: /\.js$/,
        use: [
          'kotlin-source-map-loader'
        ],
        enforce: 'pre'
      }
    ]
  },
  entry: {
    main: [
      '/Users/fivestars.user/source/fivestars/cordova-alternative-pattern/build/js/packages/CordovaAlternativePattern-bluetoothSerialJs/kotlin-dce/CordovaAlternativePattern-bluetoothSerialJs.js'
    ]
  },
  output: {
    path: '/Users/fivestars.user/source/fivestars/cordova-alternative-pattern/bluetoothSerialJs/build/distributions',
    filename: [Function: filename],
    library: 'bluetoothSerialJs',
    libraryTarget: 'umd'
  },
  devtool: 'source-map'
}