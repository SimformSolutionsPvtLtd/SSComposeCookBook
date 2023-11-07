package com.jetpack.compose.learning.readmoretextview.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.Text
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
import androidx.compose.ui.res.stringResource
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
import com.jetpack.compose.learning.R
import com.jetpack.compose.learning.readmoretextview.data.CollapseConfig
import com.jetpack.compose.learning.readmoretextview.data.ExpandConfig
import com.jetpack.compose.learning.readmoretextview.utils.Constants

/**
Maximum number of lines to be shown in collapsed state, applicable only if the text is long enough
 */
private const val MINIMIZED_MAX_LINES = 2

/**
 * This is our main custom composable which is responsible to show ReadMoreTextView text component.
 * It can be used with various params as described below.
 *
 * @param text Text to be shown.
 * @param textSize Text size for the text to be shown
 * @param enableCollapse Whether to show the collapse action text after expanding text or not.
 * @param expandConfig Configuration data class for Expand action text
 * @param collapseConfig Configuration data class for Collapse action text
 * @param enableActionIcons Whether to show icons for expand and collapse option
 * @param enableActionText Whether to show text for expand and collapse option
 */
@Composable
fun ReadMoreTextView(
    text: String,
    textSize: TextUnit = 16.sp,
    enableCollapse: Boolean = true,
    expandConfig: ExpandConfig = ExpandConfig(
        expandText = stringResource(id = R.string.read_more),
        expandTextSize = textSize,
        expandTextColor = Color.Blue,
        showUnderline = true,
        expandIcon = Icons.Outlined.KeyboardArrowDown
    ),
    collapseConfig: CollapseConfig = CollapseConfig(
        collapseText = stringResource(id = R.string.read_less),
        collapseTextSize = textSize,
        collapseTextColor = Color.Blue,
        showUnderline = true,
        collapseIcon = Icons.Outlined.KeyboardArrowUp
    ),
    enableActionIcons: Boolean = true,
    enableActionText: Boolean = true
) {

    // if the text is not long enough, expand option won't be shown
    if (text.length <= 200) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = text,
            fontSize = textSize,
        )
        return
    }

    // Whether the text is in expanded state or not
    var isExpanded by remember { mutableStateOf(false) }
    // TextLayoutChange event state
    val textLayoutResultState = remember { mutableStateOf<TextLayoutResult?>(null) }
    // Whether the text is clickable or not,
    var isClickable by remember { mutableStateOf(false) }
    // TextLayout event state is stored here
    val textLayoutResult = textLayoutResultState.value
    // final text to be shown after adding the expand/collapse action text
    var finalText by remember { mutableStateOf(AnnotatedString(text)) }
    // Map for Icon to be shown for expand/collapse action
    var actionIcon by remember { mutableStateOf(mapOf<String, InlineTextContent>()) }

    LaunchedEffect(textLayoutResult) {
        if (textLayoutResult == null) return@LaunchedEffect
        when {
            isExpanded -> {
//                default:  collapse option will always be visible
                if (enableCollapse) {
                    if (enableActionIcons) {
                        // creating icon for collapse action
                        actionIcon = mapOf(
                            Constants.ACTION_ICON_ID to InlineTextContent(
                                Placeholder(24.sp, 24.sp, PlaceholderVerticalAlign.TextCenter)
                            ) {
                                Image(imageVector = collapseConfig.collapseIcon, contentDescription = "")
                            })
                    }

                    // building annotated string to display by concating text and collapse action text
                    finalText = buildAnnotatedString {
                        withStyle(SpanStyle(fontSize = textSize)) {
                            append("$text ")
                        }

                        // creating an annotation to detect action text click
                        pushStringAnnotation(Constants.EXPAND, Constants.EXPAND)
                        if (enableActionText) {
                            // assigning action text styles from collapseConfig instance
                            withStyle(
                                SpanStyle(
                                    color = collapseConfig.collapseTextColor,
                                    fontSize = collapseConfig.collapseTextSize,
                                    textDecoration = if (collapseConfig.showUnderline) TextDecoration.Underline else null,
                                    fontFamily = collapseConfig.collapseFontFamily,
                                )
                            ) {
                                append(collapseConfig.collapseText)
                            }
                        }
                        // appending action icon if applicable
                        if (enableActionIcons) {
                            appendInlineContent(Constants.ACTION_ICON_ID)
                        }
                    }
                    isClickable = true
                } else {
                    isClickable = false
                }
            }

            textLayoutResult.hasVisualOverflow -> {
                // creating shortened text to display with Expand action
                val lastCharIndex = textLayoutResult.getLineEnd(MINIMIZED_MAX_LINES - 1)
                val adjustedText = text.substring(startIndex = 0, endIndex = lastCharIndex).dropLast(
                    if (enableActionIcons) {
                        (expandConfig.expandText.length * 1.6).toInt()
                    } else {
                        (expandConfig.expandText.length * 1.2).toInt()
                    }
                ).dropLastWhile { it == ' ' || it == '.' } + Constants.ELLIPSES
                // appending action icon if applicable
                if (enableActionIcons) {
                    actionIcon = mapOf(Constants.ACTION_ICON_ID to InlineTextContent(
                        Placeholder(24.sp, 24.sp, PlaceholderVerticalAlign.TextCenter)
                    ) {
                        Image(
                            imageVector = expandConfig.expandIcon,
                            contentDescription = "",
                        )
                    })
                }
                // building annotated string to display by concating text and collapse action text
                finalText = buildAnnotatedString {
                    withStyle(SpanStyle(fontSize = textSize)) {
                        append(adjustedText)
                    }
                    // creating an annotation to detect action text click
                    pushStringAnnotation(Constants.EXPAND, Constants.EXPAND)
                    if (enableActionText) {
                        // assigning action text styles from expandConfig instance
                        withStyle(
                            SpanStyle(
                                color = expandConfig.expandTextColor,
                                fontSize = expandConfig.expandTextSize,
                                textDecoration = if (expandConfig.showUnderline) TextDecoration.Underline else null,
                                fontFamily = expandConfig.expandFontFamily
                            )
                        ) {
                            append(expandConfig.expandText)
                        }
                    }
                    // appending action icon if applicable
                    if (enableActionIcons) {
                        appendInlineContent(Constants.ACTION_ICON_ID)
                    }
                }
                isClickable = true
            }
        }
    }

    Box(modifier = Modifier.fillMaxWidth()) {
        MyClickableText(
            text = finalText,
            modifier = Modifier.padding(8.dp),
            maxLines = if (isExpanded) Int.MAX_VALUE else MINIMIZED_MAX_LINES,
            onTextLayout = { textLayoutResultState.value = it },
            inlineContent = actionIcon,
            onClick = { offset ->
                finalText.getStringAnnotations(Constants.EXPAND, offset, offset)
                    .firstOrNull()?.let {
                        isExpanded = !isExpanded
                    }
            },
        )
    }
}