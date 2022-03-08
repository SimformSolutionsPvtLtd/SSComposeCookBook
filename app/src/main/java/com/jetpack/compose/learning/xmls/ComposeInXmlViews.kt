package com.jetpack.compose.learning.xmls

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.jetpack.compose.learning.R
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController
import com.jetpack.compose.learning.theme.pink40
import com.jetpack.compose.learning.theme.pink50

class ComposeInXmlViews : AppCompatActivity() {

    private lateinit var xmlLayout: ConstraintLayout
    private lateinit var composeToolbar: CustomComposeToolbar
    private lateinit var composeView: ComposeView
    private lateinit var appCompatButton: AppCompatButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compose_in_xml)

        composeView = findViewById(R.id.composeView)
        composeToolbar = findViewById(R.id.composeToolbar)
        xmlLayout = findViewById(R.id.xmlLayout)
        appCompatButton = findViewById(R.id.btnAppCompatButton)

        composeToolbar.onBackPressed = {
            onBackPressed()
        }

        appCompatButton.setOnClickListener {
            composeView.isVisible = !composeView.isVisible
            appCompatButton.text = if (composeView.isVisible) "Click Me to hide Compose Views" else "Click Me to show Compose Views"
        }

        composeView.setContent {
            val systemUiController = remember { SystemUiController(window) }
            val appTheme = remember { mutableStateOf(AppThemeState()) }
            BaseView(appTheme.value, systemUiController) {
                ComposeContent()
            }
        }
    }

    @Composable
    @Preview
    private fun ComposeContent() {

        var isXmlViewsVisible by remember { mutableStateOf(true) }
        xmlLayout.isVisible = isXmlViewsVisible

        Card(
            backgroundColor = pink50,
            modifier = Modifier
                .fillMaxWidth(),
            elevation = 0.dp,
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(2.dp, pink40)
        ) {
            Column(
                modifier = Modifier.wrapContentHeight(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Compose Views :-",
                    modifier = Modifier.align(Alignment.Start).padding(10.dp, 10.dp)
                )
                Text(
                    text = "This is TextView in Compose."
                )
                Spacer(modifier = Modifier.height(10.dp))
                Button(onClick = {
                    isXmlViewsVisible = !isXmlViewsVisible
                }) {
                    Text(text = if (isXmlViewsVisible) "Click Me to hide Xml Views" else "Click Me to show Xml Views")
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }

}