package com.example.myassistantappcompose.core.presentation.composable

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.myassistantappcompose.R
import com.example.myassistantappcompose.features.courses.presentation.CourseEvent

@Composable
fun StandardOutlinedTextField(
    modifier: Modifier = Modifier,
    value: String,
    @StringRes label: Int,
    onValueChanged: (String) -> Unit,
    singleLine: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    spacer: Dp? = 8.dp

) {
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = value,
        onValueChange = { onValueChanged(it) },
        label = { Text(stringResource(label)) },
        singleLine = singleLine,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType
        )
    )
    if (spacer != null){
        Spacer(modifier = Modifier.height(spacer))
    }

}