if (typeof kotlin === 'undefined') {
  throw new Error("Error loading module 'bluetoothSerialJs'. Its dependency 'kotlin' was not found. Please, check whether 'kotlin' is loaded prior to 'bluetoothSerialJs'.");
}if (typeof this['CordovaAlternativePattern-SharedCode'] === 'undefined') {
  throw new Error("Error loading module 'bluetoothSerialJs'. Its dependency 'CordovaAlternativePattern-SharedCode' was not found. Please, check whether 'CordovaAlternativePattern-SharedCode' is loaded prior to 'bluetoothSerialJs'.");
}var bluetoothSerialJs = function (_, Kotlin, $module$CordovaAlternativePattern_SharedCode) {
  'use strict';
  var Action = $module$CordovaAlternativePattern_SharedCode.com.fivestars.cordovaalternativepattern.model.Action;
  var cordovaalternativepattern = $module$CordovaAlternativePattern_SharedCode.com.fivestars.cordovaalternativepattern;
  var to = Kotlin.kotlin.to_ujzrz7$;
  var mapOf = Kotlin.kotlin.collections.mapOf_x2b85n$;
  var JavascriptMessage = $module$CordovaAlternativePattern_SharedCode.model.JavascriptMessage;
  var Kind_OBJECT = Kotlin.Kind.OBJECT;
  var Unit = Kotlin.kotlin.Unit;
  var throwCCE = Kotlin.throwCCE;
  var toString = Kotlin.toString;
  function BluetoothSerial() {
    BluetoothSerial_instance = this;
  }
  BluetoothSerial.prototype.connect_ts1nhe$ = function (macAddress, success, failure) {
    var message = new JavascriptMessage(Action.CONNECT, mapOf(to(cordovaalternativepattern.KEY_MAC_ADDRESS, macAddress)));
    outputPort.postMessage(message);
  };
  BluetoothSerial.prototype.disconnect = function () {
    outputPort.postMessage(new JavascriptMessage(Action.DISCONNECT, null));
  };
  BluetoothSerial.prototype.send_caf5l0$ = function (data, success, failure) {
  };
  BluetoothSerial.prototype.listen_9dmrm4$ = function (success, failure) {
  };
  BluetoothSerial.prototype.getAddress_9dmrm4$ = function (success, failure) {
  };
  BluetoothSerial.prototype.registerOnDataCallback_o14v8n$ = function (success) {
  };
  BluetoothSerial.prototype.registerOnConnectCallback_o14v8n$ = function (success) {
  };
  BluetoothSerial.prototype.registerOnCloseCallback_o14v8n$ = function (success) {
  };
  BluetoothSerial.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'BluetoothSerial',
    interfaces: []
  };
  var BluetoothSerial_instance = null;
  function BluetoothSerial_getInstance() {
    if (BluetoothSerial_instance === null) {
      new BluetoothSerial();
    }return BluetoothSerial_instance;
  }
  var channel;
  var incomingPort;
  var outputPort;
  function configureChannel$lambda(it) {
    return Unit;
  }
  function configureChannel$lambda_0(it) {
    var tmp$, tmp$_0;
    tmp$_0 = toString((Kotlin.isType(tmp$ = it, MessageEvent) ? tmp$ : throwCCE()).data);
    window.alert(tmp$_0);
    return Unit;
  }
  function configureChannel$lambda_1(it) {
    var tmp$, tmp$_0;
    tmp$_0 = toString((Kotlin.isType(tmp$ = it, MessageEvent) ? tmp$ : throwCCE()).data);
    window.alert(tmp$_0);
    return Unit;
  }
  function configureChannel() {
    window.addEventListener('message', configureChannel$lambda, false);
    incomingPort.addEventListener('message', configureChannel$lambda_0, false);
    outputPort.addEventListener('message', configureChannel$lambda_1, false);
    incomingPort.start();
    outputPort.start();
  }
  function postAlert() {
    window.alert('yolo');
  }
  Object.defineProperty(_, 'BluetoothSerial', {
    get: BluetoothSerial_getInstance
  });
  Object.defineProperty(_, 'channel', {
    get: function () {
      return channel;
    }
  });
  Object.defineProperty(_, 'incomingPort', {
    get: function () {
      return incomingPort;
    },
    set: function (value) {
      incomingPort = value;
    }
  });
  Object.defineProperty(_, 'outputPort', {
    get: function () {
      return outputPort;
    },
    set: function (value) {
      outputPort = value;
    }
  });
  _.configureChannel = configureChannel;
  _.postAlert = postAlert;
  channel = new MessageChannel();
  incomingPort = channel.port1;
  outputPort = channel.port2;
  Kotlin.defineModule('bluetoothSerialJs', _);
  return _;
}(typeof bluetoothSerialJs === 'undefined' ? {} : bluetoothSerialJs, kotlin, this['CordovaAlternativePattern-SharedCode']);

//# sourceMappingURL=bluetoothSerialJs.js.map
