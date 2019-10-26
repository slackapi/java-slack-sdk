package examples.oauth_flow

import com.github.seratch.jslack.lightning.App
import com.github.seratch.jslack.lightning.jetty.SlackAppServer
import org.slf4j.LoggerFactory
import util.ResourceLoader

fun main() {

    val logger = LoggerFactory.getLogger("main")

    // export SLACK_SIGNING_SECRET=123abc***
    // export SLACK_APP_CLIENT_ID=12345.12345
    // export SLACK_APP_CLIENT_SECRET=123abc***
    // export SLACK_APP_REDIRECT_URI=https://your-domain.ngrok.io/slack/oauth/callback
    // export SLACK_APP_SCOPE=commands,bot
    // export SLACK_APP_OAUTH_START_PATH=/start
    // export SLACK_APP_OAUTH_CALLBACK_PATH=/callback
    // export SLACK_APP_OAUTH_CANCELLATION_URL=https://your-domain.ngrok.io/slack/oauth/completion
    // export SLACK_APP_OAUTH_COMPLETION_URL=https://your-domain.io/slack/oauth/cancellation
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