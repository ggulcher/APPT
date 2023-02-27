package com.example.appt.navigation

sealed class Screens(
    val route: String,
    val title: String? = null
) {

    object ApptList: Screens(
        route = "appt_list",
        title = "List"
    )

    object ApptAdd: Screens(
        route = "appt_add",
        title = "Add"
    )

    object ApptUpdate: Screens(
        route = "appt_update",
        title = "Update"
    )
}
