package com.jetpack.compose.learning.readmoretextview.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jetpack.compose.learning.readmoretextview.data.CollapseConfig
import com.jetpack.compose.learning.readmoretextview.data.ExpandConfig
import com.jetpack.compose.learning.readmoretextview.utils.Constants

private const val MINIMIZED_MAX_LINES = 2


@Composable
fun ExpandableTextView(
    text: String,
    textSize: TextUnit = 16.sp,
    enableCollapse: Boolean = true,
    expandConfig: ExpandConfig = ExpandConfig(expandText = "Read More", expandTextSize = textSize, expandTextColor = Color.Blue, showUnderline = true, expandIcon = Icons.Outlined.KeyboardArrowDown),
    collapseConfig: CollapseConfig = CollapseConfig(
        collapseText = "Read Less", collapseTextSize = textSize, collapseTextColor = Color.Blue, showUnderline = true, collapseIcon = Icons.Outlined.KeyboardArrowUp
    ),
    enableActionIcons: Boolean = true,
    enableActionText: Boolean = true
) {
    var isExpanded by remember { mutableStateOf(false) }
    val textLayoutResultState = remember { mutableStateOf<TextLayoutResult?>(null) }
    var isClickable by remember { mutableStateOf(false) }
    val textLayoutResult = textLayoutResultState.value
    var actionText by remember { mutableStateOf(AnnotatedString(text)) }
    var actionIcon by remember { mutableStateOf(mapOf<String, InlineTextContent>()) }



    LaunchedEffect(textLayoutResult) {
        if (textLayoutResult == null) return@LaunchedEffect

        when {
            isExpanded -> {
//                default:  show less option always visible
                if (enableCollapse) {
                    if (enableActionIcons) {
                        actionIcon = mapOf(Constants.actionIconId to InlineTextContent(Placeholder(24.sp, 24.sp, PlaceholderVerticalAlign.TextCenter)) {
                            Image(
                                imageVector = collapseConfig.collapseIcon, contentDescription = ""
                            )
                        })
                    }
                    actionText = buildAnnotatedString {
                        withStyle(SpanStyle(fontSize = textSize)) {
                            append("$text ")
                        }
                        pushStringAnnotation("expand", "expand")
                        if (enableActionText) {
                            withStyle(
                                SpanStyle(
                                    color = collapseConfig.collapseTextColor,
                                    fontSize = collapseConfig.collapseTextSize,
                                    textDecoration = if (collapseConfig.showUnderline) TextDecoration.Underline else null
                                )
                            ) {
                                append(collapseConfig.collapseText)
                            }
                        }
                        if (enableActionIcons) {
                            appendInlineContent(Constants.actionIconId)
                        }
                    }
                    isClickable = true
                } else {
                    isClickable = false
                }
            }

            !isExpanded && textLayoutResult.hasVisualOverflow -> {
                val lastCharIndex = textLayoutResult.getLineEnd(MINIMIZED_MAX_LINES - 1)
                val adjustedText = text.substring(startIndex = 0, endIndex = lastCharIndex).dropLast(
                    if (enableActionIcons) (expandConfig.expandText.length * 1.6).toInt() else (expandConfig.expandText.length * 1.2).toInt()
                ).dropLastWhile { it == ' ' || it == '.' } + "... "

                if (enableActionIcons) {
                    actionIcon = mapOf(Constants.actionIconId to InlineTextContent(Placeholder(24.sp, 24.sp, PlaceholderVerticalAlign.TextCenter)) {
                        Image(
                            imageVector = expandConfig.expandIcon,
                            contentDescription = "",
                        )
                    })
                }
                actionText = buildAnnotatedString {
                    withStyle(SpanStyle(fontSize = textSize)) {
                        append(adjustedText)
                    }
                    pushStringAnnotation("expand", "expand")
                    if (enableActionText) {
                        withStyle(
                            SpanStyle(
                                color = expandConfig.expandTextColor, fontSize = expandConfig.expandTextSize, textDecoration = if (expandConfig.showUnderline) TextDecoration.Underline else null
                            )
                        ) {
                            append(expandConfig.expandText)
                        }
                    }
                    if (enableActionIcons) {
                        appendInlineContent(Constants.actionIconId)
                    }
                }
                isClickable = true
            }
        }
    }

    Box(modifier = Modifier.fillMaxWidth()) {
        MyClickableText(
            text = actionText,
            modifier = Modifier.padding(8.dp),
            maxLines = if (isExpanded) Int.MAX_VALUE else MINIMIZED_MAX_LINES,
            onTextLayout = { textLayoutResultState.value = it },
            inlineContent = actionIcon,
            onClick = { offset ->
                actionText.getStringAnnotations("expand", offset, offset).firstOrNull()?.let {
                    isExpanded = !isExpanded
                }
            },
        )
    }

}