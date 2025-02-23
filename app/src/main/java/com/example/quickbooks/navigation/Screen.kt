package com.example.quickbooks.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Home : Screen("home")
    object AddContact : Screen("add_contact")
    object Detail : Screen("detail/{contactId}") {
        fun createRoute(contactId: Int) = "detail/$contactId"
    }
    object EditContact : Screen("edit/{contactId}") {
        fun createRoute(contactId: Int) = "edit/$contactId"
    }
    object About : Screen("about")

    companion object {
        const val CONTACT_ID = "contactId"
    }
}