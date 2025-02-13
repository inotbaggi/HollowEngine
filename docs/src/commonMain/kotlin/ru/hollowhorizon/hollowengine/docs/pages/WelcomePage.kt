package ru.hollowhorizon.hollowengine.docs.pages

import de.fabmax.kool.modules.ui2.*
import ru.hollowhorizon.hollowengine.docs.*

object WelcomePage : Composable {
  override fun UiScope.compose() {
    br()
    text("Добро пожаловать", THType.H1, true)
    text("на официальную документацию по моду", THType.H2, true)
    text("\"HollowEngine\"", THType.H3, true)
    br()
    title("test_title")

    br()
    divite()
    br()

    Row {
      modifier.align(AlignmentX.Center)
        .margin(8.dp)
      text("Данная документация должна обучить", margin=false); text("вас", bold=true, margin=false); text("основам скриптинга в данном моде.", margin=false)
    }
  }
}