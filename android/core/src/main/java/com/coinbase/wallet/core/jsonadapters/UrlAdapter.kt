package com.coinbase.wallet.core.jsonadapters

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.ToJson
import java.io.IOException
import java.net.URL

class UrlAdapter : JsonAdapter<URL>() {
    @FromJson
    @Throws(IOException::class)
    override fun fromJson(reader: JsonReader): URL? {
        if (reader.peek() == JsonReader.Token.NULL) return reader.nextNull()
        return URL(reader.nextString())
    }

    @ToJson
    override fun toJson(writer: JsonWriter, value: URL?) {
        if (value == null) writer.value(null as String?)
        else writer.value(value.toString())
    }
}
