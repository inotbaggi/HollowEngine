package ru.hollowhorizon.hollowengine.docs.pages

import de.fabmax.kool.Assets
import de.fabmax.kool.loadImage2d
import de.fabmax.kool.math.Vec2f
import de.fabmax.kool.modules.ui2.*
import de.fabmax.kool.pipeline.Texture2d
import de.fabmax.kool.util.Color
import de.fabmax.kool.util.MsdfFont
import ru.hollowhorizon.hollowengine.docs.HACK_FONT
import ru.hollowhorizon.hollowengine.docs.Header
import ru.hollowhorizon.hollowengine.docs.divide
import ru.hollowhorizon.hollowengine.docs.shaders.BlurImageShader
import ru.hollowhorizon.hollowengine.docs.text

object Credits: Composable {
    enum class Author(val nick: String, val color: String, val avatarId: String) {
        NONE("NONE_AUTHOR_SELECTED", "777777", "none"),
        BENDY659("_BENDY659_", "ad0e0e", "bendy659"),
        THEHOLLOWHORIZON("HollowHorizon", "db911a", "thehollowhorizon"),
        UNKNOWN("Неизвестный", "551270", "unknown")
    }
    var authorSelect = Author.NONE

    override fun UiScope.compose() {
        text("Над документацией работали", Header.H1)

        // ==== //

        divide()

        Box { modifier.align(AlignmentX.Center, AlignmentY.Top)
            Row {
                authorButton(Author.NONE.nick, Author.NONE.color) { authorSelect = Author.NONE }
                authorButton(Author.THEHOLLOWHORIZON.nick, Author.THEHOLLOWHORIZON.color) { authorSelect = Author.THEHOLLOWHORIZON }
                authorButton(Author.BENDY659.nick, Author.BENDY659.color) { authorSelect = Author.BENDY659 }
                authorButton(Author.UNKNOWN.nick, Author.UNKNOWN.color) { authorSelect = Author.UNKNOWN }
            }
        }

        divide(Color(authorSelect.color))

        // ==== //

        when(authorSelect) {
            Author.NONE -> {
                avatar(authorSelect.avatarId)
                divide(Color(authorSelect.color))
                text("Выбери любого для дополнительной информации")
            }
            Author.THEHOLLOWHORIZON -> {
                avatar(authorSelect.avatarId)
                divide(Color(authorSelect.color))
                text("Является техническим разработчиком документации.")
                text("Холоу там потом сам себе добавить если надо :D")
            }
            Author.BENDY659 -> {
                avatar(authorSelect.avatarId)
                divide(Color(authorSelect.color))
                text("Основной документатор документации.")
                text("Самый ебнарь года, но титул конченного достаётся всё же - TheHollowHorizon :D")
            }
            Author.UNKNOWN -> {
                avatar(authorSelect.avatarId)
                divide(Color(authorSelect.color))
                text("Может это будешь ты?")
                text("Тебе всего-то нужно принести в доки многа контента (не всё подряд, что потребуют лентяи), а только реально важное :D")
            }
        }
    }

    private fun UiScope.authorButton(text: String, color: String, onClick: () -> Unit) = Button(text) {
        modifier
            .font(MsdfFont(HACK_FONT))
            .colors(buttonColor = Color(color))
            .textColor(Color.WHITE)
            .border(RoundRectBorder(Color.BLACK, 2.dp, sizes.borderWidth + 1.dp))
            .onHover {
                modifier
                    .textColor(Color.BLACK)
                    .border(RoundRectBorder(Color.WHITE, 2.dp, sizes.borderWidth + 2.dp))
            }
            .onClick { onClick() }
    }
    fun UiScope.avatar(id: String) = Image(remember {
        Texture2d { Assets.loadImage2d("hollowengine:docs/authors/$id.png").getOrThrow() }
    }) {
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
}