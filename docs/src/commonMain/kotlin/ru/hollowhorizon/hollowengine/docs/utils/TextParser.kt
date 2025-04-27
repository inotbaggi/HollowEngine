package ru.hollowhorizon.hollowengine.docs.utils

import de.fabmax.kool.math.Vec2i
import de.fabmax.kool.modules.ui2.*
import de.fabmax.kool.util.Color
import de.fabmax.kool.util.MsdfFont
import de.fabmax.kool.util.MsdfFont.Companion.CUTOFF_SOLID
import de.fabmax.kool.util.MsdfFont.Companion.ITALIC_NONE
import de.fabmax.kool.util.MsdfFont.Companion.ITALIC_STD
import de.fabmax.kool.util.MsdfFont.Companion.WEIGHT_BOLD
import de.fabmax.kool.util.MsdfFont.Companion.WEIGHT_EXTRA_BOLD
import de.fabmax.kool.util.MsdfFont.Companion.WEIGHT_REGULAR
import de.fabmax.kool.util.MsdfFontData
import ru.hollowhorizon.hollowengine.docs.HACK_FONT

object TextParser {
    class Component(
        val text: String = "",
        val style: Style = Style.DEFAULT
    ) {
        val attributes = 0
    }
    class Style(
        // Formating //
        private val bold: Float,
        private val italic: Float,
        private val cutoff: Float,
        private val size: Float,
        private val align: AlignmentX,
        // Font //
        private val font: MsdfFontData,
        private val color: Color,
        private val glowColor: Color?,
        // Additions //
        private val rainbow: Boolean,
        private val gradColors: List<Color>?
    ) {
        companion object {
            val DEFAULT = Style(
                WEIGHT_REGULAR,
                ITALIC_NONE, CUTOFF_SOLID,
                14f,
                AlignmentX.Start,
                HACK_FONT,
                Color.WHITE,
                null,
                false,
                null
            )
        }
        val msdf: MsdfFont get() = MsdfFont(font, size, italic, bold, cutoff, glowColor)
        val isRainbow get() = this.rainbow
        val gradientColors: List<Color>? get() = this.gradColors
        val textColor: Color get() = color
        val textAlign: AlignmentX get() = align
        val px: Float get() = size

        fun copy(
            bold: Float = this.bold,
            italic: Float = this.italic,
            cutoff: Float = this.cutoff,
            size: Float = this.size,
            align: AlignmentX = this.align,
            font: MsdfFontData = this.font,
            color: Color = this.color,
            glowColor: Color? = this.glowColor,
            rainbow: Boolean = this.rainbow,
            gradColor: List<Color>? = this.gradColors
        ): Style = Style(bold, italic, cutoff, size, align, font, color, glowColor, rainbow, gradColor)
    }

    fun Style.toAttributes(overrideColor: Color? = null): TextAttributes = TextAttributes(msdf, overrideColor ?: textColor, null)

