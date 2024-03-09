package com.example.fruitapp.ui.start

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
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

    if (rememberUpdatedState(state).value.showMessage) {
        Snackbar {
            Text(text = stringResource(R.string.fruit_not_found))
        }
    }

    Image(
        modifier = Modifier.fillMaxSize(),
        bitmap = ImageBitmap.imageResource(R.drawable.background),
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
            modifier = Modifier.padding(vertical = 80.dp),
            textAlign = TextAlign.Center,
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            text = stringResource(R.string.app_name)
        )

        Text(
            modifier = Modifier.padding(bottom = 16.dp),
            textAlign = TextAlign.Center,
            fontSize = 25.sp,
            color = Color.Black,
            text = stringResource(R.string.start_description)
        )

        TextField(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
            value = state.text,
            onValueChange = { text -> onIntent(StartIntent.EnterText(text)) },
            shape = RoundedCornerShape(30),
            label = { Text(text = stringResource(R.string.text_field_name)) }
        )

        Button(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
            shape = RoundedCornerShape(30),
            colors = ButtonDefaults.buttonColors(Green),
            onClick = { onIntent(StartIntent.OnSearchButtonClick) }
        ) {
            Text(
                text = stringResource(R.string.search_button),
                color = Color.Black
            )
        }

        Text(
            modifier = Modifier.padding(top = 16.dp),
            textAlign = TextAlign.Center,
            fontSize = 25.sp,
            color = Color.Black,
            text = stringResource(R.string.start_description_2)
        )

        Button(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
            shape = RoundedCornerShape(30),
            colors = ButtonDefaults.buttonColors(Green),
            onClick = { onIntent(StartIntent.OnListButtonClick) }) {
            Text(
                text = stringResource(R.string.list_button),
                color = Color.Black
            )
        }
    }
}

@Preview
@Composable
fun StartScreenPreview(){
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
    showMessage = false)

    FruitAppTheme {
        StartScreen(state) {}
    }
}