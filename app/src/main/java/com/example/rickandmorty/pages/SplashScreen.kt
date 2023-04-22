package com.example.rickandmorty.pages


import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.rickandmorty.R
import com.example.rickandmorty.viewmodel.DataStoreViewModel
import kotlinx.coroutines.*

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun SplashScreen(navController: NavController){
        val isFirstTime = remember { mutableStateOf(false) }
        val isFirst = remember { mutableStateOf(false) }
        val coroutineScope = rememberCoroutineScope()
        val viewModel: DataStoreViewModel =  hiltViewModel()

        coroutineScope.launch {
            isFirst.value = viewModel.readRunInfo()
            isFirstTime.value = true
        }

        LaunchedEffect(isFirstTime.value){
            delay(3000L)
            navController.navigate("main_screen")
            viewModel.saveRunInfo(true)
        }

        if (isFirstTime.value){
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(painter = painterResource(id = R.drawable.rickandmorty), contentDescription = "")
                Spacer(modifier = Modifier.height(60.dp))
                if (isFirst.value){
                    Text(text = "Hello!", fontSize = 35.sp, fontWeight = FontWeight.Bold)
                }else{
                    Text(text = "Welcome!", fontSize = 35.sp, fontWeight = FontWeight.Bold)

                }

            }
        }

}
