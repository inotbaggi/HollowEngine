package ru.hollowhorizon.hollowengine.client.gui.docs

import de.fabmax.kool.modules.ui2.*
import de.fabmax.kool.modules.ui2.ArrowScope.Companion.ROTATION_DOWN
import de.fabmax.kool.modules.ui2.ArrowScope.Companion.ROTATION_RIGHT
import de.fabmax.kool.modules.ui2.docking.DockNode
import net.minecraft.client.Minecraft
import ru.hollowhorizon.hollowengine.client.gui.kool.hoverBg
import ru.hollowhorizon.hollowengine.client.gui.scripting.*
import ru.hollowhorizon.hollowengine.client.gui.scripting.docking.insertItem
import ru.hollowhorizon.hollowengine.client.gui.scripting.files.DocFileData
import ru.hollowhorizon.hollowengine.client.utils.lang

class DocsNode(name: String, path: String, val page: Composable? = null) : FileNode(name, path) {
     constructor(path: String, page: Composable? = null) : this(
         "hollowengine.gui.docs.${path.replace('/', '.')}".lang,
         path,
         page
     )

     override fun toggleExpanded() {
         if (!isFolder) return

         // Открываем / Закрываем папку
         isExpanded.set(!isExpanded.value)
     }

     override fun createFilePopup() = Popup.EMPTY

     override fun openFile(item: FileNode) {
         item as DocsNode

         val screen = Minecraft.getInstance().screen as? ScriptingEnvironmentScreen ?: return
         val file = IdeContent.files.getOrPut(item.treePath) {
             val local = DocFileData(item.treePath, item.treePath, item.page ?: Composable { Text("Here is nothing yet.") {} })
             screen.dock.addDockableSurface(local.dockable, local.surface)
             local
         }
         val dock = screen.dock
         val fileLeaf = dock.getLeafAtPath("0/1")
         if (fileLeaf != null) fileLeaf.dock(file.dockable)
         else dock.getLeafAtPath("0")?.insertItem(file.dockable, DockNode.SlotPosition.Right)
     }
 }