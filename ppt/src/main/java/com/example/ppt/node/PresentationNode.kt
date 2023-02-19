package com.example.ppt.node

import com.example.ppt.ComposePPTCanvasContent
import com.example.ppt.graphics.ComposePPTCanvas

/**
 * A presentation element in the tree built with composePPT. [PresentationNode] can
 * only have [SlideNode] nodes.
 */
internal class PresentationNode : ComposePPTNode() {
    override fun layout() {
        children.forEach {
            require(it is SlideNode) {
                "Presentation node should only have a slide node as its direct child."
            }

            it.layout()
        }
    }

    override fun render(canvas: ComposePPTCanvas): ComposePPTCanvasContent {
        layout()

        return ComposePPTCanvasContent.PresentationContent(
            slides = children.map {
                it.render(canvas) as ComposePPTCanvasContent.SlideContent
            }
        )
    }
}