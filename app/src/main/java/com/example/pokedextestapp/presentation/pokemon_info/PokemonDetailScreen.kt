package com.example.pokedextestapp.presentation.pokemon_info

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintLayoutScope
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pokedextestapp.R
import com.example.pokedextestapp.domain.model.PokemonDetailModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

/**
 * Composable function for displaying the Pokemon detail screen.
 * @param name The name of the Pokemon.
 * @param navigator The navigator used for navigation within the application.
 * @param viewModel The view model for managing Pokemon detail screen state.
 * @param red Red component of the dominant color.
 * @param blue Blue component of the dominant color.
 * @param green Green component of the dominant color.
 */
@Composable
@Destination
fun PokemonDetailScreen(
    name: String,
    navigator: DestinationsNavigator,
    viewModel: PokemonDetailViewModel = hiltViewModel(),
    red: Float, blue: Float, green: Float
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
        } else if (state.error.isNotBlank()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                androidx.compose.material3.Text(
                    text = state.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                )
            }
        } else {
            state.pokemon?.let {
                PokemonDetailContent(
                    modifier = Modifier.fillMaxSize(),
                    it,
                    navigator,
                    red, blue, green
                )
            }
        }
    }
}

/**
 * Composable function for displaying the content of Pokemon detail screen.
 * @param modifier Modifier for the composable.
 * @param pokemon The Pokemon detail model.
 * @param navigator The navigator used for navigation within the application.
 * @param red Red component of the dominant color.
 * @param blue Blue component of the dominant color.
 * @param green Green component of the dominant color.
 */
@Composable
fun PokemonDetailContent(
    modifier: Modifier,
    pokemon: PokemonDetailModel,
    navigator: DestinationsNavigator,
    red: Float, blue: Float, green: Float
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        DetailScreenBg(
            modifier = Modifier.matchParentSize(),
            pokemonName = pokemon.name,
            navigator,
            red, blue, green
        )
        CardWithPokemonInfo(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 100.dp), pokemon = pokemon, red, blue, green
        )
    }
}

/**
 * Composable function for displaying the background of Pokemon detail screen.
 * @param modifier Modifier for the composable.
 * @param pokemonName The name of the Pokemon.
 * @param navigator The navigator used for navigation within the application.
 * @param red Red component of the dominant color.
 * @param blue Blue component of the dominant color.
 * @param green Green component of the dominant color.
 */
@Composable
fun DetailScreenBg(
    modifier: Modifier, pokemonName: String, navigator: DestinationsNavigator,
    red: Float, blue: Float, green: Float
) {
    Box(
        modifier = modifier
            .background(Color(red = red, blue = blue, green = green))
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
                        .align(Alignment.BottomCenter)
                        .clickable { navigator.navigateUp() },
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
                fontSize = 25.sp,
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

/**
 * Composable function for displaying the card with Pokemon info.
 * @param modifier Modifier for the composable.
 * @param pokemon The Pokemon detail model.
 * @param red Red component of the dominant color.
 * @param blue Blue component of the dominant color.
 * @param green Green component of the dominant color.
 */
@Composable
fun CardWithPokemonInfo(
    modifier: Modifier,
    pokemon: PokemonDetailModel,
    red: Float,
    blue: Float,
    green: Float
) {
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

            DescriptionTextSection(desc, size, pokemon.description)
            SizeSection(size, desc, info, pokemon.height, pokemon.weight)
            InfoSection(info, size, type, pokemon.baseExep, pokemon.formCounts, pokemon.species)
            TypeSection(type, info, pokemon.types, red, blue, green)
        }
    }
}

/**
 * Composable function for displaying the description text section.
 * @param desc Reference to the constraint of the description section.
 * @param size Reference to the constraint of the size section.
 * @param pokemonDescription The description of the Pokemon.
 */
