package com.example.appt.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.appt.ui.apptdetail.ApptAddCompose
import com.example.appt.ui.apptdetail.ApptUpdateCompose
import com.example.appt.ui.apptlist.ApptListCompose

@Composable
fun Navigation() {

    val navController: NavHostController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screens.ApptList.route
    ) {
        composable(
            route = Screens.ApptList.route
        ) {
            ApptListCompose(navController = navController)
        }

        composable(
            route = Screens.ApptAdd.route
        ) {
            ApptAddCompose(navController = navController)
        }

        composable(
            route = Screens.ApptUpdate.route + "?name={apptName}&desc={apptDesc}&date={date}&hour={hour}&min={minute}&location={location}",
            arguments = listOf(
                navArgument("apptName") {
                    nullable = true
                    defaultValue = ""
                    type = NavType.StringType
                },
                navArgument("apptDesc") {
                    nullable = true
                    defaultValue = ""
                    type = NavType.StringType
                },
                navArgument("date") {
                    nullable = true
                    defaultValue = ""
                    type = NavType.StringType
                },
                navArgument("hour") {
                    nullable = true
                    defaultValue = ""
                    type = NavType.StringType
                },
                navArgument("minute") {
                    nullable = true
                    defaultValue = ""
                    type = NavType.StringType
                },
                navArgument("location") {
                    nullable = true
                    defaultValue = ""
                    type = NavType.StringType
                }
            )
        ) {
            val name = it.arguments?.getString("apptName")
            val desc = it.arguments?.getString("apptDesc")
            val date = it.arguments?.getString("date")
            val hour = it.arguments?.getString("hour")
            val minute = it.arguments?.getString("minute")
            val location = it.arguments?.getString("location")

            ApptUpdateCompose(
                navController = navController,
                name = name,
                desc = desc,
                date = date,
                hour = hour,
                minute = minute,
                location = location
            )
        }
    }
}
