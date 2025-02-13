package ru.hollowhorizon.hollowengine.docs.pages

import de.fabmax.kool.input.Input
import de.fabmax.kool.modules.ui2.*
import ru.hollowhorizon.hollowengine.docs.*

object TestPage: Composable {
  override fun UiScope.compose() {
    var tableTypes = TableType.NOTE

    text("test text"); text("тест текст")
    br()
    text("TEST BIG TEXT", THType.H1); text("ТЕСТ БОЛЬШОЙ ТЕКСТ", THType.H1)
    br()
    text("Test bold & italic text", bold=true, italic=true); text("Тест жирный и курсив текст", bold=true, italic=true)

    divite()

    text("Тестовое картинка")
    title("test_title")

    divite()

    text("Таблицы")
    br()
    listOf(TableType.NOTE, TableType.TIP).forEach {
      table("Example HEAD", it) {
        text("Example BODY")
      }
    }
  }
}