    private fun parseTextAttributes(text: String): List<Pair<String, TextAttributes>> {
        val result = mutableListOf<Pair<String, TextAttributes>>()
        val stack = ArrayDeque<Style>()
        var current = Style.DEFAULT
        val buffer = StringBuilder()

        var i = 0
        while (i < text.length) {
            if (text[i] == '<') {
                if (buffer.isNotEmpty()) {
                    result += buffer.toString() to current.toAttributes()
                    buffer.clear()
                }

                val isClosing = text.startsWith("</", i)
                val tagStart = i + if (isClosing) 2 else 1
                val tagEnd = text.indexOf('>', tagStart)
                if (tagEnd == -1) break

                val tagContent = text.substring(tagStart, tagEnd)

                if (isClosing) {
                    if (stack.isNotEmpty()) {
                        current = stack.removeLast()
                    }
                } else {
                    stack.addLast(current)
                    current = when {
                        tagContent.equals("b", true) -> {
                            current.copy(bold = WEIGHT_BOLD)
                        }
                        tagContent.equals("i", true) -> {
                            current.copy(italic = ITALIC_STD)
                        }
                        tagContent.startsWith("color=", true) -> {
                            val colorCode = tagContent.substringAfter("color=", "")
                            if (colorCode.isNotEmpty()) {
                                current.copy(color = Color(colorCode))
                            } else current
                        }
                        tagContent.startsWith("size=", true) -> {
                            val sizeValue = tagContent.substringAfter("size=").toFloatOrNull()
                            if (sizeValue != null) {
                                current.copy(size = sizeValue)
                            } else current
                        }
                        tagContent.equals("h1", true) -> {
                            current.copy(size = 20f)
                        }
                        tagContent.equals("h2", true) -> {
                            current.copy(size = 18f)
                        }
                        tagContent.equals("h3", true) -> {
                            current.copy(size = 15f)
                        }
                        tagContent.equals("h4", true) -> {
                            current.copy(size = 12f)
                        }
                        tagContent.equals("h5", true) -> {
                            current.copy(size = 10f)
                        }
                        tagContent.equals("h6", true) -> {
                            current.copy(size = 8f)
                        }
                        tagContent.equals("rainbow", true) -> {
                            current.copy(rainbow = true)
                        }
                        tagContent.startsWith("gradient=", true) -> {
                            val colorList = tagContent
                                .substringAfter("gradient=")
                                .split(',')
                                .mapNotNull { it.trim().takeIf { it.isNotEmpty() }?.let(::Color) }
                            if (colorList.isNotEmpty()) {
                                current.copy(gradColor = colorList)
                            } else current
                        }
                        else -> current
                    }
                }; i = tagEnd
            } else
                buffer.append(text[i])
            i++
        }

        if (buffer.isNotEmpty())
            result += buffer.toString() to current.toAttributes()

        return result
    }

    private fun parseText(text: String): List<Pair<String, Style>> {
        val result = mutableListOf<Pair<String, Style>>()
        val stack = ArrayDeque<Style>()
        var current = Style.DEFAULT
        val wordBuffer = StringBuilder()
        var i = 0

        while (i < text.length) {
            if (text[i] == '<') {
                // Сохраняем предыдущие слова, если они есть
                if (wordBuffer.isNotEmpty()) {
                    result += wordBuffer.toString() to current
                    wordBuffer.clear()
                }

                val isClosing = text.startsWith("</", i)
                val tagStart = i + if (isClosing) 2 else 1
                val tagEnd = text.indexOf('>', tagStart)
                if (tagEnd == -1) break

                val tagContent = text.substring(tagStart, tagEnd)

                if (isClosing) {
                    if (stack.isNotEmpty()) {
                        current = stack.removeLast()
                    }
                } else {
                    stack.addLast(current)
                    current = when {
                        tagContent.equals("bold", true) -> {
                            current.copy(bold = WEIGHT_EXTRA_BOLD)
                        }
                        tagContent.equals("italic", true) -> {
                            current.copy(italic = ITALIC_STD)
                        }
                        tagContent.startsWith("color=", true) -> {
                            val colorCode = tagContent.substringAfter("color=", "")
                            if (colorCode.isNotEmpty()) {
                                current.copy(color = Color(colorCode))
                            } else current
                        }
                        tagContent.startsWith("size=", true) -> {
                            val sizeValue = tagContent.substringAfter("size=").toFloatOrNull()
                            if (sizeValue != null) {
                                current.copy(size = sizeValue)
                            } else current
                        }
                        tagContent.equals("h1", true) -> {
                            current.copy(size = 25f)
                        }
                        tagContent.equals("h2", true) -> {
                            current.copy(size = 22f)
                        }
                        tagContent.equals("h3", true) -> {
                            current.copy(size = 18f)
                        }
                        tagContent.equals("h4", true) -> {
                            current.copy(size = 14f)
                        }
                        tagContent.equals("h5", true) -> {
                            current.copy(size = 12f)
                        }
                        tagContent.equals("h6", true) -> {
                            current.copy(size = 10f)
                        }
                        tagContent.equals("rainbow", true) -> {
                            current.copy(rainbow = true)
                        }
                        tagContent.startsWith("gradient=", true) -> {
                            val colorList = tagContent
                                .substringAfter("gradient=")
                                .split(',')
                                .mapNotNull { it.trim().takeIf { it.isNotEmpty() }?.let(::Color) }
                            if (colorList.isNotEmpty()) {
                                current.copy(gradColor = colorList)
                            } else current
                        }
                        tagContent.startsWith("left", true) -> {
                            current.copy(align = AlignmentX.Start)
                        }
                        tagContent.startsWith("center", true) -> {
                            current.copy(align = AlignmentX.Center)
                        }
                        tagContent.startsWith("right", true) -> {
                            current.copy(align = AlignmentX.End)
                        }
                        else -> current
                    }
                }
                i = tagEnd
            } else {
                // Добавляем символ в буфер для текущего слова
                if (text[i].isWhitespace()) {
                    // Когда встречаем пробел, сохраняем предыдущее слово
                    if (wordBuffer.isNotEmpty()) {
                        result += wordBuffer.toString() to current
                        wordBuffer.clear()
                    }
                } else {
                    wordBuffer.append(text[i])
                }
            }
            i++
        }

        // В случае, если в буфере осталось слово
        if (wordBuffer.isNotEmpty()) {
            result += wordBuffer.toString() to current
        }

        return result
    }