@Composable
fun ConstraintLayoutScope.DescriptionTextSection(
    desc: ConstrainedLayoutReference,
    size: ConstrainedLayoutReference,
    pokemonDescription: String
) {
    Text(
        text = pokemonDescription,
        fontSize = 18.sp,
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

/**
 * Composable function for displaying the type section of Pokemon detail screen.
 * @param type Reference to the constraint of the type section.
 * @param info Reference to the constraint of the info section.
 * @param types List of Pokemon types.
 * @param red Red component of the dominant color.
 * @param blue Blue component of the dominant color.
 * @param green Green component of the dominant color.
 */
@Composable
fun ConstraintLayoutScope.TypeSection(
    type: ConstrainedLayoutReference,
    info: ConstrainedLayoutReference,
    types: List<String>, red: Float, blue: Float, green: Float
) {
    Column(modifier = Modifier.constrainAs(type) {
        top.linkTo(info.bottom)
        start.linkTo(parent.start)
    }, horizontalAlignment = Alignment.Start) {
        TypeTitle()
        TypeDetail(stringList = types, red, blue, green)
    }
}

/**
 * Composable function for displaying the info section of Pokemon detail screen.
 * @param info Reference to the constraint of the info section.
 * @param size Reference to the constraint of the size section.
 * @param type Reference to the constraint of the type section.
 * @param exp The base experience of the Pokemon.
 * @param formCounts The count of forms the Pokemon has.
 * @param species The species of the Pokemon.
 */
@Composable
fun ConstraintLayoutScope.InfoSection(
    info: ConstrainedLayoutReference,
    size: ConstrainedLayoutReference,
    type: ConstrainedLayoutReference,
    exp: Number,
    formCounts: Int,
    species: String
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
        InfoDetail(exp, formCounts, species)
    }
}

/**
 * Composable function for displaying the title of the info section.
 */
@Composable
fun InfoTitle() {
    Text(
        text = "Info",
        fontSize = 20.sp,
        color = Color(48, 57, 67),
        fontWeight = FontWeight.Bold,
        overflow = TextOverflow.Ellipsis,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .wrapContentSize()
            .padding(bottom = 20.dp)

    )
}

/**
 * Composable function for displaying the title of the type section.
 */
@Composable
fun TypeTitle() {
    Text(
        text = "Type",
        fontSize = 20.sp,
        color = Color(48, 57, 67),
        fontWeight = FontWeight.Bold,
        overflow = TextOverflow.Ellipsis,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .wrapContentSize()
            .padding(bottom = 20.dp)
    )
}

/**
 * Composable function for displaying the details of Pokemon types.
 * @param stringList List of Pokemon types.
 * @param red Red component of the background color.
 * @param blue Blue component of the background color.
 * @param green Green component of the background color.
 */
@Composable
fun TypeDetail(stringList: List<String>, red: Float, blue: Float, green: Float) {
    val color = Color(red = red, blue = blue, green = green)
    LazyRow {
        items(stringList.size) { it ->
            RoundedText(text = stringList[it], color)
        }
    }
}

/**
 * Composable function for displaying a text inside rounded shape.
 * @param text The text to display.
 * @param color The background color of the text.
 */
@Composable
fun RoundedText(text: String, color: Color) {
    Text(
        text = text,
        color = Color.White,
        modifier = Modifier
            .padding(8.dp)
            .background(
                color = color,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 16.dp, vertical = 8.dp)
    )
}

/**
 * Composable function for displaying the details of Pokemon info.
 * @param exp The base experience of the Pokemon.
 * @param formCounts The count of forms the Pokemon has.
 * @param species The species of the Pokemon.
 */
@Composable
fun InfoDetail(
    exp: Number,
    formCounts: Int,
    species: String
) {
    Row {
        Column(modifier = Modifier) {
            Text(
                text = "Base exp",
                fontWeight = FontWeight.Bold,
                color = Color(48, 57, 67),
                fontSize = 18.sp,
                textAlign = TextAlign.Start
            )
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = "Species",
                fontWeight = FontWeight.Bold,
                color = Color(48, 57, 67),
                fontSize = 18.sp,
                textAlign = TextAlign.Start
            )
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = "Forms count",
                fontWeight = FontWeight.Bold,
                color = Color(48, 57, 67),
                fontSize = 18.sp,
                textAlign = TextAlign.Start
            )
        }
        Column(modifier = Modifier.padding(start = 20.dp)) {
            Text(
                text = "$exp exp",
                textAlign = TextAlign.End, fontSize = 18.sp
            )
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = species,
                textAlign = TextAlign.End, fontSize = 18.sp
            )
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = formCounts.toString(),
                textAlign = TextAlign.End, fontSize = 18.sp
            )
        }
    }
}

/**
 * Composable function for displaying the size section of Pokemon detail screen.
 * @param size Reference to the constraint of the size section.
 * @param desc Reference to the constraint of the description section.
 * @param info Reference to the constraint of the info section.
 * @param height The height of the Pokemon.
 * @param weight The weight of the Pokemon.
 * @param viewModel The view model for managing Pokemon detail screen state.
 */
@Composable
fun ConstraintLayoutScope.SizeSection(
    size: ConstrainedLayoutReference,
    desc: ConstrainedLayoutReference,
    info: ConstrainedLayoutReference,
    height: Int,
    weight: Int,
    viewModel: PokemonDetailViewModel = hiltViewModel()
) {
    val heightCm = viewModel.convertHeightToCentimeters(height)
    val weightKg = viewModel.convertWeightToKilograms(weight)
    val weightLbs = viewModel.convertWeightToPounds(weight)

    val formatWeight = "$weightLbs lbs ($weightKg kg)"
    val formatHeight = viewModel.formatHeight(heightCm = heightCm)

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
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(217, 217, 217),
                    textAlign = TextAlign.Start
                )
                Text(
                    text = formatHeight,
                    fontSize = 18.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Start
                )
            }
            Column {
                Text(
                    text = "Weight",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(217, 217, 217),
                    textAlign = TextAlign.Start
                )
                Text(
                    text = formatWeight,
                    fontSize = 18.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Start
                )
            }
        }
    }
}