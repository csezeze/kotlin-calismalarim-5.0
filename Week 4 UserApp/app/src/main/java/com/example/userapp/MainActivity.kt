package com.example.userapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.userapp.ui.screen.UserListScreen
import com.example.userapp.ui.theme.UserAppTheme
import com.example.userapp.viewmodel.UserViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UserAppTheme {
                UserListScreen(viewModel = viewModel)
            }
        }
    }
}