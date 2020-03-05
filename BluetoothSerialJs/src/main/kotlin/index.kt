import com.fivestars.cordovaalternativepattern.KEY_CAPTURE_PORT
import org.w3c.dom.MessageChannel
import org.w3c.dom.MessageEvent
import kotlin.browser.window

val channel = MessageChannel()
var inputPort = channel.port1
var outputPort = channel.port2

fun configureChannel() {
    console.log("Configuring channel")
    window.addEventListener("message", {

        console.log("Got event; $it")

        val event = it as MessageEvent

        if (event.data != KEY_CAPTURE_PORT) {
            console.log("event.data: ${event.data}")
            inputPort.postMessage(event.data)
        } else if (event.data == KEY_CAPTURE_PORT) {
            console.log("assigning captured port")
            outputPort = event.ports[0]
        }
    }, false)

    inputPort.start()
    outputPort.start()
}

fun postAlert() {
    window.alert("yolo")
}