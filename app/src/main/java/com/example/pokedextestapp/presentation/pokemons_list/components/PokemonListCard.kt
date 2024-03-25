package com.example.pokedextestapp.presentation.pokemons_list.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintLayoutScope
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.pokedextestapp.R
import com.example.pokedextestapp.domain.model.PokemonModel
import com.example.pokedextestapp.presentation.pokemons_list.PokemonsListViewModel
import java.util.Locale

@Composable
fun PokemonListCard(
    remoteImageUrl: String,
    pokemonNumber: Int,
    pokemonName: String,
    url: String,
    typeList: List<String>,
    viewModel: PokemonsListViewModel,
    onItemClick: (PokemonModel) -> Unit
) {
    CardContent(
        pokemonName = pokemonName,
        typeList = typeList,
        pokemonNumber = pokemonNumber,
        remoteImageUrl = remoteImageUrl,
        onItemClick = onItemClick, url = url, viewModel = viewModel
    )
}

@Composable
fun CardContent(
    pokemonName: String,
    typeList: List<String>,
    pokemonNumber: Int,
    remoteImageUrl: String,
    onItemClick: (PokemonModel) -> Unit,
    url: String, viewModel: PokemonsListViewModel
) {
    var dominantColor = remember { mutableStateOf(Color.Gray) }
    var painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(remoteImageUrl)
            .size(coil.size.Size.ORIGINAL)
            .build(),
        onSuccess = { success ->
            val drawable = success.result.drawable
            viewModel.calcDominantColor(drawable) { color ->
                dominantColor.value = color
            }
            println("Image loaded successfully: $dominantColor")
        },
        onLoading = {
            println("Image loading...")
        },
        onError = { error ->
            println("Error loading image: $error")
        }
    )

    if (painter.state is AsyncImagePainter.State.Success) {
        Card(shape = RoundedCornerShape(16.dp),
            backgroundColor = dominantColor.value,
            modifier = Modifier
                .wrapContentSize()
                .clickable {
                    onItemClick(
                        PokemonModel(
                            pokemonName,
                            url,
                            pokemonNumber,
                            typeList,
                            remoteImageUrl,
                            dominantColor.value
                        )
                    )
                }
        )
        {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
            ) {
                val (image, pokemonId, pokemonInfo) = createRefs()

                ImageSection(image, pokemonId, remoteImageUrl)
                PokemonIDSection(pokemonId, pokemonNumber)
                PokemonInfoSection(pokemonId, pokemonInfo, image, pokemonName, typeList)
            }
        }
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(
                color = MaterialTheme.colors.primary,
                modifier = Modifier.scale(0.5f)
            )
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
        text = "#$pokemonNumber",
        fontSize = 20.sp,
        color = Color.Black.copy(0.2f),
        maxLines = 3,
        fontWeight = FontWeight.Bold,
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
        modifier = modifier.padding(start = 3.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 5.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = pokemonName.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() },
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
                .offset(5.dp, 10.dp), colorFilter = ColorFilter.tint(Color.White)
        )
        Box(
            modifier = Modifier
                .wrapContentSize(), contentAlignment = Alignment.Center
        ) {
            Image(
                painter = rememberAsyncImagePainter(url),
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
