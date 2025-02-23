package com.example.quickbooks.di

import android.content.Context
import androidx.room.Room
import com.example.quickbooks.data.local.ContactDatabase
import com.example.quickbooks.data.repository.ContactRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideContactDatabase(
        @ApplicationContext context: Context
    ): ContactDatabase {
        return Room.databaseBuilder(
            context,
            ContactDatabase::class.java,
            "contact_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideContactRepository(
        database: ContactDatabase
    ): ContactRepository {
        return ContactRepository(database.contactDao())
    }
}