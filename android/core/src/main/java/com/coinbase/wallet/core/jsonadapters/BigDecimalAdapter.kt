package com.coinbase.wallet.core.jsonadapters

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.ToJson
import java.io.IOException
import java.math.BigDecimal

class BigDecimalAdapter : JsonAdapter<BigDecimal>() {
    @FromJson
    @Throws(IOException::class)
    override fun fromJson(reader: JsonReader): BigDecimal? {
        if (reader.peek() == JsonReader.Token.NULL) return reader.nextNull()
        return BigDecimal(reader.nextString())
    }

    @ToJson
    override fun toJson(writer: JsonWriter, value: BigDecimal?) {
        if (value == null) writer.value(null as String?)
        else writer.value(value.toString())
    }
}
