package ru.hollowhorizon.hollowengine.docs.pages.testing

import de.fabmax.kool.math.AngleF
import de.fabmax.kool.math.QuatF
import de.fabmax.kool.math.Vec3f
import de.fabmax.kool.modules.ui2.*
import de.fabmax.kool.scene.animation.*

object TestAnimations: Composable {
    var tick = 0f

    override fun UiScope.compose() {
        val box = Box {
            modifier.size(128.dp, 128.dp)
                .onHover {
                    if(!it.isLeftClick) return@onHover

                    val inter = InterpolatedFloat(4f, 68f)
                    inter.interpolate(AnimationKey.Interpolation.CUBICSPLINE.getInterpolationPos(inter.value))

                    it.position.x = inter.value
                }
        }
    }
}