package com.example.appt.ui.apptlist

import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.appt.navigation.Screens
import com.example.appt.ui.apptlist.components.ApptListRow
import com.example.appt.ui.apptlist.components.EmptyList
import com.example.appt.ui.apptlist.components.SearchBar
import com.example.appt.ui.apptlist.components.TopBar
import com.example.appt.ui.viewmodel.ApptViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ApptListCompose(
    viewModel: ApptViewModel = hiltViewModel(),
    navController: NavController
) {
    val scaffoldState = rememberScaffoldState()
    val searchState = remember { mutableStateOf(TextFieldValue("")) }
    val lazyListState = rememberLazyListState()
    val state = viewModel.apptsState

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopBar(title = "APPT")
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(
                        Screens.ApptAdd.route
                    )
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Favorites",
                    tint = Color.Black
                )
            }
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .padding(12.dp)
                    .animateContentSize(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioLowBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    ),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                SearchBar(
                    hint = "...",
                    modifier = Modifier.fillMaxWidth(),
                    state = searchState
                )
                val isBeingSearched = searchState.value.text
                if (state.appointments.isNotEmpty()) {
                    LazyColumn(
                        state = lazyListState,
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(
                            items = state.appointments.filter {
                                it.name.contains(isBeingSearched, ignoreCase = true)
                            },
                            key = { it.name }
                        ) { appt ->
                            ApptListRow(
                                appt = appt,
                                navController = navController
                            )
                        }
                    }
                } else {
                    EmptyList()
                }
            }
        }
    }
}
