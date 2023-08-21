package com.example.fruitapp.ui.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.fruitapp.R
import com.example.fruitapp.data.Fruit
import com.example.fruitapp.data.Nutritions
import com.example.fruitapp.nav.Screen
import com.example.fruitapp.ui.theme.FruitAppTheme
import com.example.fruitapp.ui.theme.Green

@Composable
fun DetailsScreen(viewModel: DetailsViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    DetailsScreen(state, viewModel::onIntent)
}

@Composable
private fun DetailsScreen(
    state: DetailsState,
    onIntent: (DetailsIntent) -> Unit
) {

    val fruit = rememberUpdatedState(state).value.fruit
    val isLoading = state.isLoading

    val modifier = Modifier
        .padding(vertical = 5.dp, horizontal = 16.dp)
        .fillMaxWidth()

    Image(
        modifier = Modifier.fillMaxSize(),
        bitmap = ImageBitmap.imageResource(R.drawable.background),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        alpha = 0.5f
    )
    if (isLoading)
        CircularProgressIndicator(
            modifier = Modifier
                .fillMaxSize()
                .size(50.dp)
                .wrapContentSize(align = Alignment.Center),
            color = Green
        )

    if (fruit != null)
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            Box {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 50.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 60.sp,
                    fontWeight = FontWeight.Bold,
                    text = fruit.name
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.TopStart)
                    .padding(10.dp)
                    .background(Color.White)
                    .border(3.dp, Color.Gray, RoundedCornerShape(10.dp))
            ) {

                Column(modifier = Modifier.padding(10.dp)) {

                    Text(
                        modifier = modifier,
                        fontSize = 25.sp,
                        text = "${stringResource(R.string.family)} ${fruit.family}"
                    )

                    Text(
                        modifier = modifier,
                        textAlign = TextAlign.Start,
                        fontSize = 25.sp,
                        text = "${stringResource(R.string.order)} ${fruit.order}"
                    )

                    Text(
                        modifier = modifier,
                        textAlign = TextAlign.Start,
                        fontSize = 25.sp,
                        text = "${stringResource(R.string.genus)} ${fruit.genus}"
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.TopStart)
                    .padding(10.dp)
                    .background(Color.White)
                    .border(3.dp, Color.Gray, RoundedCornerShape(10.dp))
            ) {

                Column(modifier = Modifier.padding(10.dp)) {

                    Text(
                        modifier = modifier,
                        textAlign = TextAlign.Start,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        text = stringResource(R.string.nutritions)
                    )

                    Text(
                        modifier = modifier,
                        textAlign = TextAlign.Start,
                        fontSize = 25.sp,
                        text = "${stringResource(R.string.calories)} ${fruit.nutritions.calories}"
                    )

                    Text(
                        modifier = modifier,
                        textAlign = TextAlign.Start,
                        fontSize = 25.sp,
                        text = "${stringResource(R.string.fat)} ${fruit.nutritions.fat}"
                    )

                    Text(
                        modifier = modifier,
                        textAlign = TextAlign.Start,
                        fontSize = 25.sp,
                        text = "${stringResource(R.string.sugar)} ${fruit.nutritions.sugar}"
                    )

                    Text(
                        modifier = modifier,
                        textAlign = TextAlign.Start,
                        fontSize = 25.sp,
                        text = "${stringResource(R.string.carbohydrates)} ${fruit.nutritions.carbohydrates}"
                    )

                    Text(
                        modifier = modifier,
                        textAlign = TextAlign.Start,
                        fontSize = 25.sp,
                        text = "${stringResource(R.string.protein)} ${fruit.nutritions.protein}"
                    )
                }
            }

            Button(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 20.dp),
                shape = RoundedCornerShape(30),
                colors = ButtonDefaults.buttonColors(Green),
                onClick = { onIntent(DetailsIntent.OnBackButtonClick) }) {
                Text(text = stringResource(R.string.back_button), color = Color.Black)
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