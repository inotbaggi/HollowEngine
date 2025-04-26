package ru.hollowhorizon.hollowengine.docs.pages

import de.fabmax.kool.modules.ui2.*
import de.fabmax.kool.util.Color
import ru.hollowhorizon.hollowengine.docs.*
import ru.hollowhorizon.hollowengine.docs.utils.TextParser.text
import ru.hollowhorizon.hollowengine.docs.utils.TextParser.textRich
import kotlin.random.Random

object WelcomePage : Composable {
    var r = Random.nextInt(0, 101)
    val titleImg: () -> Pair<String, Float> = {
        if(r in 96..98)
            "welcome" to 0.1f
        else if(r in 98..100)
            "welcome2_s" to 0.05f
        else
            "welcome2" to 0.05f
    }

    val sHide0 = mutableStateOf(false)

    override fun UiScope.compose() {
        text(
            """
            Example text
            <h1>Example Header H1</h1>
            <h2>Header 2</h2>
            <bold>Is bold text</bold>
            <italic>Is Italyano text</italic>
            Это пипец какой красивый текст. <bold>Он будет жирным</bold>, <italic>И он будет Итальянским</italic>. И да, он в ОДНУ ПОЛОСКУ!!!
            <color=#FF00000>Этот цвет красный</color>
            {DFdkmfmsdfsedfokesdjfjksoedfnojkesfnojkdsf <italic>DHFDSFCJOFNCONEPSADJFCPEDSAJFPCKENPDSFNPKdnfsfesrdfg rwsfgrsfgdsgerdsfgsrfgrewsf</italic>
            """.trimIndent()
        )
        text(
            """
            Текст который написал сеньёр данюат (это пиздёшь кнч же) Но нужно проверить - <h2>Как работает TextWrap</h2>!
            """.trimIndent()
        )

        textRich(
            """
            Тестовый текст рича парсер от ЧатГПТ. <bold>жирный</bold>, <italic>Итальянский ма-ма-мия</italic>.
            Этот будет <italic>одновременно и <bold>Итальняским и жирный</bold></italic>.
            """.trimIndent()
        )

    }
    /*
    override fun UiScope.compose() {
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
            text("Изучите документацию обязательно"); text("ПОЛНОСТЬЮ", Header.H3, bold = true); text("Потому что скорее всего ваша проблема не то что решена, а абсолютно абсурдна!")
            text("Это значит что вы задаёте вопрос, когда ответ есть прямо в документации, но вы почему-то не хотите её читать полностью :(")
            text("Я не знаю как вы там доки блин читаете. Не уж то реально, до 1-го предложения и потом сидите и - \"Пипец я умный\"!", Header.H6, italic = true)
        }
        divbr()
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
     */
}