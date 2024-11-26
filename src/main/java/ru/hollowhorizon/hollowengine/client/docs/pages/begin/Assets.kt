package ru.hollowhorizon.hollowengine.client.docs.pages.begin

import imgui.ImColor
import imgui.ImGui
import imgui.flag.ImGuiTableBgTarget
import imgui.ImGui.separator
import imgui.ImGui.sameLine
import imgui.ImGui.newLine
import imgui.flag.ImGuiStyleVar
import imgui.flag.ImGuiTableFlags
import ru.hollowhorizon.hc.client.imgui.Graphics
import ru.hollowhorizon.hollowengine.client.docs.DocsPage
import ru.hollowhorizon.hollowengine.client.docs.DocsRenderer
import ru.hollowhorizon.hollowengine.client.docs.DocsUtils
import ru.hollowhorizon.hollowengine.client.docs.DocsUtils.accentText
import ru.hollowhorizon.hollowengine.client.docs.DocsUtils.table
import ru.hollowhorizon.hollowengine.client.docs.DocsUtils.tablice
import ru.hollowhorizon.hollowengine.client.docs.DocsUtils.text

const val begin_assets = "$begin.assets"

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

  table("ResourceLocation", DocsUtils.TableType.TIP, 1024f + 512f) {
    text("Кто такой этот ваш `ResourceLocation`, или же: Как правильно обращаться к ресурсам.")

    newLine()

    table("Стоит отметить", DocsUtils.TableType.WARN, 512f + 128f + 64f + 16f) {
      text("Обозвать как угодно файлы и папки - вам не удастся. У `ResourceLocation` есть для этого строгие правила:")
      text("> Допустимы все буквы нижнего регистра и только латинский алфавит (т.е. от a-z) [a-z]", textAlign = DocsUtils.TextAlign.LEFT)
      text("> Допустимы все цифры от 0 до 9 [0-9]", textAlign = DocsUtils.TextAlign.LEFT)
      text("> Допустимы только эти символы: `_` и `-` [_, -].", textAlign = DocsUtils.TextAlign.LEFT)

      newLine()

      text("Всегда когда файлы обозваны неправильно - вылезает ошибка где написано `[a-z, 0-9, _, -]`, это и есть то же, что и было описано выше.")
    }

    newLine()

    text("Чтобы получить тот, или оной ресурс, который находится внутри игры - нужно использовать систему 'ResourceLocation`, который в свою очередь вызывается следующим способом:")
    text("`mod_id:path/to/file.format`")
    text("Освежим память:")
    text("- `mod_id` - это Уникальный идентификатор мода. У всех модов он совершенно разный. Как пример в следующей таблице:", textAlign = DocsUtils.TextAlign.LEFT)

    Graphics.withFontSize(24) {
      tablice(
        "mod-name-id",
        arrayOf(
          arrayOf("Имя мода", "Его `mod_id`"),
          arrayOf("HollowEngine", "hollowengine"),
          arrayOf("Mekanism", "mekanism"),
          arrayOf("", "")
        ),
        arrayOf()
      )
    }
  }
}