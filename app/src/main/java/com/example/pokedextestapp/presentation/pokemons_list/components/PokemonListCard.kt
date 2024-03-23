package com.example.pokedextestapp.presentation.pokemons_list.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintLayoutScope
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberImagePainter
import com.example.pokedextestapp.R
import com.example.pokedextestapp.domain.model.PokemonModel


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
    CardContent(
        pokemonName = pokemonName,
        typeList = typeList,
        pokemonNumber = pokemonNumber,
        remoteImageUrl = remoteImageUrl,
        onItemClick = onItemClick, url = url
    )
}

@Composable
fun CardContent(
    pokemonName: String,
    typeList: List<String>,
    pokemonNumber: Int,
    remoteImageUrl: String,
    onItemClick: (PokemonModel) -> Unit,
    url: String
) {
    Card(shape = RoundedCornerShape(16.dp),
        backgroundColor = Color(72, 208, 176),
        modifier = Modifier
            .wrapContentSize()
            .clickable {
                onItemClick(
                    PokemonModel(
                        pokemonName,
                        url,
                        pokemonNumber,
                        typeList,
                        remoteImageUrl
                    )
                )
            }
    )
    {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Gray)
                .padding(4.dp),
        ) {
            val (image, pokemonId, pokemonInfo) = createRefs()

            ImageSection(image, pokemonId, remoteImageUrl)
            PokemonIDSection(pokemonId, pokemonNumber)
            PokemonInfoSection(pokemonId, pokemonInfo, image, pokemonName, typeList)
        }
    }
}

@Composable
fun ConstraintLayoutScope.ImageSection(
    image: ConstrainedLayoutReference,
    titleConst: ConstrainedLayoutReference,
    url: String
) {
    Content(
        modifier = Modifier
            .constrainAs(image) {
                top.linkTo(titleConst.bottom)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            },
        url = url
    )
}

@Composable
fun ConstraintLayoutScope.PokemonIDSection(
    titleConst: ConstrainedLayoutReference,
    pokemonNumber: Int
) {
    Text(
        text = pokemonNumber.toString(),
        fontSize = 20.sp,
        color = Color.Black,
        maxLines = 3,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.constrainAs(titleConst) {
            top.linkTo(parent.top, margin = 8.dp)
            end.linkTo(parent.end, margin = 8.dp)
            width = Dimension.fillToConstraints
        }
    )
}

@Composable
fun ConstraintLayoutScope.PokemonInfoSection(
    titleConst: ConstrainedLayoutReference,
    authorConst: ConstrainedLayoutReference,
    image: ConstrainedLayoutReference,
    pokemonName: String,
    typeList: List<String>
) {
    PokemonInfo(
        pokemonName = pokemonName,
        listOfTypes = typeList,
        modifier = Modifier.constrainAs(authorConst) {
            top.linkTo(titleConst.bottom, margin = 4.dp)
            start.linkTo(parent.start, margin = 0.dp)
            end.linkTo(image.start)
            width = Dimension.fillToConstraints
        })
}

@Composable
fun PokemonInfo(pokemonName: String, listOfTypes: List<String>, modifier: Modifier) {
    Column(
        modifier = modifier
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

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)

        ) {
            StringListColumn(stringList = listOfTypes)
        }
    }
}

@Composable
fun Content(modifier: Modifier, url: String) {
    Box(
        modifier = modifier,
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
        Box(
            modifier = Modifier
                .wrapContentSize(), contentAlignment = Alignment.Center
        ) {
            Image(
                painter = rememberImagePainter(url),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp, 100.dp)
            )
        }
    }
}

@Composable
fun StringListColumn(stringList: List<String>) {
    Column {
        stringList.forEach { item ->
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(38.dp))
                    .background(Color.White.copy(alpha = 0.2f))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = item,
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
