Kybrid is an attempt at creating a Hybrid Framework utilizing Kotlin Multiplatform

The goals are:

Provide type-safety and consistent types between native and javascript implementations

Make framework adoption dirt simple

Reduce the amount of time writing bridge code

The components are:

Native: This provides the core native plugin components and 
setting up the bridge between native and javascript

Kybrid: This provides the core javascript components 

DeviceInfoPlugin:

This is a sample plugin that provides access to Native Device Information

androidsample: This is a sample android implementation of the kybrid framework that utilizes the DeviceInfoPlugin

