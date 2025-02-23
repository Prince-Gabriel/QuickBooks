package com.example.quickbooks.ui.screens.add


import android.content.Context
import android.net.Uri
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quickbooks.data.model.Contact
import com.example.quickbooks.data.repository.ContactRepository
import com.example.quickbooks.utils.DateUtils
import com.example.quickbooks.utils.FileUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddContactViewModel @Inject constructor(
    private val repository: ContactRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val _nama = mutableStateOf("")
    private val _namaPanggilan = mutableStateOf("")
    private val _photo = mutableStateOf("")
    private val _email = mutableStateOf("")
    private val _alamat = mutableStateOf("")
    private val _tanggalLahir = mutableStateOf(System.currentTimeMillis())
    val tanggalLahir: Long get() = _tanggalLahir.value

    private val _noHp = mutableStateOf("")

    var nama: String
        get() = _nama.value
        set(value) { _nama.value = value }

    var namaPanggilan: String
        get() = _namaPanggilan.value
        set(value) { _namaPanggilan.value = value }

    var email: String
        get() = _email.value
        set(value) { _email.value = value }

    var alamat: String
        get() = _alamat.value
        set(value) { _alamat.value = value }

    var noHp: String
        get() = _noHp.value
        set(value) { _noHp.value = value }

    private val _namaError = mutableStateOf(false)
    private val _namaPanggilanError = mutableStateOf(false)
    private val _photoError = mutableStateOf(false)
    private val _emailError = mutableStateOf(false)
    private val _noHpError = mutableStateOf(false)

    val namaError: Boolean get() = _namaError.value
    val namaPanggilanError: Boolean get() = _namaPanggilanError.value
    val photoError: Boolean get() = _photoError.value
    val emailError: Boolean get() = _emailError.value
    val noHpError: Boolean get() = _noHpError.value

    fun updatePhoto(uri: Uri) {
        viewModelScope.launch {
            try {
                val imagePath = FileUtils.saveImageToInternalStorage(context, uri)
                _photo.value = imagePath
                _photoError.value = false
            } catch (e: Exception) {
                // Handle error
                _photoError.value = true
            }
        }
    }


    fun updateTanggalLahir(timestamp: Long) {
        _tanggalLahir.value = timestamp
    }

    fun getFormattedDate(): String = DateUtils.formatDate(_tanggalLahir.value)

    private fun validateInput(): Boolean {
        _namaError.value = _nama.value.isBlank()
        _namaPanggilanError.value = _namaPanggilan.value.isBlank()
        _photoError.value = _photo.value.isBlank()
        _emailError.value = _email.value.isBlank() || !_email.value.contains("@")
        _noHpError.value = _noHp.value.isBlank() || _noHp.value.length < 10

        return !_namaError.value && !_namaPanggilanError.value &&
                !_photoError.value && !_emailError.value && !_noHpError.value
    }

    fun saveContact(onSuccess: () -> Unit) {
        if (!validateInput()) return

        viewModelScope.launch {
            val contact = Contact(
                nama = _nama.value,
                namaPanggilan = _namaPanggilan.value,
                photo = _photo.value,
                email = _email.value,
                alamat = _alamat.value.ifBlank { null },
                tanggalLahir = _tanggalLahir.value,
                noHp = _noHp.value
            )
            repository.insertContact(contact)
            onSuccess()
        }
    }
}