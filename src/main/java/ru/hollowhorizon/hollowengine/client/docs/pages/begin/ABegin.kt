package ru.hollowhorizon.hollowengine.client.docs.pages.begin

import ru.hollowhorizon.hollowengine.client.docs.DocsPage
import ru.hollowhorizon.hollowengine.client.docs.DocsRenderer
import ru.hollowhorizon.hollowengine.client.docs.DocsUtils

const val begin = "begin"

@DocsPage(begin)
fun DocsRenderer.begin() = DocsUtils.text("Начало")