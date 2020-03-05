BluetoothSerialJs.configureChannel();

var app = {
    connect: function() {
        //app.registerCallbacks();
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
                    app.display(data);
                });

                BluetoothSerialJs.BluetoothSerial.registerOnConnectCallback(function() {
                    console.log("Connected");
                    app.clear();
                    app.display("Connected to device");
                });

                BluetoothSerialJs.BluetoothSerial.registerOnCloseCallback(function() {
                    console.log("Disconnected");
                    app.clear();
                    app.display("Disconnected from device");
                });
            },

                send: function() {
                    BluetoothSerialJs.BluetoothSerial.send(new Uint8Array("moops").buffer,
                        function() {
                            console.log("Success");
                            app.clear();
                            app.display("sent message");
                        }, // start listening if you succeed
                        function() {
                            console.log("Not success");
                        } // show the error if you fail
                    );
                },

    }

    var stringToArrayBuffer = function(str) {
        var ret = new Uint8Array(str.length);
        for (var i = 0; i < str.length; i++) {
            ret[i] = str.charCodeAt(i);
        }
        return ret.buffer;
    };