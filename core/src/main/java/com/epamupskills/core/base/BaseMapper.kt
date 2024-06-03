package com.epamupskills.core.base

abstract class BaseMapper<IN, OUT> {

    abstract fun mapFrom(input: IN): OUT
    abstract fun mapTo(input: OUT): IN

    fun mapAll(list: List<IN>?): List<OUT> = list?.map(::mapFrom) ?: emptyList()
}