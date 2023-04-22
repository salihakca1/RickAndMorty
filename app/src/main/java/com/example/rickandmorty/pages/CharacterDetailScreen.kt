package com.example.rickandmorty.pages

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.rickandmorty.R
import com.example.rickandmorty.entity.Characters
import com.skydoves.landscapist.glide.GlideImage
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "SimpleDateFormat")
@Composable
fun CharacterDetailScreen(navController: NavController, character: Characters){

         val episodesCharacter = episodes(list = character.episode)
         val createdDate = getDate(date = character.created)

         val avenir = FontFamily(
             Font(R.font.avenir_lt_std_65_medium, FontWeight.Normal)
           )

    Scaffold(
            topBar =  {
        TopAppBar(
            backgroundColor = Color.White,
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically
                        , horizontalArrangement = Arrangement.SpaceEvenly) {
                        Icon(painter = painterResource(id = R.drawable.baseline_arrow_back_ios_24), contentDescription = "",
                        modifier = Modifier.clickable {
                            navController.navigate("main_screen")
                        })
                        Spacer(modifier = Modifier.width(70.dp))
                        Text(text = character.name, fontSize = 25.sp, fontWeight = FontWeight.Bold)
                    }
                })
        },
            content = {

               CharacterDetailCardView(
                   character = character,
                   avenir = avenir,
                   episodesCharacter,
                   createdDate)
            }

        )
}

@Composable
fun CharacterDetailCardView(character: Characters ,avenir: FontFamily,episodesCharacter: String,createdDate: String?){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        GlideImage(imageModel = character.image ,
            modifier = Modifier
                .size(360.dp, 300.dp)
                .padding(50.dp, 20.dp))
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp, 5.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Status: ", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(40.dp))
                Text(text = character.status, fontSize = 22.sp, fontFamily = avenir)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(text = "Spacy: ", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(42.dp))
                Text(text = character.species, fontSize = 22.sp, fontFamily = avenir)
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(text = "Gender: ", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(32.dp))
                Text(text = character.gender, fontSize = 22.sp, fontFamily = avenir)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(text = "Origin: ", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(44.dp))
                Text(text = character.origin.name, fontSize = 22.sp, fontFamily = avenir)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(text = "Location: ", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(20.dp))
                Text(text = character.location.name, fontSize = 22.sp, fontFamily = avenir)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(text = "Episode: ", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(27.dp))
                Text(text = episodesCharacter, fontSize = 22.sp, fontFamily = avenir)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(modifier = Modifier.size(105.dp, 60.dp), text = "Created at (in API):", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(13.dp))
                Text(text = createdDate!!, fontSize = 22.sp, fontFamily = avenir)
            }

        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}
@SuppressLint("SimpleDateFormat")
@Composable
fun getDate(date: String): String? {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    val createdDate = dateFormat.parse(date)

    return createdDate?.let { SimpleDateFormat("d MMM yyyy, HH:mm:ss").format(it) }
}

@Composable
fun episodes(list: List<String>): String {
    val episodesList = ArrayList<String>()

    for (residentLink in list) {
        val characterId = residentLink.split("/").last()
        episodesList.add(characterId)
    }

    return episodesList.toString().replace("[", "").replace("]", "")
}