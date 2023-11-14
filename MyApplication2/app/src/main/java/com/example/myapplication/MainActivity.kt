package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import com.example.myapplication.api.StackOverflowAPI
import com.example.myapplication.data.Question
import com.example.myapplication.data.getAllQuestions
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getAllQuestions().observe(this) { list ->
            if(list != null) {
                setContent {
                    QuestionList(list)
                }
            }
        }
    }

    @Composable
    fun QuestionList(listQuestion: List<Question>) {
        LazyColumn {
            items(items = listQuestion,
                key = { it.id }) { question ->
                QuestionItem(item = question)
            }
        }
    }

    @Composable
    fun QuestionItem(item : Question) {
        Card(modifier = Modifier.padding(10.dp)
            .fillMaxWidth()) {
            Text(text = item.title,
                modifier = Modifier.padding(10.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center)
        }
    }


    @Preview
    @Composable
    fun PreviewQuestionList() {
        val listQuestion = listOf<Question>()
        QuestionList(listQuestion)
    }

}