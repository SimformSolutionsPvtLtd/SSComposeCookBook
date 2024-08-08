package com.jetpack.compose.learning.timepicker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController
import java.text.SimpleDateFormat
import java.util.*

class TimePickerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainContent()
        }
    }

    @Composable
    private fun MainContent() {
        var datePicked: String? by remember {
            mutableStateOf(null)
        }
        val updatedTime = { hour: String, min: String, time: String ->
            datePicked = "$hour:$min $time"
        }
        val systemUiController = remember { SystemUiController(window) }
        val appTheme = remember { mutableStateOf(AppThemeState()) }
        BaseView(appTheme.value, systemUiController) {
            Scaffold(
                topBar = {
                    TopAppBar(title = { Text("TimePicker") }, navigationIcon = {
                        IconButton(onClick = { onBackPressed() }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = null)
                        }
                    })
                }
            ) { contentPadding ->
                DatePickerView(modifier = Modifier.padding(contentPadding), datePicked, updatedTime)
            }
        }
    }

    @Composable
    fun DatePickerView(
        modifier: Modifier = Modifier,
        datePicked: String?,
        updatedDate: (hour: String, min: String, time: String) -> Unit,
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
                .padding(horizontal = 25.dp)
                .border(
                    0.5.dp,
                    MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(5.dp)
                )
                .clickable {
                    showDatePicker(updatedDate)
                },
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .align(Alignment.Center)
            ) {
                val (lable, iconView) = createRefs()
                Text(
                    text = datePicked ?: "Select Time",
                    color = MaterialTheme.colors.onSurface,
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(lable) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(iconView.start)
                            width = Dimension.fillToConstraints
                        }
                )

                Icon(
                    imageVector = Icons.Default.Timer,
                    contentDescription = null,
                    modifier = Modifier
                        .size(20.dp, 20.dp)
                        .constrainAs(iconView) {
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        },
                    tint = MaterialTheme.colors.onSurface
                )

            }

        }
    }

    private fun showDatePicker(updatedDate: (hour: String, min: String, time: String) -> Unit) {
        val materialTimePicker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setMinute(timeFormatter("mm").toInt())
            .setHour(timeFormatter("HH").toInt())
            .build()
        materialTimePicker.addOnPositiveButtonClickListener {
            updatedDate(
                getHour(materialTimePicker.hour).toString(), materialTimePicker.minute.toString(),
                getAmPm(materialTimePicker.hour)
            )
        }
        materialTimePicker.show(supportFragmentManager, "fragment_tag")
    }

    private fun timeFormatter(formate: String?): String {
        val formatter = SimpleDateFormat(formate, Locale.US)
        val calendar: Calendar = Calendar.getInstance()
        return formatter.format(calendar.time)
    }

    private fun getAmPm(hour: Int): String {
        return if (hour < 12) "AM" else "PM"
    }

    private fun getHour(hour: Int): Int {
        return when {
            hour == 0 -> {
                hour + 12
            }
            hour > 12 -> {
                hour - 12
            }
            else -> {
                hour
            }
        }
    }
}