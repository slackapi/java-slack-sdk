package examples.oauth

import com.slack.api.bolt.App
import com.slack.api.bolt.AppConfig
import com.slack.api.bolt.ktor.respond
import com.slack.api.bolt.ktor.toBoltRequest
import com.slack.api.bolt.util.SlackRequestParser
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

val config: AppConfig = AppConfig.builder()
        .signingSecret(System.getenv(AppConfig.EnvVariableName.SLACK_SIGNING_SECRET))
        .clientId(System.getenv(AppConfig.EnvVariableName.SLACK_CLIENT_ID))
        .clientSecret(System.getenv(AppConfig.EnvVariableName.SLACK_CLIENT_SECRET))
        .scope(System.getenv(AppConfig.EnvVariableName.SLACK_SCOPES))
        .oauthInstallPath("/slack/install")
        .oauthRedirectUriPath("/slack/oauth_redirect")
        .oauthCompletionUrl("https://www.example.com/success")
        .oauthCancellationUrl("https://www.example.com/error")
        .build()


fun main() {
    val app = App(config).asOAuthApp(true)
    val requestParser = SlackRequestParser(app.config())

    app.command("/hello-ktor") { _, ctx ->
        ctx.ack("Hi!")
    }

    val server = embeddedServer(Netty, port = 3000) {
        routing {
            post("/slack/events") {
                val req = toBoltRequest(call, requestParser)
                val resp = app.run(req)
                respond(call, resp)
            }
            if (config.isOAuthInstallPathEnabled) {
                get(config.oauthInstallPath) {
                    val req = toBoltRequest(call, requestParser)
                    val resp = app.run(req)
                    respond(call, resp)
                }
            }
            if (config.isOAuthRedirectUriPathEnabled) {
                get(config.oauthRedirectUriPath) {
                    val req = toBoltRequest(call, requestParser)
                    val resp = app.run(req)
                    respond(call, resp)
                }
            }
        }
    }
    server.start(wait = true)
}
