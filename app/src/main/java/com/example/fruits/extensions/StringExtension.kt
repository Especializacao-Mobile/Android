package com.example.fruits.extensions

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log

@Throws(IllegalArgumentException::class)
fun String.convertToBitmap(): Bitmap? {
    val decodedBytes: ByteArray = Base64.decode(
            this,
            Base64.DEFAULT
    )
    return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
}