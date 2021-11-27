package com.example.week3

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberImagePainter
import com.example.week3.ui.theme.StudyComposeTheme
import kotlinx.coroutines.launch
import kotlin.math.max

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StudyComposeTheme {
                TwoTexts(text1 = "Hi", text2 = "there")
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    StudyComposeTheme {
        Greeting("Android")
    }
}

@Composable
fun PhotographerCard(modifier: Modifier = Modifier) {

    Row(
        modifier = modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(MaterialTheme.colors.surface)
            .clickable { }
            .padding(16.dp)) {

        Surface(
            modifier = Modifier.size(50.dp),
            shape = CircleShape,
            color = MaterialTheme.colors.onSurface.copy(alpha = .2f)
        ) {

        }

        Column(
            modifier = Modifier
                .padding(start = 8.dp)
                .align(Alignment.CenterVertically)
        ) {

            Text(text = "Alfred Sisley", fontWeight = FontWeight.ExtraBold)

            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(text = "3 minutes ago", style = MaterialTheme.typography.body2)
            }
        }

    }

}


@Composable
fun LayoutsCodelab() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "LayoutsCodelab"
                    )
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Filled.Favorite, contentDescription = null)
                    }
                }
            )

        }
    ) { innerPadding ->
        BodyContent(
            Modifier
                .padding(innerPadding)
                .padding(8.dp)
        )
    }
}


/**
 * Modifier가 컴포저블에 본질적인 부분이라면, 이를 안쪽에 배치하도록 하자. 만약에 그게 아니라면, 밖에 배치하자.
 * 우리의 경우 padding이 BodyContent를 호출할 때마다 항상 강제로 적용되지 않을 수 있으므로 두번째 옵션을 선택하자.
 * 이건 정말 case-by-case임을 유의하자.
 */

@Composable
fun BodyContent(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .background(color = Color.LightGray)
            .padding(16.dp)
            .size(200.dp)
            .horizontalScroll(rememberScrollState())
    ) {
        StaggeredGrid {
            for (topic in topics) {
                Chip(modifier = Modifier.padding(8.dp), text = topic)
            }
        }
    }
}


/**
 * Column은 기본적으로 스크롤은 다루지 않기 때문에, 몇몇 아이템은 화면 밖을 벗어나 보이지 않게된다.
 * Modifier에 verticalScroll을 추가하여 Column내에서 스크롤을 활성화 할 수 있다.
 */
@Composable
fun SimpleList() {
    Column {
        repeat(100) {
            Text(text = "Item #$it")
        }
    }
}

/**
 * Column은 모든 아이템들을 렌더링 한다. 심지어 화면에나오지 않는 아이템까지 렌더링 하려고 한다. 이런 점 때문에 성능 이슈가 발생하고, 목록의 사이즈가 커지면 커질수록 더욱 문제가 된다.
 * 이런 문제를 회피하기 위해, LazyColumn을 사용한다. LazyColumn은 화면에 보이는 아이템만 렌더링한다. 이는 성능을 증대시키고, Modifier의 scroll항목을 사용할 필요가 없어진다.
 * Note : Jetpack에 포함된 LazyColumn은 안드로이드 View의 RecyclerView와 동등하다.
 * LazyColumn은 DSL을 제공하는데, 이를 통해 목록 내용을 묘사한다.
 * 목록의 사이즈를 취하는 items라는 것을 사용할 것인데, 이는 배열 또는 리스트를 지원한다.
 */

@Composable
fun LazyList() {
    // 스크롤 위치를 저장한다. 이 state는 또한 스크롤을 이동할 때도 사용된다.
    val scrollState = rememberLazyListState()

    LazyColumn(state = scrollState) {
        items(100) {
            Text(text = "Item #$it")
        }
    }
}


@Composable
fun ImageListItem(index: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {

        Image(
            painter = rememberImagePainter(
                data = "https://developer.android.com/images/brand/Android_Robot.png"
            ),
            contentDescription = "Android Logo",
            modifier = Modifier.size(50.dp)
        )
        Spacer(Modifier.width(10.dp))
        Text("Item #$index", style = MaterialTheme.typography.subtitle1)
    }
}

@Composable
fun ImageList() {

    val scrollState = rememberLazyListState()

    LazyColumn(state = scrollState) {
        items(100) { index ->
            ImageListItem(index)
        }
    }

}

@Composable
fun ScrollingList() {

    val listSize = 100

    val scrollState = rememberLazyListState()

    val coroutineScope = rememberCoroutineScope()

    Column {

        Row {
            Button(onClick = {
                coroutineScope.launch {
                    scrollState.animateScrollToItem(0)
                }
            }) {
                Text(text = "Scroll to the top")
            }

            Button(onClick = {
                coroutineScope.launch {
                    scrollState.animateScrollToItem(listSize - 1)
                }
            }) {
                Text(text = "Scroll to the end")
            }
        }

        LazyColumn(state = scrollState) {
            items(100) { index ->
                ImageListItem(index)
            }
        }

    }
}


@SuppressLint("LongLogTag")
fun Modifier.firstBaselineToTop(
    firstBaselineTop: Dp
) = this.then(
    layout { measurable, constraints ->

        val placeable = measurable.measure(constraints)

        check(placeable[FirstBaseline] != AlignmentLine.Unspecified)

        val firstBaseline = placeable[FirstBaseline]


        val placeableY = firstBaselineTop.roundToPx() - firstBaseline

        val height = placeable.height + placeableY

        Log.d("결과-firstBaseline", firstBaseline.toString())
        Log.d("결과-placeable", placeable.height.toString())
        Log.d("결과-firstBaselineTop.roundToPx", firstBaselineTop.roundToPx().toString())
        Log.d("결과-placeableY", placeableY.toString())
        Log.d("결과-height", height.toString())

        layout(
            placeable.width, height
        ) {
            placeable.placeRelative(0, placeableY)
        }
    }
)

