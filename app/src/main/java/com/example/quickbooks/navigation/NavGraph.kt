package com.example.quickbooks.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.quickbooks.ui.screens.about.AboutScreen
import com.example.quickbooks.ui.screens.add.AddContactScreen
import com.example.quickbooks.ui.screens.detail.DetailScreen
import com.example.quickbooks.ui.screens.edit.EditScreen
import com.example.quickbooks.ui.screens.home.HomeScreen
import com.example.quickbooks.ui.screens.splash.SplashScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(navController)
        }
        composable(Screen.Home.route) {
            HomeScreen(navController)
        }
        composable(Screen.AddContact.route) {
            AddContactScreen(navController)
        }

        composable(
            route = Screen.Detail.route,
            arguments = listOf(
                navArgument(Screen.CONTACT_ID) {
                    type = NavType.IntType
                }
            )
        ) {
            DetailScreen(navController)
        }

        composable(
            route = Screen.EditContact.route,
            arguments = listOf(
                navArgument(Screen.CONTACT_ID) {
                    type = NavType.IntType
                }
            )
        ) {
            EditScreen(navController)
        }

        composable(Screen.About.route) {
            AboutScreen(navController)
        }
    }
}