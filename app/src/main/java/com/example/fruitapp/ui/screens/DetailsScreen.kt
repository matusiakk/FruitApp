package com.example.fruitapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fruitapp.R
import com.example.fruitapp.data.Fruit
import com.example.fruitapp.data.Nutritions


@Composable
fun DetailsScreen(fruit: Fruit) {

    val modifier = Modifier
        .padding(vertical = 5.dp, horizontal = 16.dp)
        .fillMaxWidth()

    val image = ImageBitmap.imageResource(
        id = R.drawable.background)

    Image(modifier = Modifier.fillMaxSize(),
        bitmap = image,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        alpha = 0.6f)

    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top) {

        Box() {
            Text(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 50.dp),
                textAlign = TextAlign.Center,
                fontSize = 60.sp,
                fontWeight = FontWeight.Bold,
                text = fruit.name)
        }

        Box(modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopStart)
            .padding(10.dp)
            .background(Color.White)
            .border(3.dp, Color.Gray, RoundedCornerShape(10.dp))) {

            Column(modifier = Modifier.padding(10.dp)) {

                Text(
                    modifier = modifier,
                    fontSize = 25.sp,
                    text = "Family: ${fruit.family}"
                )

                Text(
                    modifier = modifier,
                    textAlign = TextAlign.Start,
                    fontSize = 25.sp,
                    text = "Order: ${fruit.order}"
                )

                Text(
                    modifier = modifier,
                    textAlign = TextAlign.Start,
                    fontSize = 25.sp,
                    text = "Genus: ${fruit.genus}"
                )
            }
        }

        Box(modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopStart)
            .padding(10.dp)
            .background(Color.White)
            .border(3.dp, Color.Gray, RoundedCornerShape(10.dp))){

            Column(modifier = Modifier.padding(10.dp)) {

        Text(modifier = modifier,
            textAlign = TextAlign.Start,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            text = "Nutritions")

        Text(modifier = modifier,
            textAlign = TextAlign.Start,
            fontSize = 25.sp,
            text = "Calories: ${fruit.nutritions.calories}")

        Text(modifier = modifier,
            textAlign = TextAlign.Start,
            fontSize = 25.sp,
            text = "Fat: ${fruit.nutritions.fat}")

        Text(modifier = modifier,
            textAlign = TextAlign.Start,
            fontSize = 25.sp,
            text = "Sugar: ${fruit.nutritions.sugar}")

        Text(modifier = modifier,
            textAlign = TextAlign.Start,
            fontSize = 25.sp,
            text = "Carbohydrates: ${fruit.nutritions.carbohydrates}")

        Text(modifier = modifier,
            textAlign = TextAlign.Start,
            fontSize = 25.sp,
            text = "Protein: ${fruit.nutritions.protein}")
    }}
}}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DetailsScreenPreview () {
    val nutritions = Nutritions(1,2.1,3.3,4.3,5.3)
    val fruit = Fruit("family", "genus", 2,
        "kiwi", nutritions, "order")
    DetailsScreen(fruit)
}