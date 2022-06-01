package com.example.myassistantappcompose.core.presentation.composable

import android.app.TimePickerDialog
import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.myassistantappcompose.R
import com.example.myassistantappcompose.core.util.Constants
import java.text.SimpleDateFormat
import java.util.*

@ExperimentalMaterialApi
@Composable
fun TimePicker(
    context: Context,
    selectedTime: Date?,
    timeChange: (Date?) -> Unit
) {
    val calendar = Calendar.getInstance()
    val curHour = calendar.get(Calendar.HOUR)
    val curMinute = calendar.get(Calendar.MINUTE)

    val timePickerDialog = TimePickerDialog(
        context,
        { _, hour: Int, minute: Int ->
            val time = calendar.apply {
                set(Calendar.HOUR_OF_DAY,hour)
                set(Calendar.MINUTE,minute)
            }
            timeChange(time.time)
        }, curHour, curMinute, false
    )

    OutlinedButton(
        modifier = Modifier.fillMaxWidth(),
        onClick = { timePickerDialog.show() },
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            val formattedTime = selectedTime?.let { SimpleDateFormat(Constants.TIME_PATTERN, Locale.ROOT).format(it) }
            Text(text = formattedTime ?:"Click to pick time" , modifier = Modifier.align(Alignment.Center))
            if (selectedTime != null) {
                IconButton(
                    onClick = { timeChange(null) },
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .size(15.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Cancel,
                        contentDescription = stringResource(id = R.string.clear_text),
                    )
                }
            }

        }

    }
}