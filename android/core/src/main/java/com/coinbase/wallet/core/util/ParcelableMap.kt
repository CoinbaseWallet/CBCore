package com.coinbase.wallet.core.util

import android.os.Parcel
import android.os.Parcelable
import java.lang.IllegalStateException

class ParcelableMap(val map: Map<String, Any>) : Parcelable {
    companion object CREATOR : Parcelable.Creator<ParcelableMap> {
        override fun createFromParcel(parcel: Parcel): ParcelableMap {
            val size = parcel.readInt()
            val map = mutableMapOf<String, Any>()
            for (i in 0..size) {
                val key = parcel.readString() ?: throw IllegalStateException("No key at $i")
                val value: Any? = when (parcel.readString()) {
                    String::class.java.name -> parcel.readString()
                    else -> null
                }
                value?.let { map[key] = value }
            }
            return ParcelableMap(map.toMap())
        }

        override fun newArray(size: Int): Array<ParcelableMap?> {
            return arrayOfNulls(size)
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(map.size)

        map.entries.forEach { (key, value) ->
            parcel.writeString(key)
            parcel.writeString(value::class.java.name)
            when (value) {
                is String -> parcel.writeString(value)
                else -> {} // No - op
            }
        }
    }

    override fun describeContents(): Int = 0
}
