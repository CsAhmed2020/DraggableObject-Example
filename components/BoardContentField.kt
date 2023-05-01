package com.csahmed.deardiary.ui.presentation.addEditBoardSession.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun BoardTextField(
    text: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    textStyle: TextStyle = TextStyle(),
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 50.dp, start = 28.dp, end = 30.dp, bottom = 35.dp)
    ) {
        BasicTextField(
            value = text,
            onValueChange = onValueChange,
            textStyle = textStyle,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        )
        }
    }
