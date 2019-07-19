/*
 * Copyright 2014-2019 JetBrains s.r.o and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package io.ktor.client.features.json

import com.google.gson.*
import io.ktor.client.call.*
import io.ktor.http.content.*
import io.ktor.http.*
import kotlinx.io.core.*
import kotlin.reflect.*

/**
 * [JsonSerializer] using [Gson] as backend.
 */
class GsonSerializer(block: GsonBuilder.() -> Unit = {}) : JsonSerializer {

    private val backend: Gson = GsonBuilder().apply(block).create()

    override fun write(data: Any, contentType: ContentType): OutgoingContent =
        TextContent(backend.toJson(data), contentType)

    override fun read(type: KType, body: Input): Any {
        val text = body.readText()
        return backend.fromJson(text, type.javaClass)
    }
}
