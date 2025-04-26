package ru.hollowhorizon.hollowengine.docs.utils

import de.fabmax.kool.modules.ui2.*
import de.fabmax.kool.util.Color
import de.fabmax.kool.util.MsdfFont
import de.fabmax.kool.util.MsdfFont.Companion.CUTOFF_SOLID
import de.fabmax.kool.util.MsdfFont.Companion.ITALIC_NONE
import de.fabmax.kool.util.MsdfFont.Companion.ITALIC_STD
import de.fabmax.kool.util.MsdfFont.Companion.WEIGHT_BOLD
import de.fabmax.kool.util.MsdfFont.Companion.WEIGHT_REGULAR
import de.fabmax.kool.util.MsdfFontData
import ru.hollowhorizon.hollowengine.docs.HACK_FONT

object TextParser {
    // FUN :) //
    /**
     * Это ребёнок
     */
    class Childer(val name: String) {
        init {
            println("Ребёнок \"\" появился на свет!")
        }

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

    class TextFormat(
        val text: String = "",
        private val data: MsdfFontData = HACK_FONT,
        private val sizePts: Float = 14f,
        private val italic: Float = ITALIC_NONE,
        private val weight: Float = WEIGHT_REGULAR,
        private val cutoff: Float = CUTOFF_SOLID,
        private val fontColor: Color = Color.WHITE,
        private val glowColor: Color? = null,
        private val headerType: Header = Header.H4,
        private val textAlignX: AlignmentX = AlignmentX.Start,
        private val textAlignY: AlignmentY = AlignmentY.Center
    ) {
        val font: MsdfFont get() = MsdfFont(data, sizePts, italic, weight, cutoff, glowColor)
        val color: Color get() = fontColor
        val align: Pair<AlignmentX, AlignmentY> get() = textAlignX to textAlignY
        val header: Header get() = headerType

        fun copy(
            text: String = this.text,
            data: MsdfFontData = this.data,
            sizePts: Float = this.sizePts,
            italic: Float = this.italic,
            weight: Float = this.weight,
            cutoff: Float = this.cutoff,
            fontColor: Color = this.fontColor,
            glowColor: Color? = this.glowColor,
            headerType: Header = this.headerType,
            textAlignX: AlignmentX = this.textAlignX,
            textAlignY: AlignmentY = this.textAlignY
        ): TextFormat = TextFormat(text, data, sizePts, italic, weight, cutoff, fontColor, glowColor, headerType, textAlignX, textAlignY)

    }

    private fun textParser(input: String): List<TextFormat> {
        val regex = Regex("""<(/?)(\w+)(?::([^>]+))?>""")
        val result = mutableListOf<TextFormat>()
        var currentFormat = TextFormat(text = "")
        val formatStack = ArrayDeque<TextFormat>()
        var lastIndex = 0

        regex.findAll(input).forEach { match ->
            val start = match.range.first
            val end = match.range.last + 1

            // Текст перед тегом
            val rawText = input.substring(lastIndex, start)
            if (rawText.isNotBlank()) {
                result += currentFormat.copy(text = rawText)
            }

            val isClosing = match.groupValues[1].isNotEmpty()
            val tagName = match.groupValues[2].lowercase()

            // Обновление формата
            currentFormat = updateFormat(tagName, isClosing, currentFormat, formatStack)

            lastIndex = end
        }

        // Остаточный текст
        if (lastIndex < input.length) {
            val remainingText = input.substring(lastIndex)
            if (remainingText.isNotBlank()) {
                result += currentFormat.copy(text = remainingText)
            }
        }

        return result
    }

