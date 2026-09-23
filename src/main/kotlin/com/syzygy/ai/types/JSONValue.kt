package com.syzygy.ai.types

sealed class JSONValue {
    object Null : JSONValue()

    data class Bool(val value: Boolean) : JSONValue()

    data class Number(val value: Double) : JSONValue()

    data class StringValue(val value: String) : JSONValue()

    data class Array(val value: List<JSONValue>) : JSONValue()

    data class Object(val value: Map<String, JSONValue>) : JSONValue()
}

typealias JSONObject = Map<String, JSONValue>
typealias JSONArray = List<JSONValue>
