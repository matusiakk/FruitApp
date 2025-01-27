package com.example.fruitapp.ui.quiz

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
fun QuizScreen(viewModel: QuizViewModel = hiltViewModel()) {
    val state = viewModel.state.collectAsState().value
    QuizScreen(state, viewModel::onIntent)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun QuizScreen(
    state: QuizState,
    onIntent: (QuizIntent) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = colorResource(id = R.color.creme)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            TopAppBar(colors = TopAppBarDefaults.smallTopAppBarColors(Green),
                title = {
                    Text(
                        modifier = Modifier.padding(horizontal = 80.dp),
                        text = stringResource(R.string.quiz)
                    )
                }, navigationIcon = {
                    IconButton(onClick = { onIntent(QuizIntent.OnBackClick) }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                })

            Text(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                fontSize = 32.sp,
                text = stringResource(R.string.quiz_header)
            )
            if (!state.showResult) {
                LazyColumn {
                    itemsIndexed(QuizQuestions.values()) { index, question ->
                        QuestionItem(question, onIntent, index, state)
                    }
                }
                Button(modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xFF338B4F)),
                    onClick = { onIntent(QuizIntent.OnCheckClick) }) {
                    Text(
                        text = stringResource(R.string.check),
                        color = Color.Black
                    )
                }
            } else {
                Row(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth()
                        .background(Color.White)
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        fontSize = 24.sp,
                        text = stringResource(id = state.result)
                    )
                }

            }
        }
    }
}

@Composable
fun QuestionItem(
    question: QuizQuestions,
    onIntent: (QuizIntent) -> Unit,
    questionIndex: Int,
    state: QuizState
) {
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(top = 3.dp)
            .fillMaxWidth()
            .background(Color.White)
            .clickable { onIntent(QuizIntent.OnQuestionClick(questionIndex)) }) {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            fontSize = 24.sp,
            text = stringResource(id = question.question)
        )
    }
    if (state.questionsExpanded[questionIndex])
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            listOf(
                question.answerA,
                question.answerB,
                question.answerC,
                question.answerD
            ).forEachIndexed { index, answerId ->
                AnswerOption(
                    answerId = answerId,
                    answerIndex = index,
                    onIntent = onIntent,
                    state = state,
                    questionIndex = questionIndex
                )
            }
        }
}

@Composable
fun AnswerOption(
    answerId: Int,
    answerIndex: Int,
    onIntent: (QuizIntent) -> Unit,
    state: QuizState,
    questionIndex: Int
) {
    Text(
        text = stringResource(id = answerId),
        fontSize = 20.sp,
        fontWeight = if (state.selectedAnswers[questionIndex] == answerIndex) FontWeight.Bold
        else FontWeight.Normal,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable {
                onIntent(QuizIntent.OnAnswerClick(questionIndex, answerIndex))
            }
    )

}


@Preview
@Composable
fun AboutScreenPreview() {
    val state = QuizState()
    FruitAppTheme {
        QuizScreen(state) {}
    }
}
