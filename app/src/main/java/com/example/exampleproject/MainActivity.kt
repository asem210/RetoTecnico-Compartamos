package com.example.exampleproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import com.example.exampleproject.navigation.TransactionNavHost
import com.example.exampleproject.ui.theme.ExampleProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExampleProjectTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TransactionNavHost(
                        onBackToHome = {
                            // Could navigate to a home screen or finish activity
                            finish()
                        }
                    )
                }
            }
        }
    }
}
