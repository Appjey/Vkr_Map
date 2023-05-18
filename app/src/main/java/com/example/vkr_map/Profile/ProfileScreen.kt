package com.example.vkr_map.Profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.vkr_map.Login.activeUser

@Composable
@Preview(showBackground = true)
fun ProfileScreen() {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        Text(text = "Profile", style = MaterialTheme.typography.headlineLarge)
        Row {
            Text(text = "id:")
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = activeUser[0])
        }
        Row {
            Text(text = "Username:")
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = activeUser[1])
        }
        Row {
            Text(text = "Email:")
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = activeUser[2])
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileTopAppBar(title: String) {
    CenterAlignedTopAppBar(
        title = { Text(text = title, fontSize = MaterialTheme.typography.headlineLarge.fontSize) },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = Color.Black,
        ),
    )
}