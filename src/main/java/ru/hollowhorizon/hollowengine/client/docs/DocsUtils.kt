package ru.hollowhorizon.hollowengine.client.docs

import imgui.ImGui
import imgui.extension.texteditor.TextEditor
import imgui.flag.ImGuiCol
import imgui.flag.ImGuiStyleVar
import imgui.flag.ImGuiWindowFlags
import net.minecraft.Util
import ru.hollowhorizon.hc.client.imgui.Graphics
import ru.hollowhorizon.hc.client.utils.rl
import ru.hollowhorizon.hc.client.utils.toTexture
import ru.hollowhorizon.hc.common.config.HollowConfig
import ru.hollowhorizon.hollowengine.EngineConfig
import ru.hollowhorizon.hollowengine.client.gui.scripting.KotlinLanguage
import kotlin.io.path.Path
import kotlin.io.path.createDirectory
import kotlin.io.path.exists

/**
 * Утилиты специально для документации
 */
object DocsUtils {
  private val docsLang = DocsLanguage.getInstance()

  fun text(textId: String, fontSize: Int = 30, center: Boolean = true, shadow: Boolean = false) {
    Graphics.withFontSize(fontSize) {
      val contextWidth = ImGui.getContentRegionAvailX()
      val text =
        if (docsLang.has(textId)) docsLang.getOrDefault(textId)
        else textId

      val textWidth = ImGui.calcTextSize(text, true, contextWidth).x

      if (center) ImGui.sameLine(contextWidth / 2 - textWidth / 2)
      if (shadow) textShadow(text) else ImGui.textWrapped(text)
    }
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

  fun code(id: String, lang: String = "kts", title: String, code: () -> String) {
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
      ImGuiWindowFlags.NoMove or ImGuiWindowFlags.NoResize
    )

    EDITOR.text = text
    EDITOR.render("##code_block-$id")

    ImGui.endChild()
    ImGui.popStyleColor(4)
  }
}

val EDITOR = TextEditor().apply {
  setLanguageDefinition(KotlinLanguage)

  tabSize = EngineConfig.IDEConfig().tabSpace
  text = ""
  isReadOnly = true
}