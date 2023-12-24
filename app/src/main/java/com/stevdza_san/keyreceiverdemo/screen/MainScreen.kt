package com.stevdza_san.keyreceiverdemo.screen

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.stevdza_san.keyreceiverdemo.R
import com.stevdza_san.keyreceiverdemo.model.Keys
import com.stevdza_san.keyreceiverdemo.util.RequestState

@Composable
fun MainScreen(
    apiKeysReady: RequestState<Boolean>,
    apiKeys: Keys?,
    onTryAgain: () -> Unit
) {
    if (apiKeysReady.isLoading()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                trackColor = if (isSystemInDarkTheme()) Color.White.copy(alpha = 0.1f)
                else Color.Black.copy(alpha = 0.1f)
            )
        }
    } else if (apiKeysReady.isSuccess()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface),
            contentAlignment = Alignment.Center
        ) {
            if (apiKeys != null) {
                Text(text = "First: ${apiKeys.firstKey}\nSecond: ${apiKeys.secondKey}")
            } else {
                Text(text = "Api Keys are null.")
            }
        }
    } else if (apiKeysReady.isError()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
                .padding(all = 24.dp)
                .navigationBarsPadding()
                .animateContentSize(tween(durationMillis = 300)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .weight(1.2f),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                        .size(100.dp)
                        .alpha(0.15f),
                    painter = painterResource(id = R.drawable.round_warning_amber_24),
                    contentDescription = "Error Image",
                    tint = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    modifier = Modifier
                        .alpha(0.5f)
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    color = MaterialTheme.colorScheme.onSurface,
                    text = (apiKeysReady as RequestState.Error).parseError(),
                    textAlign = TextAlign.Center,
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize
                )
            }
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.BottomCenter
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(6.dp),
                    onClick = onTryAgain
                ) {
                    Text(text = "Try again", color = MaterialTheme.colorScheme.onPrimary)
                }
            }
        }
    }
}