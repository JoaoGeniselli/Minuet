package com.dosei.music.repertoire

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.dosei.music.repertoire.ui.navigation.MinuetApp
import com.dosei.music.repertoire.ui.theme.MinuetTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MinuetTheme {
                MinuetApp()
            }
        }
    }
}
