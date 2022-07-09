package com.example.myassistantappcompose.core.presentation.composable

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myassistantappcompose.core.presentation.ui.theme.PrimaryColor

@Composable
fun ExpandingText(
    modifier: Modifier = Modifier,
    text: String,
    multiSelectionMode: Boolean
) {
    var isExpanded by remember { mutableStateOf(false) }
    var isClickable by remember { mutableStateOf(false) }
    val textLayoutResultState = remember { mutableStateOf<TextLayoutResult?>(null) }
    var finalText by remember { mutableStateOf(AnnotatedString(text)) }

    val textLayoutResult = textLayoutResultState.value
    LaunchedEffect(textLayoutResult) {
        if (textLayoutResult == null) return@LaunchedEffect

        when {
            isExpanded -> {
                finalText = buildAnnotatedString {
                    append(text)
                    withStyle(
                        SpanStyle(
                            color = PrimaryColor,
                            fontWeight = FontWeight.SemiBold
                        )
                    ) {
                        append("  Show Less")
                    }
                }
            }
            !isExpanded && textLayoutResult.hasVisualOverflow -> {
                val lastCharIndex = textLayoutResult.getLineEnd(MINIMIZED_MAX_LINES - 1)
                val showMoreString = "... Show More"
                val adjustedText = text
                    .substring(startIndex = 0, endIndex = lastCharIndex)
                    .dropLast(showMoreString.length)
                    .dropLastWhile { it == ' ' || it == '.' }
                finalText = buildAnnotatedString {
                    append(adjustedText)
                    withStyle(SpanStyle(
                        color = PrimaryColor,
                        fontWeight = FontWeight.SemiBold
                    )) {
                        append(showMoreString)
                    }
                }
                isClickable = true
            }
        }
    }

    Text(
        text = finalText,
        maxLines = if (isExpanded) Int.MAX_VALUE else MINIMIZED_MAX_LINES,
        onTextLayout = { textLayoutResultState.value = it },
        modifier = modifier
            .clickable(enabled = isClickable && !multiSelectionMode) { isExpanded = !isExpanded }
            .padding(start = 8.dp, end = 8.dp, bottom = 16.dp)
            .animateContentSize(),
        style = TextStyle(
            textAlign = TextAlign.Center,
            lineHeight = 20.sp
        )
    )
}

private const val MINIMIZED_MAX_LINES = 2