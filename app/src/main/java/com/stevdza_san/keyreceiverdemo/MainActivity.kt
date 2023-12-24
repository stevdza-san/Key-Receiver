package com.stevdza_san.keyreceiverdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.stevdza_san.keyreceiverdemo.screen.MainScreen
import com.stevdza_san.keyreceiverdemo.screen.MainViewModel
import com.stevdza_san.keyreceiverdemo.ui.theme.KeyReceivedDemoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KeyReceivedDemoTheme {
                val viewModel: MainViewModel = hiltViewModel()
                val apiKeysReady by viewModel.apiKeysReady
                val apiKeys by viewModel.apiKeys
                MainScreen(
                    apiKeysReady = apiKeysReady,
                    apiKeys = apiKeys,
                    onTryAgain = { viewModel.fetchData() }
                )
            }
        }
    }
}