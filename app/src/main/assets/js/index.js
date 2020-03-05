//const channel = new MessageChannel();
//var nativeJsPortOne = channel.port1;
//var nativeJsPortTwo = channel.port2;
//window.addEventListener('message', function(event) {
//    if (event.data != 'capturePort') {
//        nativeJsPortOne.postMessage(event.data)
//    } else if (event.data == 'capturePort') {
//        /* The following three lines form Android class 'WebViewCallBackDemo' capture the port and assign it to nativeJsPortTwo
//        var destPort = arrayOf(nativeToJsPorts[1])
//        nativeToJsPorts[0].setWebMessageCallback(nativeToJs!!)
//        WebViewCompat.postWebMessage(webView, WebMessageCompat("capturePort", destPort), Uri.EMPTY) */
//        if (event.ports[0] != null) {
//            nativeJsPortTwo = event.ports[0]
//        }
//    }
//}, false);
//
//nativeJsPortOne.addEventListener('message', function(event) {
//    alert(event.data + " port one");
//}, false);
//
//// This is the port that incoming message arrive on
//nativeJsPortTwo.addEventListener('message', function(event) {
//    alert(event.data + " port two");
//}, false);
//nativeJsPortOne.start();
//nativeJsPortTwo.start();

BluetoothSerialJs.configureChannel();

var app = {
    connect: function() {
//    app.registerCallbacks();
        BluetoothSerialJs.BluetoothSerial.connect(
            "18:21:95:5A:A3:80", // device to connect to
            function() {
                console.log("Success");
                app.clear();
                app.display("Connection in progress");
            }, // start listening if you succeed
            function() {
                console.log("Not success");
            } // show the error if you fail
        );
    },
        registerCallbacks: function() {
                                        bluetoothSerial.registerOnDataCallback(function(data) {
                                            app.display(data);
                                        });

                                                bluetoothSerial.registerOnConnectCallback(function() {
                                                    console.log("Connected");
                                                    app.clear();
                                                    app.display("Connected to device");
                                                });

                                                bluetoothSerial.registerOnCloseCallback(function() {
                                                    console.log("Disconnected");
                                                    app.clear();
                                                    app.display("Disconnected from device");
                                                });
                                        },

}