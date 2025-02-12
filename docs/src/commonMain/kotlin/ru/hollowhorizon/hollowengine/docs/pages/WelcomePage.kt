package ru.hollowhorizon.hollowengine.docs.pages

import de.fabmax.kool.modules.ui2.Column
import de.fabmax.kool.modules.ui2.Composable
import de.fabmax.kool.modules.ui2.UiScope
import ru.hollowhorizon.hollowengine.docs.*

object WelcomePage : Composable {
  override fun UiScope.compose() {
    br()
    text("Добро пожаловать", THType.H1, true)
    text("на официальную документацию по моду", THType.H2, true)
    text("\"HollowEngine\"", THType.H3, true)
    br()
    title("welcome")

    br()
    divite()
    br()

    Column {
      text("Данная документация должна обучить"); text(" вас", bold=true); text("основам скриптинга в данном моде.")
    }
  }
}