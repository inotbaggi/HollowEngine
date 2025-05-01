@file:JvmName("DocsUtilsJVM")

package ru.hollowhorizon.hollowengine.docs

lateinit var OPEN_URL: (String) -> Unit

actual fun openUrl(url: String) { OPEN_URL(url) }