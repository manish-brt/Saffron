package com.app.saffron

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.app.saffron.data.models.FlowerDto
import com.app.saffron.ui.flower.FlowerDetailsScreen
import com.app.saffron.ui.flower.FlowerListScreen
import com.app.saffron.ui.login.LoginScreen
import com.app.saffron.ui.login.rememberAuthSource
import com.app.saffron.ui.profile.ProfileScreen
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        val navController = rememberNavController()
        val dataStore = rememberDataStore()
        val authStore = rememberAuthSource(dataStore)
        val scope = rememberCoroutineScope()

        val isLoggedIn by authStore.getIsLogin().collectAsState(initial = null) // Collect the state

        LaunchedEffect(isLoggedIn, navController) {
            if (isLoggedIn == true) {
                if (navController.currentBackStackEntry?.destination?.parent?.route != Route.HomeGraph.toString()) {
                    navController.navigate(Route.HomeGraph) {
                        popUpTo(Route.AuthGraph) { // Clear AuthGraph (login, signup, etc.)
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            } else if (isLoggedIn == false) {
                if (navController.currentBackStackEntry?.destination?.parent?.route != Route.AuthGraph.toString()) {
                    navController.navigate(Route.LoginRoute) { // Or Route.AuthGraph if Login is its start
                        popUpTo(navController.graph.findStartDestination().id) { // Clear everything
                            inclusive = false
                        }
                        launchSingleTop = true
                    }
                }
            }
        }

        NavHost(
            navController = navController,
            startDestination = Route.AuthGraph
        ) {
            navigation<Route.AuthGraph>(startDestination = Route.LoginRoute) {
                composable<Route.LoginRoute> {
                    LoginScreen {
                        scope.launch {
                            authStore.toggleLogin(true)
                        }

//                        navController.navigate(Route.HomeGraph) {
//                            popUpTo(Route.AuthGraph) {
//                                inclusive = true
//                            }
//                        }
                    }
                }
            }

            navigation<Route.HomeGraph>(startDestination = Route.FlowerListRoute) {

                composable<Route.FlowerListRoute> {
                    FlowerListScreen(
                        onFlowerClick = { dto ->
                            val arg = Json.encodeToString(dto)
                            navController.navigate(
                                Route.FlowerDetailsRoute(arg),
                            )
                        },
                        onProfileClick = {
                            navController.navigate(Route.ProfileRoute)
                        }
                    )
                }

                composable<Route.FlowerDetailsRoute> {
                    val arg = it.toRoute<Route.FlowerDetailsRoute>().dto
                    val dto = Json.decodeFromString<FlowerDto>(arg)
                    FlowerDetailsScreen(dto) {
                        navController.popBackStack()
                    }
                }

                composable<Route.ProfileRoute> {
                    ProfileScreen(
                        onBackClick = {
                            navController.popBackStack()
                        },
                        onLogOutClick = {
                            scope.launch {
                                authStore.toggleLogin(false)
                            }
//                            navController.navigate(Route.LoginRoute) {
//                                popUpTo(navController.graph.findStartDestination().id) {
//                                    inclusive = false
//                                }
//                                popUpTo(Route.HomeGraph)
//                                launchSingleTop = true
//                            }
                        }
                    )
                }

            }
        }
    }
}

fun today(): String {
    fun LocalDateTime.mFormat() = toString().substringBefore('T')

    val now = Clock.System.now()
    val zone = TimeZone.currentSystemDefault()

    return now.toLocalDateTime(zone).mFormat()
}
