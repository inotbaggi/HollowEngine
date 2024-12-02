package ru.hollowhorizon.hollowengine.client.docs.pages.begin

import imgui.ImGui
import ru.hollowhorizon.hc.client.imgui.Graphics
import ru.hollowhorizon.hollowengine.client.docs.DocsPage
import ru.hollowhorizon.hollowengine.client.docs.DocsRenderer
import ru.hollowhorizon.hollowengine.client.docs.DocsUtils
import ru.hollowhorizon.hollowengine.client.docs.DocsUtils.button
import ru.hollowhorizon.hollowengine.client.docs.DocsUtils.table
import ru.hollowhorizon.hollowengine.client.docs.DocsUtils.tablice
import ru.hollowhorizon.hollowengine.client.docs.DocsUtils.text

const val begin_commands = "$begin.commands"

@DocsPage(begin_commands)
fun DocsRenderer.commands() {
  text("С данным модом, в списке команд появилась новая категория \"hollowengine\".")
  ImGui.newLine()

  ImGui.setCursorPosX(ImGui.getWindowSizeX() / 2 - 128f / 2)
  button(
    "Ввести команду \"/hollowengine\"",
    "Просто вводит команду в чат, но не запускает её.",
    buttonType = DocsUtils.ButtonType.ENTER_COMMAND
  ) { DocsUtils.enterCommand("/hollowengine") }

  text("Обо всех командах можно узнать ниже.")

  ImGui.newLine()
  ImGui.separator()
  ImGui.newLine()

  table("Описание к командам", DocsUtils.TableType.TIP, tableSizeY = 512f + 256f) {
    Graphics.withFontSize(24) {
      tablice(
        "command-list",
        arrayOf(
          arrayOf("Команда", "Аргументы", "Описание"),
          arrayOf(
            "/hollowengine pos",
            "Отсутствуют",
            "Получает точку позиции, куда вы смотрите и копирует координаты этой точки к вам в буфер обмена.",
          ),
          arrayOf(
            "/hollowengine hand",
            "Отсутствуют",
            "Копирует данные предмета (такие как `item_id`, `cound` и `nbt_tags`) и копирует их в буфер обмена.",
          ),
          arrayOf(
            "/hollowengine active-events",
            "Отсутствуют",
            "Выводит в чат список активных скриптов."
          ),
          arrayOf(
            "/hollowengine model",
            "path: ResourceLocation",
            "Выводит в чат информацию о модели, а именно: Список анимаций и Список текстур."
          ),
          arrayOf(
            "/hollowengine start-script",
            "path: ScriptPath",
            "Запускает скрипт по указанному в аргументе пути."
          ),
          arrayOf(
            "/hollowengine open-gui",
            "path: ScriptPath",
            "Открывает скрипт для интерфейса по указанному в аргументе пути."
          ),
          arrayOf(
            "unknown",
            "unknown",
            "unknown"
          )
        ),
        arrayOf(ImGui.getWindowSizeX(), 512f)
      )
    }
  }
}