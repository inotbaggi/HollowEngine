package ru.hollowhorizon.hollowengine.docs

import de.fabmax.kool.modules.ui2.Text
import de.fabmax.kool.modules.ui2.UiScope
import de.fabmax.kool.modules.ui2.font
import de.fabmax.kool.util.MsdfFont

fun UiScope.text(text: String) = Text(text) {
  modifier.font(MsdfFont(sizePts=16f))
}
fun UiScope.textHead(head: String) = Text(head) {
    modifier.font(MsdfFont(sizePts=24f))
}