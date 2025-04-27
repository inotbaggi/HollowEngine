package ru.hollowhorizon.hollowengine.docs

import de.fabmax.kool.Assets
import de.fabmax.kool.loadBlob
import de.fabmax.kool.loadImage2d
import de.fabmax.kool.math.Vec2f
import de.fabmax.kool.modules.ui2.*
import de.fabmax.kool.pipeline.MipMapping
import de.fabmax.kool.pipeline.SamplerSettings
import de.fabmax.kool.pipeline.TexFormat
import de.fabmax.kool.pipeline.Texture2d
import de.fabmax.kool.util.*
import de.fabmax.kool.util.MsdfFont.Companion.ITALIC_STD
import de.fabmax.kool.util.MsdfFont.Companion.WEIGHT_EXTRA_BOLD
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

enum class Header(val fontSize: Float) {
    H1(30f),
    H2(26f),
    H3(22f),
    H4(18f),
    H5(14f),
    H6(10f)
}

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
        weight = if (bold) WEIGHT_EXTRA_BOLD else 0f,
        italic = if (italic) ITALIC_STD else 0f
    )

    modifier
        .font(font)
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
    NOTE("878787", "D4D4D4", "note"),
    TIP("438C34", "73D160", "tip"),
    INFO("3C86a3", "5FAFCF", "info"),
    WARN("8a6932", "E8C268", "warn"),
    ERR("913131", "E84646", "err"),
    SPOILER("6C348C", "A24FD1", "spoiler")
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
            Text(title) { modifier.alignY(AlignmentY.Center).font(MsdfFont(HACK_FONT, sizePts = Header.H3.fontSize)) }
            Image(loadImage("icons/table_${type.icon}.png")) { modifier.tint(Color(type.border)).margin(sizes.gap).size(sizes.gap * 2, sizes.gap * 2) }
        }
        divide(Color(type.border))
        body()
    }
}

fun UiScope.loadImage(path: String): Texture2d = remember {
    Texture2d(TexFormat.RGBA, MipMapping.Off, SamplerSettings()) {
        Assets.loadImage2d("hollowengine:docs/$path").getOrThrow()
    }
}

/**
 * Типы кнопок.
 * @property DEFAULT - Обычная кнопка.
 * @property LINK - Кнопка, которая указывает что она является ссылкой,
 * @property SPOILER - Кнопка, которая указывает что это кнопка-спойлер.
 */
enum class ButtonType(val basic: String, val hover: String) {
    DEFAULT("D4AF37", "B68F2D"),
    LINK("5DADE2", "3498DB"),
    SPOILER("793EDE", "9A5EFF")
}

/**
 * Кнопка.
 * @param text Текст на кнопке.
 * @param textSize Размер текста на кнопке.
 * @param textColor Цвет текста на кнопке. По умолчанию - Чёрный.
 * @param type Тип кнопки. Определяет её цвет. Типы: [ButtonType]
 * @param buttonColor Цвет кнопки. Порядок: 0 - Обычная, 1 - Наведённая.
 * @param action Логика при нажатии на кнопку.
 */
fun UiScope.button(
    text: String = "",
    textSize: Header = Header.H3,
    textColor: Color = Color.BLACK,
    type: ButtonType? = ButtonType.DEFAULT,
    buttonColor: List<Color>? = null, // 0 = Basic | 1 = Hover
    action: (() -> Unit) = {}
) = Button(text) {
    modifier
        .font(MsdfFont(HACK_FONT, sizePts = textSize.fontSize))
        .textColor(textColor)
        .margin(4.dp)
        .colors(
            buttonColor = buttonColor?.get(0) ?: Color(type?.basic ?: ButtonType.DEFAULT.basic),
            buttonHoverColor = buttonColor?.get(1) ?: Color(type?.hover ?: ButtonType.DEFAULT.hover)
        )
        .onClick { action() }
}
expect fun openUrl(url: String)

fun UiScope.spoiler(button: String = "Spoiler", hiddenVar: MutableStateValue<Boolean>, hidendCotent: UiScope.() -> Unit) {
    if(!hiddenVar.value)
        Box { modifier.align(AlignmentX.Center, AlignmentY.Center)
            button(button, type = ButtonType.SPOILER) { hiddenVar.value = true }
        }
    else {
        table(button, TableType.SPOILER) {
            hidendCotent()
            divbr(Color(TableType.SPOILER.border))
            Box { modifier.align(AlignmentX.Center, AlignmentY.Bottom)
                button("Скрыть", type = ButtonType.SPOILER) { hiddenVar.value = false }
            }
        }
    }
}