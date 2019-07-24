package com.coinbase.wallet.core.jsonadapters

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.ToJson
import java.io.IOException

@ExperimentalUnsignedTypes
class UIntAdapter : JsonAdapter<UInt>() {
    @FromJson
    @Throws(IOException::class)
    override fun fromJson(reader: JsonReader): UInt? {
        if (reader.peek() == JsonReader.Token.NULL) return reader.nextNull()
        return reader.nextString().toUInt()
    }

    @ToJson
    override fun toJson(writer: JsonWriter, value: UInt?) {
        if (value == null) writer.value(null as String?)
        else writer.value(value.toString())
    }
}
