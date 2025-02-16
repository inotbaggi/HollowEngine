package ru.hollowhorizon.hollowengine.docs.pages.testing

import de.fabmax.kool.modules.ui2.Composable
import de.fabmax.kool.modules.ui2.UiScope
import ru.hollowhorizon.hollowengine.docs.*

object TestFont : Composable {
    override fun UiScope.compose() {
        br()

        text("test text"); text("тест текст")
        br()
        text("TEST BIG TEXT", Header.H1); text("ТЕСТ БОЛЬШОЙ ТЕКСТ", Header.H1)
        br()
        text("Test bold & italic text", bold = true, italic = true); text(
            "Тест жирный и курсив текст",
            bold = true,
            italic = true
        )

        br()
    }
}