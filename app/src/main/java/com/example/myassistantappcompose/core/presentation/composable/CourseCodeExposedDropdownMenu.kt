package com.example.myassistantappcompose.core.presentation.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.myassistantappcompose.R

@ExperimentalMaterialApi
@Composable
fun CourseCodeExposedDropdownMenu(
    courseCodes: List<String>,
    selectedCode: String?,
    selectedCodeChange: (String) -> Unit,
) {
    var expanded by rememberSaveable { mutableStateOf(false)}

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            value = selectedCode ?: "Choose course code",
            onValueChange = {},
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            label = { Text(text = "Course Code", fontWeight = FontWeight.Bold) }
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            if (courseCodes.isEmpty()){
                DropdownMenuItem(onClick = {expanded = false }) {
                    Text(text = stringResource(R.string.empty_codes))
                }
            }
            courseCodes.forEach {
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                        selectedCodeChange(it)
                    }
                ) { Text(text = it)}
            }
        }

    }
}