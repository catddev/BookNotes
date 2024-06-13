package com.epamupskills.booknotes.base

abstract class BaseMapper<IN, OUT> {

    abstract fun transform(input: IN): OUT

    fun transformAll(list: List<IN>?): List<OUT> = list?.map(::transform) ?: emptyList()
}