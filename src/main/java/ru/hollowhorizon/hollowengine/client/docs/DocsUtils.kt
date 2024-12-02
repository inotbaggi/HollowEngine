package ru.hollowhorizon.hollowengine.client.docs

import imgui.ImColor
import imgui.ImGui
import imgui.extension.texteditor.TextEditor
import imgui.flag.*
import imgui.type.ImBoolean
import net.minecraft.Util
import net.minecraft.client.gui.screens.ChatScreen
import ru.hollowhorizon.hc.client.imgui.Graphics
import ru.hollowhorizon.hc.client.utils.mc
import ru.hollowhorizon.hc.client.utils.rl
import ru.hollowhorizon.hc.client.utils.toTexture
import ru.hollowhorizon.hollowengine.EngineConfig
import ru.hollowhorizon.hollowengine.HollowEngine.MODID
import ru.hollowhorizon.hollowengine.client.gui.scripting.KotlinLanguage
import java.net.URL
import kotlin.io.path.Path
import kotlin.io.path.createDirectory
import kotlin.io.path.exists
import kotlin.properties.Delegates

/**
 * Утилиты для документации
 */
object DocsUtils {
  private val docsLang = DocsLanguage.getInstance()

  val EDITOR = TextEditor().apply {
    setLanguageDefinition(KotlinLanguage)

    tabSize = EngineConfig.IDEConfig().tabSpace
    text = ""
    isReadOnly = true
  }

  fun code(
    id: String,
    lang: String = "kts",
    title: String,
    code: () -> String
  ) {
    val text = code()

    val codeBlockSize = ImGui.calcTextSize(text)

    ImGui.pushStyleColor(ImGuiCol.ScrollbarBg, 0f, 0f, 0f, 0f)
    ImGui.pushStyleColor(ImGuiCol.ScrollbarGrab, 0f, 0f, 0f, 0f)
    ImGui.pushStyleColor(ImGuiCol.ScrollbarGrabActive, 0f, 0f, 0f, 0f)
    ImGui.pushStyleColor(ImGuiCol.ScrollbarGrabHovered, 0f, 0f, 0f, 0f)
    ImGui.beginChild(
      "##code_block-$id",
      ImGui.getContentRegionAvailX() * 0.9f / 2 - ImGui.getContentRegionAvailX() / 2, codeBlockSize.y + 35f,
      true,
      ImGuiWindowFlags.NoMove or ImGuiWindowFlags.NoResize or ImGuiWindowFlags.AlwaysVerticalScrollbar
    )

    EDITOR.text = text
    //EDITOR.render("##code_block-$id")
    EDITOR.render(title)

    ImGui.endChild()
    ImGui.popStyleColor(4)
  }

  enum class TextAlign {
    LEFT,
    CENTER,
    RIGHT
  }

  fun text(textId: String, fontSize: Int = 30, textAlign: TextAlign = TextAlign.CENTER) {
    Graphics.withFontSize(fontSize) {
      val contextWidth = ImGui.getContentRegionAvailX()
      val text =
        if (docsLang.has(textId)) docsLang.getOrDefault(textId)
        else textId

      val textWidth = ImGui.calcTextSizeX(text, true, contextWidth)

      when (textAlign) {
        TextAlign.LEFT -> ImGui.setCursorPosX(8f)
        TextAlign.CENTER -> ImGui.setCursorPosX((contextWidth - textWidth) / 2)
        TextAlign.RIGHT -> ImGui.setCursorPosX(contextWidth - textWidth)
      }
      DocsUtils.textShadow(text)
    }
  }

  fun textShadow(text: String) {
    val cursor = ImGui.getCursorPos()
    ImGui.setCursorPos(cursor.x + 2.5f, cursor.y + 2.5f)
    val color = ImGui.getStyle().getColor(ImGuiCol.Text)
    ImGui.pushStyleColor(ImGuiCol.Text, color.x * 0.5f, color.y * 0.5f, color.z * 0.5f, color.w * 0.5f)
    ImGui.textWrapped(text)
    ImGui.popStyleColor()
    ImGui.setCursorPos(cursor.x, cursor.y)
    ImGui.textWrapped(text)
  }

  enum class TableType(val rgbBorder: Array<Int>, val rgbBackground: Array<Int>) {
    NOTE(arrayOf(191, 191, 191), arrayOf(107, 107, 107)),
    TIP(arrayOf(58, 186, 54), arrayOf(28, 97, 26)),
    INFO(arrayOf(78, 165, 199), arrayOf(43, 93, 112)),
    WARN(arrayOf(207, 145, 45), arrayOf(117, 82, 25)),
    ERROR(arrayOf(209, 42, 42), arrayOf(135, 26, 26))
  }

