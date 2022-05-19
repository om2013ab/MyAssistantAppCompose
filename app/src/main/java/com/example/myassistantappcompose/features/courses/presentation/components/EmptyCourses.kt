package com.example.myassistantappcompose.features.courses.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myassistantappcompose.R

@Composable
fun EmptyCourses() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(120.dp).alpha(0.70f),
            painter = painterResource(R.drawable.empty),
            contentDescription = stringResource(R.string.no_courses),
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            modifier = Modifier.alpha(0.5f),
            text = stringResource(id = R.string.no_courses),
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            modifier = Modifier.fillMaxWidth().alpha(0.5f),
            text = stringResource(id = R.string.no_courses_msg),
            textAlign = TextAlign.Center
        )
    }
}