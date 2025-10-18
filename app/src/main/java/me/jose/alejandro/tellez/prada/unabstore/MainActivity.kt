package me.jose.alejandro.tellez.prada.unabstore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import me.jose.alejandro.tellez.prada.unabstore.ui.theme.UnabStoreTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UnabStoreTheme {
                NavigationApp()
            }

        }
    }
}
