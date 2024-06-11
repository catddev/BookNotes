package com.epamupskills.book_notes.presentation.utils

fun Boolean?.orTrue() = this ?: true

fun Boolean?.orFalse() = this ?: false