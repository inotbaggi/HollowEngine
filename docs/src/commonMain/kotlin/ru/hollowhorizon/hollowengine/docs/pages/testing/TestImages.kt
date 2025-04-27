package ru.hollowhorizon.hollowengine.docs.pages.testing

import de.fabmax.kool.Assets
import de.fabmax.kool.loadImage2d
import de.fabmax.kool.math.Vec2f
import de.fabmax.kool.math.toRad
import de.fabmax.kool.modules.ui2.*
import de.fabmax.kool.pipeline.Texture2d
import ru.hollowhorizon.hollowengine.docs.*
import ru.hollowhorizon.hollowengine.docs.shaders.BlurImageShader

object TestImages: Composable {
    override fun UiScope.compose() {
        val img = remember { Texture2d { Assets.loadImage2d("hollowengine:docs/titles/welcome.png").getOrThrow() } }
        val imgRot = remember { Texture2d { Assets.loadImage2d("hollowengine:docs/authors/glint.png").getOrThrow() } }

        br()

        Image(img) {
            modifier
                .imageSize(ImageSize.FitContent)
                .alignX(AlignmentX.Center)
                .size(Grow(0.9f, Grow.Std), FitContent)
        }

        divide()

        Image(img) {
            val shader = BlurImageShader()

            modifier
                .imageSize(ImageSize.FitContent)
                .alignX(AlignmentX.Center)
                .size(Grow(0.9f, Grow.Std), FitContent)
                .customShader(shader)
                .onPositioned {
                    modifier.imageProvider?.getTexture(uiNode.innerWidthPx, uiNode.innerHeightPx)?.let {
                        shader.image = it
                        shader.resolution = Vec2f(uiNode.innerWidthPx, uiNode.innerHeightPx)
                        shader.power = 0.1f
                    }
                }
        }

        divide()

        Image(imgRot) {
            modifier
                .imageSize(ImageSize.FitContent)
                .alignX(AlignmentX.Center)
                .size(Grow(0.9f, Grow.Std), FitContent)
        }

        br()
    }
}