  fun table(
    headText: String,
    tableType: TableType,
    tableSizeY: Float = 256f,
    body: () -> Any
  ) {
    val sizeX = ImGui.getWindowSizeX() * 0.9f / 2 - ImGui.getWindowSizeX() / 2
    val iconTypes = arrayOf(
      arrayOf(0f, 0f, 0.25f, 0.5f), // note
      arrayOf(0.25f, 0f, 0.5f, 0.5f), // tip
      arrayOf(0.5f, 0f, 0.75f, 0.5f), // info
      arrayOf(0.75f, 0f, 1f, 0.5f), // warn
      arrayOf(0f, 0.5f, 0.25f, 1f) // error
    )
    val iconSelected =
      when (tableType) {
        TableType.NOTE -> iconTypes[0]
        TableType.TIP -> iconTypes[1]
        TableType.INFO -> iconTypes[2]
        TableType.WARN -> iconTypes[3]
        TableType.ERROR -> iconTypes[4]
      }

    ImGui.pushStyleVar(ImGuiStyleVar.ChildRounding, 16f)
    ImGui.pushStyleVar(ImGuiStyleVar.ChildBorderSize, 8f)
    ImGui.pushStyleVar(ImGuiStyleVar.WindowPadding, 8f, 8f)
    ImGui.pushStyleColor(
      ImGuiCol.ChildBg,
      tableType.rgbBackground[0],
      tableType.rgbBackground[1],
      tableType.rgbBackground[2],
      255
    )
    ImGui.pushStyleColor(ImGuiCol.Border, tableType.rgbBorder[0], tableType.rgbBorder[1], tableType.rgbBorder[2], 255)
    ImGui.pushStyleColor(ImGuiCol.ScrollbarBg, 0, 0, 0, 0)
    ImGui.pushStyleColor(
      ImGuiCol.ScrollbarGrab,
      tableType.rgbBorder[0],
      tableType.rgbBorder[1],
      tableType.rgbBorder[2],
      255
    )
    ImGui.pushStyleColor(
      ImGuiCol.ScrollbarGrabActive,
      tableType.rgbBorder[0],
      tableType.rgbBorder[1],
      tableType.rgbBorder[2],
      255
    )
    ImGui.pushStyleColor(
      ImGuiCol.ScrollbarGrabHovered,
      tableType.rgbBorder[0],
      tableType.rgbBorder[1],
      tableType.rgbBorder[2],
      255
    )
    ImGui.setCursorPosX(ImGui.getWindowSizeX() / 2f - (ImGui.getWindowSizeX() * 0.9f) / 2f)
    ImGui.beginChild("##table-$headText", sizeX, tableSizeY, true, ImGuiWindowFlags.AlwaysAutoResize)
    ImGui.setWindowSize(sizeX, tableSizeY)

    ImGui.setCursorPos(8f, 8f)
    ImGui.image(
      "$MODID:docs/icons/table_icons.png".rl.toTexture().id.toLong(),
      64f, 64f,
      iconSelected[0], iconSelected[1], iconSelected[2], iconSelected[3],
      tableType.rgbBorder[0] / 255f, tableType.rgbBorder[1] / 255f, tableType.rgbBorder[2] / 255f, 1f
    )
    ImGui.setCursorPosY(32f)
    text(headText, 40)
    ImGui.sameLine()
    ImGui.setCursorPos(ImGui.getWindowSizeX() - 64f - 32f, 8f)
    ImGui.image(
      "$MODID:docs/icons/table_icons.png".rl.toTexture().id.toLong(),
      64f, 64f,
      iconSelected[0], iconSelected[1], iconSelected[2], iconSelected[3],
      tableType.rgbBorder[0] / 255f, tableType.rgbBorder[1] / 255f, tableType.rgbBorder[2] / 255f, 1f
    )
    ImGui.newLine()
    ImGui.separator()
    ImGui.newLine()
    body()
    ImGui.newLine()

    ImGui.endChild()
    ImGui.popStyleColor(6)
    ImGui.popStyleVar(3)
  }

  enum class ButtonType(val r: Int, val g: Int, val b: Int) {
    BASIC(92, 92, 92),
    IMAGE(255, 255, 255),
    LINK(50, 103, 184),
    DIR(179, 138, 36),
    ENTER_COMMAND(164, 32, 32),
    HIDDEN(135, 100, 184)
  }

  fun button(
    label: String,
    lore: String = "",
    width: Float = 128f,
    height: Float = 64f,
    imagePath: String = "",
    buttonType: ButtonType = ButtonType.BASIC,
    action: () -> Any
  ) {
    ImGui.pushID("button-$label")
    ImGui.pushStyleVar(ImGuiStyleVar.FrameRounding, 8f)
    ImGui.pushStyleColor(ImGuiCol.Button, buttonType.r, buttonType.g, buttonType.b, 255)
    ImGui.pushStyleColor(ImGuiCol.ButtonHovered, buttonType.r, buttonType.g, buttonType.b, 255)
    ImGui.pushStyleColor(ImGuiCol.ButtonActive, buttonType.r, buttonType.g, buttonType.b, 255)

    var button by Delegates.notNull<Boolean>()

    if (imagePath != "" && buttonType == ButtonType.IMAGE)
      button = ImGui.imageButton(imagePath.rl.toTexture().id.toLong(), width, height)
    else
      button = ImGui.button(label, width, height)

    if (button) action()
    if (ImGui.isItemHovered() && lore != "") ImGui.setTooltip(lore)

    ImGui.popStyleColor(3)
    ImGui.popStyleVar()
    ImGui.popID()
  }

