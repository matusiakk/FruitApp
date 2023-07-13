package com.example.fruitapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.TextUnit
import com.example.fruitapp.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartScreen(
    onClickButton: (name: String) -> Unit) {

    var name by remember {
        mutableStateOf("")
    }

    val image = ImageBitmap.imageResource(
        id = R.drawable.background)

    Image(modifier = Modifier.fillMaxSize(),
        bitmap = image,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        alpha = 0.6f)

    Column (modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top) {

        Text(modifier = Modifier.padding(vertical = 80.dp),
            textAlign = TextAlign.Center,
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            text = "Fruit App")

        Text(modifier = Modifier.padding(bottom = 50.dp),
            textAlign = TextAlign.Center,
            fontSize = 25.sp,
            color = Color.Black,
            text = "Enter the name of the fruit for details")

        TextField(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
            value = name,
            onValueChange = { str -> name = str},
            shape = RoundedCornerShape(30),
            label = { Text(text = "Name") }
        )

        Button(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 20.dp),
            shape = RoundedCornerShape(30),
            colors = ButtonDefaults.buttonColors(Color(0xFF338B4F)),
            onClick = { onClickButton(name)}) {
            Text(text = "Search", color = Color.Black)
        }
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun StartScreenPreview () {

    StartScreen(onClickButton = {})
}