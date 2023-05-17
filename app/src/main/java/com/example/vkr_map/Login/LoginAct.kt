import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.POST
import retrofit2.http.Query
import com.example.vkr_map.ui.theme.Vkr_MapTheme

data class User(
    val id: Int,
    val username: String,
    val password: String
)

interface LoginService {
    @POST("/api/login")
    suspend fun login(
        @Query("username") username: String,
        @Query("password") password: String,
    ): Response<User>


}

interface AuthService {
    @POST("/api/signup")
    suspend fun signup(
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("email") email: String,
        @Query("role") role: String
    ): Response<User>
}



object ApiService {
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://195.135.254.122:5433")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val loginService: LoginService = retrofit.create(LoginService::class.java)
    val authService: AuthService = retrofit.create(AuthService::class.java) // Fix the typo here
}






class LoginViewModel : ViewModel() {
    fun loginUser(username: String, password: String, onLoginResult: (String) -> Unit) {

        viewModelScope.launch {
            val response = ApiService.loginService.login(username, password)
            onLoginResult(response.message())
        }
    }


}

class AuthViewModel : ViewModel() {
    var user: User? = null

    fun signupUser(username: String, password: String, email: String, role: String, onSignupResult: (String) -> Unit) {
        viewModelScope.launch {
            val response = ApiService.authService.signup(username, password, email, role)
            onSignupResult(response.message())
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(viewModel: LoginViewModel) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loginError by remember { mutableStateOf("") }
    val context = LocalContext.current
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
                    Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                    loginError = "Please fill all fields"
                    return@Button
                }

                viewModel.loginUser(username, password) { success ->

                    if (success == "OK") {
                        Log.d("a", "Success")
                        Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                    } else {
                        if (success == "UNAUTHORIZED") {
                            Toast.makeText(context, "Username or Password is incorrect", Toast.LENGTH_SHORT).show()
                            loginError = "Username or Password is incorrect"
                            return@loginUser
                        }
                        else{
                            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                            loginError = success
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
        ErrorCard(errorMessage = loginError, isVisible = loginError.isNotEmpty())
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(viewModel: AuthViewModel) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var registrationError by remember { mutableStateOf("") }
    var agree by remember { mutableStateOf(true) }
    val context = LocalContext.current
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
        ){
            Checkbox(checked = agree, onCheckedChange =  {agree = it})
            Text("I am agree to receive your newsletter and other promotional information.")
        }

        Button(
            onClick = {
                if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                    registrationError = "Please fill all fields"
                    return@Button
                }

                if (!email.contains("@")) {
                    Toast.makeText(context, "Please enter valid email", Toast.LENGTH_SHORT).show()
                    registrationError = "Please enter valid email"
                    return@Button
                }
                if (password.length < 8) {
                    Toast.makeText(context, "Password must be at least 8 characters", Toast.LENGTH_SHORT).show()
                    registrationError = "Password must be at least 8 characters"
                    return@Button
                }

                if (username.length > 32) {
                    Toast.makeText(context, "Username must be less than 20 characters", Toast.LENGTH_SHORT).show()
                    registrationError = "Username must be less than 20 characters"
                    return@Button
                }

                if (!password.contains(Regex("[0-9]"))) {
                    Toast.makeText(context, "Password must contain at least one digit", Toast.LENGTH_SHORT).show()
                    registrationError = "Password must contain at least one digit"
                    return@Button
                }

                if (!password.contains(Regex("[A-Z]"))) {
                    Toast.makeText(context, "Password must contain at least one uppercase letter", Toast.LENGTH_SHORT).show()
                    registrationError = "Password must contain at least one uppercase letter"
                    return@Button
                }

                viewModel.signupUser(username, password, email, "User") { success ->
                    if (success == "CREATED") {
                        Log.d("a", "Success")
                        Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                    } else {
                        if (success == "CONFLICT") {
                            Toast.makeText(context, "User or Email already exists", Toast.LENGTH_SHORT).show()
                            registrationError = "User or Email already exists"
                            return@signupUser
                        }
                        else{
                            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                            registrationError = success
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
        ErrorCard(errorMessage = registrationError, isVisible = registrationError.isNotEmpty())
    }
}

@Composable
fun ErrorCard(errorMessage: String, isVisible: Boolean) {
    if (isVisible) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(12.dp),
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Ошибка",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.Red
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = errorMessage,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

fun PasswdCheck(password: String){

}


@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    Vkr_MapTheme {
        Surface {
//            LoginScreen(viewModel = ViewModelProvider.NewInstanceFactory().create(LoginViewModel::class.java))
            AuthScreen(viewModel = ViewModelProvider.NewInstanceFactory().create(AuthViewModel::class.java))
        }
    }
}