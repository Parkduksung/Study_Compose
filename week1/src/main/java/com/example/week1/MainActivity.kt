package com.example.week1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.week1.ui.theme.StudyComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //@Composable 어노테이션을 붙여서 Compose 의 구성요소가 될 수 있게끔 해준다.
        //내부를 들여다보면 setContent 라는 확장함수는 파라메터로 parent 와 붙여줄 @Composable 어노테이션을 붙인 content 를 람다로 넘기게끔 되어있다.
        //처음에 window 의 decorView 에서 content 에 달려있는 자식이 아무것도 없기에 새롭게 ComposeView 를 생성한다.
        //그리고나서 parent 가 null 이게 되면 parentContext 가 window 에 달라붙어있는거로 자동적으로 적용된다.
        //또한 mutableStateOf 에 @Composable 이 붙은 Text 등을 람다로 넘겨준 것들을 set 해주게 된다.
        //여기서 중요한건 setContent 에서 createComposition 이 불려지게 되면,
        // window 가 attach 되어있고 parentContext 가 null 이 아닐경우 ensureCompositionCreated 가 불려지는데
        // try-catch 문으로 setContent 에 Content() 를 호출하여 (템플릿메소드패턴) invoke 를 시켜주고 ViewGroup 에 addView 를 해준다.
        // 맨 화면 위에 wrap 으로 나오는 이유는 addView 할때 DefaultLayoutParams 로 default 값이 되어있기 때문이다.
        // 그리고 setOwner 는 AppCompat 에 하위호환 이라고 보면 될 것 같다.
        // 마지막으로 setContentView 에 ViewGroup 인 ComposeView 가 추가되고 어떠한 layoutParam 특성을 주지 않았기 때문에 DefaultActivityContentLayoutParams(wrap,wrap 이라 보면 된다.) 가 추가된다.
        // 결론적으로 아래 3줄의 코드를 실행했을때의 결과는 맨 위에 왼쪽에 Hello world 가 나오게 된다.
        setContent {
            Text("Hello world")
        }
    }
}
