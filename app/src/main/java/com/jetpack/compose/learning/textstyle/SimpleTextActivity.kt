package com.jetpack.compose.learning.textstyle

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jetpack.compose.learning.R
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController
import com.jetpack.compose.learning.theme.pink200
import com.jetpack.compose.learning.theme.pink700

@ExperimentalFoundationApi
class SimpleTextActivity : ComponentActivity() {

    val fontSize = 18.sp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = remember { SystemUiController(window) }
            val appTheme = remember { mutableStateOf(AppThemeState()) }
            BaseView(appTheme.value, systemUiController) {
                Column(Modifier.background(MaterialTheme.colors.background)) {
                    TopAppBar(
                        title = { Text("Text") },
                        navigationIcon = {
                            IconButton(onClick = { onBackPressed() }) {
                                Icon(Icons.Filled.ArrowBack, contentDescription = null)
                            }
                        }
                    )
                    TextExamples()
                }
            }
        }
    }


    @Preview
    @Composable
    fun TextExamples() {
        Column(
            modifier = Modifier.padding(16.dp).fillMaxHeight(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            SimpleText()
            TextFromResource()
            ColorText()
            TextColorFromResource()
            DifferentFonts()
            TextUnderLine()
            TextLineThrough()
            TextUnderLineWithLineThrough()
            OverFlowText()
            MultipleStylesInText()
            SelectableText()
            ClickableText()
            BackGroundText()
            ShadowText()
        }
    }

    @Composable
    fun SimpleText() {
        // Text composable is used to display some text
        Text(text = "This is Simple Text", Modifier.padding(bottom = 10.dp), fontSize = 15.sp)
    }

    @Composable
    fun TextFromResource() {
        // Text composable is used to display some text
        Text(
            stringResource(R.string.text_from_resource),
            Modifier.padding(bottom = 10.dp),
            fontSize = fontSize
        )
    }

    @Composable
    fun ShadowText() {
        // Text composable is used to display some text
        Text(
            "Shadow on text",
            Modifier.padding(bottom = 10.dp),
            fontSize = fontSize, style = TextStyle(
                shadow = Shadow(
                    color = pink700, blurRadius = 5f,
                    offset = Offset(2f, 2f)
                )
            )
        )

    }

    @Composable
    fun BoldText() {
        Text(
            "Text style is bold",
            Modifier.padding(bottom = 10.dp),
            fontWeight = FontWeight.Bold,
            fontSize = fontSize
        )
    }

    @Composable
    fun ColorText() {
        Text(
            "Text color is Blue ",
            Modifier.padding(bottom = 10.dp),
            color = Color.Blue,
            fontSize = fontSize
        )
    }

    @Composable
    fun TextColorFromResource() {
        Text(
            "Text color from colors.xml ",
            color = Color(R.color.teal_700),
            fontSize = fontSize,
            modifier = Modifier.padding(bottom = 10.dp)
        )
    }

    @Composable
    fun DifferentFonts() {
        Column {
            Text(
                "This is FontFamily.Serif",
                fontSize = fontSize,
                fontFamily = FontFamily.Serif,
                modifier = Modifier.padding(bottom = 10.dp)
            )
        }
    }

    @Composable
    fun TextLineThrough() {
        Column {
            Text(
                "This is text with Line Through",
                textDecoration = TextDecoration.LineThrough,
                fontSize = fontSize,
                fontFamily = FontFamily.Serif,
                modifier = Modifier.padding(bottom = 10.dp)
            )
        }
    }

    @Composable
    fun TextUnderLine() {
        Column {
            Text(
                "This is text with Underline",
                textDecoration = TextDecoration.Underline,
                fontSize = fontSize,
                fontFamily = FontFamily.Serif,
                modifier = Modifier.padding(bottom = 10.dp)
            )
        }
    }

    @Composable
    fun TextUnderLineWithLineThrough() {
        Column {
            Text(
                "This is text with Underline and LineThrough",
                textDecoration = TextDecoration.Underline.plus(TextDecoration.LineThrough),
                fontSize = fontSize,
                fontFamily = FontFamily.Serif,
                modifier = Modifier.padding(bottom = 10.dp)
            )
        }
    }

    @Composable
    fun OverFlowText() {
        Column {
            // Overflow text

            //Clip (Clip the overflowing text to fix its container.)
            Text(
                "This is Overflow text with Clip property. This is Overflow text with Clip property. This is Overflow text with Clip property.",
                fontSize = fontSize,
                overflow = TextOverflow.Clip,
                fontFamily = FontFamily.Serif,
                maxLines = 1,
                modifier = Modifier.padding(bottom = 10.dp)
            )

            //Ellipsis (Use an ellipsis to indicate that the text has overflowed.)
            Text(
                "This is Overflow text with Ellipsis property. This is Overflow text with Ellipsis property. This is Overflow text with Ellipsis property.",
                fontSize = fontSize,
                overflow = TextOverflow.Ellipsis,
                fontFamily = FontFamily.Serif,
                maxLines = 1,
                modifier = Modifier.padding(bottom = 10.dp)
            )

            //Visible (When overflow is visible, text may be rendered outside the bounds of the composable displaying the text)
            Text(
                "This is Overflow text with Visible property. This is Overflow text with Visible property. This is Overflow text with Visible property.",
                fontSize = fontSize,
                overflow = TextOverflow.Visible,
                fontFamily = FontFamily.Serif,
                maxLines = 1,
                modifier = Modifier.padding(bottom = 10.dp)
            )
        }
    }

    @Composable
    fun MultipleStylesInText() {
        Text(
            buildAnnotatedString {
                withStyle(style = SpanStyle(color = pink200)) {
                    append("Different")
                }
                append(" Color")
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray,
                    )
                ) {
                    append(" Same ")
                }
                append(" TEXT")
            },
            modifier = Modifier.padding(bottom = 10.dp), fontSize = fontSize,
        )
    }

    @Composable
    fun SelectableText() {
        SelectionContainer {
            Text(
                "Only this text is selectable",
                modifier = Modifier.padding(bottom = 10.dp),
                fontSize = fontSize
            )
        }
    }

    @Composable
    fun ClickableText() {
        Text(
            "Click this text",
            modifier = Modifier
                .padding(bottom = 10.dp)
                .clickable {
                    showToast("Clickable text")
                },
            fontSize = fontSize
        )

        Text(
            "Long Press this text",
            modifier = Modifier
                .padding(bottom = 10.dp)
                .combinedClickable(
                    onClick = { },
                    onLongClick = { showToast("Long Click text") }
                ),
            fontSize = fontSize
        )

        Text(
            "Double Click this text",
            modifier = Modifier
                .padding(bottom = 10.dp)
                .combinedClickable(
                    onClick = { },
                    onDoubleClick = { showToast("Double Click text") }
                ),
            fontSize = fontSize
        )
    }

    @Composable
    fun BackGroundText() {
        Text(
            "This text field has background",
            modifier = Modifier
                .padding(bottom = 10.dp)
                .background(pink200),
            fontSize = fontSize,
        )
    }

    private fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }
}
