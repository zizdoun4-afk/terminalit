package com.terminalit.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.compose.runtime.remember
import com.terminalit.ui.screens.ConnectionScreen
import com.terminalit.ui.screens.ProfileListScreen
import com.terminalit.ui.screens.TerminalScreen
import com.terminalit.ui.screens.ExtraKeysConfigScreen
import com.terminalit.di.ExtraKeyStoreEntryPoint

object Routes {
    const val PROFILE_LIST = "profile_list"
    const val CONNECTION = "connection?profileId={profileId}"
    const val TERMINAL = "terminal"
    const val EXTRA_KEYS_CONFIG = "extra_keys_config"

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
                    navController.navigate(Routes.connection(profile.id))
                },
                onNavigateToExtraKeys = { navController.navigate(Routes.EXTRA_KEYS_CONFIG) }
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

        composable(Routes.EXTRA_KEYS_CONFIG) {
            val context = androidx.compose.ui.platform.LocalContext.current
            val entryPoint = remember(context) {
                dagger.hilt.EntryPoints.get(
                    context.applicationContext,
                    ExtraKeyStoreEntryPoint::class.java
                )
            }
            ExtraKeysConfigScreen(
                extraKeyStore = entryPoint.extraKeyStore(),
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
