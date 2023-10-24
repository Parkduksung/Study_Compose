package com.example.constraintlayout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.example.constraintlayout.ui.theme.StudyComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StudyComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    //type1
    //한번에 나열해 놓고 하기.
    //내부에서 라인수가 많이지거나 하면 type1 으로 빼서 사용
    //혹시나 재사용 하고 싶으면 변수이므로 빼서 사용 가능.
    val constraintSet = ConstraintSet {

        val text = createRefFor("text")

        constrain(text) {
            linkTo(
                top = parent.top,
                bottom = parent.bottom,
                start = parent.start,
                end = parent.end,
            )
            width = Dimension.wrapContent
            height = Dimension.wrapContent
        }
    }
    ConstraintLayout(modifier = modifier.fillMaxSize(), constraintSet = constraintSet) {

        Text(
            text = "Hello $name!",
            modifier = Modifier.layoutId("text")
        )
    }

    //type2
    //내부에서 하기
    //단순한거면 type2 가 더 나은 선택일수도.
    ConstraintLayout(modifier = modifier.fillMaxSize()) {

        val (text) = createRefs()

        Text(
            text = "Hello $name!",
            modifier = Modifier.constrainAs(text) {
                linkTo(
                    top = parent.top,
                    bottom = parent.bottom,
                    start = parent.start,
                    end = parent.end,
                )
                width = Dimension.wrapContent
                height = Dimension.wrapContent
            }
        )
    }


}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    StudyComposeTheme {
        Greeting("Android")
    }
}