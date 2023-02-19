package com.example.ppt.node

import com.example.ppt.ComposePPTCanvasContent
import com.example.ppt.foundation.text.DefaultTextStyle
import com.example.ppt.foundation.text.TextStyle
import com.example.ppt.graphics.ComposePPTCanvas

/**
 * A text element in the tree built with composePPT.
 */
class TextNode : ComposePPTNode() {
    var text: String = ""
    var style: TextStyle = DefaultTextStyle

    override var positionIndex: Int = 0

    override fun layout() {
        // No child node. Parent will adjust the position index.
    }

    override fun render(canvas: ComposePPTCanvas): ComposePPTCanvasContent {
        canvas.drawText(
            text,
            positionIndex,
            style
        )

        return canvas.render()
    }
}