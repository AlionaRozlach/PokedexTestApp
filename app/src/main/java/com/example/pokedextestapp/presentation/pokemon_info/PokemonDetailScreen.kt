package com.example.pokedextestapp.presentation.pokemon_info

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintLayoutScope
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pokedextestapp.R
import com.ramcosta.composedestinations.annotation.Destination

@Composable
@Destination
fun PokemonDetailScreen(
    name: String,
    viewModel: PokemonDetailViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()

    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {
        if (state.isLoading) {
            // Show loading indicator
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {

            state.pokemon?.let { PokemonDetailContent(modifier = Modifier.fillMaxSize(), it.name) }
        }
    }
}

@Composable
fun PokemonDetailContent(modifier: Modifier, pokemon: String) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        DetailScreenBg(modifier = Modifier.matchParentSize(), pokemonName = pokemon) // Рисуем фон

        CardWithPokemonInfo(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 100.dp)
        )
    }
}


@Composable
fun DetailScreenBg(modifier: Modifier, pokemonName: String) {
    Box(
        modifier = modifier
            .background(color = Color(72, 208, 176))
            .fillMaxSize()
    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxWidth()
        ) {
            val (leftImage, text, rightImage) = createRefs()

            Box(
                modifier = Modifier
                    .size(170.dp)
                    .offset(-70.dp, -70.dp)
                    .constrainAs(leftImage) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                    }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ornament),
                    contentDescription = "",
                    modifier = Modifier.fillMaxSize(),
                    colorFilter = ColorFilter.tint(Color.White)
                )

                Icon(
                    imageVector = androidx.compose.material.icons.Icons.Default.ArrowBack,
                    contentDescription = "Icon",
                    modifier = Modifier
                        .size(24.dp)
                        .offset(20.dp, -35.dp)
                        .align(Alignment.BottomCenter),
                    tint = Color.White
                )
            }

            Text(
                text = pokemonName,
                modifier = Modifier
                    .constrainAs(text) {
                        top.linkTo(parent.top)
                        start.linkTo(leftImage.end)
                        end.linkTo(rightImage.start)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.wrapContent
                    }
                    .padding(bottom = 70.dp),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = Color.White
            )

            Box(
                modifier = Modifier
                    .size(180.dp)
                    .offset(70.dp, -40.dp)
                    .constrainAs(rightImage) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.pokeball),
                    contentDescription = "",
                    modifier = Modifier.fillMaxSize(),
                    colorFilter = ColorFilter.tint(Color.White)
                )
            }
        }
    }
}

@Composable
fun CardWithPokemonInfo(modifier: Modifier) {
    val scrollState = rememberScrollState()
    Card(
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        backgroundColor = Color.White,
        modifier = modifier
            .fillMaxSize()
    )
    {
        ConstraintLayout(
            modifier = Modifier
                .verticalScroll(state = scrollState)
                .fillMaxSize()
                .padding(40.dp),
        ) {
            val (desc, size, info, type) = createRefs()

            DescriptionTextSection(desc, size)
            SizeSection(size, desc, info)
            InfoSection(info, size, type)
            TypeSection(type, info)
        }
    }
}

@Composable
fun ConstraintLayoutScope.DescriptionTextSection(
    desc: ConstrainedLayoutReference,
    size: ConstrainedLayoutReference
) {
    Text(
        text = "Bulbasaur can be seen in bright sunlight. There is a seed on its back. By soaking up the sun's rays, the seed grows progressively larger. ",
        fontSize = 14.sp,
        color = Color(48, 57, 67),
        overflow = TextOverflow.Ellipsis,
        textAlign = TextAlign.Start,
        modifier = Modifier.constrainAs(desc) {
            top.linkTo(parent.top)
            end.linkTo(parent.end)
            bottom.linkTo(size.top)
            width = Dimension.fillToConstraints
        }
    )
}

@Composable
fun ConstraintLayoutScope.TypeSection(
    type: ConstrainedLayoutReference,
    info: ConstrainedLayoutReference
) {
    Column(modifier = Modifier.constrainAs(type) {
        top.linkTo(info.bottom)
        start.linkTo(parent.start)
    }, horizontalAlignment = Alignment.Start) {
        val stringList = listOf(
            "String 1",
            "String 2",
            "String 3",
            "String 4",
            "String 4",
            "String 4",
            // Add more strings as needed
        )
        TypeTitle()
        TypeDetail(stringList = stringList)
    }
}

