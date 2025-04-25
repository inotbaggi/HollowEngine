package ru.hollowhorizon.hollowengine.docs

import de.fabmax.kool.Assets
import de.fabmax.kool.loadBlob
import de.fabmax.kool.loadImage2d
import de.fabmax.kool.math.Vec2f
import de.fabmax.kool.modules.ui2.*
import de.fabmax.kool.pipeline.*
import de.fabmax.kool.util.*
import de.fabmax.kool.util.MsdfFont.Companion.ITALIC_STD
import de.fabmax.kool.util.MsdfFont.Companion.WEIGHT_LIGHT
import kotlinx.serialization.json.Json
import ru.hollowhorizon.hollowengine.docs.shaders.BlurImageShader

private val json = Json { ignoreUnknownKeys = true }

lateinit var HACK_FONT: MsdfFontData

suspend fun loadResources() {
    val fontInfo = json.decodeFromString<MsdfMeta>(
        Assets.loadBlob("hollowengine:fonts/hack.json").getOrThrow().decodeToString()
    )
    val msdfMap = Texture2d(TexFormat.RGBA, MipMapping.Off, SamplerSettings(), "MsdfFont:${fontInfo.name}") {
        Assets.loadImage2d("hollowengine:fonts/hack.png").getOrThrow()
    }
    HACK_FONT = MsdfFontData(msdfMap, fontInfo)
}

enum class Header(val fontSize: Float) { H1(20f), H2(18f), H3(14f), H4(12f), H5(10f), H6(8f) }

fun UiScope.text(
    text: String,
    header: Header = Header.H4,
    alignmentX: AlignmentX = AlignmentX.Center,
    alignmentY: AlignmentY = AlignmentY.Top,
    bold: Boolean = false,
    italic: Boolean = false,
    margin: Boolean = true,
): UiScope = Text(text) {
    val font = MsdfFont(
        HACK_FONT,
        sizePts = header.fontSize,
        weight = if (bold) WEIGHT_LIGHT else 0f,
        italic = if (italic) ITALIC_STD else 0f
    )

    modifier.font(font)
        .align(alignmentX, alignmentY)
        .textAlignX(alignmentX)
        .margin(if (margin) sizes.gap else 0.dp, sizes.smallGap)
        .width(Grow(1f))
        .isWrapText(true)
}

fun UiScope.divide(color: Color = Color.WHITE) = Box { modifier.size(Grow.Std, sizes.borderWidth).backgroundColor(color).margin(sizes.gap) }
fun UiScope.br() = Box { modifier.size(Grow.Std, 4.dp).margin(sizes.gap) }
fun UiScope.divbr(color: Color = Color.WHITE) { br(); divide(color); br() }

fun UiScope.title(id: String = "no_title", borderBlendPower: Float = 0.1f) = Image(remember {
    Texture2d { Assets.loadImage2d("hollowengine:docs/titles/$id.png").getOrThrow() }
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
                shader.power = borderBlendPower
            }
        }
}

enum class TableType(val bg: String, val border: String, val icon: String) {
    NOTE("878787", "d4d4d4", "note"),
    TIP("438c34", "73d160", "tip"),
    INFO("3c86a3", "5fafcf", "info"),
    WARN("8a6932", "e8c268", "warn"),
    ERR("913131", "e84646", "err"),
    SPOILER("", "", "spoiler")
}

inline fun UiScope.table(title: String, type: TableType, body: UiScope.() -> Unit) {
    Column {
        modifier
            .align(AlignmentX.Center)
            .backgroundColor(Color(type.bg))
            .margin(sizes.largeGap)
            .border(RectBorder(Color(type.border), sizes.borderWidth + 1.dp))
            .width(Grow.Std)

        Row {
            modifier
                .align(AlignmentX.Center, AlignmentY.Top)
                .margin(sizes.gap)

            Image(loadImage("icons/table_${type.icon}.png")) { modifier.tint(Color(type.border)).margin(sizes.gap).size(sizes.gap * 2, sizes.gap * 2) }
            Text(title) { modifier.alignY(AlignmentY.Center).font(MsdfFont(HACK_FONT, sizePts = 14f)) }
            Image(loadImage("icons/table_${type.icon}.png")) { modifier.tint(Color(type.border)).margin(sizes.gap).size(sizes.gap * 2, sizes.gap * 2) }
        }
        divide(Color(type.border))
        body()
    }
}

fun UiScope.loadImage(path: String) = remember {
    Texture2d(TexFormat.RGBA, MipMapping.Off, SamplerSettings()) {
        Assets.loadImage2d("hollowengine:docs/$path").getOrThrow()
    }
}

enum class ButtonType(val basic: String, val hover: String) {
    DEFAULT("D4AF37", "B68F2D"),
    LINK("5DADE2", "3498DB"),
    SPOILER("", "")
}
fun UiScope.button(
    text: String = "",
    type: ButtonType = ButtonType.DEFAULT,
    action: () -> Unit
) = Button(text) {
    modifier
        .font(MsdfFont(HACK_FONT))
        .margin(4.dp)
        .colors(buttonColor = Color(type.basic), buttonHoverColor = Color(type.hover))
        .onClick { action() }
}
expect fun openUrl(url: String)

fun UiScope.spoiler(button: String = "Spoiler", hiddenVar: MutableStateValue<Boolean>, hidendCotent: UiScope.() -> Unit) {
    if(!hiddenVar.value)
        button(button) { hiddenVar.value = true }
    else {
        table(button, TableType.SPOILER) {
            hidendCotent()
            divbr(Color(TableType.SPOILER.border))
            button("Скрыть") { hiddenVar.value = false }
        }
    }
}