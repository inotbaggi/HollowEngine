package ru.hollowhorizon.hollowengine.client.docs.pages

import imgui.ImGui
import net.minecraft.Util
import ru.hollowhorizon.hollowengine.client.docs.DocsPage
import ru.hollowhorizon.hollowengine.client.docs.DocsRenderer
import ru.hollowhorizon.hollowengine.client.docs.DocsUtils.button
import ru.hollowhorizon.hollowengine.client.docs.DocsUtils.dline
import ru.hollowhorizon.hollowengine.client.docs.DocsUtils.table
import ru.hollowhorizon.hollowengine.client.docs.DocsUtils.text
import ru.hollowhorizon.hollowengine.client.docs.DocsUtils.titleImg
import ru.hollowhorizon.hollowengine.client.docs.TableType

const val a = "0_welcome"

@DocsPage(a)
fun DocsRenderer.heWelcome() {
  text("Добро пожаловать", 90)
}