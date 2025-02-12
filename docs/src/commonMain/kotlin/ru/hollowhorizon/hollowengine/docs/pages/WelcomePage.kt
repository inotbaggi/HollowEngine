package ru.hollowhorizon.hollowengine.docs.pages

import de.fabmax.kool.modules.ui2.*
import de.fabmax.kool.util.Color
import ru.hollowhorizon.hollowengine.docs.*

object WelcomePage : Composable {
    override fun UiScope.compose() {
        val titleFont = remember { sizes.normalText.derive(50f) }

        textHead("Блаблабла I LOVE KOOL! :HEART: :BOOM: :BOOM:")
    }
}

fun UiScope.br() {
    Box {
        modifier.size(Grow.Std, sizes.smallGap)
            .backgroundColor(Color.WHITE)
            .margin(sizes.gap)
    }
}