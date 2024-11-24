package ru.hollowhorizon.hollowengine.client.docs.pages.begin

import imgui.ImGui
import ru.hollowhorizon.hc.client.utils.rl
import ru.hollowhorizon.hc.client.utils.toTexture
import ru.hollowhorizon.hollowengine.HollowEngine.MODID
import ru.hollowhorizon.hollowengine.client.docs.DocsPage
import ru.hollowhorizon.hollowengine.client.docs.DocsRenderer
import ru.hollowhorizon.hollowengine.client.docs.DocsUtils
import ru.hollowhorizon.hollowengine.client.docs.DocsUtils.accentText
import ru.hollowhorizon.hollowengine.client.docs.DocsUtils.text
import ru.hollowhorizon.hollowengine.client.docs.DocsUtils.table

const val begin_scriptsType = "$begin.scripts_type"

@DocsPage(begin_scriptsType)
fun DocsRenderer.scriptsType() {
  text("Мод предлагает реализовывать ваши штучки через скрипты, и бывает их несколько типов.")

  ImGui.newLine()
  ImGui.separator()
  ImGui.newLine()

  table("Тип скрипта \"Сюжет\"", DocsUtils.TableType.TIP, 512f) {
    ImGui.setCursorPosX(ImGui.getWindowSizeX() / 2f - (1024f / 2f) / 2f)
    ImGui.image("$MODID:docs/scripts_types/story.png".rl.toTexture().id.toLong(), 1024f / 2f, 350f / 2f)

    ImGui.newLine()

    text("Данный тип скриптов предназначен для написания сюжетов.")
    ImGui.setCursorPosX(256f)
    text("Расширение у таких файлов скриптов должно быть:"); ImGui.sameLine(); accentText(".story.kts")
  }
  ImGui.newLine()
  table("Тип скрипта \"Ивенты\"", DocsUtils.TableType.TIP, 512f + 8f) {
    ImGui.setCursorPosX(ImGui.getWindowSizeX() / 2f - (1024f / 2f) / 2f)
    ImGui.image("$MODID:docs/scripts_types/event.png".rl.toTexture().id.toLong(), 1024f / 2f, 350f / 2f)

    ImGui.newLine()

    text("Данный тип скриптов предназначен для написания специальных событий.")
    ImGui.setCursorPosX(256f - 16f)
    text("Расширение у таких файлов скриптов должно быть:"); ImGui.sameLine(); accentText(".event.kts")
  }
  ImGui.newLine()
  table("Тип скрипта \"Интерфейс (GUI)\"", DocsUtils.TableType.TIP, 512f + 8f) {
    ImGui.setCursorPosX(ImGui.getWindowSizeX() / 2f - (1024f / 2f) / 2f)
    ImGui.image("$MODID:docs/scripts_types/gui.png".rl.toTexture().id.toLong(), 1024f / 2f, 350f / 2f)

    ImGui.newLine()

    text("Данный тип скриптов предназначен для написания собственных интерфейсов (GUI).")
    ImGui.setCursorPosX(256f - 16f)
    text("Расширение у таких файлов скриптов должно быть:"); ImGui.sameLine(); accentText(".gui.kts")
  }
  ImGui.newLine()
  /*
  table("Тип скрипта \"Диалог\".", DocsUtils.TableType.TIP, 512f) {
    ImGui.setCursorPosX(ImGui.getWindowSizeX() / 2f - (1024f / 2f) / 2f)
    ImGui.image("$MODID:docs/scripts_types/story.png".rl.toTexture().id.toLong(), 1024f / 2f, 350f / 2f)

    ImGui.newLine()

    text("Данный тип скриптов предназначен для написания сюжетов.")
    ImGui.setCursorPosX(256f)
    text("Расширение у таких файлов скриптов должно быть:"); ImGui.sameLine(); accentText(".story.kts")
  }
  */
}