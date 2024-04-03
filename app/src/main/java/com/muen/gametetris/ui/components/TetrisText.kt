package com.muen.gametetris.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.muen.gametetris.ui.theme.LocalColors

// Just a wrapper so we don't have to add the colour to every Text() we use anywhere

@Composable
fun TetrisText(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        color = LocalColors.current.colors.ForegroundColor,
        modifier = modifier
    )
}

@Composable
fun TitleLarge(
    modifier: Modifier = Modifier,
    text: String,
    textAlign: TextAlign = TextAlign.Start
) {
    Text(
        modifier = modifier,
        text = text,
        color = MaterialTheme.colorScheme.primary,
        style = MaterialTheme.typography.titleLarge,
        textAlign = textAlign
    )
}

@Composable
fun DisplayLarge(
    modifier: Modifier = Modifier,
    text: String,
    textAlign: TextAlign = TextAlign.Start
) {
    Text(
        modifier = modifier,
        text = text,
        color = MaterialTheme.colorScheme.primary,
        style = MaterialTheme.typography.displayLarge,
        textAlign = textAlign
    )
}

@Composable
fun BodyLarge(modifier: Modifier = Modifier, text: String, textAlign: TextAlign = TextAlign.Start) {
    Text(
        modifier = modifier,
        text = text,
        color = MaterialTheme.colorScheme.primary,
        style = MaterialTheme.typography.bodyLarge,
        textAlign = textAlign
    )
}