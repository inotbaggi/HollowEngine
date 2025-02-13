package ru.hollowhorizon.hollowengine.docs.pages

import de.fabmax.kool.modules.ui2.*
import de.fabmax.kool.modules.ui2.Grow.Companion.Std
import ru.hollowhorizon.hollowengine.docs.*

object TestPage : Composable {
    override fun UiScope.compose() {
        var tableTypes = TableType.NOTE

        text("test text"); text("тест текст")
        br()
        text("TEST BIG TEXT", Header.H1); text("ТЕСТ БОЛЬШОЙ ТЕКСТ", Header.H1)
        br()
        text("Test bold & italic text", bold = true, italic = true); text(
            "Тест жирный и курсив текст",
            bold = true,
            italic = true
        )

        divide()

        text("Тестовое картинка")
        title("test_title")
    }
}