    private fun processSpecialText(text: String, style: Style): List<Pair<String, TextAttributes>> {
        val result = mutableListOf<Pair<String, TextAttributes>>()
        val chars = text.toCharArray()
        val count = chars.size

        if(style.isRainbow) {
            chars.forEachIndexed { i, c ->
                val hue = (i.toFloat() / count) * 360f
                val color = Color(hue, 1f, 1f)

                result += c.toString() to style.toAttributes(color)
            }
        } else if(style.gradientColors != null ) {
            val gradColor = style.gradientColors

            if(gradColor != null && gradColor.size >= 2) {
                val steps = gradColor.size - 1
                chars.forEachIndexed { i, c ->
                    val t = i.toFloat() / (count - 1)
                    val segment = (t * steps).toInt().coerceAtMost(steps - 1)
                    val localT = (t * steps) - segment

                    val start = gradColor[segment]
                    val end = gradColor[segment + 1]
                    val color = lerpColor(start, end, localT)

                    result.add(c.toString() to style.toAttributes(color))
                }
            }
        } else
            result.add(text to style.toAttributes())

        return result
    }


    // Use this //
    fun UiScope.textp(vararg texts: String) {
        val lineBuffer = mutableListOf<Pair<String, Style>>()
        var currentLineWidth = 0f

        texts.forEach { text ->
            parseText(text).forEach { (word, style) ->
                val wordWidth = calculateTextWidth(word, style)

                if (currentLineWidth + wordWidth > 1f) {
                    displayLine(lineBuffer)

                    lineBuffer.clear()
                    currentLineWidth = 0f
                }

                lineBuffer.add(word to style)
                currentLineWidth += wordWidth + calculateTextWidth(" ", style)
            }
        }

        if (lineBuffer.isNotEmpty()) displayLine(lineBuffer)
    }

    private fun calculateTextWidth(text: String, style: Style): Float =
        text.length * style.px * 0.6f

    private fun UiScope.displayLine(lineBuffer: List<Pair<String, Style>>) {
        lineBuffer.forEach { (word, style) ->
            Text(word) {
                modifier
                    .font(style.msdf)
                    .textColor(style.textColor)
                    .textAlignX(style.textAlign)
                    .width(Grow.Std)
            }
        }
    }

    private fun lerpColor(a: Color, b: Color, t: Float): Color = Color(
        lerp(a.r, b.r, t),
        lerp(a.g, b.g, t),
        lerp(a.b, b.b, t),
        lerp(a.a, b.a, t)
    )

    private fun lerp(start: Float, end: Float, t: Float): Float = start + (end - start) * t

    object ReadOnlyTextEditorHandler: TextEditorHandler {
        override fun insertText(line: Int, caret: Int, insertion: String, textAreaScope: TextAreaScope): Vec2i =
            Vec2i(line, caret)

        override fun replaceText(
            selectionStartLine: Int,
            selectionEndLine: Int,
            selectionStartChar: Int,
            selectionEndChar: Int,
            replacement: String,
            textAreaScope: TextAreaScope,
        ): Vec2i =
            Vec2i(selectionEndChar, selectionStartLine)
    }
}