package com.jetpack.compose.learning

import com.jetpack.compose.learning.zoomview.ZoomViewActivity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.OutlinedButton
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jetpack.compose.learning.animation.AnimationActivity
import com.jetpack.compose.learning.appbar.TopAppBarActivity
import com.jetpack.compose.learning.bottomnav.BottomNavigationActivity
import com.jetpack.compose.learning.button.ButtonActivity
import com.jetpack.compose.learning.checkbox.CheckBoxActivity
import com.jetpack.compose.learning.constraintlayout.ConstraintLayoutActivity
import com.jetpack.compose.learning.dialog.DialogActivity
import com.jetpack.compose.learning.dropdownmenu.DropDownMenuActivity
import com.jetpack.compose.learning.floatingactionbutton.FloatingActionButtonActivity
import com.jetpack.compose.learning.model.Component
import com.jetpack.compose.learning.navigationdrawer.NavigationDrawerTypesActivity
import com.jetpack.compose.learning.radiobutton.RadioButtonActivity
import com.jetpack.compose.learning.slider.SliderActivity
import com.jetpack.compose.learning.snackbar.SnackBarActivity
import com.jetpack.compose.learning.switch.SwitchActivity
import com.jetpack.compose.learning.tabarlayout.TabBarLayoutActivity
import com.jetpack.compose.learning.textfield.TextFieldActivity
import com.jetpack.compose.learning.textstyle.SimpleTextActivity
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController
import com.jetpack.compose.learning.theme.ThemeActivity

@ExperimentalComposeUiApi
@ExperimentalFoundationApi
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val systemUiController = remember { SystemUiController(window) }
            val appTheme = remember { mutableStateOf(AppThemeState()) }
            BaseView(appTheme.value, systemUiController) {
                MainScreen()
            }
        }
    }

    @Preview
    @Composable
    fun MainScreen() {
        Column {
            TopAppBar(
                title = { Text(text = "ComposeCookBook", color = Color.White) },
            )
            LazyColumn {
                items(getComponents()) {
                    ButtonComponent(it.componentName, it.className)
                }
            }
        }
    }

    @Composable
    fun ButtonComponent(buttonText: String, className: Class<*>) {
        var context = LocalContext.current
        OutlinedButton(
            onClick = {
                context.startActivity(Intent(applicationContext, className))
            },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary.copy(0.04f)),
            border = BorderStroke(2.dp, MaterialTheme.colors.primary.copy(0.5f)),
        ) {
            Text(
                modifier = Modifier
                    .padding(8.dp),
                text = buttonText,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.primary
            )
        }
    }

    private fun getComponents(): List<Component> = listOf<Component>(
        Component("Animation", AnimationActivity::class.java),
        Component("App Bar", TopAppBarActivity::class.java),
        Component("Bottom Navigation", BottomNavigationActivity::class.java),
        Component("Button", ButtonActivity::class.java),
        Component("Checkbox", CheckBoxActivity::class.java),
        Component("Constraint Layout", ConstraintLayoutActivity::class.java),
        Component("Dialog", DialogActivity::class.java),
        Component("Dropdown menu", DropDownMenuActivity::class.java),
        Component("Floating Action Buttons", FloatingActionButtonActivity::class.java),
        Component("Navigation Drawer", NavigationDrawerTypesActivity::class.java),
        Component("Radio Button", RadioButtonActivity::class.java),
        Component("Slider", SliderActivity::class.java),
        Component("Snackbar", SnackBarActivity::class.java),
        Component("Switch", SwitchActivity::class.java),
        Component("Text", SimpleTextActivity::class.java),
        Component("TextField", TextFieldActivity::class.java),
        Component("Theme", ThemeActivity::class.java),
        Component("ZoomView", ZoomViewActivity::class.java),
        Component("Tab Bar", TabBarLayoutActivity::class.java)
    )
}