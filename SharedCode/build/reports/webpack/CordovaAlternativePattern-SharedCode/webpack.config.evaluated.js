{
  mode: 'production',
  resolve: {
    modules: [
      '/rom/source/fivestars/CordovaAlternativePattern/build/js/packages/CordovaAlternativePattern-SharedCode/kotlin-dce',
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
      '/rom/source/fivestars/CordovaAlternativePattern/build/js/packages/CordovaAlternativePattern-SharedCode/kotlin-dce/CordovaAlternativePattern-SharedCode.js'
    ]
  },
  output: {
    path: '/rom/source/fivestars/CordovaAlternativePattern/SharedCode/build/distributions',
    filename: [Function: filename],
    library: 'SharedCode',
    libraryTarget: 'umd'
  },
  devtool: 'source-map'
}