@Composable
fun MyOwnColumn(
    modifier: Modifier = Modifier,
    // 커스텀 layout 속성
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->

        val placeables = measurables.map { measurable ->
            measurable.measure(constraints)
        }

        var yPosition = 0

        layout(constraints.maxWidth, constraints.maxHeight) {
            placeables.forEach { placeable ->
                placeable.placeRelative(x = 0, y = yPosition)
                yPosition += placeable.height
            }
        }

    }
}


@Composable
fun StaggeredGrid(
    modifier: Modifier = Modifier,
    rows: Int = 3,
    content: @Composable () -> Unit
) {

    Layout(content = content, modifier = modifier) { measurables, constraints ->

        val rowWidths = IntArray(rows) { 0 }
        val rowHeights = IntArray(rows) { 0 }

        val placeables = measurables.mapIndexed { index, measurable ->

            val placeable = measurable.measure(constraints)

            val row = index % rows

            rowWidths[row] += placeable.width
            rowHeights[row] = max(rowHeights[row], placeable.height)

            placeable
        }

        val width =
            rowWidths.maxOrNull()?.coerceIn(constraints.minWidth.rangeTo(constraints.maxWidth))
                ?: constraints.minWidth
        val height =
            rowHeights.sumOf { it }.coerceIn(constraints.minHeight.rangeTo(constraints.maxHeight))

        val rowY = IntArray(rows) { 0 }

        for (i in 1 until rows) {
            rowY[i] = rowY[i - 1] + rowHeights[i - 1]
        }

        layout(width, height) {

            val rowX = IntArray(rows) { 0 }

            placeables.forEachIndexed { index, placeable ->

                val row = index % rows
                placeable.placeRelative(
                    x = rowX[row],
                    y = rowY[row]
                )

                rowX[row] += placeable.width
            }
        }
    }
}

val topics = listOf(
    "Arts & Crafts", "Beauty", "Books", "Business", "Comics", "Culinary",
    "Design", "Fashion", "Film", "History", "Maths", "Music", "People", "Philosophy",
    "Religion", "Social sciences", "Technology", "TV", "Writing"
)

@Composable
fun Chip(modifier: Modifier = Modifier, text: String) {

    Card(
        modifier = modifier,
        border = BorderStroke(color = Color.Black, width = Dp.Hairline),
        shape = RoundedCornerShape(8.dp)
    ) {

        Row(
            modifier = Modifier.padding(start = 8.dp, top = 4.dp, end = 8.dp, bottom = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(16.dp, 16.dp)
                    .background(color = MaterialTheme.colors.secondary)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = text)
        }

    }

}

@ExperimentalComposeUiApi
@Composable
fun ConstraintLayoutContent() {

    ConstraintLayout() {

        val (button1, button2, text) = createRefs()
        Button(
            onClick = { },
            modifier = Modifier.constrainAs(button1) {
                top.linkTo(parent.top, margin = 16.dp)
            }
        ) {
            Text("Button 1")
        }
        Text(text = "Text", modifier = Modifier.constrainAs(text) {
            top.linkTo(button1.bottom, margin = 16.dp)
            centerHorizontallyTo(parent)
        })

        val barrier = createEndBarrier(button1, text)

        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier.constrainAs(button2) {
                top.linkTo(parent.top, margin = 16.dp)
                start.linkTo(barrier)
            }
        ) {
            Text("Button 2")
        }
    }
}

@Composable
fun LargeConstraintLayout() {

    ConstraintLayout {
        val text = createRef()
        val guideline = createGuidelineFromStart(fraction = 0.5f)

        Text(
            text = "This is a very very very very very very very long text",
            modifier = Modifier.constrainAs(text) {
                linkTo(start = guideline, end = parent.end)
                width = Dimension.preferredWrapContent
            }
        )
    }
}

@Composable
fun DecoupledConstraintLayout() {
    BoxWithConstraints {
        val constraints = if (maxWidth < maxHeight) {
            decoupledConstraints(margin = 16.dp) // Portrait constraints
        } else {
            decoupledConstraints(margin = 32.dp) // Landscape constraints
        }

        ConstraintLayout(constraints) {
            Button(
                onClick = { /* Do something */ },
                modifier = Modifier.layoutId("button")
            ) {
                Text("Button")
            }

            Text("Text", Modifier.layoutId("text"))
        }
    }
}

private fun decoupledConstraints(margin: Dp): ConstraintSet {
    return ConstraintSet {
        val button = createRefFor("button")
        val text = createRefFor("text")

        constrain(button) {
            top.linkTo(parent.top, margin = margin)
        }
        constrain(text) {
            top.linkTo(button.bottom, margin)
        }
    }
}


@Composable
fun TwoTexts(modifier: Modifier = Modifier, text1: String, text2: String) {
    Row(modifier = modifier.height(IntrinsicSize.Min)) {
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(start = 4.dp)
                .wrapContentWidth(Alignment.Start),
            text = text1
        )

        Divider(
            color = Color.Black, modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
        )
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(end = 4.dp)
                .wrapContentWidth(Alignment.End),
            text = text2
        )
    }
}
