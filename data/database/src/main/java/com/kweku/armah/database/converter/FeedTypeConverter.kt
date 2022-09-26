package com.kweku.armah.database.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import javax.inject.Inject

@ProvidedTypeConverter
class FeedTypeConverter @Inject constructor(private val moshi: Moshi) {

    private val type = Types.newParameterizedType(
        List::class.java,
        String::class.java
    )

    @TypeConverter
    fun listToJson(value: List<String>): String {
        return moshi.adapter<List<String>>(type).toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): List<String>? {
        return moshi.adapter<List<String>>(type).fromJson(value)
    }
}
