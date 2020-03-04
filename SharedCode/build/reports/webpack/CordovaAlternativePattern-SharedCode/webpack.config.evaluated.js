{
  mode: 'production',
  resolve: {
    modules: [
      '/Users/fivestars.user/source/fivestars/cordova-alternative-pattern/build/js/packages/CordovaAlternativePattern-SharedCode/kotlin-dce',
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
      '/Users/fivestars.user/source/fivestars/cordova-alternative-pattern/build/js/packages/CordovaAlternativePattern-SharedCode/kotlin-dce/CordovaAlternativePattern-SharedCode.js'
    ]
  },
  output: {
    path: '/Users/fivestars.user/source/fivestars/cordova-alternative-pattern/SharedCode/build/distributions',
    filename: [Function: filename],
    library: 'SharedCode',
    libraryTarget: 'umd'
  },
  devtool: 'source-map'
}