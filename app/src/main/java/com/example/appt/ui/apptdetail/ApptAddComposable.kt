package com.example.appt.ui.apptdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.appt.data.model.UserAppointment
import com.example.appt.navigation.Screens
import com.example.appt.ui.viewmodel.ApptViewModel
import com.example.appt.ui.theme.Purple500
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockSelection

@Composable
fun ApptAddCompose(
    viewModel: ApptViewModel = hiltViewModel(),
    navController: NavController
) {

    var nameText by remember { mutableStateOf("") }
    var descText by remember { mutableStateOf("") }

    var expanded by remember { mutableStateOf(false) }
    val suggestions = listOf("San Diego", "St. George", "Park City", "Dallas", "Memphis", "Orlando")
    var selectedText by remember { mutableStateOf("") }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }
    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    val clockState = rememberSheetState()
    val hourSelection = remember { mutableStateOf("") }
    val minuteSelection = remember { mutableStateOf("") }
    ClockDialog(
        state = clockState,
        selection = ClockSelection.HoursMinutes { hour, minute ->
            hourSelection.value = hour.toString()
            minuteSelection.value = minute.toString()
        }
    )

    val calendarState = rememberSheetState()
    val calendarSelection = remember { mutableStateOf("") }
    CalendarDialog(
        state = calendarState,
        selection = CalendarSelection.Date { date ->
            calendarSelection.value = date.toString()
        }
    )
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
            .background(MaterialTheme.colors.background),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text(
            text = "Schedule Here:",
            style = MaterialTheme.typography.h6,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Appointment Name") },
            value = nameText,
            onValueChange = { nameText = it },
            textStyle = MaterialTheme.typography.body1,
            singleLine = true
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Description") },
            value = descText,
            onValueChange = { descText = it },
            textStyle = MaterialTheme.typography.body1
        )

        Column(
            modifier = Modifier
        ) {
            OutlinedTextField(
                value = selectedText,
                onValueChange = { selectedText = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        //This value is used to assign to the DropDown the same width
                        textFieldSize = coordinates.size.toSize()
                    },
                label = { Text("Location") },
                trailingIcon = {
                    Icon(
                        icon,
                        "contentDescription",
                        Modifier.clickable { expanded = !expanded }
                    )
                },
                readOnly = true
            )
            DropdownMenu(
                modifier = Modifier.width(with(LocalDensity.current){textFieldSize.width.toDp()}),
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                suggestions.forEach { selection ->
                    DropdownMenuItem(onClick = {
                        selectedText = selection
                        expanded = false
                    }) {
                        Text(text = selection)
                    }
                }
            }
        }

        Row(
            modifier = Modifier.clip(RoundedCornerShape(40.dp)),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            IconButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(Purple500),
                onClick = { calendarState.show() }
            ) {
                Icon(
                    tint = Color.White,
                    imageVector = Icons.Default.DateRange,
                    contentDescription = null,
                )
            }

            IconButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(Purple500),
                onClick = { clockState.show() }
            ) {
                Icon(
                    tint = Color.White,
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                )
            }

            IconButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(Purple500),
                onClick = {
                    viewModel.addAppointment(
                        UserAppointment(
                            name = nameText,
                            desc = descText,
                            date = calendarSelection.value,
                            hour = hourSelection.value,
                            minute = minuteSelection.value,
                            location = selectedText
                        )
                    )
                    navController.navigate(Screens.ApptList.route) { popUpTo(0) }
                }
            ) {
                Icon(
                    tint = Color.White,
                    imageVector = Icons.Default.Favorite,
                    contentDescription = null,
                )
            }
        }
    }
}
