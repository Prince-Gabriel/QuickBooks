package com.example.quickbooks.ui.screens.home



import androidx.lifecycle.ViewModel
import com.example.quickbooks.data.repository.ContactRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: ContactRepository
) : ViewModel() {
    val contacts = repository.getAllContacts()
}