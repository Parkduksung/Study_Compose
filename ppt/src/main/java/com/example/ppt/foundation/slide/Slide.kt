package com.example.ppt.foundation.slide

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import com.example.ppt.ComposePPTNodeApplier
import com.example.ppt.node.SlideNode

/**
 * A core element to display a slide.
 */
@Composable
fun Slide(
    content: @Composable () -> Unit
) {
    ComposeNode<SlideNode, ComposePPTNodeApplier>(
        factory = {
            SlideNode(SlideNode.SlideType.ONLY_BODY)
        },
        update = {},
        content = content
    )
}

/**
 * A core element to display a slide with [title].
 */
@Composable
fun Slide(
    title: String,
    content: @Composable () -> Unit
) {
    ComposeNode<SlideNode, ComposePPTNodeApplier>(
        factory = {
            SlideNode(SlideNode.SlideType.TITLE_AND_BODY)
        },
        update = {
            set(title) {
                this.title = title
            }
        },
        content = content
    )
}