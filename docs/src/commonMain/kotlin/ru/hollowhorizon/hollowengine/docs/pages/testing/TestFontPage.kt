package ru.hollowhorizon.hollowengine.docs.pages.testing

import de.fabmax.kool.modules.ui2.Composable
import de.fabmax.kool.modules.ui2.UiScope
import ru.hollowhorizon.hollowengine.docs.Header
import ru.hollowhorizon.hollowengine.docs.br
import ru.hollowhorizon.hollowengine.docs.divide
import ru.hollowhorizon.hollowengine.docs.text

object TestFontPage: Composable {
    override fun UiScope.compose() {
        br()

        text("Обычный текст")
        divide()
        text("БОЛЬШОЙ ТЕКСТ #1", Header.H1)
        text("Большой текст #2", Header.H2)
        text("Большой текст #3", Header.H3)
        text("Большой текст #4", Header.H4)
        text("Большой текст #5", Header.H5)
        text("Большой текст #6", Header.H6)
        divide()
        text("Полужирный текст", bold = true)
        text("Кривой текст", italic = true)
        text("Полужирный и кривой текст", bold = true, italic = true)

        br()
    }
}