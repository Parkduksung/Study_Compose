package com.example.ppt.foundation.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import com.example.ppt.ComposePPTNodeApplier
import com.example.ppt.node.ListNode

@Composable
fun List(content: @Composable () -> Unit) {
    ComposeNode<ListNode, ComposePPTNodeApplier>(
        factory = ::ListNode,
        update = {},
        content = content
    )
}