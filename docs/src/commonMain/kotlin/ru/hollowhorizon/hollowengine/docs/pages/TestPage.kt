package ru.hollowhorizon.hollowengine.docs.pages

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

    divide()

    text("Тестовое картинка")
    title("test_title")

    divide()

    text("Таблицы")
    br()
    HorizontalScrollbar("tables_scrolling") {
      ScrollArea { modifier.width(FitContent)
        Row {
          modifier.align(AlignmentX.Center)

          listOf(TableType.NOTE, TableType.TIP, TableType.INFO, TableType.WARN, TableType.ERR).forEach {
            table("Example HEAD", it) {
              text("Example BODY")
            }
          }
        }
      }
    }

    br()
  }
}