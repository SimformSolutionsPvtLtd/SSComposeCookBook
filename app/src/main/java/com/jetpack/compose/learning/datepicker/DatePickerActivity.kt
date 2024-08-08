package com.jetpack.compose.learning.datepicker

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.google.android.material.datepicker.MaterialDatePicker
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController
import java.text.SimpleDateFormat
import java.util.*

class DatePickerActivity : AppCompatActivity() {
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
        val updatedDate = { date: Long? ->
            datePicked = dateFormatter(date)
        }
        val systemUiController = remember { SystemUiController(window) }
        val appTheme = remember { mutableStateOf(AppThemeState()) }
        BaseView(appTheme.value, systemUiController) {
            Scaffold(
                topBar = {
                    TopAppBar(title = { Text("DatePicker") }, navigationIcon = {
                        IconButton(onClick = { onBackPressed() }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = null)
                        }
                    })
                }
            ) { contentPadding ->
                DatePickerView(modifier = Modifier.padding(contentPadding), datePicked, updatedDate)
            }
        }
    }

    @Composable
    fun DatePickerView(
        modifier: Modifier,
        datePicked: String?,
        updatedDate: (date: Long?) -> Unit,
    ) {
        val activity = LocalContext.current as AppCompatActivity
        Box(
            modifier = modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
                .padding(horizontal = 25.dp)
                .border(0.5.dp, MaterialTheme.colors.onSurface.copy(alpha = 0.5f), shape =  RoundedCornerShape(5.dp))
                .clickable {
                    showDatePicker(activity, updatedDate)
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
                    text = datePicked ?: "Select Date",
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
                    imageVector = Icons.Default.DateRange,
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

    private fun showDatePicker(
        activity: AppCompatActivity,
        updatedDate: (Long?) -> Unit
    ) {
        val picker = MaterialDatePicker.Builder.datePicker().build()
        picker.show(activity.supportFragmentManager, picker.toString())
        picker.addOnPositiveButtonClickListener {
            updatedDate(it)
        }
    }

    private fun dateFormatter(milliseconds: Long?): String? {
        milliseconds?.let {
            val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.US)
            val calendar: Calendar = Calendar.getInstance()
            calendar.timeInMillis = it
            return formatter.format(calendar.time)
        }
        return null
    }
}