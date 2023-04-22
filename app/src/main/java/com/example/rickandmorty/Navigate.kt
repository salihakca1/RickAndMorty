package com.example.rickandmorty

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.rickandmorty.entity.Characters
import com.example.rickandmorty.pages.CharacterDetailScreen
import com.example.rickandmorty.pages.MainScreen
import com.example.rickandmorty.pages.SplashScreen


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TransitionsPage(){
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = "splash_screen" ){
            composable("splash_screen"){
                SplashScreen(navController)
            }

            composable("main_screen"){
                MainScreen(navController)
            }

            composable("character_detail_screen"){
                val character =  navController.previousBackStackEntry?.savedStateHandle?.get<Characters>("character")
                character?.let {
                    CharacterDetailScreen(navController = navController, character = character)
                }

            }
        }
}
