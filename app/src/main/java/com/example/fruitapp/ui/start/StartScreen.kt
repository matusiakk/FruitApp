package com.example.fruitapp.ui.start

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.fruitapp.R
import com.example.fruitapp.data.Fruit
import com.example.fruitapp.data.Nutritions
import com.example.fruitapp.ui.theme.FruitAppTheme
import com.example.fruitapp.ui.theme.Green

@Composable
fun StartScreen(viewModel: StartViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    StartScreen(state, viewModel::onIntent)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun StartScreen(
    state: StartState,
    onIntent: (StartIntent) -> Unit
) {

    Surface(color = colorResource(R.color.creme)) {

        Image(
            modifier = Modifier.fillMaxWidth(),
            bitmap = ImageBitmap.imageResource(R.drawable.start_bar),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            alpha = 0.5f
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            Text(
                modifier = Modifier.padding(vertical = 40.dp),
                textAlign = TextAlign.Center,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                text = stringResource(R.string.app_name)
            )

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                value = state.text,
                onValueChange = { text ->
                    onIntent(StartIntent.EnterText(text))
                },
                shape = RoundedCornerShape(30),
                label = {
                    Text(
                        text = stringResource(R.string.text_field_name)
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = ""
                    )
                },
                trailingIcon = {
                    if (state.text != ("")) {
                        IconButton(
                            onClick = { onIntent(StartIntent.OnCloseClick) }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = ""
                            )
                        }
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        onIntent(StartIntent.OnSearchClick)
                    }
                ),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    cursorColor = Color.Black,
                    textColor = Color.Black
                )
            )

            if (state.filteredFruitList.isNotEmpty())
                LazyColumn {
                    itemsIndexed(state.filteredFruitList) { index, fruit ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 2.dp)
                                .background(Color.White)
                                .clickable { onIntent(StartIntent.OnSearchItemClick(fruit.name)) },
                            horizontalArrangement = Arrangement.Start
                        ) {

                            Text(
                                modifier = Modifier
                                    .padding(horizontal = 5.dp, vertical = 5.dp),
                                text = fruit.name,
                                fontSize = 20.sp
                            )
                        }
                    }
                }

            Row(
                modifier = Modifier
                    .padding(top = 36.dp, start = 6.dp, end = 6.dp)
                    .height(160.dp)
            ) {
                Button(modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxSize()
                    .weight(1f),
                    shape = RoundedCornerShape(10),
                    colors = ButtonDefaults.buttonColors(Color.White),
                    border = BorderStroke(
                        width = 1.dp,
                        color = Green
                    ),
                    onClick = { onIntent(StartIntent.OnListButtonClick) }) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.list),
                            contentDescription = "",
                            tint = Color.Black,
                            modifier = Modifier
                                .size(50.dp)
                        )
                        Text(modifier = Modifier
                            .padding(top = 10.dp),
                            text = stringResource(R.string.list_button),
                            color = Color.Black,
                            fontSize = 20.sp
                        )
                    }
                }
                Button(modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxSize()
                    .weight(1f),
                    shape = RoundedCornerShape(10),
                    colors = ButtonDefaults.buttonColors(Color.White),
                    border = BorderStroke(
                        width = 1.dp,
                        color = Green
                    ),
                    onClick = { onIntent(StartIntent.OnFavoriteClick) }) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.favorite_border),
                            contentDescription = "",
                            tint = Color.Black,
                            modifier = Modifier
                                .size(50.dp)
                        )
                        Text(modifier = Modifier
                            .padding(top = 10.dp),
                            text = stringResource(R.string.favorite),
                            color = Color.Black,
                            fontSize = 20.sp
                        )
                    }
                }
            }
            Row(
                modifier = Modifier
                    .padding(top = 14.dp, start = 6.dp, end = 6.dp)
                    .height(160.dp)
            ) {
                Button(modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxSize()
                    .weight(1f),
                    shape = RoundedCornerShape(10),
                    colors = ButtonDefaults.buttonColors(Color.White),
                    border = BorderStroke(
                        width = 1.dp,
                        color = Green
                    ),
                    onClick = { onIntent(StartIntent.OnListButtonClick) }) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.quiz),
                            contentDescription = "",
                            tint = Color.Black,
                            modifier = Modifier
                                .size(50.dp)
                        )
                        Text(modifier = Modifier
                            .padding(top = 10.dp),
                            text = stringResource(R.string.quiz),
                            color = Color.Black,
                            fontSize = 20.sp
                        )
                    }
                }
                Button(modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxSize()
                    .weight(1f),
                    shape = RoundedCornerShape(10),
                    colors = ButtonDefaults.buttonColors(Color.White),
                    border = BorderStroke(
                        width = 1.dp,
                        color = Green
                    ),
                    onClick = { onIntent(StartIntent.OnAboutAppClick)}) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_question_mark_24),
                            contentDescription = "",
                            tint = Color.Black,
                            modifier = Modifier
                                .size(50.dp)
                        )
                        Text(modifier = Modifier
                            .padding(top = 10.dp),
                            text = stringResource(R.string.about_app),
                            color = Color.Black,
                            fontSize = 20.sp
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun StartScreenPreview() {
    val state = StartState(
        text = "",
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
        ),
        //showMessage = false
    )

    FruitAppTheme {
        StartScreen(state) {}
    }
}