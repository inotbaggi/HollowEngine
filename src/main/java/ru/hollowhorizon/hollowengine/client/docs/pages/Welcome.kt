package ru.hollowhorizon.hollowengine.client.docs.pages

import imgui.ImGui
import ru.hollowhorizon.hollowengine.client.docs.DocsPage
import ru.hollowhorizon.hollowengine.client.docs.DocsRenderer
import ru.hollowhorizon.hollowengine.client.docs.DocsUtils
import ru.hollowhorizon.hollowengine.client.docs.DocsUtils.text

const val welcome = "0_welcome"
/*
@DocsPage(a)
fun DocsRenderer.heWelcome() {
  docsPages = DocsPages.WELCOME

  text("Добро пожаловать", 70, true, true)
  ImGui.newLine()
  text("на документацию по HollowEngine!", 50, true, true)
  ImGui.newLine()
  ImGui.separator()

  ImGui.setCursorPosX(ImGui.getWindowSizeX() / 2f - (ImGui.getWindowSizeX() * 0.75f) / 2f)
  ImGui.pushStyleVar(ImGuiStyleVar.WindowRounding, 16f)
  ImGui.beginChild("title", ImGui.getWindowSize().x * 0.75f, 512f)

  val center = (ImGui.getWindowWidth() / 2f - 512f / 2f)
  ImGui.setCursorPos(center, -16f)
  nModel.apply {
    this[AnimatedEntityCapability::class.java].layers += AnimationLayer(
      "welcome_title",
      LayerMode.ADD,
      PlayMode.LAST_FRAME,
      1f
    )
    tickCount = ticks
  }
  entity(nModel, 512f, 512f, rotation = false, offsetY = 448f, scale = 1.5f )
  ImGui.endChild()
  ImGui.popStyleVar()
}
*/

@DocsPage(welcome)
fun DocsRenderer.welcomePage() {
  text("Добро пожаловать", 70)
  ImGui.newLine()
  text("на документацию по HollowEngine!", 50)
  ImGui.newLine()
  ImGui.separator()
  ImGui.newLine()

  DocsUtils.table("Смысл документации", DocsUtils.TableType.TIP) {
    text("Данная документация должна обучить вас пользоваться базовыми возможностями \"HollowEngine\".")
  }
  ImGui.newLine()
  DocsUtils.table("Язык программирования \"Kotlin\"", DocsUtils.TableType.WARN, tableSizeY = 512f - 64f) {
    text("Весь движок работает исключительно на языке программирования \"Kotlin\"! Так что для простого понимания \"Как работает движок\" рекомендуем вам изучить хотя бы базовый синтаксис этого языка.")
    ImGui.newLine()
    val buttonsWidth = 512f + 128f
    ImGui.setCursorPosX(ImGui.getWindowSizeX() / 2 - (buttonsWidth + 128f) / 2f)
    DocsUtils.button(
      "Официальная документация по \"Kotlin\"",
      "Открывает официальную документацию по \"Kotlin\".",
      buttonsWidth + 128f,
      buttonType = DocsUtils.ButtonType.LINK
    ) { DocsUtils.openUrl("https://kotlinlang.org/docs/home.html") }
    ImGui.setCursorPosX(ImGui.getWindowSizeX() / 2 - buttonsWidth / 2f)
    DocsUtils.button(
    "Документация по \"KotlinScript\"",
      "Открывает документацию по \"KotlinScript\"",
      buttonsWidth,
      buttonType = DocsUtils.ButtonType.LINK
    ) { DocsUtils.openUrl("https://beta.0mods.team/ru-RU/docs/Legacy/category/kotlinscript") }
  }
}