package ru.hollowhorizon.hollowengine.docs.pages.begin

import de.fabmax.kool.modules.ui2.*
import de.fabmax.kool.util.Color
import ru.hollowhorizon.hollowengine.docs.*

object DirectoryPage: Composable {
    enum class DirType(val type: String, val color: String, val icon: String) {
        NONE("None", "444444", "none"),
        ASSETS("Ассеты [assets]", "c4a92f", "assets"),
        DATA("Дата [data]", "2abf8e", "data"),
        CAMERA("Камера [camera]", "2e3bab", "camera"),
        REPLAYS("Реплеи [replays]", "bf7c2a", "replays"),
        SCRIPTS("Скрипты [scripts]", "812eab", "scripts"),
        STORYTELLER_WORLD("Измерение рассказчика [storyteller_dimension]", "819096", "storyteller_world")
    }
    private val dirs = listOf(DirType.NONE, DirType.ASSETS, DirType.DATA, DirType.CAMERA, DirType.REPLAYS, DirType.SCRIPTS, DirType.STORYTELLER_WORLD)
    private var dirSelect = DirType.NONE
    private var hidden = mutableStateOf(false)

    override fun UiScope.compose() {
        text("Директория мода", Header.H1)
        Row {
            modifier.width(Grow.Std).align(AlignmentX.Center, AlignmentY.Center)

            Image(loadImage("titles/dirs_icon.png")) {
                modifier.align(AlignmentX.Center, AlignmentY.Center)
            }
            text("hollowengine", Header.H1, alignmentX = AlignmentX.Center, alignmentY = AlignmentY.Center) {
                modifier.isWrapText(false).width(FitContent)
            }
        }

        br()
        divide()
        br()

        text("При первом запуске игры с данным модом, в директории игры появится довая папка под названием [hollowengine]. Это ключевая папка, с которой вы будете работать.")
        text("Всё что находится внутри этой папки - работает по принципу ресурс паков (т.е. поддерживаются папки [assets], [data] и т.д.).")

        br()
        divide(Color(dirSelect.color))
        br()

        var selectIndex by remember { mutableStateOf(0) }

        table("Описание возможных директорий", TableType.TIP) {
            ComboBox {
                modifier
                    .align(AlignmentX.Center, AlignmentY.Top)
                    .margin(sizes.gap)
                    .items(dirs.map { it.type })
                    .selectedIndex(selectIndex)
                    .onItemSelected {
                        selectIndex = it
                        dirSelect = dirs[it]
                    }
            }
            Row { modifier.align(AlignmentX.Center, AlignmentY.Center).size(Grow.Std, Grow.Std)
                Image(loadImage("icons/dirs/${dirSelect.icon}.png")) { modifier.width(FitContent) }
                Box {
                    modifier
                        .size(sizes.borderWidth*.5f, Grow.Std)
                        .margin(horizontal = sizes.smallGap * 0.5f, vertical = sizes.smallGap * 0.25f)
                        .backgroundColor(Color(dirSelect.color))
                }
                Column {
                    text(dirSelect.type)
                    divbr(Color(dirSelect.color))
                    when(dirSelect) {
                        DirType.ASSETS -> { text("Место для хранения ресурсов (Модели, текстуры, звуки и т.д.).") }
                        DirType.DATA -> { text("Место для хранения данных (таблица лута, структуры и т.д.).") }
                        DirType.CAMERA -> { text("Место, где будут хранится все пути движения камеры.") }
                        DirType.REPLAYS -> { text("Место, где будут хранится все записанные действия игрока.") }
                        DirType.SCRIPTS -> { text("Место хранения скриптов. Здесь будут хранится все скрипты и отсюда вы будете их запускать.") }
                        DirType.STORYTELLER_WORLD -> { text("Место, где хранятся данные для эксклюзивного, специально измерения.") }
                        else -> { text("Выбери директорию из списка выше.") }
                    }
                }
            }

            divide(Color(dirSelect.color))

            table("Папки отсутствуют", TableType.WARN) {
                text("Не нада тут ныть и со слезами бежать на дискорд сервер в канал #помощь и там рыдать что у вас нет этих папок.")
                text("Лишь только некоторые папки - создаются либо сразу, либо после определённых действий. Остальные - вам нужно создавать самому.")
                br()
                spoiler("Спойлер", hidden) {
                    text("(Будете ныть? Тогда к вам явится BLACK_HollowHorizon и накажет вас дома по полной) :)")
                }
            }
        }

        br()
    }
}