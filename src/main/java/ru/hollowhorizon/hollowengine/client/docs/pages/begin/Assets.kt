package ru.hollowhorizon.hollowengine.client.docs.pages.begin

import imgui.ImGui
import imgui.ImGui.*
import imgui.type.ImBoolean
//? if fabric {
import net.fabricmc.loader.api.FabricLoader
//?} else forge || neoforge {
/*import net.minecraftforge.fml.ModList
*///?}
import ru.hollowhorizon.hc.client.imgui.Graphics
import ru.hollowhorizon.hollowengine.client.docs.DocsPage
import ru.hollowhorizon.hollowengine.client.docs.DocsRenderer
import ru.hollowhorizon.hollowengine.client.docs.DocsUtils
import ru.hollowhorizon.hollowengine.client.docs.DocsUtils.accentText
import ru.hollowhorizon.hollowengine.client.docs.DocsUtils.code
import ru.hollowhorizon.hollowengine.client.docs.DocsUtils.hiddenButton
import ru.hollowhorizon.hollowengine.client.docs.DocsUtils.table
import ru.hollowhorizon.hollowengine.client.docs.DocsUtils.tablice
import ru.hollowhorizon.hollowengine.client.docs.DocsUtils.text

const val begin_assets = "$begin.assets"

val hide0 = ImBoolean(true)
val hide1 = ImBoolean(true)
val hide2 = ImBoolean(true)
val hide3 = ImBoolean(true)

@DocsPage(begin_assets)
fun DocsRenderer.assets() {
  text("То самое месте, где вы можете хранить свои ресурсы чтобы в последующем - использовать их.")
  newLine()
  text("Чтобы начать хранить ресурсы - просто создайте папку \"assets\". Работает данная папка как ресурс паки. Т.е. всё что в ней помещается - переходит во внутрь игры.")
  newLine()
  text("Но к таким ресурсам просто так не обратишься, как: \"assets/my_mod_id/models/table.gltf\" - нет, Это будет - грубой ошибкой. Так что нужно сначала разораться \"Как правильно обращаться к ресурсам\".")

  newLine()
  separator()
  newLine()

  hiddenButton("О `ResourceLocation`", "", hide0) {
    table("ResourceLocation", DocsUtils.TableType.TIP, 1024f + 512f) {
      text("Кто такой этот ваш `ResourceLocation`, или же: Как правильно обращаться к ресурсам.")

      newLine()

      table("Стоит отметить", DocsUtils.TableType.WARN, 512f + 128f + 64f + 16f) {
        text("Обозвать как угодно файлы и папки - вам не удастся. У `ResourceLocation` есть для этого строгие правила:")
        text(
          "> Допустимы все буквы нижнего регистра и только латинский алфавит (т.е. от a-z) [a-z]",
          textAlign = DocsUtils.TextAlign.LEFT
        )
        text("> Допустимы все цифры от 0 до 9 [0-9]", textAlign = DocsUtils.TextAlign.LEFT)
        text("> Допустимы только эти символы: `_` и `-` [_, -].", textAlign = DocsUtils.TextAlign.LEFT)

        newLine()

        text("Всегда когда файлы обозваны неправильно - вылезает ошибка где написано `[a-z, 0-9, _, -]`, это и есть то же, что и было описано выше.")
      }

      newLine()

      text("Чтобы получить тот, или оной ресурс, который находится внутри игры - нужно использовать систему 'ResourceLocation`, который в свою очередь вызывается следующим способом:")
      text("`mod_id:path/to/file.format`")
      text("Освежим память:")
      text(
        "- `mod_id` - это Уникальный идентификатор мода. У всех модов он совершенно разный. Как пример в следующей таблице:",
        textAlign = DocsUtils.TextAlign.LEFT
      )

      Graphics.withFontSize(24) {
        tablice("mod-name-id", modListNameAndId(), arrayOf(getWindowSizeX(), 256f))
      }

      text(
        "- `path/to/file - это путь к нужному ресурсу, относительно от мода, в котором он находится.",
        textAlign = DocsUtils.TextAlign.LEFT
      )
      text("- `format` - это расширение требуемого файла.", textAlign = DocsUtils.TextAlign.LEFT)

      newLine()

      text("Приведём пример:", textAlign = DocsUtils.TextAlign.LEFT)
      text("Предположим, что у нас есть мод `hollowengine`, где есть модель которая расположена по пути:")
      accentText("assets/hollowengine/models/item/apple3d.gltf`")
      text("внутри мода.", textAlign = DocsUtils.TextAlign.LEFT)
      text(
        "Теперь, чтобы получить данный ресурс, в начале нужно указать - с какого мода мы запрашиваем ресурс по его `mod_id`. В нашем случае, данный мод - HollowEngine, где его `mod_id` - это `hollowengine`.",
        textAlign = DocsUtils.TextAlign.LEFT
      )
      text(
        "Дальше мы указывает просто путь относительно папки, которая названа `mod_id`. То есть, мы указали путь `hollowengine:models/item/apple3d.gltf`.",
        textAlign = DocsUtils.TextAlign.LEFT
      )
    }
  }

  separator()

  text("Поддерживаемые форматы файлов", 40)
  newLine()

  tablice(
    "support-file",
    arrayOf(
      arrayOf("Название", "Расширение"),
      arrayOf("Изображение (Текстура)", ".png, .gif"),
      arrayOf("Модели", ".gltf, .glb"),
      arrayOf("Звук", ".ogg")
    ),
    arrayOf(ImGui.getWindowSizeX() - 32f, 256f)
  )

  separator()
  newLine()


}

private fun modListNameAndId(): Array<Array<String>> {
  val modNameAndId =
    mutableListOf(
      arrayOf("Имя мода", "Его `mod_id`")
    )
  //? if fabric {
  for(mod in FabricLoader.getInstance().allMods) {
    modNameAndId += arrayOf(mod.metadata.name, mod.metadata.id)
  }
  //?} else forge || neoforge {
  /*for(mdo in net.minecraftforge.fml.ModList) {
    modNameAndId += arrayOf(mod.displayName, mod.modId)
  }
  *///?}
  return modNameAndId.toTypedArray()
}