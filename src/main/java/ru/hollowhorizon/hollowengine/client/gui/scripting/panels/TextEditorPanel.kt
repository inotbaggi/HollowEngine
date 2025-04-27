package ru.hollowhorizon.hollowengine.client.gui.scripting.panels

import de.fabmax.kool.input.Input
import de.fabmax.kool.modules.ui2.*
import de.fabmax.kool.modules.ui2.docking.Dock
import de.fabmax.kool.util.Color
import de.fabmax.kool.util.MsdfFont
import de.fabmax.kool.util.MsdfFontData
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.jetbrains.kotlin.com.intellij.openapi.editor.markup.TextAttributes
import ru.hollowhorizon.hollowengine.docs.HACK_FONT
import java.io.File

class TextEditorPanel(dock: Dock): DockPanel("hollowengine.gui.ide.text_editor", dock)  {
    override val icon: String = "hollowengine:textures/gui/icons/text_editor.png"

    private val providerEditor = ListTextLineProvider(
        mutableListOf(
            TextLine(
                listOf(
                    "Example Text" to TextAttributes(MsdfFont(HACK_FONT, 16f), Color.WHITE)
                )
            )
        )
    )
    private val providerExport = ListTextLineProvider(
        mutableListOf(
            TextLine(
                listOf(
                    "result" to TextAttributes(MsdfFont(HACK_FONT, 16f), Color.WHITE)
                )
            )
        )
    )

    private val json = Json { prettyPrint = true }

    @Serializable
    class Style(
        // Basic //
        val text: String,
        // Formating //
        val bold: Boolean,
        val italic: Boolean,
        val size: Float,
        val align: Align,
        // Font //
        val color: String,
        val glowColor: String?
    ) {
        companion object {
            val DEFAULT = Style("", false, false, 14f, Align.LEFT, "#FF0000", null)
        }
    }

    @Serializable
    enum class Align { LEFT, CENTER, RIGHT }

    override fun UiScope.compose() {
        modifier.margin(sizes.smallGap)

        Box {
            modifier
                .size(Grow.Std, Grow.Std)
                .margin(8.dp).padding(8.dp)
                .border(RectBorder(Color.WHITE, 4.dp, 16.dp))

            TextArea(providerEditor) {
                modifier
                    .size(Grow.Std, Grow.Std)
                    .align(AlignmentX.Center, AlignmentY.Center)
                    .editorHandler(DefaultTextEditorHandler(providerEditor.lines))
            }
        }
    }

    private fun save() {
        val buf = providerEditor.lines
        val file = File("${providerExport.lines[0].text}.json")

        if(!file.exists()) file.mkdirs()
    }
}