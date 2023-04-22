package com.example.rickandmorty.pages

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.example.rickandmorty.R
import com.example.rickandmorty.entity.Characters
import com.example.rickandmorty.entity.Results
import com.example.rickandmorty.viewmodel.CharactersScreenViewModel
import com.example.rickandmorty.viewmodel.LocationViewModel
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(navController: NavController){

    val viewModel: LocationViewModel = hiltViewModel()
    val locations = viewModel.locations.collectAsLazyPagingItems()

    val selectionItem = remember { mutableStateOf(0) }
    val coroutineScope = rememberCoroutineScope()


    val viewModelCharacter: CharactersScreenViewModel = hiltViewModel()
    val charactersList = viewModelCharacter.charactersList.observeAsState(listOf())

    Scaffold(
        topBar = {
                 TopAppBar(backgroundColor = Color.White,
                 title = {
                     Spacer(modifier = Modifier.width(90.dp))
                     Image(painterResource(id = R.drawable.rickandmorty) , contentDescription = "")
                 })
        },
        content = {

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
               LazyLoadCardView(
                   locations = locations,
                   selectionItem = selectionItem,
                   viewModelCharacter = viewModelCharacter
             ,coroutineScope  )

               LazyColumCardView(
                   charactersList = charactersList,
                   navController = navController
               )





            }

        }
    )
}

@Composable
fun LazyLoadCardView(locations: LazyPagingItems<Results>,  selectionItem: MutableState<Int>, viewModelCharacter: CharactersScreenViewModel, coroutineScope: CoroutineScope){
    LazyRow(modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp,
            vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp
        )){

        itemsIndexed(locations) { it, location ->
            Card(
                modifier = Modifier
                    .padding(12.dp)
                    .size(165.dp, 50.dp)
                    .selectable(
                        selected = selectionItem.value == it,

                        onClick = {

                        }
                    ),
                border = BorderStroke(1.dp, Color.Black),
                shape = RoundedCornerShape(10.dp),
                backgroundColor =
                if (selectionItem.value == it)
                    Color.LightGray
                else
                    Color.White
            ) {
                Row(modifier = Modifier.clickable {

                    if (location!!.residents.isEmpty()){

                    }else{
                        val list = ArrayList<String>()

                        for (residentLink in location.residents){

                            val characterId = residentLink.split("/").last()
                            list.add(characterId)
                        }
                        val characterById = list.toString()
                        coroutineScope.launch {
                            viewModelCharacter.run { charactersInLocation(characterById) }
                        }

                    }

                    selectionItem.value =
                        if (selectionItem.value != it) {
                            it
                        }
                        else
                            0
                }) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = location!!.name, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        locations.apply {
            when{
                loadState.refresh is LoadState.Loading ->{
                    item {
                        LoadingItem()
                    }
                }
                loadState.append is LoadState.Loading ->{
                    item {
                        LoadingItem()
                    }
                }
                loadState.prepend is LoadState.Loading ->{
                    item {
                        LoadingItem()
                    }
                }
            }

        }

    }
}

@Composable
fun LazyColumCardView(charactersList:  State<List<Characters>>, navController: NavController){

    LazyColumn{
        items(
            count = charactersList.value.size,
            itemContent = {
                val character = charactersList.value[it]
                if ( character.gender == "Male"){
                    Card(
                        modifier = Modifier
                            .padding(8.dp, 12.dp)
                            .fillMaxWidth()
                            .height(400.dp),
                        border = BorderStroke(2.dp, Color.Blue),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Row(modifier = Modifier.clickable {

                            navController.currentBackStackEntry!!.savedStateHandle.set(
                                key = "character",
                                value = character
                            )
                            navController.navigate("character_detail_screen"){
                                popUpTo("character_detail_screen"){inclusive = true}
                            }
                        }) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                GlideImage(imageModel = character.image ,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(350.dp))

                                Text(text = character.name, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }

                if ( character.gender == "Female"){
                    Card(
                        modifier = Modifier
                            .padding(8.dp, 12.dp)
                            .fillMaxWidth()
                            .height(400.dp),
                        border = BorderStroke(2.dp, Color.Red),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Row(modifier = Modifier.clickable {

                            navController.currentBackStackEntry!!.savedStateHandle.set(
                                key = "character",
                                value = character
                            )
                            navController.navigate("character_detail_screen"){
                                popUpTo("character_detail_screen"){inclusive = true}
                            }
                        }) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                GlideImage(imageModel = character.image ,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(350.dp))

                                Text(text = character.name, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
                if ( character.gender == "unknown"){
                    Card(
                        modifier = Modifier
                            .padding(8.dp, 12.dp)
                            .fillMaxWidth()
                            .height(400.dp),
                        border = BorderStroke(3.dp, Color.Black),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Row(modifier = Modifier.clickable {

                            navController.currentBackStackEntry!!.savedStateHandle.set(
                                key = "character",
                                value = character
                            )
                            navController.navigate("character_detail_screen"){
                                popUpTo("character_detail_screen"){inclusive = true}
                            }

                        }) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                GlideImage(imageModel = character.image ,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(350.dp))

                                Text(text = character.name, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }

            }
        )
    }
}

@Composable
fun LoadingItem(){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = Color.Gray,
            modifier = Modifier
                .size(42.dp)
                .padding(8.dp),
            strokeWidth = 5.dp)
    }
}
