package com.example.lifecycle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.example.lifecycle.ui.theme.StudyComposeTheme

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
                    LifeCycleScreen()
                }
            }
        }
    }
}

@Composable
fun LifeCycleScreen() {

    //해당 버전에서는 mutableIntStateOf 가 없다.
    //androidx.compose.runtime:runtime-*:1.5.0 이상부터 추가.
    var count by remember { mutableStateOf(0) }


    //이건 처음 onResume 시에는 실행되지 않다가 onPause 시에 count++ 실행.
    //그래서 onResume 으로 다시 돌아왔을때 count 가 1이 된다.
    //Pause 에 onPauseOrDispose 내부가 실행되는거니 너무 헤비한 작업은 하면 안되겠다.
    LifecycleResumeEffect(key1 = Unit) {
        onPauseOrDispose {
            count++
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "OnResume Count: $count")

    }

}


/**
 * lifecycle 2.7.0 에 추가된 내용들.
 */
@Composable
private fun LifecycleEventEffect(
    event: Lifecycle.Event,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    onEvent: () -> Unit
) {
    //ON_DESTROY 일 경우엔 예외.
    if (event == Lifecycle.Event.ON_DESTROY) {
        throw IllegalArgumentException(
            "LifecycleEventEffect cannot be used to " +
                    "listen for Lifecycle.Event.ON_DESTROY, since Compose disposes of the " +
                    "composition before ON_DESTROY observers are invoked."
        )
    }
    //새로운 람다 (onEvent) 에 대한 안전한 업데이트를 위해
    val currentOnEvent by rememberUpdatedState(onEvent)

    //lifecycleOwner 에 대한 매번 초기화.
    DisposableEffect(lifecycleOwner) {

        //수신객체로 lifecycleOwner 와 event 를 받는다.
        val observer = LifecycleEventObserver { _, e ->

            //수신객체로 받은 event 가 현재 event 와 같다면
            if (e == event) {

                //onEvent 를 실행한다.
                //(내부에 mutableStateOf 의 값을 변경해주는 역할)
                currentOnEvent()
            }
        }
        //위의 옵저버 틍록
        lifecycleOwner.lifecycle.addObserver(observer)

        //onDispose 에서는 옵저버 제거.
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}


@Composable
fun LifecycleResumeEffect(
    key1: Any?,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    effects: LifecycleResumePauseEffectScope.() -> LifecyclePauseOrDisposeEffectResult //이쪽으로 LifecycleResumeEffectImpl 에서 onResume 이벤트 위로 올려줌.
) {
    val lifecycleResumePauseEffectScope = remember(key1, lifecycleOwner) {
        LifecycleResumePauseEffectScope(lifecycleOwner.lifecycle)
    }
    LifecycleResumeEffectImpl(lifecycleOwner, lifecycleResumePauseEffectScope, effects)
}


//LifecycleOwner 를 상속받은 ResumePauseEffectScope 클래스.
class LifecycleResumePauseEffectScope(override val lifecycle: Lifecycle) : LifecycleOwner {

    // inline crossinline 으로 onPauseOrDispose 인자로 들어오는걸 알수 있음.
    inline fun onPauseOrDispose(
        crossinline onPauseOrDisposeEffect: LifecycleOwner.() -> Unit
    ): LifecyclePauseOrDisposeEffectResult = object : LifecyclePauseOrDisposeEffectResult {
        override fun runPauseOrOnDisposeEffect() {
            onPauseOrDisposeEffect()
        }
    }
}


interface LifecyclePauseOrDisposeEffectResult {
    fun runPauseOrOnDisposeEffect()
}


@Composable
private fun LifecycleResumeEffectImpl(
    lifecycleOwner: LifecycleOwner,
    scope: LifecycleResumePauseEffectScope,
    effects: LifecycleResumePauseEffectScope.() -> LifecyclePauseOrDisposeEffectResult
) {
    //key 가 lifecycleOwner 랑 scope
    //둘중 하나 변하면 DisposableEffect 를 다시 실행.
    DisposableEffect(lifecycleOwner, scope) {
        //초기값 설정.
        var effectResult: LifecyclePauseOrDisposeEffectResult? = null

        //옵저버 생성
        val observer = LifecycleEventObserver { _, event ->
            when (event) {

                //onResume 일 경우 위에 전달해줄수 있게 effectResult = effects() 치환.
                Lifecycle.Event.ON_RESUME -> with(scope) {
                    effectResult = effects()
                }

                //onPause 일 경우 effectResult?.runPauseOrOnDisposeEffect() 실행.
                Lifecycle.Event.ON_PAUSE -> effectResult?.runPauseOrOnDisposeEffect()

                else -> {}
            }
        }

        //옵저버 등록.
        lifecycleOwner.lifecycle.addObserver(observer)

        //onDispose 에서는 옵저버 제거.
        //runPauseOrOnDisposeEffect 실행.
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            effectResult?.runPauseOrOnDisposeEffect()
        }
    }
}

