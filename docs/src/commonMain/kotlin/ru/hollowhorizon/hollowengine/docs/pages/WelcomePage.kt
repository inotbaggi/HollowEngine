package ru.hollowhorizon.hollowengine.docs.pages

import de.fabmax.kool.modules.ui2.*
import ru.hollowhorizon.hollowengine.docs.*

object WelcomePage : Composable {
    override fun UiScope.compose() {
        br()
        text("Добро пожаловать", Header.H1)
        text("на официальную документацию по моду", Header.H2)
        text("\"HollowEngine\"", Header.H3, bold = true)
        br()
        title("test_title")

        br()
        divide()
        br()

        text("Данная документация должна обучить вас основам скриптинга в данном моде.")

        br()
        divide()
        br()

        table("Внимание", TableType.WARN) {
            text("Это бета-версия документации, так что всё в будущем может изменится.")
            divide()
            text("При обнаружении ошибок сообщите или на GitHub Issues или на Discord сервере Phase Of Horizon.")
            text("Успехов в разработке!")

            text("Для не шарящих, есть спец-кнопки:")
            button("Ginhub Issues") { openUrl("https://www.google.com/search?q=HollowHorizon+boosty") }
        }

        br()
    }
}