@Composable
fun ConstraintLayoutScope.InfoSection(
    info: ConstrainedLayoutReference,
    size: ConstrainedLayoutReference, type: ConstrainedLayoutReference
) {
    Column(
        modifier = Modifier
            .constrainAs(info) {
                top.linkTo(size.bottom, margin = 8.dp)
                end.linkTo(parent.end, margin = 8.dp)
                start.linkTo(parent.start)
                bottom.linkTo(type.top)
                width = Dimension.fillToConstraints
            }
            .padding(bottom = 30.dp)
    ) {
        InfoTitle()
        InfoDetail()
    }
}

@Composable
fun InfoTitle() {
    Text(
        text = "Info",
        fontSize = 16.sp,
        color = Color(48, 57, 67),
        fontWeight = FontWeight.Bold,
        overflow = TextOverflow.Ellipsis,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .wrapContentSize()
            .padding(bottom = 20.dp)

    )
}

@Composable
fun TypeTitle() {
    Text(
        text = "Type",
        fontSize = 16.sp,
        color = Color(48, 57, 67),
        fontWeight = FontWeight.Bold,
        overflow = TextOverflow.Ellipsis,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .wrapContentSize()
            .padding(bottom = 20.dp)
    )
}

@Composable
fun TypeDetail(stringList: List<String>) {
    LazyRow {
        items(stringList.size) { it ->
            RoundedText(text = stringList[it])
        }
    }
}

@Composable
fun RoundedText(text: String) {
    Text(
        text = text,
        color = Color.White,
        modifier = Modifier
            .padding(8.dp)
            .background(
                color = MaterialTheme.colors.primary,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 16.dp, vertical = 8.dp)
    )
}

@Composable
fun InfoDetail() {
    Row {
        Column(modifier = Modifier) {
            Text(
                text = "Base exp",
                fontWeight = FontWeight.Bold,
                color = Color(48, 57, 67),
                fontSize = 14.sp,
                textAlign = TextAlign.Start
            )
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = "Species",
                fontWeight = FontWeight.Bold,
                color = Color(48, 57, 67),
                fontSize = 14.sp,
                textAlign = TextAlign.Start
            )
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = "Forms count",
                fontWeight = FontWeight.Bold,
                color = Color(48, 57, 67),
                fontSize = 14.sp,
                textAlign = TextAlign.Start
            )
        }
        Column(modifier = Modifier.padding(start = 20.dp)) {
            Text(
                text = "40 exp",
                textAlign = TextAlign.End
            )
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = "Monster",
                textAlign = TextAlign.End
            )
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = "5",
                textAlign = TextAlign.End
            )
        }
    }
}

@Composable
fun ConstraintLayoutScope.SizeSection(
    size: ConstrainedLayoutReference,
    desc: ConstrainedLayoutReference,
    info: ConstrainedLayoutReference
) {
    Card(
        modifier = Modifier
            .constrainAs(size) {
                top.linkTo(desc.bottom)
                end.linkTo(parent.end)
                start.linkTo(parent.start)
                bottom.linkTo(info.top)
                width = Dimension.fillToConstraints
            }
            .padding(top = 30.dp, bottom = 30.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp, 20.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Column(modifier = Modifier.wrapContentSize()) {
                Text(
                    text = "Height",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(217, 217, 217),
                    textAlign = TextAlign.Start
                )
                Text(
                    text = "2’3.6” (0.70 cm)",
                    fontSize = 14.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Start
                )
            }
            Column {
                Text(
                    text = "Weight",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(217, 217, 217),
                    textAlign = TextAlign.Start
                )
                Text(
                    text = "15.2 lbs (6.9 kg)",
                    fontSize = 14.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Start
                )
            }
        }
    }
}

@Preview
@Composable
fun myPreview() {
    PokemonDetailContent(Modifier.fillMaxSize(), "BBBBBB")
}