package util

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.slack.api.bolt.AppConfig
import org.slf4j.LoggerFactory
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.stream.Collectors.joining

class ResourceLoader {

    companion object {
        private val logger = LoggerFactory.getLogger("resource-loader")

        @Suppress("DEPRECATION")
        fun loadAppConfig(name: String = "appConfig.json"): AppConfig {
            val config = AppConfig()
            val classLoader = ResourceLoader::class.java.classLoader
            try {
                classLoader.getResourceAsStream(name).use { resource ->
                    InputStreamReader(resource).use { isr ->
                        val json = BufferedReader(isr).lines().collect(joining())
                        val j = Gson().fromJson(json, JsonElement::class.java).asJsonObject
                        config.signingSecret = j.get("signingSecret").asString
                        config.verificationToken = j.get("verificationToken")?.asString
                        config.singleTeamBotToken = j.get("singleTeamBotToken")?.asString
                        config.clientId = j.get("clientId")?.asString
                        config.clientSecret = j.get("clientSecret")?.asString
                        config.redirectUri = j.get("redirectUri")?.asString
                        config.scope = j.get("scope")?.asString
                        config.userScope = j.get("userScope")?.asString
                        config.oauthStartPath = j.get("oauthStartPath")?.asString
                        config.oauthCallbackPath = j.get("oauthCallbackPath")?.asString
                        config.oauthCompletionUrl = j.get("oauthCompletionUrl")?.asString
                        config.oauthCancellationUrl = j.get("oauthCancellationUrl")?.asString
                        config.isOAuthStartEnabled = config.oauthStartPath != null
                        config.isOAuthCallbackEnabled = config.oauthCallbackPath != null
                    }
                }
            } catch (e: Throwable) {
                logger.error(e.message, e)
                throw e
            }
            return config
        }

        fun load(filepath: String): String? {
            return try {
                val classLoader = ResourceLoader::class.java.classLoader
                classLoader.getResourceAsStream(filepath).use { resource ->
                    InputStreamReader(resource).use { isr ->
                        BufferedReader(isr).lines().collect(joining())
                    }
                }
            } catch (e: IOException) {
                logger.error(e.message, e)
                null
            }
        }

    }

}