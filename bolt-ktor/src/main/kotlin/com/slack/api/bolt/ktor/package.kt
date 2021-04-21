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

suspend fun toBoltRequest(call: ApplicationCall, parser: SlackRequestParser): Request<*>? {
    val requestBody = call.receiveText()
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
        call.respondText(boltResponse.body, ContentType.parse(boltResponse.contentType), status)
    } else call.respond(status)
}