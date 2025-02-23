package com.example.quickbooks.data.repository

import com.example.quickbooks.data.local.ContactDao
import com.example.quickbooks.data.model.Contact
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class ContactRepository @Inject constructor(
    private val contactDao: ContactDao
) {
    fun getAllContacts(): Flow<List<Contact>> = contactDao.getAllContacts()

    suspend fun getContactById(id: Int): Contact? = contactDao.getContactById(id)

    fun getContactByIdAsFlow(id: Int): Flow<Contact?> {
        return contactDao.getContactByIdAsFlow(id)
    }

    suspend fun insertContact(contact: Contact) = contactDao.insertContact(contact)

    suspend fun updateContact(contact: Contact) = contactDao.updateContact(contact)

    suspend fun deleteContact(contact: Contact) = contactDao.deleteContact(contact)
}