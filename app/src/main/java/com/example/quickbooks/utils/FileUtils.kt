package com.example.quickbooks.utils


import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream
import java.util.UUID

object FileUtils {
    fun saveImageToInternalStorage(context: Context, uri: Uri): String {
        val inputStream = context.contentResolver.openInputStream(uri)
        val fileName = "IMG_${UUID.randomUUID()}.jpg"
        val directory = File(context.filesDir, "photos")
        if (!directory.exists()) {
            directory.mkdir()
        }

        val file = File(directory, fileName)
        FileOutputStream(file).use { outputStream ->
            inputStream?.copyTo(outputStream)
        }
        inputStream?.close()

        return file.absolutePath
    }

    fun deleteImage(path: String) {
        val file = File(path)
        if (file.exists()) {
            file.delete()
        }
    }
}