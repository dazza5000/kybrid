const channel = new MessageChannel();
var nativeJsPortOne = channel.port1;
var nativeJsPortTwo = channel.port2;
window.addEventListener('message', function(event) {
    if (event.data != 'capturePort') {
        nativeJsPortOne.postMessage(event.data)
    } else if (event.data == 'capturePort') {
        /* The following three lines form Android class 'WebViewCallBackDemo' capture the port and assign it to nativeJsPortTwo
        var destPort = arrayOf(nativeToJsPorts[1])
        nativeToJsPorts[0].setWebMessageCallback(nativeToJs!!)
        WebViewCompat.postWebMessage(webView, WebMessageCompat("capturePort", destPort), Uri.EMPTY) */
        if (event.ports[0] != null) {
            nativeJsPortTwo = event.ports[0]
        }
    }
}, false);

nativeJsPortOne.addEventListener('message', function(event) {
    alert(event.data);
}, false);

nativeJsPortTwo.addEventListener('message', function(event) {
    alert(event.data);
}, false);
nativeJsPortOne.start();
nativeJsPortTwo.start();