package com.mindorks.example.jetpack.compose.text

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.annotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class TextStylingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // ScrollableColumn is a composable that is used to keep all its children in vertical orientation
            // and at the same time it is scrollable also i.e. when the content will cover the whole screen,
            // then you can scroll down and see other contents. It behaves similar to Vertical ScrollView.
            ScrollableColumn {
                // Simple Text
                SetTextStyling(
                    "I am a Simple Text"
                )
                // Text with Font Size
                SetTextStyling(
                    "I am having font size as 24sp",
                    style = TextStyle(
                        fontSize = 24.sp
                    )
                )
                // Text with Font Weight
                SetTextStyling(
                    "I am a Bold text",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold
                    )
                )
                // Text with Font Style
                SetTextStyling(
                    "I am an Italic text",
                    style = TextStyle(
                        fontStyle = FontStyle.Italic
                    )
                )
                // Text with Font Style and Font Size
                SetTextStyling(
                    "I am an Italic text of size 16sp",
                    style = TextStyle(
                        fontStyle = FontStyle.Italic,
                        fontSize = 16.sp
                    )
                )
                // Text with Color
                SetTextStyling(
                    "I am a Simple Text having RED Color",
                    style = TextStyle(
                        color = Color.Red
                    )
                )
                // Text with Font Family
                SetTextStyling(
                    "My font family is Cursive",
                    style = TextStyle(
                        fontFamily = FontFamily.Cursive
                    )
                )
                // Text with Color, Font Weight, Font Style and Font Size
                SetTextStyling(
                    "I am a text with more than one Text Style",
                    style = TextStyle(
                        color = Color.Red,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Italic,
                        fontSize = 16.sp
                    )
                )
                // Text with Decoration
                SetTextStyling(
                    "I am an Underlined text",
                    style = TextStyle(
                        textDecoration = TextDecoration.Underline
                    )
                )
                SetTextStyling(
                    "I am a Strikethrough text",
                    style = TextStyle(
                        textDecoration = TextDecoration.LineThrough
                    )
                )
                // Text with Shadow
                SetTextStyling(
                    "I am a text having shadow",
                    style = TextStyle(
                        shadow = Shadow(
                            color = Color.Red, blurRadius = 8f,
                            offset = Offset(2f, 2f)
                        )
                    )
                )
                // Text with Background Color = Cyan
                SetTextStyling(
                    "I am a text having background color",
                    style = TextStyle(
                        background = Color.Cyan
                    )
                )
                // Text with Padding = 30sp
                SetTextStyling(
                    "I am a text having 16dp padding in first line",
                    style = TextStyle(
                        textIndent = TextIndent(firstLine = 30.sp)
                    )
                )
                // Text with Spans
                val spannableString = annotatedString {
                    append("I am a text having different colors.")
                    addStyle(style = SpanStyle(color = Color.Red), start = 0, end = 6)
                    addStyle(style = SpanStyle(color = Color.Magenta), start = 7, end = 18)
                    addStyle(style = SpanStyle(color = Color.Cyan), start = 19, end = 36)
                }
                Text(spannableString, modifier = Modifier.padding(16.dp))
                // Text with Letter Spacing = 4sp
                SetTextStyling(
                    "I am a text having 4Sp letter spacing",
                    style = TextStyle(
                        letterSpacing = TextUnit.Sp(4)
                    )
                )
                // Text with MaxLine
                SetTextStyling(
                    "I am an One Lined Text. If I cross the limit of one line then an Ellipsis(...)",
                    maxLines = 1
                )
                // Text with Alignment
                SetTextStyling(
                    "I am a Text having Justified alignment. " +
                            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do " +
                            "eiusmod tempor incididunt ut labore et dolore magna aliqua. " +
                            "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris " +
                            "nisi ut aliquip ex ea commodo consequat.",
                    style = TextStyle(
                        textAlign = TextAlign.Justify
                    )
                )
                SetTextStyling(
                    "I am a Text having Centered alignment. " +
                            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do " +
                            "eiusmod tempor incididunt ut labore et dolore magna aliqua. " +
                            "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris " +
                            "nisi ut aliquip ex ea commodo consequat.",
                    style = TextStyle(
                        textAlign = TextAlign.Center
                    )
                )
                // Text with Alignment and Line Height
                SetTextStyling(
                    "I am a Text having Centered alignment and 16sp line height. " +
                            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do " +
                            "eiusmod tempor incididunt ut labore et dolore magna aliqua. " +
                            "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris " +
                            "nisi ut aliquip ex ea commodo consequat.",
                    style = TextStyle(
                        textAlign = TextAlign.Center,
                        lineHeight = 16.sp
                    )
                )
            }
        }
    }
}

@Composable
fun SetTextStyling(displayText: String, style: TextStyle? = null, maxLines: Int? = null) {
    // style is used to put some styling to Compose UI elements. Here Default TextStyle is used.
    // overflow is used when the content of the Text crosses the limit. For example, if the maximum
    // allowed lines is one and the total number of lines is two then after 1st line, ellipsis(...)
    // will be shown
    Text(
        text = displayText,
        modifier = Modifier.padding(16.dp),
        style = style ?: TextStyle.Default,
        overflow = TextOverflow.Ellipsis,
        maxLines = maxLines ?: Int.MAX_VALUE
    )
    // There are many other styling that can be done on text. For example, textAlign, lineHeight,
    // letterSpacing, textDecoration, color, background, fontFamily, fontSize, fontStyle, fontWeight, etc
}