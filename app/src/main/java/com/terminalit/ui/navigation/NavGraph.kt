package com.terminalit.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.terminalit.ui.screens.ConnectionScreen
import com.terminalit.ui.screens.ProfileListScreen
import com.terminalit.ui.screens.TerminalScreen

object Routes {
    const val PROFILE_LIST = "profile_list"
    const val CONNECTION = "connection?profileId={profileId}"
    const val TERMINAL = "terminal"

    fun connection(profileId: String? = null): String {
        return if (profileId != null) "connection?profileId=$profileId" else "connection"
    }
}

@Composable
fun TerminalitNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.PROFILE_LIST
    ) {
        composable(Routes.PROFILE_LIST) {
            ProfileListScreen(
                onNavigateToCreate = { navController.navigate(Routes.connection()) },
                onNavigateToEdit = { id -> navController.navigate(Routes.connection(id)) },
                onConnect = { profile -> 
                    // To handle direct connection we can pass profile to a shared ViewModel
                    // But simpler: just navigate to connection screen with profileId and let user click connect
                    // OR we navigate to terminal but we need to trigger connect.
                    // For now, let's navigate to connection screen so they can click Connect, 
                    // or ideally we could just connect directly. Let's navigate to connection screen
                    navController.navigate(Routes.connection(profile.id))
                }
            )
        }

        composable(
            route = Routes.CONNECTION,
            arguments = listOf(navArgument("profileId") { 
                type = NavType.StringType
                nullable = true
                defaultValue = null 
            })
        ) {
            ConnectionScreen(
                onConnected = {
                    navController.navigate(Routes.TERMINAL) {
                        popUpTo(Routes.PROFILE_LIST) // Keep list in backstack
                    }
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Routes.TERMINAL) {
            TerminalScreen()
        }
    }
}
