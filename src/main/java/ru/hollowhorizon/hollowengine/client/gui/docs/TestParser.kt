package ru.hollowhorizon.hollowengine.client.gui.docs

import de.fabmax.kool.modules.ui2.*
import de.fabmax.kool.util.Color
import de.fabmax.kool.util.MsdfFont
import net.minecraft.client.Minecraft
import net.minecraft.locale.Language
import net.minecraft.network.chat.*
import net.minecraft.network.chat.contents.LiteralContents
import net.minecraft.network.chat.contents.TranslatableContents
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.EntityType
import net.minecraft.world.item.ItemStack
import ru.hollowhorizon.hc.common.utils.literal
import ru.hollowhorizon.hollowengine.docs.HACK_FONT
import java.awt.Color as JavaColor
import de.fabmax.kool.util.Color as KoolColor
import java.util.*
import kotlin.math.PI
import kotlin.math.sin

object TextParser {
    class Format(
        private val color: Int = 0xFFFFFF,
        private val bold: Boolean = false,
        private val italic: Boolean = false,
        private val underline: Boolean = false,
        private val strikethrough: Boolean = false,
        private val obfuscated: Boolean = false,
        private val hoverEvent: HoverEvent? = null,
        private val clickEvent: ClickEvent? = null,
        private val insertion: String? = null,
        private val font: ResourceLocation = Style.DEFAULT_FONT
    ) {
        fun copy(
            color: Int = this.color,
            bold: Boolean = this.bold,
            italic: Boolean = this.italic,
            underline: Boolean = this.underline,
            strikethrough: Boolean = this.strikethrough,
            obfuscated: Boolean = this.obfuscated,
            hoverEvent: HoverEvent? = this.hoverEvent,
            clickEvent: ClickEvent? = this.clickEvent,
            insertion: String? = this.insertion,
            font: ResourceLocation = this.font
        ): Format =
            Format(color, bold, italic, underline, strikethrough, obfuscated, hoverEvent, clickEvent, insertion, font)

        val withBold get(): Format = copy(bold = true)
        val withItalic get(): Format = copy(italic = true)
        val withUnderline get(): Format = copy(underline = true)
        val withStrikethrough get(): Format = copy(strikethrough = true)
        val withObfuscated get(): Format = copy(obfuscated = true)
        fun hoverEvent(event: () -> Unit): Format = copy(hoverEvent = null)
        fun clickEvent(event: () -> Unit): Format = copy(clickEvent = null)

        val toStyle: Style
            get() = Style.EMPTY
                .withColor(color)
                .withBold(bold)
                .withItalic(italic)
                .withUnderlined(underline)
                .withStrikethrough(strikethrough)
                .withObfuscated(obfuscated)
                .withHoverEvent(hoverEvent)
                .withClickEvent(clickEvent)
                .withInsertion(insertion)
                .withFont(font)
    }

    fun openUrl(url: String): ClickEvent = ClickEvent(ClickEvent.Action.OPEN_URL, url)
    fun openFile(path: String): ClickEvent =
        ClickEvent(ClickEvent.Action.OPEN_FILE, "${Minecraft.getInstance().gameDirectory.path}/$path")

