package com.example.appt.ui.apptlist.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.appt.data.model.UserAppointment
import com.example.appt.navigation.Screens

@Composable
fun ApptListRow(
    appt: UserAppointment,
    navController: NavController
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                border = ButtonDefaults.outlinedBorder,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable {
                navController.navigate(
                    Screens.ApptUpdate.route
                            + "?name=${appt.name}&desc=${appt.desc}&date=${appt.date}&hour=${appt.hour}&min=${appt.minute}&location=${appt.location}"
                )
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = appt.name,
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Start
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = appt.location,
                    style = MaterialTheme.typography.subtitle2,
                    color = Color.Black,
                    textAlign = TextAlign.Start
                )
                Text(
                    text = appt.date,
                    style = MaterialTheme.typography.subtitle2,
                    color = Color.Black,
                    textAlign = TextAlign.Start
                )
            }
            Text(
                text = appt.desc,
                style = MaterialTheme.typography.body1,
                color = Color.Black,
                textAlign = TextAlign.Start,
                maxLines = 2
            )
        }
    }
}
