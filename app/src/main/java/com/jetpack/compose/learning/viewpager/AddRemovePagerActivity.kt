package com.jetpack.compose.learning.viewpager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.material.IconButton
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Icon
import androidx.compose.material.Card
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.jetpack.compose.learning.R
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController
import com.jetpack.compose.learning.viewpager.viewmodel.AddRemovePagerViewModel
import kotlinx.coroutines.launch

/**
 * This class contains example of add/remove Composable function dynamically
 */
class AddRemovePagerActivity : ComponentActivity() {

    private var currentIndex = 0
    private lateinit var viewModel: AddRemovePagerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = remember { SystemUiController(window) }
            val appTheme = remember { mutableStateOf(AppThemeState()) }
            BaseView(appThemeState = appTheme.value, systemUiController = systemUiController) {
                viewModel = viewModel()
                AddRemovePager()
            }
        }
    }

    @OptIn(ExperimentalPagerApi::class)
    @Composable
    fun AddRemovePager() {
        val pagerState = rememberPagerState()
        val scope = rememberCoroutineScope()
        val composableList by viewModel.composableListFlow.collectAsState(initial = viewModel.getComposableList())
        val dropdownSelectedState by viewModel.selectedDropdownValue.collectAsState()

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopBar(dropDownClick = {
                viewModel.selectedDropdownValue.value = it
            })
            if (composableList.isEmpty()) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(stringResource(R.string.text_empty_pager), color = Color.Gray)
                }
            }
            PreviewHorizontalPager(
                pageCount = composableList.size,
                pagerState = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                currentIndex = page
                composableList[page].first.invoke()
            }
            if (dropdownSelectedState.isNotEmpty()) {
                AddOrRemovePageDialog(
                    dropdownSelectedState,
                    composableList.size,
                    ::resetDialogValue
                ) {
                    if (dropdownSelectedState == OptionType.ADD.value) {
                        scope.launch {
                            val dynamicAddedItem: ComposableFun = {
                                DynamicAddedItem()
                            }
                            viewModel.addComposable(it, dynamicAddedItem)
                        }
                    } else {
                        scope.launch {
                            viewModel.removeComposable(it)
                        }
                    }
                    resetDialogValue()
                }
            }
        }
    }

    private fun resetDialogValue() {
        viewModel.positionValueTextField.value = ""
        viewModel.selectedDropdownValue.value = ""
        viewModel.isShowPositionError.value = false
    }

    @Composable
    fun DynamicAddedItem() {
        val number = viewModel.getComposableNumber(currentIndex)
        Box(
            modifier = Modifier
                .width(220.dp)
                .height(220.dp)
                .background(MaterialTheme.colors.background)
                .border(1.dp, MaterialTheme.colors.primary),
        ) {
            Text(
                text = "Page $number",
                color = MaterialTheme.colors.primary,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }

    @Composable
    fun TopBar(dropDownClick: (String) -> Unit) {
        val isShowDropdown by viewModel.isShowDropdown.collectAsState()

        TopAppBar(
            title = {
                Text(
                    stringResource(R.string.title_add_or_remove_page_in_pager),
                    maxLines = 1
                )
            },
            actions = {
                IconButton(onClick = { viewModel.isShowDropdown.value = !isShowDropdown }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "", tint = Color.White)
                }
                DropdownMenu(
                    expanded = isShowDropdown,
                    onDismissRequest = { viewModel.isShowDropdown.value = false },
                    modifier = Modifier
                        .width(120.dp)
                        .padding(10.dp)
                ) {
                    SingleLineTextWithIcon(Icons.Filled.Add, OptionType.ADD.value) {
                        dropDownClick(it)
                    }
                    SingleLineTextWithIcon(Icons.Filled.Delete, OptionType.DELETE.value) {
                        dropDownClick(it)
                    }
                }
            },
            navigationIcon = {
                IconButton(onClick = { onBackPressed() }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = null)
                }
            }
        )
    }

    @Composable
    fun AddOrRemovePageDialog(
        optionType: String,
        totalListCount: Int,
        dismissDialog: () -> Unit,
        addRemoveButtonClick: (Int) -> Unit
    ) {
        val positionValueTextField by viewModel.positionValueTextField.collectAsState()
        val isShowPositionError by viewModel.isShowPositionError.collectAsState()

        Dialog(
            onDismissRequest = {
                dismissDialog()
            },
        ) {
            Card(
                modifier = Modifier
                    .width(320.dp)
                    .height(190.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White)
            ) {
                Column {
                    Text(
                        "Enter position to $optionType page",
                        modifier = Modifier.padding(15.dp)
                    )
                    OutlinedTextField(
                        value = positionValueTextField,
                        onValueChange = {
                            viewModel.isShowPositionError.value = false
                            if (it.isDigitsOnly())
                                viewModel.positionValueTextField.value = it
                        },
                        modifier = Modifier.padding(start = 15.dp),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        isError = isShowPositionError,
                    )
                    if (isShowPositionError) {
                        Text(
                            stringResource(R.string.text_invalid_position),
                            modifier = Modifier.padding(start = 15.dp),
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    ) {
                        TextButton(onClick = {
                            dismissDialog()
                        }) {
                            Text(stringResource(R.string.btn_cancel))
                        }
                        TextButton(onClick = {
                            when {
                                validInputForAddComposable(positionValueTextField, optionType, totalListCount) || validInputForDeleteComposable(positionValueTextField, optionType, totalListCount) -> {
                                    addRemoveButtonClick(positionValueTextField.toInt())
                                }
                                else -> {
                                    viewModel.isShowPositionError.value = true
                                }
                            }
                        }) {
                            Text(optionType)
                        }
                    }
                }
            }
        }
    }

    private fun validInputForAddComposable(positionValueTextField: String, optionType: String, totalListCount: Int) : Boolean {
        return  positionValueTextField.isNotEmpty() && optionType == OptionType.ADD.value && positionValueTextField.toInt() <= totalListCount
    }

    private fun validInputForDeleteComposable(positionValueTextField: String, optionType: String, totalListCount: Int) : Boolean {
        return positionValueTextField.isNotEmpty() && optionType == OptionType.DELETE.value && positionValueTextField.toInt() < totalListCount
    }

    @Composable
    fun SingleLineTextWithIcon(icon: ImageVector, text: String, dropDownClick: (String) -> Unit) {
        Row(
            modifier = Modifier
                .clickable {
                    viewModel.isShowDropdown.value = false
                    dropDownClick(text)
                }
                .padding(10.dp)
        ) {
            Icon(icon, contentDescription = "")
            Text(text)
        }
    }
}

enum class OptionType(val value: String) {
    ADD("Add"),
    DELETE("Delete")
}