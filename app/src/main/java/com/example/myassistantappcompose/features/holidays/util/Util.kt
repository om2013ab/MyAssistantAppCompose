package com.example.myassistantappcompose.features.holidays.util

fun String.takeMonth(): Int {
    return if (this.length > 10) {
        this.drop(5).dropLast(18).toInt()
    } else {
        this.drop(5).dropLast(3).toInt()
    }
}

fun String.takeDay(): String {
    return if (this.length > 10) {
        this.drop(8).dropLast(15)
    } else {
        this.drop(8)
    }
}