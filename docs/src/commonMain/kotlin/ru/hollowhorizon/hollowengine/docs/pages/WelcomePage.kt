package ru.hollowhorizon.hollowengine.docs.pages

import de.fabmax.kool.modules.ui2.*
import de.fabmax.kool.util.Color
import ru.hollowhorizon.hollowengine.docs.*
import kotlin.random.Random

/*
 * Спасибо тем, кто голосовал за доки в опросе, я рад за вас.
 * Остальным - соболезную. Хотя как, если вы и так знаете как работать с HollowEngine, потому что умеете читать исходники - то я рад за вас,
 * но вот если вы вообще не понимаете и проголосовали за всё остальное с надеждой, что вам разжуют — вам соболезную. Вы опустились для меня.
*/

object WelcomePage : Composable {
    private var r = Random.nextInt(0, 101)
    private val titleImg: () -> Pair<String, Float> = {
        if(r in 96..98)
            "welcome" to 0.1f
        else if(r in 98..100)
            "welcome2_s" to 0.05f
        else
            "welcome2" to 0.05f
    }

    private val sHide0 = mutableStateOf(false)

    override fun UiScope.compose() {
        wow()

        text("Добро пожаловать", Header.H1)
        text("на официальную документацию по моду", Header.H2)
        text("\"HollowEngine\"", Header.H3, bold = true)
        br()
        title(titleImg().first, titleImg().second)
        divbr()
        text("Данная документация должна обучить вас основам скриптинга в данном моде.")
        br()
        spoiler("Спойлер", sHide0) {
            text("Не нада со слезами бежать в канал #помощь и там просить-молить чтоб вам помогли. >:(")
            text("Изучите документацию обязательно !!!ПОЛНОСТЬЮ!!! Потому что скорее всего ваша проблема не то что решена, а абсолютно абсурдна!")
            text("Это значит что вы задаёте вопрос, когда ответ есть прямо в документации, но вы почему-то не хотите её читать полностью :(")
            text("Я не знаю как вы там доки блин читаете. Не уж то реально, до 1-го предложения и потом сидите и - \"Пипец я умный\"!", Header.H6, italic = true)
        }
        divbr()
        table("Язык программирования Kotlin", TableType.TIP) {
            text("Перед прочтением документации, рекомендуется ознакомится с таким языком программирования как \"Kotlin\"!")
            text("Без знания хотя бы \"Базового уровня\" - вам будет сложно понимать систему работы скриптов.")
            // ==== //
            Box { modifier.align(AlignmentX.Center, AlignmentY.Bottom)
                button("Документация по Kotlin", type = ButtonType.LINK) { openUrl("https://kotlinlang.org/docs/basic-syntax.html") }
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
                    button("GitHub Issues", type = ButtonType.LINK) { openUrl("https://github.com/HollowHorizon/HollowEngine/issues") }
                    button("Discord", type = ButtonType.LINK) { openUrl("https://discord.gg/qYzFXXpzZk") }
                }
            }
        }
    }

    private fun UiScope.wow() {
        var iRememberThat = remember { false }

        if(!iRememberThat) return

        when {
            (r in 96..98) -> println("Ух ты. Старый титульник! Повезло тебе увидеть его")
            (r in 98..100) -> println("Офигеть! Ты увидел самый редкий встречный титульник! Холоу + Lays: Pressure air = :love: (XD)")
            else -> println("Ну.. самый обычный титульник. Может быть тебе повезёт в следующий раз?")
        }

        iRememberThat = true
    }
}