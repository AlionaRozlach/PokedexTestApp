package com.example.pokedextestapp.presentation.pokemons_list.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberImagePainter
import com.example.pokedextestapp.R
import com.example.pokedextestapp.domain.model.PokemonModel
import com.example.pokedextestapp.presentation.pokemons_list.CustomBackgroundColumn


@Composable
fun PokemonListCard(
    remoteImageUrl: String,
    pokemonNumber: Int,
    pokemonName: String,
    url: String,
    typeList: List<String>,
    onItemClick: (PokemonModel) -> Unit,
    content: @Composable () -> Unit
) {
CardContent(pokemonName = pokemonName, typeList = typeList, pokemonNumber = pokemonNumber, remoteImageUrl = remoteImageUrl,
//    click = onItemClick,url = url
Modifier.wrapContentSize())
//    Card(
//        shape = RoundedCornerShape(16.dp),
//        backgroundColor = Color(72, 208, 176),
//        modifier = Modifier
//            .size(width = 200.dp, height = 155.dp)
//            .clickable { onItemClick(PokemonModel(pokemonName,url,pokemonNumber,typeList,remoteImageUrl)) }
//
//    ) {
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Color(72, 208, 176))
//        ) {
//
//            Text(
//                text = pokemonNumber.toString(),
//                fontSize = 20.sp,
//                color = Color.Black.copy(alpha = 0.2f),
//                modifier = Modifier
//                    .padding(8.dp)
//                    .align(Alignment.TopEnd)
//            )
//
//            Column(
//                modifier = Modifier
//                    .align(Alignment.TopStart)
//                    .padding(start = 10.dp, top = 40.dp, bottom = 10.dp)
//                    .widthIn(max = 95.dp)
//
//            ) {
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth(),
//                    horizontalArrangement = Arrangement.SpaceBetween
//                ) {
//                    Text(
//                        text = pokemonName,
//                        color = Color.White,
//                        fontWeight = FontWeight.Bold,
//                        fontSize = 20.sp,
//                        maxLines = 1,
//                        overflow = TextOverflow.Ellipsis
//                    )
//                    Spacer(modifier = Modifier.width(16.dp))
//                }
//
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//
//                ) {
//                    StringListColumn(stringList = typeList)
//                }
//            }
//
//            Image(
//                painter = imagePainter,
//                contentDescription = null,
//                contentScale = ContentScale.Crop,
//                modifier = Modifier
//                    .size(95.dp)
//                    .align(Alignment.BottomEnd)
//                    .offset(5.dp, 10.dp)
//            )
//
//            Image(
//                painter = rememberImagePainter(remoteImageUrl),
//                contentDescription = null,
//                contentScale = ContentScale.Crop,
//                modifier = Modifier
//                    .size(200.dp, 200.dp)
//                    .align(Alignment.BottomEnd)
//                    .offset(55.dp, 30.dp)
//            )
//
//            content()
//        }
//    }

}
@Composable
fun CardContent( pokemonName: String,
                 typeList: List<String>,
                 pokemonNumber: Int, remoteImageUrl: String,
//                 click:  (PokemonModel) -> Unit,  url: String,
                 modifier: Modifier
)
{
    Card(
        modifier = modifier
            .padding(16.dp)
            .background(Color.Green)
            // Ensure consistent height
            .wrapContentSize()
//            .size(200.dp, 155.dp)
//                .clickable { click(PokemonModel(pokemonName,url,pokemonNumber,typeList,remoteImageUrl)) }
    ) {
        Column(
            modifier = modifier
                .wrapContentSize()
                .background(Color.Green)
        ) {
            TextOnRightSide(text = pokemonNumber.toString())

            ConstraintLayout(
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                val (title, type) = createRefs()

                Content(
                    modifier = modifier
                        .constrainAs(type) {
                            bottom.linkTo(parent.bottom)
                            end.linkTo(parent.end)
                            width = Dimension.wrapContent
                            height = Dimension.wrapContent
                        },remoteImageUrl
                )
                PokemonInfo(pokemonName, typeList,
                    modifier = modifier.constrainAs(title) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start, 16.dp)
                        end.linkTo(type.start)
                        width = Dimension.fillToConstraints
                    }
                )
            }
        }
    }
}
@Composable
fun PokemonInfo(pokemonName: String, listOfTypes: List<String>, modifier: Modifier) {
    Column(
        modifier = modifier
            .wrapContentSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = pokemonName,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.width(16.dp))
        }

//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .align(Alignment.CenterHorizontally)
//
//        ) {
//            StringListColumn(stringList = listOfTypes)
//        }
    }
}

@Composable
fun TextOnRightSide(text: String) {
    Text(
        text = text,
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 16.dp),
        textAlign = TextAlign.End
    )
}

@Composable
fun Content(modifier: Modifier, url: String) {
    Box(
        modifier = modifier.wrapContentSize(),
    ) {
        Image(
                painter = painterResource(id = R.drawable.pokemon_ball30),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(95.dp)
                    .align(Alignment.BottomEnd)
                    .offset(5.dp, 10.dp)
            )

            Image(
                painter = rememberImagePainter(url),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(200.dp, 200.dp)
                    .align(Alignment.BottomEnd)
                    .offset(55.dp, 30.dp)
            )
    }
}

@Composable
fun StringListColumn(stringList: List<String>) {
    LazyColumn {
        items(stringList.size) { index ->
            val text = stringList[index]
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(38.dp))
                    .background(Color.White.copy(alpha = 0.2f))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = text,
                    color = Color.White,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Preview
@Composable
fun ExampleContent() {
    val stringList: List<String> = mutableListOf(
        "Item 1sergesrgseargsergerg",
        "Item 2aergegraergaegaeg",
        "Item aegaergegregregraegerg"
    )
//    PokemonListCard(
//        imagePainter = painterResource(R.drawable.pokemon_ball30), // Ваше локальное изображение из ресурсов
//        remoteImageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png",
//        1,
//        "BUlbasaur","11111111111111111111111111",
//        stringList
//    ) {
//        // Весь остальной контент карточки
//    }

    CardContent("UAAAAAAASSSSSSSSSSSSSAAAA", stringList, 10, remoteImageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png", Modifier.wrapContentSize())
}