  fun openDir(dir: String) {
    val directory = Path(dir)

    if (!directory.exists()) directory.createDirectory()

    //? if >=1.21 {
    /*Util.getPlatform().openPath(directory)
    *///?} else {
    Util.getPlatform().openFile(directory.toFile())
    //?}
  }

  fun openUrl(url: String) {
    //? if >=1.21 {
    /*Util.getPlatform().openUrl(URL(url))
    *///?} else {
    Util.getPlatform().openUrl(URL(url))
    //?}
  }

  fun enterCommand(command: String) = mc.setScreen(ChatScreen(command))

  /**
   * @HollowHorizon Нужна такая же 3D сцена как в "Create".
   * т.е. чтобы можно было посмотреть как будет выглядеть работа скрипта (виртуально)
   * Например будет как:
   * preview3DScript {
   *   val vitalik = NPCEntity.creating {
   *     name = "Виталик"
   *     pos = pos(0, 0, 0)
   *   }
   *   vitalik move pos(9, vitalik.position.y, 3)
   * }
   */
  fun preview3DScript(script: () -> Unit) {
    // function body
  }

  fun accentText(text: String) {
    val textSize = ImGui.calcTextSize(text, true, ImGui.getContentRegionAvailX())
    val width =
      if(textSize.x + 24f > ImGui.getContentRegionAvailX())
        ImGui.getContentRegionAvailX()
      else
        textSize.x + 24f
    val height =
      if(textSize.y + 16f < ImGui.getContentRegionAvailY())
        ImGui.getContentRegionAvailY()
      else
        textSize.y + 16f

    ImGui.pushStyleVar(ImGuiStyleVar.ChildBorderSize, 4f)
    ImGui.pushStyleVar(ImGuiStyleVar.ChildRounding, 8f)
    ImGui.pushStyleColor(ImGuiCol.ChildBg, 64, 64, 64, 255)
    ImGui.pushStyleColor(ImGuiCol.Border, 128, 128, 128, 255)
    ImGui.beginChild("##accent_text", width, height, true)
    ImGui.textWrapped(text)
    ImGui.endChild()
    ImGui.popStyleColor(2)
    ImGui.popStyleVar(2)
  }
  fun tablice(tableID: String = "table", body: Array<Array<String>>, tabliceSize: Array<Float> = arrayOf(512f, 512f)) {
    val columnCount = body[0].size
    val rowCount = body.size

    ImGui.pushStyleColor(ImGuiCol.ChildBg, 16, 16, 16, 255)
    ImGui.beginChild("tableID=$tableID", tabliceSize[0], tabliceSize[1], false, ImGuiWindowFlags.HorizontalScrollbar)
    if (ImGui.beginTable(tableID, columnCount, ImGuiTableFlags.Borders or ImGuiTableFlags.RowBg)) {
      // HEADERS //
      for (i in 0 until columnCount) {
        ImGui.tableSetupColumn(" ${body[0][i]} ", ImGuiTableColumnFlags.WidthFixed, tabliceSize[0] / columnCount)
      }
      ImGui.tableHeadersRow()

      // BODY //
      for (j in 1 until rowCount) {
        ImGui.tableNextRow()
        for (k in 0 until columnCount) {
          ImGui.tableNextColumn()
          ImGui.tableSetBgColor(ImGuiTableBgTarget.CellBg, ImColor.rgba(64, 64, 64, 128))
          ImGui.pushStyleVar(ImGuiStyleVar.FramePadding, 8f, 4f)
          ImGui.textWrapped(" ${body[j][k]} ")
          ImGui.popStyleVar()
        }
        ImGui.text("")
      }

      ImGui.endTable()
    }
    ImGui.endChild()
    ImGui.popStyleColor()
  }
  fun hiddenButton(label: String, lore: String = "", contentHidden: ImBoolean, content: () -> Any) {
    val buttonLabel =
      if(!contentHidden.get())
        "Скрыть"
      else
        "Показать"
    val width = ImGui.calcTextSize("$label | $buttonLabel")

    ImGui.setCursorPosX(ImGui.getWindowSizeX() / 2f - width.x / 2f)
    button(
      "$label | $buttonLabel",
      "Показывает/Скрывает часть содержимого." + if(lore!="") lore else "",
      width = width.x + 24f,
      buttonType = ButtonType.HIDDEN
    ) {
      if(contentHidden.get())
        contentHidden.set(false)
      else
        contentHidden.set(true)
    }
    ImGui.newLine()

    if(!contentHidden.get())
      content()
  }
}