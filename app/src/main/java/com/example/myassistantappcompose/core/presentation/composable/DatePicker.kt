package com.example.myassistantappcompose.core.presentation.composable

import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
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

@Composable
fun DatePicker(
    context: Context,
    selectedDate: Date?,
    dateChange: (Date?) -> Unit
) {
    val calender = Calendar.getInstance()
    val curYear = calender.get(Calendar.YEAR)
    val curMonth = calender.get(Calendar.MONTH)
    val curDay = calender.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        context,
        {_, year: Int, month: Int, day: Int ->
            val date = calender.apply {
                set(Calendar.YEAR,year)
                set(Calendar.MONTH,month)
                set(Calendar.DAY_OF_MONTH,day)
            }
            dateChange(date.time)

        }, curYear, curMonth, curDay
    )

    OutlinedButton(
        modifier = Modifier.fillMaxWidth(),
        onClick = {
            datePickerDialog.apply {
                datePicker.minDate = calender.timeInMillis //disable dates before today
                show()
            }},
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            val formattedDate = selectedDate?.let {
                SimpleDateFormat(Constants.DATE_PATTERN, Locale.US).format(it)
            }
            Text(text = formattedDate ?: "Click to pick date", modifier = Modifier.align(Alignment.Center))
            if (selectedDate != null) {
                IconButton(
                    onClick = { dateChange(null)},
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