package com.jetpack.compose.learning.readmoretextview.components

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow

/**
 * The main component to display our final textview, with clickable action text.
 *
 * It takes the following params
 * @param text The text to display with content text string and action text like 'Read More/Read Less'
 * @param modifier  Modifier for TextView
 * @param style TextStyle for TextView
 * @param softWrap Whether the text should break at soft line breaks.
 * If false, the glyphs in the text will be positioned as if there was unlimited horizontal space.
 * If softWrap is false, overflow and TextAlign may have unexpected effects.
 * @param overflow How visual overflow should be handled.
 * @param maxLines An optional maximum number of lines for the text to span, wrapping if necessary.
 * @param onTextLayout Callback that is executed when a new text layout is calculated.
 * A TextLayoutResult object that callback provides contains paragraph information, size of the text, baselines and other details.
 * The callback can be used to add additional decoration or functionality to the text. For example, to draw selection around the text.
 * @param inlineContent A map store composables that replaces certain ranges of the text. It's used to insert composables into text layout.
 * Here we are using it in case if you want to insert action icon with action text
 * @param onClick  Callback that is executed when users click the text. This callback is called with clicked character's offset.
 */
@Composable
fun MyClickableText(
    text: AnnotatedString,
    modifier: Modifier = Modifier,
    style: TextStyle = TextStyle.Default,
    softWrap: Boolean = true,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    inlineContent: Map<String, InlineTextContent> = mapOf(),
    onClick: (Int) -> Unit
) {
    val layoutResult = remember { mutableStateOf<TextLayoutResult?>(null) }
    val pressIndicator = Modifier.pointerInput(onClick) {
        detectTapGestures { pos ->
            layoutResult.value?.let { layoutResult ->
                onClick(layoutResult.getOffsetForPosition(pos))
            }
        }
    }

    BasicText(
        text = text,
        modifier = modifier.then(pressIndicator),
        style = style,
        softWrap = softWrap,
        overflow = overflow,
        maxLines = maxLines,
        onTextLayout = {
            layoutResult.value = it
            onTextLayout(it)
        },
        inlineContent = inlineContent,
    )
}