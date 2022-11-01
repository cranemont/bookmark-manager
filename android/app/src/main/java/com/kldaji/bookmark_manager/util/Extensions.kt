package com.kldaji.bookmark_manager.util

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import javax.inject.Inject

inline fun <reified T> Moshi.parseList(jsonString: String): List<T>? {
	return adapter<List<T>>(Types.newParameterizedType(List::class.java, T::class.java)).fromJson(jsonString)
}

inline fun <reified T> Moshi.toJson(list: List<T>?): String? {
	return adapter<List<T>>(Types.newParameterizedType(List::class.java, T::class.java)).toJson(list)
}

@ProvidedTypeConverter
class StringListTypeConverter @Inject constructor(private val moshi: Moshi) {

	@TypeConverter
	fun toJson(type: List<String>?) = moshi.toJson<String>(type)

	@TypeConverter
	fun fromJson(json: String): List<String>? = moshi.parseList(json)
}