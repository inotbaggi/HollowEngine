package ru.hollowhorizon.hollowengine.client.gui.scripting.panels

import de.fabmax.kool.modules.ui2.UiScope
import de.fabmax.kool.modules.ui2.docking.Dock
import ru.hollowhorizon.hollowengine.client.gui.docs.DocsNode
import ru.hollowhorizon.hollowengine.client.gui.scripting.FileNode
import ru.hollowhorizon.hollowengine.docs.pages.Credits
import ru.hollowhorizon.hollowengine.docs.pages.testing.TestFont
import ru.hollowhorizon.hollowengine.docs.pages.Welcome
import ru.hollowhorizon.hollowengine.docs.pages.testing.TestImages

class DocsTreePanel(dock: Dock) : DockPanel("hollowengine.gui.ide.docs", dock) {
    override val icon = "hollowengine:textures/gui/icons/docs.svg"

    override fun UiScope.compose() {
        docsTree()
    }

    val docsTree = DocsNode("HollowEngine", "").apply {
        //initPages()

        isFolder = true

        children += DocsNode("welcome", Welcome)
        children += DocsNode("begin", "begin").apply {
            isFolder = true

            children += DocsNode("directory", Directory)
        }
        children += DocsNode("credits", Credits)
        children += DocsNode("Testing", "testing").apply {
            isFolder = true
            children += DocsNode("test_font", TestFont)
            children += DocsNode("test_images", TestImages)
        }
    }.resize()

    private fun initPages() {}

    private fun FileNode.resize(depth: Int = 0): FileNode {
        this.depth = depth
        children.forEach { it.resize(depth + 1) }
        return this
    }
}