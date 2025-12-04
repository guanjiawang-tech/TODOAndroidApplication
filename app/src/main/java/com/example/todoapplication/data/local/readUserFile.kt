package com.example.todoapplication.data.local

import android.content.Context
import java.io.File

fun readUserFile(context: Context): String? {
    val file = File(context.filesDir, "data.json")
    return if (file.exists()) {
        file.readText()
    } else {
        null
    }
}