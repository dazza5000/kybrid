{
  mode: 'production',
  resolve: {
    modules: [
      '/rom/source/fivestars/CordovaAlternativePattern/build/js/packages/CordovaAlternativePattern-js/kotlin-dce',
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
      '/rom/source/fivestars/CordovaAlternativePattern/build/js/packages/CordovaAlternativePattern-js/kotlin-dce/CordovaAlternativePattern-js.js'
    ]
  },
  output: {
    path: '/rom/source/fivestars/CordovaAlternativePattern/js/build/distributions',
    filename: [Function: filename],
    library: 'js',
    libraryTarget: 'umd'
  },
  devtool: 'source-map'
}