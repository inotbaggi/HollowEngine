package ru.hollowhorizon.hollowengine.client.docs.pages.begin

import imgui.ImColor
import imgui.ImGui
import imgui.flag.ImGuiTableBgTarget
import imgui.flag.ImGuiTableColumnFlags
import imgui.flag.ImGuiTableFlags
import ru.hollowhorizon.hc.client.imgui.Graphics
import ru.hollowhorizon.hollowengine.client.docs.DocsPage
import ru.hollowhorizon.hollowengine.client.docs.DocsRenderer
import ru.hollowhorizon.hollowengine.client.docs.DocsUtils
import ru.hollowhorizon.hollowengine.client.docs.DocsUtils.button
import ru.hollowhorizon.hollowengine.client.docs.DocsUtils.table
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
      if (ImGui.beginTable("Описание к командам", 5, ImGuiTableFlags.Borders)) {
        // HEAD //
        ImGui.tableSetupColumn("Команда")
        ImGui.tableSetupColumn("Аргументы", ImGuiTableColumnFlags.WidthFixed, 256f + 64f)
        ImGui.tableSetupColumn("Описание", ImGuiTableColumnFlags.WidthFixed, 512f)
        ImGui.tableSetupColumn("Опробовать")
        ImGui.tableHeadersRow()

        // POS //
        ImGui.tableNextRow()
        ImGui.tableSetColumnIndex(0)
        ImGui.tableSetBgColor(ImGuiTableBgTarget.CellBg, ImColor.rgba(64, 64, 64, 128))
        ImGui.text("/hollowengine pos")
        ImGui.tableSetColumnIndex(1)
        ImGui.tableSetBgColor(ImGuiTableBgTarget.CellBg, ImColor.rgba(64, 64, 64, 128))
        ImGui.text("Отсутствуют")
        ImGui.tableSetColumnIndex(2)
        ImGui.tableSetBgColor(ImGuiTableBgTarget.CellBg, ImColor.rgba(64, 64, 64, 128))
        ImGui.textWrapped("Получает точку позиции, куда вы смотрите и копирует эту позицию.")
        ImGui.tableSetColumnIndex(3)
        ImGui.tableSetBgColor(ImGuiTableBgTarget.CellBg, ImColor.rgba(64, 64, 64, 128))
        button("Вставить", width = 128f) { DocsUtils.enterCommand("/hollowengine pos") }

        // HAND //
        ImGui.tableNextRow()
        ImGui.tableSetColumnIndex(0)
        ImGui.tableSetBgColor(ImGuiTableBgTarget.CellBg, ImColor.rgba(64, 64, 64, 128))
        ImGui.text("/hollowengine hand")
        ImGui.tableSetColumnIndex(1)
        ImGui.tableSetBgColor(ImGuiTableBgTarget.CellBg, ImColor.rgba(64, 64, 64, 128))
        ImGui.text("Отсутствуют")
        ImGui.tableSetColumnIndex(2)
        ImGui.tableSetBgColor(ImGuiTableBgTarget.CellBg, ImColor.rgba(64, 64, 64, 128))
        ImGui.textWrapped("Копирует данные предмета (item_id, count и nbt_tags) который находится в главной руке.")
        ImGui.tableSetColumnIndex(3)
        ImGui.tableSetBgColor(ImGuiTableBgTarget.CellBg, ImColor.rgba(64, 64, 64, 128))
        button("Вставить", width = 128f) { DocsUtils.enterCommand("/hollowengine hand") }

        // MODEL //
        ImGui.tableNextRow()
        ImGui.tableSetColumnIndex(0)
        ImGui.tableSetBgColor(ImGuiTableBgTarget.CellBg, ImColor.rgba(64, 64, 64, 128))
        ImGui.text("/hollowengine model")
        ImGui.tableSetColumnIndex(1)
        ImGui.tableSetBgColor(ImGuiTableBgTarget.CellBg, ImColor.rgba(64, 64, 64, 128))
        ImGui.text("path: ResourceLocation")
        ImGui.tableSetColumnIndex(2)
        ImGui.tableSetBgColor(ImGuiTableBgTarget.CellBg, ImColor.rgba(64, 64, 64, 128))
        ImGui.textWrapped("Выводит информацию о модели (список анимаций и текстур) в чат. В качестве аргумента команды нужно указать путь до модели.")
        ImGui.tableSetColumnIndex(3)
        ImGui.tableSetBgColor(ImGuiTableBgTarget.CellBg, ImColor.rgba(64, 64, 64, 128))
        button("Вставить", width = 128f) { DocsUtils.enterCommand("/hollowengine model ") }

        // START-SCRIPT //
        ImGui.tableNextRow()
        ImGui.tableSetColumnIndex(0)
        ImGui.tableSetBgColor(ImGuiTableBgTarget.CellBg, ImColor.rgba(64, 64, 64, 128))
        ImGui.text("/hollowengine start-script")
        ImGui.tableSetColumnIndex(1)
        ImGui.tableSetBgColor(ImGuiTableBgTarget.CellBg, ImColor.rgba(64, 64, 64, 128))
        ImGui.text("path: ScriptPath")
        ImGui.tableSetColumnIndex(2)
        ImGui.tableSetBgColor(ImGuiTableBgTarget.CellBg, ImColor.rgba(64, 64, 64, 128))
        ImGui.textWrapped("Запускает скрипт по указанному пути в аргументе.")
        ImGui.tableSetColumnIndex(3)
        ImGui.tableSetBgColor(ImGuiTableBgTarget.CellBg, ImColor.rgba(64, 64, 64, 128))
        button("Вставить", width = 128f) { DocsUtils.enterCommand("/hollowengine start-script ") }

        // OPEN-GUI //
        ImGui.tableNextRow()
        ImGui.tableSetColumnIndex(0)
        ImGui.tableSetBgColor(ImGuiTableBgTarget.CellBg, ImColor.rgba(64, 64, 64, 128))
        ImGui.text("/hollowengine open-gui")
        ImGui.tableSetColumnIndex(1)
        ImGui.tableSetBgColor(ImGuiTableBgTarget.CellBg, ImColor.rgba(64, 64, 64, 128))
        ImGui.text("path: ScriptPath")
        ImGui.tableSetColumnIndex(2)
        ImGui.tableSetBgColor(ImGuiTableBgTarget.CellBg, ImColor.rgba(64, 64, 64, 128))
        ImGui.textWrapped("Запускает скрипт интерфейса по указанному пути в аргументе.")
        ImGui.tableSetColumnIndex(3)
        ImGui.tableSetBgColor(ImGuiTableBgTarget.CellBg, ImColor.rgba(64, 64, 64, 128))
        button("Вставить", width = 128f) { DocsUtils.enterCommand("/hollowengine open-gui ") }

        // ACTIVE-EVENTS //
        ImGui.tableNextRow()
        ImGui.tableSetColumnIndex(0)
        ImGui.tableSetBgColor(ImGuiTableBgTarget.CellBg, ImColor.rgba(64, 64, 64, 128))
        ImGui.text("/hollowengine active-events")
        ImGui.tableSetColumnIndex(1)
        ImGui.tableSetBgColor(ImGuiTableBgTarget.CellBg, ImColor.rgba(64, 64, 64, 128))
        ImGui.text("Отсутствуют")
        ImGui.tableSetColumnIndex(2)
        ImGui.tableSetBgColor(ImGuiTableBgTarget.CellBg, ImColor.rgba(64, 64, 64, 128))
        ImGui.textWrapped("Отображает активные в данный момент скрипты.")
        ImGui.tableSetColumnIndex(3)
        ImGui.tableSetBgColor(ImGuiTableBgTarget.CellBg, ImColor.rgba(64, 64, 64, 128))
        button("Вставить", width = 128f) { DocsUtils.enterCommand("/hollowengine active-events") }

        // DIALOGUE //
        ImGui.tableNextRow()
        ImGui.tableSetColumnIndex(0)
        ImGui.tableSetBgColor(ImGuiTableBgTarget.CellBg, ImColor.rgba(64, 64, 64, 128))
        ImGui.text("/hollowengine dialogue")
        ImGui.tableSetColumnIndex(1)
        ImGui.tableSetBgColor(ImGuiTableBgTarget.CellBg, ImColor.rgba(64, 64, 64, 128))
        ImGui.text("?")
        ImGui.tableSetColumnIndex(2)
        ImGui.tableSetBgColor(ImGuiTableBgTarget.CellBg, ImColor.rgba(64, 64, 64, 128))
        ImGui.textWrapped("information_not_found")
        ImGui.tableSetColumnIndex(3)
        ImGui.tableSetBgColor(ImGuiTableBgTarget.CellBg, ImColor.rgba(64, 64, 64, 128))
        button("Вставить", width = 128f) { DocsUtils.enterCommand("/hollowengine dialogue") }

        ImGui.endTable()
      }
    }
  }
}