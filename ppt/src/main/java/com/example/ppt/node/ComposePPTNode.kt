package com.example.ppt.node

import com.example.ppt.ComposePPTNodeApplier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Composition
import androidx.compose.runtime.CompositionContext
import com.example.ppt.ComposePPTCanvasContent
import com.example.ppt.graphics.ComposePPTCanvas

/**
 * A base node to represent any node in composePPT in slot table.
 */
abstract class ComposePPTNode {

    /**
     * Ideally this is a coordinate rather than index. But for the simplicity,
     * only position index inside the node hierarchy is used.
     */
    open var positionIndex: Int = 0

    /**
     * The children of this node.
     */
    val children = mutableListOf<ComposePPTNode>()

    /**
     * This function measures the bounds of the node.
     */
    abstract fun layout()

    /**
     * Renders the current node to the [canvas] and returns the content.
     */
    abstract fun render(canvas: ComposePPTCanvas): ComposePPTCanvasContent
}

/**
 * Creates the composition from the [ComposePPTNode] and sets its content.
 */
fun ComposePPTNode.setContent(
    parent: CompositionContext,
    content: @Composable () -> Unit
): Composition {
    return Composition(ComposePPTNodeApplier(this), parent).apply {
        setContent(content)
    }
}