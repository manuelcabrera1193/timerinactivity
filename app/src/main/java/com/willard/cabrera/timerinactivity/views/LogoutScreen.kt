package com.willard.cabrera.timerinactivity.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.willard.cabrera.timerinactivity.SessionViewModel

@Composable
fun LogoutScreen(viewModel: SessionViewModel) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("Sesión cerrada por inactividad.")

        Spacer(Modifier.height(16.dp))

        IconButton(
            onClick = {
                viewModel.resetSession()
            },
        ) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = "Reiniciar sesión"
            )
        }
    }
}