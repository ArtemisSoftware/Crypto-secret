package com.artemissoftware.cryptosecret

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@Composable
fun CryptoScreen(dataStore: DataStore<UserSettings>) {
    var username by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var settings by remember {
        mutableStateOf(UserSettings())
    }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
    ) {
        TextField(
            value = username,
            onValueChange = { username = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(text = "Username") },
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(text = "Password") },
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            Button(onClick = {
                scope.launch {
                    dataStore.updateData {
                        UserSettings(
                            username = username,
                            password = password,
                        )
                    }
                }
            }) {
                Text(text = "Save")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                scope.launch {
                    settings = dataStore.data.first()
                }
            }) {
                Text(text = "Load")
            }
        }
        Text(text = settings.toString())
    }
}