    fun tooltip(info: Component): HoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, info)
    fun item(itemStack: ItemStack): HoverEvent =
        HoverEvent(HoverEvent.Action.SHOW_ITEM, HoverEvent.ItemStackInfo(itemStack))

    fun entity(entity: EntityType<*>, uuid: UUID, name: Component = "".literal): HoverEvent =
        HoverEvent(HoverEvent.Action.SHOW_ENTITY, HoverEvent.EntityTooltipInfo(entity, uuid, name))

    operator fun MutableComponent.plus(add: MutableComponent): MutableComponent = this.append(add)

    fun parseText(text: String): MutableComponent {
        val regex = Regex("<(/?)([a-zA-Z0-9#:-]+)>|([^<>]+)")
        val matcher = regex.findAll(text)

        var currentFormat = Format()
        val formatStack = mutableListOf<Format>()

        val gradientStack = mutableListOf<GradientInfo>()
        val rainbowStack = mutableListOf<RainbowInfo>()

        val result = Component.empty()

        for (match in matcher) {
            if (match.groupValues[2].isNotEmpty()) {
                val isTagClose = match.groupValues[1] == "/"
                val tagName = match.groupValues[2]

                if (tagName.startsWith("gradient:") && !isTagClose) {
                    val parts = tagName.removePrefix("gradient:").split(":")
                    val start = parts.getOrNull(0)?.let { JavaColor.decode(it).rgb } ?: 0xFFFFFF
                    val end = parts.getOrNull(1)?.let { JavaColor.decode(it).rgb } ?: 0xFFFFFF
                    gradientStack.add(GradientInfo(start, end, StringBuilder()))
                } else if (tagName == "gradient" && isTagClose) {
                    val gradient = gradientStack.removeLast()
                    val gradientText = gradient.text.toString()
                    val length = gradientText.length

                    for ((i, c) in gradientText.withIndex()) {
                        val color = interpolateColor(gradient.startColor, gradient.endColor, i.toFloat() / (length - 1))
                        val comp = "$c".literal.withStyle { it.withColor(color) }
                        result.append(comp)
                    }
                } else if (tagName.equals("rainbow", ignoreCase = true) && !isTagClose) {
                    rainbowStack.add(RainbowInfo(StringBuilder()))
                } else if (tagName.equals("rainbow", ignoreCase = true) && isTagClose) {
                    val rainbow = rainbowStack.removeLast()
                    val rainbowText = rainbow.text.toString()
                    val length = rainbowText.length

                    for ((i, c) in rainbowText.withIndex()) {
                        val color = rainbowColor(i.toFloat() / 3f)
                        val comp = "$c".literal.withStyle { it.withColor(color) }
                        result.append(comp)
                    }
                } else if (gradientStack.isEmpty() && rainbowStack.isEmpty()) {
                    when {
                        !isTagClose -> { // Открывающий тег
                            formatStack.add(currentFormat)
                            currentFormat = when {
                                tagName.equals("bold", true) -> currentFormat.copy(bold = true)
                                tagName.equals("italic", true) -> currentFormat.copy(italic = true)
                                tagName.equals("underline", true) -> currentFormat.copy(underline = true)
                                tagName.equals("strikethrough", true) -> currentFormat.copy(strikethrough = true)
                                tagName.equals("obfuscated", true) -> currentFormat.copy(obfuscated = true)
                                tagName.startsWith("color:", true) -> {
                                    val colorHex = tagName.substringAfter("color:").trim()
                                    currentFormat.copy(color = JavaColor.decode(colorHex).rgb)
                                }
                                else -> currentFormat
                            }
                        }
                        else -> { // Закрывающий тег
                            if (formatStack.isNotEmpty()) {
                                currentFormat = formatStack.removeLast()
                            }
                        }
                    }
                }
            } else if (match.groupValues[3].isNotEmpty()) {
                val normalText = match.groupValues[3]
                when {
                    gradientStack.isNotEmpty() -> gradientStack.last().text.append(normalText)
                    rainbowStack.isNotEmpty() -> rainbowStack.last().text.append(normalText)
                    else -> result.append(normalText.literal.withStyle(currentFormat.toStyle))
                }
            }
        }

        return result
    }

    data class GradientInfo(val startColor: Int, val endColor: Int, val text: StringBuilder)
    private fun interpolateColor(startColor: Int, endColor: Int, fraction: Float): Int {
        val sr = (startColor shr 16) and 0xFF
        val sg = (startColor shr 8) and 0xFF
        val sb = startColor and 0xFF

        val er = (endColor shr 16) and 0xFF
        val eg = (endColor shr 8) and 0xFF
        val eb = endColor and 0xFF

        val r = (sr + ((er - sr) * fraction)).toInt()
        val g = (sg + ((eg - sg) * fraction)).toInt()
        val b = (sb + ((eb - sb) * fraction)).toInt()

        return (r shl 16) or (g shl 8) or b
    }

    data class RainbowInfo(val text: StringBuilder)
    fun rainbowColor(position: Float): Int {
        val r = (sin(position + 0f) * 127 + 128).toInt()
        val g = (sin(position + 2f * PI.toFloat() / 3f) * 127 + 128).toInt()
        val b = (sin(position + 4f * PI.toFloat() / 3f) * 127 + 128).toInt()
        return (r shl 16) or (g shl 8) or b
    }

    private fun Component.coloredText(parentColor: Int = 0xFFFFFF): MutableList<Pair<String, TextAttributes>> {
        val list = mutableListOf(attributes(parentColor))
        list += siblings.flatMap { it.coloredText(style.color?.value ?: parentColor) }.toMutableList()
        return list
    }

    private fun Component.attributes(parentColor: Int): Pair<String, TextAttributes> {
        val color = style.color?.value ?: parentColor
        val isUnderlined = style.isUnderlined
        val isStrikethrough = style.isStrikethrough
        val isObfuscated = style.isObfuscated

        val red = (color shr 16 and 0xFF).toFloat() / 255.0f
        val green = (color shr 8 and 0xFF).toFloat() / 255.0f
        val blue = (color and 0xFF).toFloat() / 255.0f

        var text = when (val content = contents) {
            is LiteralContents -> content.text
            is TranslatableContents -> {
                String.format(
                    Language.getInstance().getOrDefault(content.key),
                    *content.args.map { if (it is Component) it.string else it }.toTypedArray()
                )
            }

            ComponentContents.EMPTY -> ""
            else -> error("Unknown text component: $content")
        }

        if (isObfuscated) text = obfuscatedString(text.length)

        return text to TextAttributes(MsdfFont(HACK_FONT, 30f), KoolColor(red, green, blue))
    }

    private fun obfuscatedString(length: Int): String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9') + ('А'..'Я') + ('а'..'я')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }

    // Use this //
    fun UiScope.text(
        vararg components: Component,
        block: TextAreaScope.() -> Unit,
    ) {
        TextArea(
            ListTextLineProvider(components.map { TextLine(it.coloredText()) }.toMutableList()),
            hScrollbarModifier = { it.margin(start = sizes.gap, end = sizes.gap * 2f, bottom = sizes.gap) },
            vScrollbarModifier = { it.margin(sizes.gap) },
            scopeName = "Component",
            block = block
        )
    }
}