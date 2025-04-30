package ru.hollowhorizon.hollowengine.client.gui.docs

import de.fabmax.kool.modules.ui2.*
import ru.hollowhorizon.hollowengine.client.gui.kool.hoverBg
import ru.hollowhorizon.hollowengine.client.gui.scripting.FileNode
import ru.hollowhorizon.hollowengine.client.gui.scripting.IdeContent
import ru.hollowhorizon.hollowengine.client.utils.lang

class DocsNode(name: String, path: String, val page: Composable? = null) : FileNode(name, path) {
    constructor(path: String, page: Composable? = null) : this(
        "hollowengine.gui.docs.${path.replace('/', '.')}".lang,
        path,
        page
    )

    private var isHovered = false

    override fun toggleExpanded() {
        if (!isFolder) return

        // Открываем / Закрываем папку
        isExpanded.set(!isExpanded.value)
    }

    override fun UiScope.sceneObjectItem(item: FileNode) {
        modifier
            .onClick { evt ->
                if (evt.pointer.isLeftButtonClicked) {
                    if (item.isFolder && evt.pointer.leftButtonRepeatedClickCount == 2) {
                        item.toggleExpanded()
                    } else {
                        IdeContent.openDocFile(item)
                    }
                }
            }
            .margin(horizontal = sizes.smallGap)
            .padding(horizontal = sizes.smallGap)
            .onEnter { isHovered = true }
            .onExit { isHovered = false }

        if (isHovered) modifier.background(RoundRectBackground(colors.hoverBg, sizes.smallGap))

        val fgColor = if (isHovered) colors.primary else colors.secondary

        sceneObjectLabel(item, fgColor)
    }
}