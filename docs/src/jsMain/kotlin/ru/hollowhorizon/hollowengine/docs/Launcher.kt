package ru.hollowhorizon.hollowengine.docs

import de.fabmax.kool.KoolApplication
import de.fabmax.kool.KoolConfigJs
import de.fabmax.kool.scene.scene
import kotlinx.browser.window

fun main() = KoolApplication(
    config = KoolConfigJs(
        canvasName = "HollowEngine Docs"
    )
) {
    ctx.scenes += scene {
        DOCS_GENERATOR()
    }
}