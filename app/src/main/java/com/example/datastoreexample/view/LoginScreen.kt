package com.example.datastoreexample.view

import UserStore
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun LoginScreen() {

    val context = LocalContext.current
    val store = UserStore(context)
    val tokenUser = store.getAccessToken.collectAsState(initial = "")
    val tokenPassword = store.getAccessToken.collectAsState(initial = "")


    // State variables to store user input
    val userName = remember { mutableStateOf(TextFieldValue()) }
    val userPassword = remember { mutableStateOf(TextFieldValue()) }

    // Column to arrange UI elements vertically
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(40.dp)
    ) {

        // Welcome message
        Text(
            text = "Hello,\nWelcome to the login page",
            fontSize = 25.sp,
            color = Color.Blue,
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 50.dp, 0.dp, 0.dp)
        )

        // Username input field
        OutlinedTextField(
            value = userName.value,
            onValueChange = { userName.value = it },
            leadingIcon = { Icon(Icons.Default.Person, contentDescription = "person") },
            label = { Text(text = "username") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 20.dp, 0.dp, 0.dp)
        )

        // Password input field
        OutlinedTextField(
            value = userPassword.value,
            onValueChange = { userPassword.value = it },
            leadingIcon = { Icon(Icons.Default.Info, contentDescription = "password") },
            label = { Text(text = "password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 20.dp, 0.dp, 0.dp),
            visualTransformation = PasswordVisualTransformation()
        )

        // Login button
        OutlinedButton( onClick = {
            CoroutineScope(Dispatchers.IO).launch {
                store.saveToken(userName.value.text)
                store.saveToken(userPassword.value.text)

            }
                                 },
            modifier = Modifier.fillMaxWidth().padding(0.dp, 25.dp, 0.dp, 0.dp)) {
            Text(text = "Login",
                modifier = Modifier.fillMaxWidth().padding(5.dp),
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )
        }
        // Update userName whenever tokenUser.value changes
        LaunchedEffect(tokenUser.value) {
            userName.value = TextFieldValue(tokenUser.value)
            userPassword.value = TextFieldValue(tokenPassword.value)

        }
    }
}
