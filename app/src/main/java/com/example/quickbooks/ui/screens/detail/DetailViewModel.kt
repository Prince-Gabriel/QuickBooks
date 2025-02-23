package com.example.quickbooks.ui.screens.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quickbooks.data.model.Contact
import com.example.quickbooks.data.repository.ContactRepository
import com.example.quickbooks.utils.DateUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: ContactRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val contactId: Int = checkNotNull(savedStateHandle["contactId"])
    private val _contact = MutableStateFlow<Contact?>(null)
    val contact = repository.getContactByIdAsFlow(contactId)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    fun getFormattedDate(timestamp: Long): String = DateUtils.formatDate(timestamp)

    init {
        loadContact()
    }

    private fun loadContact() {
        viewModelScope.launch {
            _contact.value = repository.getContactById(contactId)
        }
    }

    fun deleteContact(onSuccess: () -> Unit) {
        viewModelScope.launch {
            contact.value?.let { contact ->
                repository.deleteContact(contact)
                onSuccess()
            }
        }
    }
}