package examples.middleware

import com.slack.api.bolt.App
import com.slack.api.bolt.jetty.SlackAppServer

fun main() {

    // export SLACK_BOT_TOKEN=xoxb-***
    // export SLACK_SIGNING_SECRET=123abc***
    val app = App()

    app.use { req, _, chain ->
        val logger = req.context.logger
        logger.info("Request - $req")
        val resp = chain.next(req)
        logger.info("Response - $resp")
        resp
    }

    val server = SlackAppServer(app)
    server.start()
}