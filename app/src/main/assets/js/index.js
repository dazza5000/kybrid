BluetoothSerialJs.com.fivestars.bluetooth.BluetoothSerial.configureChannel();

var app = {
    connect: function() {
        BluetoothSerialJs.com.fivestars.bluetooth.BluetoothSerial.connect(
            "18:21:95:5A:A3:80", // device to connect to
            function() {
                console.log("Success function in connect");
            }, // start listening if you succeed
            function() {
                console.log("Not success");
            } // show the error if you fail
        );
        },
                listen: function() {
                        BluetoothSerialJs.com.fivestars.bluetooth.BluetoothSerial.listen(function(data) {
                            console.log("This is the incoming data:" +data);
                        }, function() {
                                           console.log("Listen success");
                                       });
},

        registerCallbacks: function() {
                BluetoothSerialJs.com.fivestars.bluetooth.BluetoothSerial.registerOnDataCallback(function(data) {
                    console.log("This is the incoming data:" +data);
                });

//                BluetoothSerialJs.com.fivestars.bluetooth.BluetoothSerial.registerOnConnectCallback(function() {
//                    console.log("Connected");
//                });
//
//                BluetoothSerialJs.com.fivestars.bluetooth.BluetoothSerial.registerOnCloseCallback(function() {
//                    console.log("Disconnected");
//                });
            },

                send: function() {
                BluetoothSerialJs.com.fivestars.bluetooth.BluetoothSerial.postAlert()
                    BluetoothSerialJs.com.fivestars.bluetooth.BluetoothSerial.send("moops",
                        function() {
                            console.log("Success");
                        }, // start listening if you succeed
                        function() {
                            console.log("Not success");
                        } // show the error if you fail
                    );
                },

    }