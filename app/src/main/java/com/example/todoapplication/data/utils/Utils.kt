package com.example.todoapplication.data.utils

/**
 * 显示文字自定义长度，超长则末尾加 "..."
 */
fun truncateString(str: String, n: Int): String {
    return if (str.length <= n) {
        str
    } else {
        str.take(n) + "..."
    }
}