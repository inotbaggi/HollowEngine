package ru.hollowhorizon.hollowengine.docs

import kotlinx.browser.window

actual fun openUrl(url: String) { window.open(url, target="_blank") }