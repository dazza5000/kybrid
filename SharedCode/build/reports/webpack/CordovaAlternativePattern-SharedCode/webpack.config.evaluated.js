{
  mode: 'development',
  resolve: {
    modules: [
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
      '/rom/source/fivestars/CordovaAlternativePattern/build/js/packages/CordovaAlternativePattern-SharedCode/kotlin/CordovaAlternativePattern-SharedCode.js'
    ]
  },
  output: {
    path: '/rom/source/fivestars/CordovaAlternativePattern/SharedCode/build/distributions',
    filename: [Function: filename],
    library: 'SharedCode',
    libraryTarget: 'umd'
  },
  devtool: 'eval-source-map'
}