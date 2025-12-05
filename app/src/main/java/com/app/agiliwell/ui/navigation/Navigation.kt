package com.app.agiliwell.ui.navigation

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import com.app.agiliwell.ui.composables.home.Home
import com.app.agiliwell.ui.composables.notification.NotificationScreen
import com.app.agiliwell.ui.composables.onboarding.OnboardingScreen
import com.app.agiliwell.ui.composables.privacy.PrivacyScreen
import com.app.agiliwell.ui.composables.settings.SettingsScreen
import com.app.agiliwell.ui.composables.userdetails.UserDetailForm
import com.app.agiliwell.ui.composables.waterdetails.WaterDetailForm
import com.app.agiliwell.ui.composables.yoga.YogaScreen
import com.app.agiliwell.ui.composables.exercise.ExerciseScreen
import com.app.agiliwell.ui.viewmodel.SharedViewModel
import com.app.agiliwell.utils.AppUtils.isFreshInstall
import com.app.agiliwell.utils.PreferencesManager

@Composable
fun Navigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    sharedViewModel: SharedViewModel
) {
    val context = LocalContext.current
    val preferencesManager = remember { PreferencesManager(context) }
    var startDestination by rememberSaveable { mutableStateOf(Destination.Onboarding.path) }

    val userDetailsFilled = preferencesManager.isUserDetailsFilled()
    val waterDetailsFilled = preferencesManager.isWaterDetailsFilled()
    val isFirstLaunch = preferencesManager.isFirstLaunch()

    val userOnboarded = if (isFirstLaunch) {
        val isFreshInstall = context.isFreshInstall
        val onboarded = preferencesManager.isOnboarded(!isFreshInstall)
        preferencesManager.saveFirstLaunchStatus(false)
        onboarded
    } else false

    // 🔹 Define where the user should start
    val initialDestination = when {
        userDetailsFilled && waterDetailsFilled -> Destination.HomeScreen.path
        waterDetailsFilled -> Destination.HomeScreen.path
        userDetailsFilled -> Destination.WaterDetails.path
        userOnboarded -> Destination.UserDetails.path
        else -> Destination.Onboarding.path
    }
    startDestination = initialDestination

    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = startDestination
    ) {
        // 🔹 Onboarding
        composable(route = Destination.Onboarding.path) {
            OnboardingScreen {
                preferencesManager.saveOnboarding()
                navController.navigate(Destination.UserDetails.path) {
                    popUpTo(route = Destination.Onboarding.path) { inclusive = true }
                }
            }
        }

        // 🔹 User details setup
        composable(route = Destination.UserDetails.path) {
            UserDetailForm(
                agiliwellEventListener = sharedViewModel::handleEvent,
                onProceed = {
                    preferencesManager.saveUserDetails()
                    navController.navigate(Destination.WaterDetails.path) {
                        popUpTo(route = Destination.UserDetails.path) { inclusive = true }
                    }
                }
            )
        }

        // 🔹 Water intake setup
        composable(route = Destination.WaterDetails.path) {
            WaterDetailForm(
                agiliwellEventListener = sharedViewModel::handleEvent,
                onProceed = {
                    preferencesManager.saveWaterDetails()
                    navController.navigate(Destination.HomeScreen.path) {
                        popUpTo(route = Destination.WaterDetails.path) { inclusive = true }
                    }
                },
                userDetails = sharedViewModel.userDetails.collectAsState().value
            )
        }

        // 🔹 Main Home screen (entry point)
        composable(route = Destination.HomeScreen.path) {
            Home(
                agiliwellEventListener = sharedViewModel::handleEvent,
                todayIntake = sharedViewModel.todayTotalIntake.collectAsState().value,
                targetIntake = sharedViewModel.targetIntakeAmount.collectAsState().value,
                intakeList = sharedViewModel.todayAllIntakes.collectAsState().value,
                userDetails = sharedViewModel.userDetails.collectAsState().value,
                navigateToSettings = {
                    navController.navigate(Destination.Settings.path)
                },
                navigateToNotifications = {
                    navController.navigate(Destination.Notification.path)
                },
                navigateToYoga = {
                    navController.navigate(Destination.Yoga.path)
                },
                navigateToExercise = {
                    navController.navigate(Destination.Exercise.path)
                }
            )
        }

        // 🔹 Notifications
        composable(route = Destination.Notification.path) {
            NotificationScreen(
                onBack = {
                    navController.navigate(Destination.HomeScreen.path) {
                        popUpTo(Destination.HomeScreen.path) { inclusive = true }
                    }
                },
                notificationList = sharedViewModel.allNotifications.collectAsState().value,
                agiliwellEventListener = sharedViewModel::handleEvent
            )
        }

        // 🔹 Privacy
        composable(route = Destination.Privacy.path) {
            PrivacyScreen(onBack = {
                navController.navigate(Destination.HomeScreen.path) {
                    popUpTo(Destination.HomeScreen.path) { inclusive = true }
                }
            })
        }

        // 🔹 Settings
        composable(route = Destination.Settings.path) {
            SettingsScreen(
                agiliwellEventListener = sharedViewModel::handleEvent,
                onPrivacy = {
                    navController.navigate(Destination.Privacy.path)
                },
                userDetails = sharedViewModel.userDetails.collectAsState().value,
                waterDrinkTarget = sharedViewModel.targetIntakeAmount.collectAsState().value,
                onBack = {
                    navController.navigate(Destination.HomeScreen.path) {
                        popUpTo(Destination.HomeScreen.path) { inclusive = true }
                    }
                }
            )
        }

        // 🔹 Yoga screen
        composable(route = Destination.Yoga.path) {
            YogaScreen(
                yogaSessions = sharedViewModel.allYogaSessions.collectAsState().value,
                agiliwellEventListener = sharedViewModel::handleEvent,
                onBack = { navController.navigateUp() }
            )
        }

        // 🔹 Exercise screen
        composable(route = Destination.Exercise.path) {
            ExerciseScreen(
                exercises = sharedViewModel.allExercises.collectAsState().value,
                agiliwellEventListener = sharedViewModel::handleEvent,
                onBack = { navController.navigateUp() }
            )
        }
    }
}
