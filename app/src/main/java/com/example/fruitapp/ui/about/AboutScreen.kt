package com.example.fruitapp.ui.about

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.fruitapp.R
import com.example.fruitapp.ui.theme.FruitAppTheme
import com.example.fruitapp.ui.theme.Green

@Composable
fun AboutScreen(viewModel: AboutViewModel = hiltViewModel()) {
    AboutScreen(viewModel::onIntent)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AboutScreen(onIntent: (AboutIntent) -> Unit) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = colorResource(id = R.color.creme)
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
        ) {
            TopAppBar(colors = TopAppBarDefaults.smallTopAppBarColors(Green),
                title = {
                    Text(
                        modifier = Modifier.padding(horizontal = 30.dp),
                        text = stringResource(R.string.about_app)
                    )
                }, navigationIcon = {
                    IconButton(onClick = { onIntent(AboutIntent.OnBackClick) }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                })
            Text(
                modifier = Modifier.padding(start = 15.dp, top = 15.dp),
                fontSize = 22.sp,
                text = stringResource(R.string.app_name_label)
            )

            Text(
                modifier = Modifier.padding(start = 15.dp, top = 5.dp),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                text = stringResource(R.string.app_name)
            )

            Text(
                modifier = Modifier.padding(start = 15.dp, top = 15.dp),
                fontSize = 22.sp,
                text = stringResource(R.string.version_label)
            )

            Text(
                modifier = Modifier.padding(start = 15.dp, top = 5.dp),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                text = stringResource(R.string.version)
            )

            Text(
                modifier = Modifier.padding(start = 15.dp, top = 15.dp),
                fontSize = 22.sp,
                text = stringResource(R.string.data_source)
            )

            Text(
                modifier = Modifier.padding(start = 15.dp, top = 5.dp),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                text = stringResource(R.string.data_url)
            )

            Text(
                modifier = Modifier.padding(start = 15.dp, top = 15.dp),
                fontSize = 22.sp,
                text = stringResource(R.string.pictures_source)
            )

            Text(
                modifier = Modifier.padding(start = 15.dp, top = 5.dp),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                text = stringResource(R.string.pictures_url)
            )
        }
    }
}


@Preview
@Composable
fun AboutScreenPreview() {
    FruitAppTheme {
        AboutScreen() {}
    }
}
