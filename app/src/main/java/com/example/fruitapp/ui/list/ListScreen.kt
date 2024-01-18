package com.example.fruitapp.ui.list

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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.fruitapp.R
import com.example.fruitapp.data.Fruit
import com.example.fruitapp.data.Nutritions
import com.example.fruitapp.ui.theme.FruitAppTheme
import com.example.fruitapp.ui.theme.Green

@Composable
fun ListScreen(viewModel: ListViewModel = hiltViewModel()) {
    val state = viewModel.state.collectAsState().value
    ListScreen(state, viewModel::onIntent)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ListScreen(
    state: ListState,
    onIntent: (ListIntent) -> Unit
) {

    val list = rememberUpdatedState(state).value.fruitList
    val nutrition = state.nutrition
    val isLoading = state.isLoading
    val isSortingMenuVisible = state.isSortingMenuVisible
    val sortingOption = state.sortingOption
    val isOptionMenuVisible = state.isOptionMenuVisible
    val optionList = state.nutritionList


    if (isLoading)
        CircularProgressIndicator(
            modifier = Modifier
                .fillMaxSize()
                .size(50.dp)
                .wrapContentSize(align = Alignment.Center),
            color = Green
        )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = colorResource(R.color.creme)
    ) {

        Column {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(Green),
                title = {
                    Text(
                        modifier = Modifier.padding(horizontal = 90.dp),
                        text = stringResource(R.string.list_header)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onIntent(ListIntent.OnBackClick) })
                    {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { onIntent(ListIntent.OnSortingClick) }) {
                        Icon(
                            painter = painterResource(id = R.drawable.sorting),
                            contentDescription = "Sorting",
                            tint = Color.Black
                        )
                    }
                }
            )
            DropdownMenu(
                modifier = Modifier.background(Color.White),
                expanded = isSortingMenuVisible,
                onDismissRequest = { onIntent(ListIntent.OnSortingDismiss) },
                offset = DpOffset(300.dp, (-730).dp)
            ) {
                DropdownMenuItem(modifier = Modifier.background(Color.White),
                    text = {
                        Text(
                            fontSize = 20.sp,
                            text = stringResource(id = R.string.ascending)
                        )
                    },
                    onClick = { onIntent(ListIntent.OnAscendingClick) },
                    trailingIcon = {
                        if (sortingOption == 1) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null
                            )
                        }
                    })
                DropdownMenuItem(modifier = Modifier.background(Color.White),
                    text = {
                        Text(
                            fontSize = 20.sp,
                            text = stringResource(id = R.string.descending)
                        )
                    },
                    onClick = { onIntent(ListIntent.OnDescendingClick) },
                    trailingIcon = {
                        if (sortingOption == 2) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null
                            )
                        }
                    })
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(vertical = 5.dp, horizontal = 16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier
                        .padding(horizontal = 16.dp),
                    fontSize = 20.sp,
                    text = stringResource(R.string.name)
                )
                TextButton(onClick = { onIntent(ListIntent.OnOptionClick) }) {

                    Text(
                        fontSize = 20.sp,
                        color = Color.Black,
                        text = nutrition
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_arrow_drop_down_24),
                        contentDescription = null,
                        tint = Color.Black
                    )
                }
            }

            DropdownMenu(
                modifier = Modifier.background(Color.White),
                expanded = isOptionMenuVisible,
                onDismissRequest = { onIntent(ListIntent.OnOptionDismiss) },
                offset = DpOffset(260.dp, (-680).dp)
            ) {
                optionList.forEach { option ->
                    DropdownMenuItem(modifier = Modifier.background(Color.White),
                        text = {
                            Text(
                                fontSize = 20.sp,
                                text = option
                            )
                        },
                        onClick = { onIntent(ListIntent.SelectOption(option)) },
                        trailingIcon = {
                            if (option == nutrition) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null
                                )
                            }
                        })
                }
            }

            LazyColumn {
                itemsIndexed(list) { index, fruit ->
                    FruitListItem(state, fruit, onIntent)
                }
            }
        }
    }
}

@Composable
fun FruitListItem(
    state: ListState,
    fruit: Fruit,
    onIntent: (ListIntent) -> Unit
) {
    val nutrition = state.nutrition

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(vertical = 3.dp)
            .height(60.dp)
            .fillMaxWidth()
            .background(Color.White)
            .clickable {
                onIntent(ListIntent.OnFruitClick(fruit.name))
            }
    ) {

        Text(
            modifier = Modifier
                .padding(vertical = 5.dp, horizontal = 36.dp),
            fontSize = 22.sp,
            text = fruit.name
        )

        Text(
            modifier = Modifier
                .padding(vertical = 5.dp, horizontal = 46.dp),
            fontSize = 22.sp,
            text = fruit.nutritions[nutrition].toString()
        )
    }
}

@Preview
@Composable
fun ListScreenPreview() {
    val fruit = Fruit(
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
    val state = ListState(
        fruitList = listOf(fruit, fruit, fruit)
    )
    FruitAppTheme {
        ListScreen(state) {}
    }
}