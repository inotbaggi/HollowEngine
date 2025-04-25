package ru.hollowhorizon.hollowengine.docs.pages

import de.fabmax.kool.modules.ui2.Composable
import de.fabmax.kool.modules.ui2.UiScope

object CreditsPage: Composable {
    enum class Author(val nick: String, val color: String) {
        NONE("NONE_AUTHOR_SELECTED", "777777"),
        BENDY659("_BENDY659_", "ad0e0e"),
        THEHOLLOWHORIZON("HollowHorizon", "db911a"),
        UNKNOWN("Неизвестный", "551270")
    }
    private var authorSelect = Author.NONE
    private val authors = listOf(Author.NONE, Author.THEHOLLOWHORIZON, Author.BENDY659, Author.UNKNOWN)

    override fun UiScope.compose() {}
    /*
        override fun UiScope.compose() {
            val avatars = listOf(
                loadImage("authors/none.png"),
                loadImage("authors/thehollowhorizon.png"),
                loadImage("authors/bendy659.png"),
                loadImage("authors/unknown.png")
            )

            text("Над документацией работали", Header.H1)

            // ==== //

            divide()

            var selectIndex by remember { mutableStateOf(0) }

            ComboBox {
                modifier
                    .align(AlignmentX.Center, AlignmentY.Center)
                    .margin(sizes.gap)
                    .items(authors.map { it.nick })
                    .selectedIndex(selectIndex)
                    .backgroundColor(Color(authorSelect.color))
                    .onItemSelected {
                        selectIndex = it
                        authorSelect = authors[it]
                    }
                    .size(Grow.Std, FitContent)

            }

            divide(Color(authorSelect.color))

            // ==== //

            avatar(avatars[selectIndex])
            divide(Color(authorSelect.color))

            when(authorSelect) {
                Author.NONE -> {
                    text("Выбери любого для дополнительной информации")
                }
                Author.THEHOLLOWHORIZON -> {
                    text("Является техническим разработчиком документации.")
                    text("Холоу там потом сам себе добавить если надо :D")
                }
                Author.BENDY659 -> {
                    text("Основной документатор документации.")
                    text("Самый ебнарь года, но титул конченного достаётся всё же - TheHollowHorizon :D")
                }
                Author.UNKNOWN -> {
                    text("Может это будешь ты?")
                    text("Тебе всего-то нужно принести в доки многа контента (не всё подряд, что потребуют лентяи), а только реально важное :D")
                    text("А может я шучу?)")
                    text("А может - пошёл я?!")
                }
            }
        }
        private fun UiScope.avatar(id: Texture2d) = Image(id) {
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
     */
}