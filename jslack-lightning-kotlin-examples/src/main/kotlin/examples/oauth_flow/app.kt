package examples.oauth_flow

import com.github.seratch.jslack.lightning.App
import com.github.seratch.jslack.lightning.jetty.SlackAppServer
import org.slf4j.LoggerFactory
import util.ResourceLoader

fun main() {

    val logger = LoggerFactory.getLogger("main")

    // export SLACK_BOT_TOKEN=xoxb-***
    // export SLACK_SIGNING_SECRET=123abc***
    val config = ResourceLoader.loadAppConfig()
    config.isAlwaysRequestUserTokenNeeded = true
    val mainApp = App(config)

    mainApp.command("/say-something") { req, ctx ->
        val p = req.payload
        val text = "<@${p.userId}> said ${p.text} at <#${p.channelId}|${p.channelName}>"
        val res = ctx.respond { it.text(text).responseType("in_channel") }
        logger.info("respond result - {}", res)
        ctx.ack()
    }

    val oauthConfig = ResourceLoader.loadAppConfig()
    val oauthApp = App(oauthConfig).asOAuthApp(true)

    val server = SlackAppServer(mapOf(
            "/slack/events" to mainApp,
            "/slack/oauth" to oauthApp
    ))
    server.start()
}