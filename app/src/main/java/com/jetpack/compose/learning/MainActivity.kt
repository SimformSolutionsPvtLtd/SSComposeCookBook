package com.jetpack.compose.learning

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jetpack.compose.learning.checkbox.CheckBoxActivity
import com.jetpack.compose.learning.constraintlayout.ConstraintLayoutActivity
import com.jetpack.compose.learning.dialog.DialogActivity
import com.jetpack.compose.learning.model.Component
import com.jetpack.compose.learning.navigationdrawer.NavigationDrawerTypesActivity
import com.jetpack.compose.learning.radiobutton.RadioButtonActivity
import com.jetpack.compose.learning.slider.SliderActivity
import com.jetpack.compose.learning.textstyle.SimpleTextActivity

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Column {
                TopAppBar(
                    title = { Text(text = "ComposeCookBook", color = Color.White) },
                )
                Spacer(Modifier.height(16.dp))
                LazyColumn {
                    items(getComponents()) {
                        ButtonComponent(it.componentName, it.className)
                    }
                }
            }
        }
    }

    @Composable
    fun ButtonComponent(buttonText: String, className: Class<*>) {
        var context = LocalContext.current
        Button(
            onClick = {
                context.startActivity(Intent(applicationContext, className))
            },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)) {
            Text(
                modifier = Modifier
                    .padding(8.dp),
                text = buttonText,
                textAlign = TextAlign.Center,
                color = Color.White
            )
        }
    }

    private fun getComponents(): List<Component> = listOf<Component>(
        Component("Text", SimpleTextActivity::class.java),
        Component("Slider", SliderActivity::class.java),
        Component("Checkbox", CheckBoxActivity::class.java),
        Component("Radio Button", RadioButtonActivity::class.java),
        Component("Dialog", DialogActivity::class.java),
        Component("Navigation Drawer", NavigationDrawerTypesActivity::class.java),
        Component("Constraint Layout", ConstraintLayoutActivity::class.java)
    )
}