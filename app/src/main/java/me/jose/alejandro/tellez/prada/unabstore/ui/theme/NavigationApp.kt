package me.jose.alejandro.tellez.prada.unabstore.ui.theme

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import me.jose.alejandro.tellez.prada.unabstore.HomeScreen
import me.jose.alejandro.tellez.prada.unabstore.LoginScreen
import me.jose.alejandro.tellez.prada.unabstore.RegisterScreen

@Composable
fun NavigationApp() {
    val myNavController = rememberNavController()
    val myStartDestination: String = "login"

    NavHost(
        navController = myNavController,
        startDestination = myStartDestination // Aquí corriges el error tipográfico
    ) {
        composable("login") {
            LoginScreen(onClickRegister = {
                myNavController.navigate("register")
            }, onSuccesfullLogin = {myNavController.navigate("home"){
                popUpTo("login"){inclusive = true}
            }
            })
        }
        composable("register") {
            RegisterScreen(onClickBack = {
                myNavController.popBackStack()
            }, onSuccessfulRegister = {
                myNavController.navigate("home"){
                    popUpTo(0)
                }
            })
        }
        composable("home") {
            HomeScreen()
        }
    }
}
