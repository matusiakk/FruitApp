package com.example.fruitapp.ui.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.fruitapp.R
import com.example.fruitapp.data.Fruit
import com.example.fruitapp.data.Nutritions
import com.example.fruitapp.ui.list.NutritionOptions
import com.example.fruitapp.ui.theme.FruitAppTheme
import com.example.fruitapp.ui.theme.Green

@Composable
fun DetailsScreen(viewModel: DetailsViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    DetailsScreen(state, viewModel::onIntent)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DetailsScreen(
    state: DetailsState,
    onIntent: (DetailsIntent) -> Unit
) {
    val fruit = rememberUpdatedState(state).value.fruit
    val fruitImage = rememberUpdatedState(state).value.fruitImage

    if (state.isLoading)
        CircularProgressIndicator(
            modifier = Modifier
                .fillMaxSize()
                .size(50.dp)
                .wrapContentSize(align = Alignment.Center),
            color = Green
        )

    if (fruit != null)
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = colorResource(id = R.color.creme)
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
            ) {
                TopAppBar(
                    colors = TopAppBarDefaults.smallTopAppBarColors(Green),
                    title = {
                        Text(
                            modifier = Modifier.padding(horizontal = 90.dp),
                            text = fruit.name
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { onIntent(DetailsIntent.OnBackButtonClick) })
                        {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    })
                Box(modifier = Modifier.size(500.dp, 400.dp)) {
                    if (state.imageIsLoading)
                        CircularProgressIndicator(
                            modifier = Modifier
                                .fillMaxSize()
                                .size(50.dp)
                                .wrapContentSize(align = Alignment.Center),
                            color = Green
                        )
                    if (fruitImage != null)
                        AsyncImage(
                            model = fruitImage.photos[0].src.large2x,
                            contentDescription = null,
                            modifier = Modifier
                                .size(500.dp, 400.dp),
                            contentScale = ContentScale.Crop
                        )
                }
                TextButton(modifier = Modifier.height(35.dp),
                    onClick = { DetailsIntent.OnPexelsClick }) {
                    Text(text = stringResource(id = R.string.pexel))
                }
                Box(
                    modifier = Modifier
                        .background(Color.White)
                ) {
                    Column {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                            text = fruit.name
                        )
                        NutritionOptions.values().forEach { nutrition ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 10.dp, end = 30.dp, top = 5.dp, bottom = 5.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    fontSize = 22.sp,
                                    text = nutrition.name
                                )
                                Text(
                                    fontSize = 22.sp,
                                    text = fruit.nutritions[nutrition].toString()
                                )
                            }
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .background(Color.White)
                ) {
                    Column {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 10.dp, end = 30.dp, top = 5.dp, bottom = 5.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                fontSize = 22.sp,
                                text = stringResource(R.string.family)
                            )
                            Text(
                                fontSize = 22.sp,
                                text = fruit.family
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 10.dp, end = 30.dp, top = 5.dp, bottom = 5.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                fontSize = 22.sp,
                                text = stringResource(R.string.order)
                            )
                            Text(
                                fontSize = 22.sp,
                                text = fruit.order
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 10.dp, end = 30.dp, top = 5.dp, bottom = 5.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                fontSize = 22.sp,
                                text = stringResource(R.string.genus)
                            )
                            Text(
                                fontSize = 22.sp,
                                text = fruit.genus
                            )
                        }
                    }
                }
            }
        }
}

@Preview
@Composable
fun DetailsScreenPreview() {
    val state = DetailsState(
        fruit = Fruit(
            family = "family",
            genus = "genus",
            id = 12,
            name = "kiwi",
            nutritions = Nutritions(
                calories = 12,
                carbohydrates = 3.3,
                fat = 1.4,
                protein = 5.7,
                sugar = 1.5
            ),
            order = "order"
        )
    )
    FruitAppTheme {
        DetailsScreen(state) {}
    }
}