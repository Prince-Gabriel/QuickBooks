package com.example.quickbooks.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
data class Contact(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nama: String,
    val namaPanggilan: String,
    val photo: String,
    val email: String,
    val alamat: String?,
    val tanggalLahir: Long,
    val noHp: String
)