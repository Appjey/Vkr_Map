package com.example.vkr_map.Login

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.vkr_map.Profile.ProfileTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(viewModel: AuthViewModel, onRegistrated: () -> Unit) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var registrationError by remember { mutableStateOf("") }
    var agree by remember { mutableStateOf(true) }
    val context = LocalContext.current
    Scaffold(
        topBar = { ProfileTopAppBar( title = "") },
        //bottomBar = { BottomBar(navController = navController)},
        content = {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    OutlinedTextField(
                        value = username,
                        onValueChange = { username = it },
                        label = { Text("Name") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                    )

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        visualTransformation = PasswordVisualTransformation()
                    )
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.Start)
                            .fillMaxWidth()
                    ) {
                        Checkbox(checked = agree, onCheckedChange = { agree = it })
                        Text("I am agree to receive your newsletter and other promotional information.")
                    }

                    Button(
                        onClick = {
                            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                                Toast.makeText(
                                    context,
                                    "Please fill all fields",
                                    Toast.LENGTH_SHORT
                                ).show()
                                registrationError = "Please fill all fields"
                                return@Button
                            }

                            if (!email.contains("@")) {
                                Toast.makeText(
                                    context,
                                    "Please enter valid email",
                                    Toast.LENGTH_SHORT
                                ).show()
                                registrationError = "Please enter valid email"
                                return@Button
                            }
                            if (password.length < 8) {
                                Toast.makeText(
                                    context,
                                    "Password must be at least 8 characters",
                                    Toast.LENGTH_SHORT
                                ).show()
                                registrationError = "Password must be at least 8 characters"
                                return@Button
                            }

                            if (username.length > 32) {
                                Toast.makeText(
                                    context,
                                    "Username must be less than 20 characters",
                                    Toast.LENGTH_SHORT
                                ).show()
                                registrationError = "Username must be less than 20 characters"
                                return@Button
                            }

                            if (!password.contains(Regex("[0-9]"))) {
                                Toast.makeText(
                                    context,
                                    "Password must contain at least one digit",
                                    Toast.LENGTH_SHORT
                                ).show()
                                registrationError = "Password must contain at least one digit"
                                return@Button
                            }

                            if (!password.contains(Regex("[A-Z]"))) {
                                Toast.makeText(
                                    context,
                                    "Password must contain at least one uppercase letter",
                                    Toast.LENGTH_SHORT
                                ).show()
                                registrationError =
                                    "Password must contain at least one uppercase letter"
                                return@Button
                            }

                            viewModel.signupUser(username, password, email, "User") { success ->
                                if (success.message == "User created successfully") {
                                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                                    activeUser = listOf(username, password)


                                    onRegistrated()
                                } else {
                                    if (success.message == "Email already exists") {
                                        Toast.makeText(
                                            context,
                                            "User or Email already exists",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        registrationError = "User or Email already exists"
                                        return@signupUser
                                    } else {
                                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                                        registrationError = success.message
                                        return@signupUser
                                    }
                                }
                            }
                        },
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .fillMaxWidth()
                    ) {
                        Text("Sign Up")
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    ErrorCard(
                        errorMessage = registrationError,
                        isVisible = registrationError.isNotEmpty()
                    )
                }
            }
        }
    )
}