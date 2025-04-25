package ru.hollowhorizon.hollowengine.docs.utils

import de.fabmax.kool.modules.ui2.*
import de.fabmax.kool.util.Color
import de.fabmax.kool.util.MsdfFont
import de.fabmax.kool.util.MsdfFont.Companion.ITALIC_STD
import de.fabmax.kool.util.MsdfFont.Companion.WEIGHT_LIGHT
import de.fabmax.kool.util.MsdfFontData
import ru.hollowhorizon.hollowengine.docs.HACK_FONT

object TextParser {
    // FUN :) //
    /**
     * Это ребёнок
     */
    class Childer(val name: String) {
        init { println("Ребёнок \"\" появился на свет!") }

        /**
         * Взрывает ребёнка
         */
        fun boom() = println("Ребёнок \"$name\" был взорван :D")
    }

    fun childLive() {
        val child = Childer("KaBoom")
        println("После того, как ребёнок \"${child.name}\" появился на свет - случилось следующее:")
        child.boom()
    }

    // SERIOUSLY >:| //
    private val headerTags = mapOf(
        "h1" to Header.H1,
        "h2" to Header.H2,
        "h3" to Header.H3,
        "h4" to Header.H4,
        "h5" to Header.H5,
        "h6" to Header.H6
    )

    data class TextFormat(
        val text: String,
        val font: MsdfFontData = HACK_FONT,
        val header: Header = Header.H4,
        val bold: Boolean = false,
        val italic: Boolean = false,
        val textAlignX: AlignmentX = AlignmentX.Start,
        val textAlignY: AlignmentY = AlignmentY.Center,
        val textColor: Color = Color.WHITE
    )

    private fun textParser(input: String): List<TextFormat> {
        val regex = Regex("""<(/?)(\w+)(?:=([^>]+))?>""")
        val result = mutableListOf<TextFormat>()
        var currentFormat = TextFormat(text = "")
        val formatStack = ArrayDeque<TextFormat>()

        var lastIndex = 0
        regex.findAll(input).forEach { match ->
            val start = match.range.first
            val end = match.range.last + 1

            // Текст перед тегом
            val rawText = input.substring(lastIndex, start)
            if (rawText.isNotEmpty()) {
                result += currentFormat.copy(text = rawText)
            }

            val isClosing = match.groupValues[1].isNotEmpty()
            val tagName = match.groupValues[2].lowercase()
            val tagArg = match.groupValues.getOrNull(3)

            currentFormat = updateFormat(tagName, tagArg, isClosing, currentFormat, formatStack)
            lastIndex = end
        }

        // Остаточный текст
        if (lastIndex < input.length) {
            val remainingText = input.substring(lastIndex)
            if (remainingText.isNotEmpty()) {
                result += currentFormat.copy(text = remainingText)
            }
        }

        return result
    }


    private fun updateFormat(
        tag: String,
        arg: String?,
        closing: Boolean,
        current: TextFormat,
        stack: ArrayDeque<TextFormat>
    ): TextFormat {
        return when (tag) {
            "bold" -> {
                if (closing) stack.removeLastOrNull() ?: current
                else {
                    stack.addLast(current)
                    current.copy(bold = true)
                }
            }
            "italic" -> {
                if (closing) stack.removeLastOrNull() ?: current
                else {
                    stack.addLast(current)
                    current.copy(italic = true)
                }
            }
            "align" -> {
                if (closing) stack.removeLastOrNull() ?: current
                else {
                    stack.addLast(current)
                    val alignX = when (arg?.lowercase()) {
                        "c" -> AlignmentX.Center
                        "r" -> AlignmentX.End
                        "l" -> AlignmentX.Start
                        else -> current.textAlignX
                    }
                    current.copy(textAlignX = alignX)
                }
            }
            "color" -> {
                if (closing) stack.removeLastOrNull() ?: current
                else {
                    stack.addLast(current)
                    val color = arg?.let { Color(it) } ?: current.textColor
                    current.copy(textColor = color)
                }
            }
            else -> {
                val header = headerTags[tag]
                if (header != null) {
                    if (closing) stack.removeLastOrNull() ?: current
                    else {
                        stack.addLast(current)
                        current.copy(header = header)
                    }
                } else current
            }
        }
    }

    fun UiScope.text(text: String) {
        textParser(text).forEach {
            Text(it.text) {
                modifier
                    .font(MsdfFont(
                        data = it.font,
                        sizePts = it.header.fontSize,
                        weight = if(it.bold) WEIGHT_LIGHT else 0f,
                        italic = if(it.italic) ITALIC_STD else 0f
                    ))
                    .textAlign(it.textAlignX, it.textAlignY)
                    .textColor(it.textColor)
                    .isWrapText(true)
                    .size(Grow.Std, FitContent)
            }
        }
    }
}

enum class Header(val fontSize: Float) { H1(20f), H2(18f), H3(14f), H4(12f), H5(10f), H6(8f) }