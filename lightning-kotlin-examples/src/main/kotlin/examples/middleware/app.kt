package examples.middleware

import com.github.seratch.jslack.lightning.App
import com.github.seratch.jslack.lightning.jetty.SlackAppServer
import org.slf4j.LoggerFactory

fun main() {

    val logger = LoggerFactory.getLogger("main")

    // export SLACK_BOT_TOKEN=xoxb-***
    // export SLACK_SIGNING_SECRET=123abc***
    val app = App()

    app.use { req, _, chain ->
        logger.info("Request - $req")
        val resp = chain.next(req)
        logger.info("Response - $resp")
        resp
    }

    val server = SlackAppServer(app)
    server.start()
}