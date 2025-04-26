package ru.hollowhorizon.hollowengine.docs.utils

import de.fabmax.kool.util.Color
import de.fabmax.kool.util.MsdfFont
import de.fabmax.kool.util.MsdfFont.Companion.CUTOFF_SOLID
import de.fabmax.kool.util.MsdfFont.Companion.ITALIC_NONE
import de.fabmax.kool.util.MsdfFont.Companion.WEIGHT_REGULAR
import de.fabmax.kool.util.MsdfFontData
import ru.hollowhorizon.hollowengine.docs.HACK_FONT

object TextParser {
    data class Component(
        val text: String = "",
        val style: Style = Style.DEFAULt
    )
    class Style(
        // Formating //
        private val bold: Float,
        private val italic: Float,
        private val cutoff: Float,
        private val size: Float,
        // Font //
        private val font: MsdfFontData,
        private val color: Color,
        private val glowColor: Color?
    ) {
        companion object {
            val DEFAULt = Style(WEIGHT_REGULAR, ITALIC_NONE, CUTOFF_SOLID, 12f, HACK_FONT, Color.WHITE, null)
        }
        val msdf: MsdfFont get() = MsdfFont(font, size, italic, bold, cutoff, glowColor)

        fun copy(
            bold: Float = this.bold,
            italic: Float = this.italic,
            cutoff: Float = this.cutoff,
            size: Float = this.size,
            font: MsdfFontData = this.font,
            color: Color = this.color,
            glowColor: Color? = this.glowColor
        ): Style = Style(bold, italic, cutoff, size, font, color, glowColor)
    }

    data class Header(
        val h1: Float = 20f,
        val h2: Float = 18f,
        val h3: Float = 15f,
        val h4: Float = 12f,
        val h5: Float = 10f,
        val h6: Float = 8f
    )
}