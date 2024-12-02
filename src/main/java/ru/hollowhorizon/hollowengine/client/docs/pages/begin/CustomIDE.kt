package ru.hollowhorizon.hollowengine.client.docs.pages.begin

import imgui.ImGui
import imgui.ImGui.newLine
import imgui.ImGui.separator
import ru.hollowhorizon.hc.client.utils.rl
import ru.hollowhorizon.hc.client.utils.toTexture
import ru.hollowhorizon.hollowengine.HollowEngine.MODID
import ru.hollowhorizon.hollowengine.client.docs.DocsPage
import ru.hollowhorizon.hollowengine.client.docs.DocsRenderer
import ru.hollowhorizon.hollowengine.client.docs.DocsUtils
import ru.hollowhorizon.hollowengine.client.docs.DocsUtils.text

const val custom_ide = "begin.ide"

@DocsPage(custom_ide)
fun DocsRenderer.customIde() {
  val imgSizeDef = arrayOf(ImGui.getWindowSizeX() * 0.98f / 1.25f, ImGui.getWindowSizeX() * 0.98f / 1.25f / 1.75f)

  text("Движок предоставляет свою собственную \"Среду разработки\" (или же IDE), где вы можете без сторонних программ и неудобства работать со своими скриптами прямо внутри игры, не выходя из игры.")
  newLine()

  ImGui.setCursorPosX(ImGui.getWindowSizeX() / 2f - imgSizeDef[0] / 2f)
  ImGui.image("$MODID:docs/ide/menu.png".rl.toTexture().id.toLong(), imgSizeDef[0], imgSizeDef[1])
  newLine()

  text("1. - Меню. Там вы можете как и перезагрузить ресурсы игры, так и изменить количество пробелов для `Tab`.", textAlign = DocsUtils.TextAlign.LEFT)
  text("2. - Древо файлов. Тут будут показаны списком все файлы и папки.", textAlign = DocsUtils.TextAlign.LEFT)
  text("3. - Запуск. Выбираете определённый скрипт который нужно запустить.", textAlign = DocsUtils.TextAlign.LEFT)
  text("4. - Редактор. В этом окне вы можете редактировать файлы.", textAlign = DocsUtils.TextAlign.LEFT)

  newLine()
  separator()
  newLine()

  text("Нажав ПКМ по папке в Древе файлов - откроется контекстное меню, где вы можете создать файл или ещё одну папку:")

  ImGui.setCursorPosX(ImGui.getWindowSizeX() / 2f - imgSizeDef[0] / 2f / 1.25f)
  ImGui.image("$MODID:docs/ide/folder_menu.png".rl.toTexture().id.toLong(), imgSizeDef[0] / 1.25f, imgSizeDef[1] / 2f / 1.25f)
}