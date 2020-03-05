BluetoothSerialJs.configureChannel();

var app = {
    connect: function() {
        BluetoothSerialJs.BluetoothSerial.connect(
            "18:21:95:5A:A3:80", // device to connect to
            function() {
                console.log("Success function in connect");
            }, // start listening if you succeed
            function() {
                console.log("Not success");
            } // show the error if you fail
        );
        },

        registerCallbacks: function() {
                BluetoothSerialJs.BluetoothSerial.registerOnDataCallback(function(data) {
                    console.log("This is the incoming data:" +data);
                });

                BluetoothSerialJs.BluetoothSerial.registerOnConnectCallback(function() {
                    console.log("Connected");
                });

                BluetoothSerialJs.BluetoothSerial.registerOnCloseCallback(function() {
                    console.log("Disconnected");
                });
            },

                send: function() {
                    BluetoothSerialJs.BluetoothSerial.send("moops",
                        function() {
                            console.log("Success");
                        }, // start listening if you succeed
                        function() {
                            console.log("Not success");
                        } // show the error if you fail
                    );
                },

    }