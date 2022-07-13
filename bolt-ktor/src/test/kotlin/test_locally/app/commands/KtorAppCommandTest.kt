package test_locally.app.commands

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
import io.ktor.server.application.*
import io.ktor.http.*
import io.ktor.server.routing.*
import io.ktor.server.testing.*
import org.junit.After
import org.junit.Before
import util.AuthTestMockServer
import kotlin.test.Test
import kotlin.test.assertEquals

class KtorAppCommandTest {

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

        app.command("/weather") { _, ctx ->
            ctx.ack("This a clear sunny day!")
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
    fun invalidRequest() = testApplication {
        application { main() }
        val response = client.post("/slack/events")
        assertEquals(HttpStatusCode.BadRequest, response.status)
        assertEquals("Invalid Request", response.bodyAsText())
    }

    @Test
    fun validRequest() = testApplication {
        application { main() }
        val timestamp = (System.currentTimeMillis() / 1000).toString()
        val signature = SlackSignature.Generator(signingSecret).generate(timestamp, weatherCommandPayload)
        val response = client.post("/slack/events"){
            headers {
                append(SlackSignature.HeaderNames.X_SLACK_REQUEST_TIMESTAMP, timestamp)
                append(SlackSignature.HeaderNames.X_SLACK_SIGNATURE, signature)
                append("Content-type", "application/x-www-form-urlencoded")
            }
            setBody(weatherCommandPayload)
        }
        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals("""{"text":"This a clear sunny day!"}""", response.bodyAsText())
    }

    @Test
    fun commandNotFound() = testApplication {
        application { main() }
        val unknownCommandPayload = "&team_id=T0001" +
                "&channel_id=C2147483705" +
                "&user_id=U2147483697" +
                "&command=/unknown-command" +
                "&text=is available?" +
                "&response_url=https://hooks.slack.com/commands/1234/5678" +
                "&trigger_id=13345224609.738474920.8088930838d88f008e0"
        val timestamp = (System.currentTimeMillis() / 1000).toString()
        val signature = SlackSignature.Generator(signingSecret).generate(timestamp, unknownCommandPayload)
        val response = client.post("/slack/events"){
            headers {
                append(SlackSignature.HeaderNames.X_SLACK_REQUEST_TIMESTAMP, timestamp)
                append(SlackSignature.HeaderNames.X_SLACK_SIGNATURE, signature)
                append("Content-type", "application/x-www-form-urlencoded")
            }
            setBody(unknownCommandPayload)
        }
        assertEquals(HttpStatusCode.NotFound, response.status)
        assertEquals("""{"error":"no handler found"}""", response.bodyAsText())
    }

    @Test
    fun invalidSignature() = testApplication {
        application { main() }
        val timestamp = (System.currentTimeMillis() / 1000).toString()
        val signature = SlackSignature.Generator("yet-another-signature").generate(timestamp, weatherCommandPayload)
        val response = client.post("/slack/events"){
            headers {
                append(SlackSignature.HeaderNames.X_SLACK_REQUEST_TIMESTAMP, timestamp)
                append(SlackSignature.HeaderNames.X_SLACK_SIGNATURE, signature)
                append("Content-type", "application/x-www-form-urlencoded")
            }
            setBody(weatherCommandPayload)
        }
        assertEquals(HttpStatusCode.Unauthorized, response.status)
        assertEquals("""{"error":"invalid request"}""", response.bodyAsText())
    }

    private val weatherCommandPayload =
        "token=verification-token" +
        "&team_id=T0001" +
        "&team_domain=example" +
        "&enterprise_id=E0001" +
        "&enterprise_name=Globular%20Construct%20Inc" +
        "&channel_id=C2147483705" +
        "&channel_name=test" +
        "&user_id=U2147483697" +
        "&user_name=Steve" +
        "&command=/weather" +
        "&text=94070" +
        "&response_url=https://hooks.slack.com/commands/1234/5678" +
        "&trigger_id=13345224609.738474920.8088930838d88f008e0"
}