    private fun textParserPair(input: String): List<Pair<String, TextFormat>> {
        val regex = Regex("""<(/?)(\w+)(?::([^>]+))?>""")
        val result = mutableListOf<Pair<String, TextFormat>>()
        var currentFormat = TextFormat(text = "")
        val formatStack = ArrayDeque<TextFormat>()
        var lastIndex = 0

        regex.findAll(input).forEach { match ->
            val start = match.range.first
            val end = match.range.last + 1

            val rawText = input.substring(lastIndex, start)
            if (rawText.isNotBlank()) {
                result += rawText to currentFormat
            }

            val isClosing = match.groupValues[1].isNotEmpty()
            val tagName = match.groupValues[2].lowercase()

            currentFormat = updateFormat(tagName, isClosing, currentFormat, formatStack)
            lastIndex = end
        }

        if (lastIndex < input.length) {
            val remainingText = input.substring(lastIndex)
            if (remainingText.isNotBlank()) {
                result += remainingText to currentFormat
            }
        }

        return result
    }


    private fun updateFormat(
        tag: String,
        closing: Boolean,
        current: TextFormat,
        stack: ArrayDeque<TextFormat>
    ): TextFormat {
        // Отделим имя тега от параметров
        val tagParts = tag.removePrefix("</").removeSuffix(">").split(":", limit = 2)
        val tagName = tagParts[0]
        val tagArg = tagParts.getOrNull(1)

        return when (tagName) {
            "bold" -> {
                if (closing)
                    stack.removeLastOrNull() ?: current
                else {
                    stack.addLast(current)
                    current.copy(weight = WEIGHT_BOLD)
                }
            }
            "italic" -> {
                if(closing)
                    stack.removeLastOrNull() ?: current
                else {
                    stack.addLast(current)
                    current.copy(italic = ITALIC_STD)
                }
            }
            "align" -> {
                if (closing) stack.removeLastOrNull() ?: current
                else {
                    stack.addLast(current)
                    val alignX = when (tagArg?.lowercase()) {
                        "c" -> AlignmentX.Center
                        "r" -> AlignmentX.End
                        "l" -> AlignmentX.Start
                        else -> current.align.first
                    }
                    current.copy(textAlignX = alignX)
                }
            }

            "color" -> {
                if (closing) stack.removeLastOrNull() ?: current
                else {
                    stack.addLast(current)
                    val color = tagArg?.let { Color(it) } ?: current.color
                    current.copy(fontColor = color)
                }
            }

            else -> {
                val header = headerTags[tagName]
                if (header != null) {
                    if (closing) stack.removeLastOrNull() ?: current
                    else {
                        stack.addLast(current)
                        current.copy(headerType = header)
                    }
                } else current
            }
        }
    }

    fun UiScope.text(text: String) {
        textParser(text).forEach {
            Text(it.text) {
                modifier
                    .font(it.font)
                    .textAlign(it.align.first, it.align.second)
                    .textColor(it.color)
                    .isWrapText(true)
                    .width(Grow.Std)
            }
        }
    }

    enum class Header(val fontSize: Float) { H1(20f), H2(18f), H3(14f), H4(12f), H5(10f), H6(8f) }

    private fun textLineFromRichText(input: String): TextLine {
        val regex = Regex("""<(/?)(\w+)(?::([^>]+))?>""")
        val result = mutableListOf<Pair<String, TextAttributes>>()
        var currentFormat = TextFormat(text = "")
        val formatStack = ArrayDeque<TextFormat>()

        var lastIndex = 0
        regex.findAll(input).forEach { match ->
            val start = match.range.first
            val end = match.range.last + 1

            val rawText = input.substring(lastIndex, start)
            if (rawText.isNotEmpty()) {
                result += rawText to currentFormat.toTextAttributes()
            }

            val isClosing = match.groupValues[1].isNotEmpty()
            val tagName = match.groupValues[2].lowercase()
            currentFormat = updateFormat(tagName, isClosing, currentFormat, formatStack)

            lastIndex = end
        }

        if (lastIndex < input.length) {
            val remainingText = input.substring(lastIndex)
            if (remainingText.isNotEmpty()) {
                result += remainingText to currentFormat.toTextAttributes()
            }
        }

        return TextLine(result)
    }

    private fun TextFormat.toTextAttributes(): TextAttributes = TextAttributes(this.font, this.color)

    fun UiScope.textRich(text: String) {
        Row { textLineFromRichText(text).spans.forEach {
            Text(it.first) { modifier.font(it.second.font).textColor(it.second.color) }
        } }
    }
}