package com.example.fruitapp.ui.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fruitapp.R
import com.example.fruitapp.data.Fruit


@Composable
fun ListScreen(state: ListState,
               onIntent: (ListIntent) -> Unit) {


    val list = rememberUpdatedState(state).value.fruitList

        Image(
            modifier = Modifier.fillMaxSize(),
            bitmap = ImageBitmap.imageResource(R.drawable.background),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            alpha = 0.5f
        )
        Column {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 50.dp),
                textAlign = TextAlign.Center,
                fontSize = 60.sp,
                fontWeight = FontWeight.Bold,
                text = stringResource(R.string.list_header)
            )

            LazyColumn{
                itemsIndexed(list){index, fruit ->
                    FruitListItem(fruit, onIntent)
                }
            }
        }
    }

@Composable
fun FruitListItem(fruit: Fruit,
                  onIntent: (ListIntent) -> Unit)
{
    Box(modifier = Modifier
        .fillMaxWidth()
        .wrapContentSize(Alignment.TopStart)
        .padding(10.dp)
        .background(Color.White)
        .border(3.dp, Color.Gray, RoundedCornerShape(10.dp))
        .clickable {
            onIntent(ListIntent.OnFruitClick(fruit.name))
        }
    ) {
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(vertical = 5.dp, horizontal = 16.dp)
                .fillMaxWidth()){

        Text(
            modifier = Modifier
                .padding(vertical = 5.dp, horizontal = 16.dp),
            fontSize = 25.sp,
            text = fruit.name)

        Icon(painter = painterResource(R.drawable.baseline_arrow_forward_24),
            contentDescription = null)
    }
    }
}
