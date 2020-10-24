package test_locally.app.events

import com.slack.api.Slack
import com.slack.api.SlackConfig
import com.slack.api.app_backend.SlackSignature
import com.slack.api.bolt.App
import com.slack.api.bolt.AppConfig
import com.slack.api.bolt.ktor.respond
import com.slack.api.bolt.ktor.toBoltRequest
import com.slack.api.bolt.util.SlackRequestParser
import com.slack.api.model.event.AppMentionEvent
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.routing.*
import io.ktor.server.testing.*
import org.junit.After
import org.junit.Before
import util.AuthTestMockServer
import kotlin.test.Test
import kotlin.test.assertEquals

const val signingSecret = "secret"
val authTestMockServer = AuthTestMockServer()
val slackConfig = SlackConfig()

fun Application.main() {
    val appConfig = AppConfig.builder()
            .slack(Slack.getInstance(slackConfig))
            .signingSecret(signingSecret)
            .singleTeamBotToken(AuthTestMockServer.ValidToken)
            .build()
    val app = App(appConfig)

    app.event(AppMentionEvent::class.java) { _, ctx ->
        ctx.ack()
    }

    val requestParser = SlackRequestParser(app.config())
    routing {
        post("/slack/events") {
            respond(call, app.run(toBoltRequest(call, requestParser)))
        }
    }
}

class KtorAppTest {

    @Before
    fun setUp() {
        authTestMockServer.start()
        slackConfig.methodsEndpointUrlPrefix = authTestMockServer.methodsEndpointPrefix
    }

    @After
    fun tearDown() {
        authTestMockServer.stop()
    }

    @Test
    fun invalidRequest() = withTestApplication(Application::main) {
        with(handleRequest(HttpMethod.Post, "/slack/events")) {
            assertEquals(HttpStatusCode.BadRequest, response.status())
            assertEquals("Invalid Request", response.content)
        }
    }

    @Test
    fun validRequest() = withTestApplication(Application::main) {
        val timestamp = (System.currentTimeMillis() / 1000).toString()
        val signature = SlackSignature.Generator(signingSecret).generate(timestamp, appMentionEventPayload)
        val req = handleRequest(HttpMethod.Post, "/slack/events") {
            addHeader(SlackSignature.HeaderNames.X_SLACK_REQUEST_TIMESTAMP, timestamp)
            addHeader(SlackSignature.HeaderNames.X_SLACK_SIGNATURE, signature)
            addHeader("Content-type", "application/json")
            setBody(appMentionEventPayload)

        }
        with(req) {
            assertEquals(HttpStatusCode.OK, response.status())
        }
    }

    @Test
    fun invalidSignature() = withTestApplication(Application::main) {
        val timestamp = (System.currentTimeMillis() / 1000).toString()
        val signature = SlackSignature.Generator("yet-another-signature").generate(timestamp, appMentionEventPayload)
        val req = handleRequest(HttpMethod.Post, "/slack/events") {
            addHeader(SlackSignature.HeaderNames.X_SLACK_REQUEST_TIMESTAMP, timestamp)
            addHeader(SlackSignature.HeaderNames.X_SLACK_SIGNATURE, signature)
            addHeader("Content-type", "application/json")
            setBody(appMentionEventPayload)

        }
        with(req) {
            assertEquals(HttpStatusCode.Unauthorized, response.status())
            assertEquals("""{"error":"invalid request"}""", response.content)
        }
    }
}

const val appMentionEventPayload = """
{
  "token": "verification-token",
  "team_id": "T111",
  "enterprise_id": "E111",
  "api_app_id": "A111",
  "event": {
    "bot_id": "B111",
    "type": "app_mention",
    "text": "\u003c@W111\u003e Hello!",
    "user": "W222",
    "ts": "1601584748.000800",
    "team": "T111",
    "bot_profile": {
      "id": "B111",
      "deleted": false,
      "name": "test-app",
      "updated": 1589780796,
      "app_id": "A111",
      "icons": {
        "image_36": "https://a.slack-edge.com/80588/img/plugins/app/bot_36.png",
        "image_48": "https://a.slack-edge.com/80588/img/plugins/app/bot_48.png",
        "image_72": "https://a.slack-edge.com/80588/img/plugins/app/service_72.png"
      },
      "team_id": "T111"
    },
    "channel": "C111",
    "event_ts": "1601584748.000800"
  },
  "type": "event_callback",
  "event_id": "Ev111",
  "event_time": 1601584748,
  "authorizations": [
    {
      "enterprise_id": "E111",
      "team_id": "T111",
      "user_id": "W111",
      "is_bot": true,
      "is_enterprise_install": false
    }
  ],
  "is_ext_shared_channel": false,
  "event_context": "1-app_mention-T111-C111"
} 
"""