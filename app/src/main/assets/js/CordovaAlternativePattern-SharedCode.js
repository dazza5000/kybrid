(function (root, factory) {
  if (typeof define === 'function' && define.amd)
    define(['exports', 'kotlin'], factory);
  else if (typeof exports === 'object')
    factory(module.exports, require('kotlin'));
  else {
    if (typeof kotlin === 'undefined') {
      throw new Error("Error loading module 'CordovaAlternativePattern-SharedCode'. Its dependency 'kotlin' was not found. Please, check whether 'kotlin' is loaded prior to 'CordovaAlternativePattern-SharedCode'.");
    }root['CordovaAlternativePattern-SharedCode'] = factory(typeof this['CordovaAlternativePattern-SharedCode'] === 'undefined' ? {} : this['CordovaAlternativePattern-SharedCode'], kotlin);
  }
}(this, function (_, Kotlin) {
  'use strict';
  var Enum = Kotlin.kotlin.Enum;
  var Kind_CLASS = Kotlin.Kind.CLASS;
  var throwISE = Kotlin.throwISE;
  Action.prototype = Object.create(Enum.prototype);
  Action.prototype.constructor = Action;
  function createApplicationScreenMessage() {
    return 'Kotlin Rocks on ' + platformName();
  }
  var KEY_MAC_ADDRESS;
  function Action(name, ordinal) {
    Enum.call(this);
    this.name$ = name;
    this.ordinal$ = ordinal;
  }
  function Action_initFields() {
    Action_initFields = function () {
    };
    Action$CONNECT_instance = new Action('CONNECT', 0);
    Action$DISCONNECT_instance = new Action('DISCONNECT', 1);
    Action$SEND_instance = new Action('SEND', 2);
    Action$LISTEN_instance = new Action('LISTEN', 3);
    Action$GET_ADDRESS_instance = new Action('GET_ADDRESS', 4);
    Action$REGISTER_DATA_CALLBACK_instance = new Action('REGISTER_DATA_CALLBACK', 5);
    Action$REGISTER_CONNECT_CALLBACK_instance = new Action('REGISTER_CONNECT_CALLBACK', 6);
    Action$REGISTER_CLOSE_CALLBACK_instance = new Action('REGISTER_CLOSE_CALLBACK', 7);
  }
  var Action$CONNECT_instance;
  function Action$CONNECT_getInstance() {
    Action_initFields();
    return Action$CONNECT_instance;
  }
  var Action$DISCONNECT_instance;
  function Action$DISCONNECT_getInstance() {
    Action_initFields();
    return Action$DISCONNECT_instance;
  }
  var Action$SEND_instance;
  function Action$SEND_getInstance() {
    Action_initFields();
    return Action$SEND_instance;
  }
  var Action$LISTEN_instance;
  function Action$LISTEN_getInstance() {
    Action_initFields();
    return Action$LISTEN_instance;
  }
  var Action$GET_ADDRESS_instance;
  function Action$GET_ADDRESS_getInstance() {
    Action_initFields();
    return Action$GET_ADDRESS_instance;
  }
  var Action$REGISTER_DATA_CALLBACK_instance;
  function Action$REGISTER_DATA_CALLBACK_getInstance() {
    Action_initFields();
    return Action$REGISTER_DATA_CALLBACK_instance;
  }
  var Action$REGISTER_CONNECT_CALLBACK_instance;
  function Action$REGISTER_CONNECT_CALLBACK_getInstance() {
    Action_initFields();
    return Action$REGISTER_CONNECT_CALLBACK_instance;
  }
  var Action$REGISTER_CLOSE_CALLBACK_instance;
  function Action$REGISTER_CLOSE_CALLBACK_getInstance() {
    Action_initFields();
    return Action$REGISTER_CLOSE_CALLBACK_instance;
  }
  Action.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Action',
    interfaces: [Enum]
  };
  function Action$values() {
    return [Action$CONNECT_getInstance(), Action$DISCONNECT_getInstance(), Action$SEND_getInstance(), Action$LISTEN_getInstance(), Action$GET_ADDRESS_getInstance(), Action$REGISTER_DATA_CALLBACK_getInstance(), Action$REGISTER_CONNECT_CALLBACK_getInstance(), Action$REGISTER_CLOSE_CALLBACK_getInstance()];
  }
  Action.values = Action$values;
  function Action$valueOf(name) {
    switch (name) {
      case 'CONNECT':
        return Action$CONNECT_getInstance();
      case 'DISCONNECT':
        return Action$DISCONNECT_getInstance();
      case 'SEND':
        return Action$SEND_getInstance();
      case 'LISTEN':
        return Action$LISTEN_getInstance();
      case 'GET_ADDRESS':
        return Action$GET_ADDRESS_getInstance();
      case 'REGISTER_DATA_CALLBACK':
        return Action$REGISTER_DATA_CALLBACK_getInstance();
      case 'REGISTER_CONNECT_CALLBACK':
        return Action$REGISTER_CONNECT_CALLBACK_getInstance();
      case 'REGISTER_CLOSE_CALLBACK':
        return Action$REGISTER_CLOSE_CALLBACK_getInstance();
      default:throwISE('No enum constant com.fivestars.cordovaalternativepattern.model.Action.' + name);
    }
  }
  Action.valueOf_61zpoe$ = Action$valueOf;
  function JavascriptMessage(action, data) {
    this.action = action;
    this.data = data;
  }
  JavascriptMessage.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'JavascriptMessage',
    interfaces: []
  };
  JavascriptMessage.prototype.component1 = function () {
    return this.action;
  };
  JavascriptMessage.prototype.component2 = function () {
    return this.data;
  };
  JavascriptMessage.prototype.copy_6uonil$ = function (action, data) {
    return new JavascriptMessage(action === void 0 ? this.action : action, data === void 0 ? this.data : data);
  };
  JavascriptMessage.prototype.toString = function () {
    return 'JavascriptMessage(action=' + Kotlin.toString(this.action) + (', data=' + Kotlin.toString(this.data)) + ')';
  };
  JavascriptMessage.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.action) | 0;
    result = result * 31 + Kotlin.hashCode(this.data) | 0;
    return result;
  };
  JavascriptMessage.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.action, other.action) && Kotlin.equals(this.data, other.data)))));
  };
  function MacAddress(macAddress) {
    this.macAddress = macAddress;
  }
  MacAddress.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'MacAddress',
    interfaces: []
  };
  MacAddress.prototype.component1 = function () {
    return this.macAddress;
  };
  MacAddress.prototype.copy_glch7p$ = function (macAddress) {
    return new MacAddress(macAddress === void 0 ? this.macAddress : macAddress);
  };
  MacAddress.prototype.toString = function () {
    return 'MacAddress(macAddress=' + Kotlin.toString(this.macAddress) + ')';
  };
  MacAddress.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.macAddress) | 0;
    return result;
  };
  MacAddress.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && Kotlin.equals(this.macAddress, other.macAddress))));
  };
  function platformName() {
    return 'JS';
  }
  var package$com = _.com || (_.com = {});
  var package$fivestars = package$com.fivestars || (package$com.fivestars = {});
  var package$cordovaalternativepattern = package$fivestars.cordovaalternativepattern || (package$fivestars.cordovaalternativepattern = {});
  package$cordovaalternativepattern.createApplicationScreenMessage = createApplicationScreenMessage;
  Object.defineProperty(package$cordovaalternativepattern, 'KEY_MAC_ADDRESS', {
    get: function () {
      return KEY_MAC_ADDRESS;
    }
  });
  Object.defineProperty(Action, 'CONNECT', {
    get: Action$CONNECT_getInstance
  });
  Object.defineProperty(Action, 'DISCONNECT', {
    get: Action$DISCONNECT_getInstance
  });
  Object.defineProperty(Action, 'SEND', {
    get: Action$SEND_getInstance
  });
  Object.defineProperty(Action, 'LISTEN', {
    get: Action$LISTEN_getInstance
  });
  Object.defineProperty(Action, 'GET_ADDRESS', {
    get: Action$GET_ADDRESS_getInstance
  });
  Object.defineProperty(Action, 'REGISTER_DATA_CALLBACK', {
    get: Action$REGISTER_DATA_CALLBACK_getInstance
  });
  Object.defineProperty(Action, 'REGISTER_CONNECT_CALLBACK', {
    get: Action$REGISTER_CONNECT_CALLBACK_getInstance
  });
  Object.defineProperty(Action, 'REGISTER_CLOSE_CALLBACK', {
    get: Action$REGISTER_CLOSE_CALLBACK_getInstance
  });
  var package$model = package$cordovaalternativepattern.model || (package$cordovaalternativepattern.model = {});
  package$model.Action = Action;
  var package$model_0 = _.model || (_.model = {});
  package$model_0.JavascriptMessage = JavascriptMessage;
  package$model.MacAddress = MacAddress;
  package$cordovaalternativepattern.platformName = platformName;
  KEY_MAC_ADDRESS = 'macAddress';
  Kotlin.defineModule('CordovaAlternativePattern-SharedCode', _);
  return _;
}));

//# sourceMappingURL=CordovaAlternativePattern-SharedCode.js.map
