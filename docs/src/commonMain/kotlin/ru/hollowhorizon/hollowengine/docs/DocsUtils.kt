package ru.hollowhorizon.hollowengine.docs

import de.fabmax.kool.Assets
import de.fabmax.kool.loadBlob
import de.fabmax.kool.loadImage2d
import de.fabmax.kool.math.Vec2f
import de.fabmax.kool.modules.ui2.*
import de.fabmax.kool.pipeline.Texture2d
import de.fabmax.kool.util.*
import de.fabmax.kool.util.MsdfFont.Companion.MSDF_TEX_PROPS
import kotlinx.serialization.json.Json
import ru.hollowhorizon.hollowengine.docs.shaders.BlurImageShader

enum class THType(val fontSize: Float) { H1(16f), H2(14f), H3(12f), H4(10f), H5(8f), H6(6f) }
fun UiScope.text(
  text: String,
  headType: THType = THType.H4,
  bold: Boolean = false,
  italic: Boolean = false,
  margin: Boolean = true
): UiScope = Text(text) {
  launchOnMainThread {
    val meta = Json.decodeFromString<MsdfMeta>(Assets.loadBlob("hollowengine:fonts/hack.json").getOrThrow().decodeToString())
    val data = Texture2d(MSDF_TEX_PROPS, "MsdfFont:${meta.name}") { Assets.loadImage2d("hollowengine:fonts/hack.png").getOrThrow() }
    modifier.font(MsdfFont(
      MsdfFontData(data, meta),
      sizePts = headType.fontSize,
      weight = if (bold) 0.5f else 0f,
      italic = if (italic) 0.25f else 0f
    ))
  }
  modifier
    .align(AlignmentX.Center)
    .margin(if(margin) 8.dp else 0.dp, 4.dp)
}

fun UiScope.divite() = Box {  modifier.size(Grow.Std, 1.dp).backgroundColor(Color.WHITE).margin(sizes.gap) }
fun UiScope.br() = Box { modifier.size(Grow.Std, sizes.smallGap).margin(8.dp, 32.dp) }

fun UiScope.title(id: String = "null_title") = Image(remember {
  Texture2d { Assets.loadImage2d("hollowengine:docs/titles/$id.png").getOrThrow() }
}) {
  val shader = BlurImageShader()
  modifier.imageSize(ImageSize.FitContent).alignX(AlignmentX.Center)
    .size(Grow(0.9f, Grow.Std), FitContent)
    .customShader(shader)

  modifier.onPositioned {
    modifier.imageProvider?.getTexture(uiNode.innerWidthPx, uiNode.innerHeightPx)?.let {
      shader.image = it
      shader.resolution = Vec2f(uiNode.innerWidthPx, uiNode.innerHeightPx)
      shader.power = 0.15f
    }
  }
}

enum class TableType(val bg: String, val border: String, val icon: String) {
  NOTE("969696", "e8e8e8", "hollowengine:docs/icons/table_note.png"),
  TIP("34783a", "7bed85", "hollowengine:docs/icons/table_tip.png")
}
fun UiScope.table(title: String, type: TableType, body: UiScope.() -> Unit) {
  Box {
    modifier
      .align(AlignmentX.Center)
      .backgroundColor(Color(type.bg))
      .padding(8.dp)
      .margin(4.dp)
      .border(RectBorder(Color(type.border), 4.dp))

    Row {
      modifier.align(AlignmentX.Center)

      Image(loadImage("icons/${type.icon}_old.png")) { modifier.align(AlignmentX.Start) }
      Text(title) {}
      Image(loadImage("icons/${type.icon}_old.png")) { modifier.align(AlignmentX.End) }
      divite()
      br()

      body()
    }
  }
}

fun UiScope.loadImage(path: String) = remember { Texture2d { Assets.loadImage2d("hollowengine:docs/$path").getOrThrow() } }