package com.github.ameen.emplyeemanagement.presentation.util

import android.content.Context
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

fun loadJSONFromAsset(context: Context): String? {
    var json: String? = null
    json = try {
        val `is`: InputStream = context.assets.open("skills.json")
        val size: Int = `is`.available()
        val buffer = ByteArray(size)
        `is`.read(buffer)
        `is`.close()
        String(buffer, Charset.forName("UTF-8"))
    } catch (ex: IOException) {
        ex.printStackTrace()
        return null
    }
    return json
}