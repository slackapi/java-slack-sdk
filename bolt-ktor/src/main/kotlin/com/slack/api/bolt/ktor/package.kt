package com.slack.api.bolt.ktor

import com.slack.api.bolt.request.Request
import com.slack.api.bolt.request.RequestHeaders
import com.slack.api.bolt.response.Response
import com.slack.api.bolt.util.QueryStringParser
import com.slack.api.bolt.util.SlackRequestParser
import io.ktor.application.*
import io.ktor.content.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.util.*
import io.ktor.utils.io.charsets.Charset
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun toBoltRequest(call: ApplicationCall, parser: SlackRequestParser): Request<*>? {
    val requestBody = call.receiveTextWithCorrectEncoding()
    val queryString = QueryStringParser.toMap(call.request.queryString())
    val headers = RequestHeaders(call.request.headers.toMap())
    val rawRequest = SlackRequestParser.HttpRequest.builder()
            .requestUri(call.request.path())
            .queryString(queryString)
            .requestBody(requestBody)
            .headers(headers)
            .remoteAddress(call.request.origin.remoteHost)
            .build()
    return parser.parse(rawRequest)
}

suspend fun respond(call: ApplicationCall, boltResponse: Response) {
    for (header in boltResponse.headers) {
        for (value in header.value) {
            call.response.header(header.key, value)
        }
    }
    val status = HttpStatusCode.fromValue(boltResponse.statusCode)
    if (boltResponse.body != null) {
        val message = TextContent(
            boltResponse.body,
            ContentType.parse(boltResponse.contentType),
            status
        )
        call.respond(message)
    } else call.respond(status)
}

/**
 * Temporary workaround for receiveText() decoding json strings as ISO_8859-1
 *
 * Receive the request as String.
 * If there is no Content-Type in the HTTP header specified use ISO_8859_1 as default charset, see https://www.w3.org/International/articles/http-charset/index#charset.
 * But use UTF-8 as default charset for application/json, see https://tools.ietf.org/html/rfc4627#section-3
 */
private suspend fun ApplicationCall.receiveTextWithCorrectEncoding(): String = withContext(Dispatchers.IO) {
    fun ContentType.defaultCharset(): Charset = when (this) {
        ContentType.Application.Json -> Charsets.UTF_8
        else -> Charsets.ISO_8859_1
    }

    val contentType = request.contentType()
    val suitableCharset = contentType.charset() ?: contentType.defaultCharset()
    receiveStream().bufferedReader(charset = suitableCharset).readText()
}