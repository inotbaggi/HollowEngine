package ru.hollowhorizon.hollowengine.docs

import de.fabmax.kool.Assets
import de.fabmax.kool.loadBlob
import de.fabmax.kool.loadImage2d
import de.fabmax.kool.math.Vec2f
import de.fabmax.kool.modules.ui2.*
import de.fabmax.kool.pipeline.SamplerSettings
import de.fabmax.kool.pipeline.Texture2d
import de.fabmax.kool.pipeline.TextureProps
import de.fabmax.kool.util.*
import de.fabmax.kool.util.MsdfFont.Companion.ITALIC_STD
import de.fabmax.kool.util.MsdfFont.Companion.MSDF_TEX_PROPS
import de.fabmax.kool.util.MsdfFont.Companion.WEIGHT_BOLD
import kotlinx.serialization.json.Json
import ru.hollowhorizon.hollowengine.docs.shaders.BlurImageShader

private val json = Json { ignoreUnknownKeys = true }

lateinit var HACK_FONT: MsdfFontData

suspend fun loadResources() {
    val fontInfo = json.decodeFromString<MsdfMeta>(
        Assets.loadBlob("hollowengine:fonts/hack.json").getOrThrow().decodeToString()
    )
    val msdfMap = Texture2d(MSDF_TEX_PROPS, "MsdfFont:${fontInfo.name}") {
        Assets.loadImage2d("hollowengine:fonts/hack.png", MSDF_TEX_PROPS).getOrThrow()
    }
    HACK_FONT = MsdfFontData(msdfMap, fontInfo)
}

enum class Header(val fontSize: Float) { H1(16f), H2(14f), H3(12f), H4(10f), H5(8f), H6(6f) }

fun UiScope.text(
    text: String,
    header: Header = Header.H4,
    alignment: AlignmentX = AlignmentX.Center,
    bold: Boolean = false,
    italic: Boolean = false,
    margin: Boolean = true,
): UiScope = Text(text) {

    val font = MsdfFont(
        HACK_FONT,
        sizePts = header.fontSize,
        weight = if (bold) WEIGHT_BOLD else 0f,
        italic = if (italic) ITALIC_STD else 0f
    )

    modifier.font(font)
        .align(alignment)
        .textAlignX(alignment)
        .margin(if (margin) sizes.gap else 0.dp, sizes.smallGap)
        .width(Grow(1f))
        .isWrapText(true)
}

fun UiScope.divide(color: Color = Color.WHITE) =
    Box { modifier.size(Grow.Std, sizes.borderWidth).backgroundColor(color).margin(sizes.gap) }

fun UiScope.br() = Box { modifier.size(Grow.Std, 4.dp).margin(sizes.gap) }

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
    NOTE("878787", "d4d4d4", "note"),
    TIP("438c34", "73d160", "tip"),
    INFO("3c86a3", "5fafcf", "info"),
    WARN("8a6932", "e8c268", "warn"),
    ERR("913131", "e84646", "err")
}

inline fun UiScope.table(title: String, type: TableType, body: UiScope.() -> Unit) {
    Column {
        modifier
            .align(AlignmentX.Center)
            .backgroundColor(Color(type.bg))
            .margin(sizes.largeGap)
            .border(RectBorder(Color(type.border), sizes.borderWidth))
            .width(Grow.Std)

        Row {
            modifier.align(AlignmentX.Center, AlignmentY.Top).margin(sizes.gap)

            Image(loadImage("icons/table_${type.icon}.png")) {
                modifier.tint(Color(type.border)).margin(sizes.gap).size(16.dp, 16.dp)
            }
            Text(title) { modifier.alignY(AlignmentY.Center) }
            Image(loadImage("icons/table_${type.icon}.png")) {
                modifier.tint(Color(type.border)).margin(sizes.gap).size(16.dp, 16.dp)
            }
        }
        divide(Color(type.border))
        body()
        br()
    }
}

fun UiScope.loadImage(path: String) = remember {
    Texture2d {
        Assets.loadImage2d(
            "hollowengine:docs/$path",
            TextureProps(defaultSamplerSettings = SamplerSettings().nearest())
        ).getOrThrow()
    }
}