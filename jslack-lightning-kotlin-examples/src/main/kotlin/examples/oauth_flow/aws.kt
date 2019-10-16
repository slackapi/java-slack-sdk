package examples.oauth_flow

import com.github.seratch.jslack.lightning.App
import com.github.seratch.jslack.lightning.jetty.SlackAppServer
import com.github.seratch.jslack.lightning.service.builtin.AmazonS3InstallationService
import com.github.seratch.jslack.lightning.service.builtin.AmazonS3OAuthStateService
import org.slf4j.LoggerFactory
import util.ResourceLoader

fun main() {

    val logger = LoggerFactory.getLogger("main")

    // export SLACK_BOT_TOKEN=xoxb-***
    // export SLACK_SIGNING_SECRET=123abc***
    val config = ResourceLoader.loadAppConfig()
    val mainApp = App(config)

    // export AWS_REGION=us-east-1
    // export AWS_ACCESS_KEY_ID=AAAA*************
    // export AWS_SECRET_ACCESS_KEY=4o7***********************
    val awsS3BucketName = "YOUR_OWN_BUCKET_NAME_HERE"
    val installationService = AmazonS3InstallationService(awsS3BucketName)
    installationService.setHistoricalDataEnabled(true)
    mainApp.service(installationService)

    mainApp.command("/say-something") { req, ctx ->
        val p = req.payload
        val text = "<@${p.userId}> said ${p.text} at <#${p.channelId}|${p.channelName}>"
        val res = ctx.respond { it.text(text).responseType("in_channel") }
        logger.info("respond result - {}", res)
        ctx.ack()
    }

    val oauthConfig = ResourceLoader.loadAppConfig()
    val oauthApp = App(oauthConfig).asOAuthApp(true)

    oauthApp.service(installationService)
    // for state parameter in OAuth flow
    oauthApp.service(AmazonS3OAuthStateService(awsS3BucketName))

    val server = SlackAppServer(mapOf(
            "/slack/events" to mainApp,
            "/slack/oauth" to oauthApp
    ))
    server.start()
}