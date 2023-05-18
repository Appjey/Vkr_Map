
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.vkr_map.Login.ErrorCard
import com.example.vkr_map.Login.LoginViewModel
import com.example.vkr_map.Login.activeUser
import com.example.vkr_map.Profile.ProfileTopAppBar






@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(viewModel: LoginViewModel, onAuthenticated: () -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loginError by remember { mutableStateOf("") }
    val context = LocalContext.current
    Scaffold(
        topBar = { ProfileTopAppBar(title = "") },
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
                        label = { Text("Username") },
                        modifier = Modifier.fillMaxWidth()
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

                    Button(
                        onClick = {
                            if (username.isEmpty() || password.isEmpty()) {
                                Toast.makeText(
                                    context,
                                    "Please fill all fields",
                                    Toast.LENGTH_SHORT
                                ).show()
                                loginError = "Please fill all fields"
                                return@Button
                            }

                            viewModel.loginUser(username, password) { success ->

                                if (success.id != 0) {
                                    Log.d("a", success.toString())
                                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                                    activeUser = listOf(
                                        success.id.toString(),
                                        success.username,
                                        success.email
                                    )

                                    onAuthenticated()
                                } else {
                                    if (success.username == "null" || success.email == "null") {
                                        Toast.makeText(
                                            context,
                                            "Username or Password is incorrect",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        loginError = "Username or Password is incorrect"
                                        return@loginUser
                                    } else {
                                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                                        loginError = success.username
                                        return@loginUser
                                    }
                                }
                            }
                        },
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .fillMaxWidth()
                    ) {
                        Text("Login")
                    }

                    Spacer(modifier = Modifier.height(16.dp))


                    ErrorCard(errorMessage = loginError, isVisible = loginError.isNotEmpty())
                }
            }
        }
    )
}




//@Preview(showBackground = true)
//@Composable
//fun LoginScreenPreview() {
//    Vkr_MapTheme {
//        Surface {
////            LoginScreen(ViewModelProvider.NewInstanceFactory().create(LoginViewModel::class.java), navController = navController)
//            //AuthScreen(viewModel = ViewModelProvider.NewInstanceFactory().create(AuthViewModel::class.java))
//        }
//    }
//}

