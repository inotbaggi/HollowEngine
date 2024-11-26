package ru.hollowhorizon.hollowengine.client.docs.pages

import imgui.ImGui
import imgui.type.ImBoolean
import imgui.type.ImInt
import imgui.type.ImString
import ru.hollowhorizon.hollowengine.client.docs.DocsPage
import ru.hollowhorizon.hollowengine.client.docs.DocsRenderer
import ru.hollowhorizon.hollowengine.client.docs.DocsUtils
import ru.hollowhorizon.hollowengine.client.docs.DocsUtils.text
import ru.hollowhorizon.hollowengine.client.docs.DocsUtils.TableType
import ru.hollowhorizon.hollowengine.client.docs.DocsUtils.table
import ru.hollowhorizon.hollowengine.client.docs.DocsUtils.tablice
import ru.hollowhorizon.hollowengine.common.files.DirectoryManager
import kotlin.io.path.pathString

/*
val testModel = ImString("$MODID:docs/title_model.gltf", 256)
val testAnim = ImString("test_stand", 256)
val hatModel = ImString("$MODID:docs/access/eyesglasses.gltf", 256)

@DocsPage("-1_test_page")
fun DocsRenderer.testPage() {
  docsPages = DocsPages.TEST_PAGE

  ImGui.setCursorPosX(ImGui.getWindowSizeX() / 2f - (ImGui.getWindowSizeX() * 0.75f) / 2f)
  ImGui.beginChild("test_title", ImGui.getColumnWidth() * 0.95f, 896f)

  val center = (ImGui.getWindowWidth() / 2f - 512f / 2f)
  ImGui.setCursorPos(center - 256f, ImGui.getWindowHeight() - 896f - 8f)
  nModel.apply { tickCount = ticks }
  entity(nModel, 896f, 896f, scale = 0.9f, rotation = false)

  ImGui.endChild()

  ImGui.pushItemWidth(ImGui.getWindowWidth() * 0.6f)
  if(ImGui.inputText("testModel set", testModel) && testModel.get() != "%NO_MODEL%") {
    val newModel = testModel.get().rl

    if(newModel.exists() && (newModel.path.endsWith(".gltf") || newModel.path.endsWith(".glb")))
      nModel.apply {
        this[AnimatedEntityCapability::class.java].model = newModel.toString()
      }
  }
  ImGui.pushItemWidth(ImGui.getWindowWidth() * 0.6f)
  ImGui.inputText("testAnimation set", testAnim)
  ImGui.inputText("testHatModel set", hatModel)
  ImGui.popItemWidth()
  ImGui.sameLine()
  if(ImGui.imageButton("hollowengine:textures/gui/icons/play.png".rl.toTexture().id.toLong(), 64f, 64f)) {
    nModel.apply {
      val cap = this[AnimatedEntityCapability::class.java]
      cap.layers.clear()

      cap.layers += AnimationLayer(testAnim.get(), LayerMode.ADD, PlayMode.LAST_FRAME, 1f)
    }
  }
  ImGui.newLine()
}
*/

val tTypes = arrayOf("NOTE", "TIP", "INFO", "WARN", "ERROR")
val tTypeSelected = ImInt(0)
var tableTypeSelected = TableType.NOTE

val openedUrl = ImString("https://www.google.com", 512)
val openedDir = ImString(DirectoryManager.HOLLOW_ENGINE.pathString, 512)
val enterCommand = ImString("/hollowengine", 512)

@DocsPage("test_page")
fun DocsRenderer.testPage() {
  table("Test head table", tableTypeSelected, 512f) {
    text("Test body table")
    ImGui.newLine()
    text("Как-то раз, Алго был в гостях у \"Forge\". И у него в кормане был:")
    text("- Нож", textAlign = DocsUtils.TextAlign.LEFT)
    text("- Пистолет", textAlign = DocsUtils.TextAlign.LEFT)
    text("- Дробовик", textAlign = DocsUtils.TextAlign.LEFT)
    text("- Огнемёт", textAlign = DocsUtils.TextAlign.LEFT)
    text("- Бомбардировщик", textAlign = DocsUtils.TextAlign.LEFT)
    text("С момента когда настал 18 ноября 2031 год, больше никто не видел ни \"Forge\" ни Алго...")
  }
  if(ImGui.combo("Table type", tTypeSelected, tTypes)) {
    when(tTypeSelected.get()) {
      0 -> tableTypeSelected = TableType.NOTE
      1 -> tableTypeSelected = TableType.TIP
      2 -> tableTypeSelected = TableType.INFO
      3 -> tableTypeSelected = TableType.WARN
      4 -> tableTypeSelected = TableType.ERROR
    }
  }
  ImGui.newLine()
  ImGui.separator()
  ImGui.newLine()

  DocsUtils.button(
    "Open test link",
    "Открывает тестовую ссылку. Ссылку можно изменить ниже",
    312f, 64f,
    buttonType = DocsUtils.ButtonType.LINK
  ) { DocsUtils.openUrl(openedUrl.get()) }; ImGui.sameLine()
  DocsUtils.button(
    "Open test directory",
    "Открывает тестовую директорию. Директорию можно изменить ниже",
    460f, 64f,
    buttonType = DocsUtils.ButtonType.DIR
  ) { DocsUtils.openDir(openedDir.get()) }; ImGui.sameLine()
  DocsUtils.button(
    "Open test command",
    "Вставляет тестовую команду в чат. Команду можно изменить ниже",
    312f, 64f,
    buttonType = DocsUtils.ButtonType.ENTER_COMMAND
  ) { DocsUtils.enterCommand(enterCommand.get()) }
  ImGui.inputText("Test Url", openedUrl)
  ImGui.inputText("Test Directory", openedDir)
  ImGui.inputText("Test Command", enterCommand)

  ImGui.newLine()
  ImGui.separator()
  ImGui.newLine()

  text("Test table")

  tablice(
    "test_table",
    arrayOf(
      arrayOf("head 1", "head 2", "head 3"),
      arrayOf("body 1", "body 2", "body 3")
    ),
    arrayOf(128f, 256f, 64f)
  )
}