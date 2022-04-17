package com.example.myassistantappcompose.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.myassistantappcompose.core.presentation.ui.theme.MyAssistantAppComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAssistantAppComposeTheme {
                
            }
        }
    }
}
