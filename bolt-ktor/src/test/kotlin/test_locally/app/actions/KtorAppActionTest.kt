package test_locally.app.actions

import com.slack.api.Slack
import com.slack.api.SlackConfig
import com.slack.api.app_backend.SlackSignature
import com.slack.api.bolt.App
import com.slack.api.bolt.AppConfig
import com.slack.api.bolt.ktor.respond
import com.slack.api.bolt.ktor.toBoltRequest
import com.slack.api.bolt.util.SlackRequestParser
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.testing.*
import org.junit.After
import org.junit.Before
import util.AuthTestMockServer
import java.net.URLEncoder
import kotlin.test.Test
import kotlin.test.assertEquals

class KtorAppActionTest {

    private val signingSecret = "secret"
    private val authTestMockServer = AuthTestMockServer()
    private val slackConfig = SlackConfig()

    private fun Application.main() {
        val appConfig = AppConfig.builder()
            .slack(Slack.getInstance(slackConfig))
            .signingSecret(signingSecret)
            .singleTeamBotToken(AuthTestMockServer.ValidToken)
            .build()
        val app = App(appConfig)

        app.blockAction("this-is-the-action") { _, ctx ->
            ctx.ack()
        }

        val requestParser = SlackRequestParser(app.config())
        routing {
            post("/slack/events") {
                respond(call, app.run(toBoltRequest(call, requestParser)))
            }
        }
    }

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
    fun invalidRequest() = testApplication() {
        application { main() }
        val response = client.post("/slack/events")
        assertEquals(HttpStatusCode.BadRequest, response.status)
        assertEquals("Invalid Request", response.bodyAsText())
    }

    @Test
    fun validRequest() = testApplication {
        application { main() }
        println(this.toString())
        val body = "payload=${URLEncoder.encode(buttonClickPayload, "UTF-8")}"
        val timestamp = (System.currentTimeMillis() / 1000).toString()
        val signature = SlackSignature.Generator(signingSecret).generate(timestamp, body)
        val response = client.post("/slack/events"){
            headers {
                append(SlackSignature.HeaderNames.X_SLACK_REQUEST_TIMESTAMP, timestamp)
                append(SlackSignature.HeaderNames.X_SLACK_SIGNATURE, signature)
                append("Content-type", "application/x-www-form-urlencoded")
            }
            setBody(body)
        }
        assertEquals(HttpStatusCode.OK, response.status)
    }


    @Test
    fun invalidSignature() = testApplication {
        application { main() }
        val body = "payload=${URLEncoder.encode(buttonClickPayload, "UTF-8")}"
        val timestamp = (System.currentTimeMillis() / 1000).toString()
        val signature = SlackSignature.Generator("yet-another-signature").generate(timestamp, body)
        val response = client.post("/slack/events"){
            headers {
                append(SlackSignature.HeaderNames.X_SLACK_REQUEST_TIMESTAMP, timestamp)
                append(SlackSignature.HeaderNames.X_SLACK_SIGNATURE, signature)
                append("Content-type", "application/x-www-form-urlencoded")
            }
            setBody(body)
        }
        assertEquals(HttpStatusCode.Unauthorized, response.status)
        assertEquals("""{"error":"invalid request"}""", response.bodyAsText())
    }

    private val buttonClickPayload = """
        |{
        |  "type": "block_actions",
        |  "user": {
        |    "id": "WJC6QG0MS",
        |    "username": "ksera",
        |    "name": "ksera",
        |    "team_id": "T5J4Q04QG"
        |  },
        |  "api_app_id": "A02",
        |  "token": "Shh_its_a_seekrit",
        |  "container": {
        |    "type": "message",
        |    "text": "The contents of the original message where the action originated"
        |  },
        |  "trigger_id": "12466734323.1395872398",
        |  "team": {
        |    "id": "T5J4Q04QG",
        |    "domain": "slack-pde",
        |    "enterprise_id": "E12KS1G65",
        |    "enterprise_name": "Slack Corp"
        |  },
        |  "enterprise": {
        |    "id": "E12KS1G65",
        |    "name": "Slack Corp"
        |  },
        |  "is_enterprise_install": false,
        |  "state": {
        |    "values": {}
        |  },
        |  "response_url": "https://www.postresponsestome.com/T123567/1509734234",
        |  "actions": [
        |    {
        |      "type": "button",
        |      "block_id": "khGE",
        |      "action_id": "this-is-the-action",
        |      "text": {
        |        "type": "plain_text",
        |        "text": "Click Me",
        |        "emoji": true
        |      },
        |      "value": "click_me_123",
        |      "action_ts": "1602752109.476767"
        |    }
        |  ]
        |}""".trimMargin("|")
}
