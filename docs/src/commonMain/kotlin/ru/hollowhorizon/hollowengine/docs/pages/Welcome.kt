package ru.hollowhorizon.hollowengine.docs.pages

import de.fabmax.kool.modules.ui2.*
import de.fabmax.kool.util.Color
import ru.hollowhorizon.hollowengine.docs.*

object Welcome : Composable {
    override fun UiScope.compose() {
        text("Добро пожаловать", Header.H1)
        text("на официальную документацию по моду", Header.H2)
        text("\"HollowEngine\"", Header.H3, bold = true)
        br()
        title("welcome")

        br()
        divide()
        br()

        text("Данная документация должна обучить вас основам скриптинга в данном моде.")

        br()
        divide()
        br()

        table("Язык программирования Kotlin", TableType.TIP) {
            text("Перед прочтением документации, рекомендуется ознакомится с таким языком программирования как \"Kotlin\"!")
            text("Без знания хотя бы \"Базового уровня\" - вам будет сложно понимать систему работы скриптов.")

            // ==== //

            Box { modifier.align(AlignmentX.Center, AlignmentY.Bottom)
                button("Документация по Kotlin", ButtonType.LINK) { openUrl("https://kotlinlang.org/docs/basic-syntax.html") }
            }
        }

        table("Внимание", TableType.WARN) {
            text("Это бета-версия документации, так что всё в будущем может изменится.")
            divide(Color(TableType.WARN.border))
            text("При обнаружении ошибок сообщите или на GitHub Issues или на Discord сервере Phase Of Horizon.")
            text("Успехов в разработке!")

            // ==== //

            Box { modifier.align(AlignmentX.Center, AlignmentY.Bottom)
                Row {
                    button("GitHub Issues", ButtonType.LINK) { openUrl("https://github.com/HollowHorizon/HollowEngine/issues") }
                    button("Discord", ButtonType.LINK) { openUrl("https://discord.gg/qYzFXXpzZk") }
                }
            }
        }
    }
}