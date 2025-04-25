package ru.hollowhorizon.hollowengine.docs.utils

object TextParser {
    /**
     * Модель данных
     */
    sealed class ParserElement {
        data class Text(val text: String): ParserElement()
        data class Tag(val name: String, val child: List<ParserElement>): ParserElement()
    }

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

    /**
     * Парсинг текста на токены
     */
    private fun parsingText(input: String): List<ParserElement> {
        val regex: Regex = Regex("""<(/?)(\w+)(.*?)>""")
        val result: MutableList<ParserElement> = mutableListOf()
        val stack: ArrayDeque<MutableList<ParserElement>> = ArrayDeque()
        var cur: Int = 0 // Текущее положение курсора в строке

        fun appendText(text: String) {
            if(text.isNotBlank()) {
                if(stack.isNotEmpty())
                    stack.last() += ParserElement.Text(text)
                else
                    result += ParserElement.Text(text)
            }
        }

        regex.findAll(input).forEach {
            val s = it.range.first
            val e = it.range.last + 1
            val tBef = input.substring(cur, s)
            appendText(tBef)

            val (closing, tag) = it.groupValues[1].isNotEmpty() to it.groupValues[2].lowercase()

            cur = e // Смещение курсора

            if(closing) { // Если тег - закрывающий
                val child = stack.removeLastOrNull() ?: emptyList()

                if(stack.isNotEmpty())
                    stack.last() += ParserElement.Tag(tag, child)
                else
                    result += ParserElement.Tag(tag, child)
            } else // Если это - открывающий
                stack.addLast(mutableListOf())

            appendText(input.substring(cur))
        }

        while(stack.isNotEmpty()) result.addAll(stack.removeLast())

        return result
    }
}

/*

// Простой HTML-подобный парсер rich-текста
fun parseRichText(input: String): List<RichTextElement> {

    for (match in regex.findAll(input)) {
        val start = match.range.first
        val end = match.range.last + 1
        val textBefore = input.substring(lastIndex, start)
        appendText(textBefore)

        val closing = match.groupValues[1].isNotEmpty()
        val tagName = match.groupValues[2].lowercase()
        val attributes = match.groupValues[3] // атрибуты тега, если нужны для расширений

        lastIndex = end

        when {
            closing -> {
                val content = stack.removeLastOrNull()
                if (content != null && content.first == tagName) {
                    val tag = RichTextElement.Tag(tagName, content.second)
                    if (stack.isNotEmpty()) {
                        stack.last().second.add(tag)
                    } else {
                        result.add(tag)
                    }
                }
            }

            tagName == "br" -> {
                result.add(RichTextElement.LineBreak)
            }

            tagName == "divide" -> {
                result.add(RichTextElement.Divider)
            }

            else -> {
                stack.addLast(tagName to mutableListOf())
            }
        }
    }

    appendText(input.substring(lastIndex))
    while (stack.isNotEmpty()) {
        result.addAll(stack.removeLast().second)
    }

    return result
}


// Объединить все текстовые элементы в одну строку
fun List<RichTextElement>.joinText(): String =
    joinToString("") { if (it is RichTextElement.Text) it.text else "" }

typealias RichTextHandler = UiScope.(tag: RichTextElement.Tag, context: RichTextRenderContext) -> Unit

class RichTextRenderContext(
    val handlers: Map<String, RichTextHandler>,
    val defaultAlignX: AlignmentX,
    val defaultAlignY: AlignmentY
) {
    fun renderIn(scope: UiScope, elements: List<RichTextElement>) {
        for (element in elements) {
            when (element) {
                is RichTextElement.Text -> scope.text(element.text, alignmentX = defaultAlignX)
                is RichTextElement.LineBreak -> scope.Box { modifier.height(4.dp).width(Grow(1f)) }

                is RichTextElement.Divider -> scope.Box(Grow.Std) {
                    modifier
                        .size(Grow(1f), 1.dp)
                        .backgroundColor(Color.LIGHT_GRAY)
                        .margin(vertical = 4.dp)
                }

                is RichTextElement.Tag -> renderTagIn(scope, element)
            }
        }
    }

    fun renderTagIn(scope: UiScope, tag: RichTextElement.Tag) {
        handlers[tag.name]?.invoke(scope, tag, this)
            ?: renderIn(scope, tag.children)
    }
}

// Text Parser
fun UiScope.text(
    raw: String,
    handlers: Map<String, RichTextHandler> = defaultTagHandlers,
    alignmentX: AlignmentX = AlignmentX.Start,
    alignmentY: AlignmentY = AlignmentY.Top
): UiScope {
    val parsed = parseRichText(raw)
    val context = RichTextRenderContext(handlers, alignmentX, alignmentY)

    Column(Grow.Std) { context.renderIn(this, parsed) }

    return this
}

val defaultTagHandlers: Map<String, RichTextHandler> = mapOf(
    "center" to { tag, context ->
        Column {
            modifier.alignX = AlignmentX.Center
            context.renderIn(this, tag.children)
        }
    },

    "bold" to { tag, context ->
        Column {
            for (element in tag.children) {
                if (element is RichTextElement.Text)
                    textHelper(element.text, bold = true, alignmentX = context.defaultAlignX)
                else if (element is RichTextElement.Tag)
                    context.renderTagIn(this, element)
            }
        }
    },

    "h1" to { tag, context -> textHelper(tag.children.joinText(), header = Header.H1, alignmentX = context.defaultAlignX) },
    "h2" to { tag, context -> textHelper(tag.children.joinText(), header = Header.H2, alignmentX = context.defaultAlignX) },
    "h3" to { tag, context -> textHelper(tag.children.joinText(), header = Header.H3, alignmentX = context.defaultAlignX) },
    "h4" to { tag, context -> textHelper(tag.children.joinText(), header = Header.H4, alignmentX = context.defaultAlignX) },
    "h5" to { tag, context -> textHelper(tag.children.joinText(), header = Header.H5, alignmentX = context.defaultAlignX) },
    "h6" to { tag, context -> textHelper(tag.children.joinText(), header = Header.H6, alignmentX = context.defaultAlignX) },
)

fun UiScope.textHelper(
    text: String,
    header: Header = Header.H4,
    alignmentX: AlignmentX = AlignmentX.Center,
    alignmentY: AlignmentY = AlignmentY.Top,
    bold: Boolean = false,
    italic: Boolean = false,
    margin: Boolean = true,
): UiScope = Text(text) {
    val font = MsdfFont(
        HACK_FONT,
        sizePts = header.fontSize,
        weight = if (bold) WEIGHT_LIGHT else 0f,
        italic = if (italic) ITALIC_STD else 0f
    )

    modifier.font(font)
        .align(alignmentX, alignmentY)
        .textAlignX(alignmentX)
        .margin(if (margin) sizes.gap else 0.dp, sizes.smallGap)
        .width(Grow(1f))
        .isWrapText(true)
}

enum class Header(val fontSize: Float) { H1(20f), H2(18f), H3(14f), H4(12f), H5(10f), H6(8f) }