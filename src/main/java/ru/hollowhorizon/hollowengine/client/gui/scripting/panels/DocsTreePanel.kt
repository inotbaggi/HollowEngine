package ru.hollowhorizon.hollowengine.client.gui.scripting.panels

import de.fabmax.kool.modules.ui2.UiScope
import de.fabmax.kool.modules.ui2.docking.Dock
import ru.hollowhorizon.hollowengine.HollowEngine
import ru.hollowhorizon.hollowengine.client.gui.docs.DocsNode
import ru.hollowhorizon.hollowengine.client.gui.scripting.FileNode
import ru.hollowhorizon.hollowengine.docs.pages.CreditsPage
import ru.hollowhorizon.hollowengine.docs.pages.WelcomePage
import ru.hollowhorizon.hollowengine.docs.pages.begin.DirectoryPage
import ru.hollowhorizon.hollowengine.docs.pages.testing.TestFontPage
import ru.hollowhorizon.hollowengine.docs.pages.testing.TestImages
import ru.hollowhorizon.hollowengine.docs.pages.testing.TestAnimations

class DocsTreePanel(dock: Dock) : DockPanel("hollowengine.gui.ide.docs", dock) {
    override val icon = "hollowengine:textures/gui/icons/docs.png"

    override fun UiScope.compose() {
        docsTree()
    }

    val docsTree = DocsNode("HollowEngine", "").apply {
        isFolder = true

        children += DocsNode("welcome", WelcomePage)
        children += DocsNode("credits", CreditsPage)

        children += DocsNode("begin", "").apply {
            isFolder = true
            children += DocsNode("directory", DirectoryPage)
        }

        if(HollowEngine.config.docsDebug.debugPages)
            children += DocsNode("Testing Page", "").apply {
                isFolder = true
                children += DocsNode("FONT", TestFontPage)
                children += DocsNode("IMAGES", TestImages)
                children += DocsNode("ANIMATINOS", TestAnimations)
            }
    }.resize()

    private fun FileNode.resize(depth: Int = 0): FileNode {
        this.depth = depth
        children.forEach { it.resize(depth + 1) }
        return this
    }
}