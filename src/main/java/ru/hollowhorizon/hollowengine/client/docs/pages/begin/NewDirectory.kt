package ru.hollowhorizon.hollowengine.client.docs.pages.begin

import imgui.ImGui
import imgui.type.ImInt
import net.minecraft.CrashReport
import net.minecraft.client.Minecraft
import ru.hollowhorizon.hc.client.imgui.Graphics
import ru.hollowhorizon.hc.client.models.internal.Transform
import ru.hollowhorizon.hc.client.models.internal.animations.AnimationType
import ru.hollowhorizon.hc.client.models.internal.manager.AnimatedEntityCapability
import ru.hollowhorizon.hc.client.utils.get
import ru.hollowhorizon.hc.client.utils.literal
import ru.hollowhorizon.hollowengine.HollowEngine.MODID
import ru.hollowhorizon.hollowengine.client.docs.DocsPage
import ru.hollowhorizon.hollowengine.client.docs.DocsRenderer
import ru.hollowhorizon.hollowengine.client.docs.DocsUtils
import ru.hollowhorizon.hollowengine.client.docs.DocsUtils.text
import ru.hollowhorizon.hollowengine.client.docs.DocsUtils.button
import ru.hollowhorizon.hollowengine.client.docs.DocsUtils.table
import ru.hollowhorizon.hollowengine.client.docs.pages.ticks
import ru.hollowhorizon.hollowengine.client.gui.scripting.sendToast
import ru.hollowhorizon.hollowengine.common.entities.NPCEntity
import ru.hollowhorizon.hollowengine.common.files.DirectoryManager
import ru.hollowhorizon.hollowengine.common.scripting.story.functions.npcs.transform
import java.lang.RuntimeException
import kotlin.io.path.pathString

const val begin_newDirectory = "$begin.new_directory"

val dirsList = arrayOf(
  "select_directory",
  "assets",
  "camera",
  "npcs",
  "replays",
  "scripts",
  "storyteller_world"
)
val dirsId = ImInt(0)

val instance = Minecraft.getInstance()
val specialUsers = arrayOf(
  "HollowHorizon", "TheHollowHorizon",
  "AlgorithmLX",
  "_BENDY659_"
)

val icon3DModel by lazy {
  NPCEntity(instance.level ?: error("Level ${instance.level} not found!")).apply {
    val cap = this[AnimatedEntityCapability::class.java]
    cap.model = "$MODID:docs/icons/dirs/3d_icon_model.gltf"
    transform = Transform(
      tY = 1f,
      sX = 1.5f, sY = 1.5f, sZ = 1.5f
    )
  }
}

@DocsPage(begin_newDirectory)
fun DocsRenderer.newDirectory() {
  text("При самом первом запуске мода, в корне вашей сборки появилась новая папка под названием \"hollowengine\".")
  ImGui.newLine()
  ImGui.setCursorPosX(ImGui.getWindowSizeX() / 2 - 312f / 2)
  button("Показать папку", "Открывает папку \"hollowengine\" в вашей сборке", 312f) { DocsUtils.openDir(DirectoryManager.HOLLOW_ENGINE.pathString) }
  ImGui.newLine()
  text("Внутри неё могут находится любые папки, но не все они будут *рабочими*.")
  ImGui.newLine()
  text("Но есть специальные папки, которые несут функционал. Ниже есть описание этих папок, что и для чего они нужны.")

  ImGui.newLine()
  ImGui.separator()
  ImGui.newLine()

  table("Про папки", DocsUtils.TableType.NOTE, 1024f - 128f) {
    ImGui.setCursorPosX(ImGui.getWindowSizeX() / 2f - (512f + 128f) / 2f)
    ImGui.pushItemWidth(512f)
    ImGui.combo("Описание папок", dirsId, dirsList)
    ImGui.popItemWidth()
    ImGui.newLine()
    table("Папка ${dirsList[dirsId.get()]}", DocsUtils.TableType.INFO, 512f + 128f) {
      ImGui.newLine()
      when (dirsId.get()) {
        0 -> {
          // NOT DIRECTORY SELECTED //
          text("Ничего?")
          ImGui.newLine()
          text("Просто выбери что-то другое из списка.")
        }
        1 -> {
          // ASSETS DIRECTORY //
          text("В неё можно помещать текстуры, звуки, модели и другие ресурсы, которые нужно вам и игре.")
          ImGui.newLine()
          text("Работает данная папка как обычные ресурс паки. Если что-то изменили в ней - нужно перезагрузить ресурсы на сочетание клавиш:")
          ImGui.newLine()
          ImGui.setCursorPosX(ImGui.getWindowSizeX() / 2 - 720f / 2)
          button("[F3] + [T]", "Такое сочетание клавиш перезагрузит ресурсы игры и перегенерирует некоторые данные.", 720f) {
            Minecraft.getInstance().reloadResourcePacks()
          }
          ImGui.setCursorPosX(ImGui.getWindowSizeX() / 2 - 720f / 2)
          button("[F3] + [T]*", "Такое сочетание клавиш перезагрузит ресурсы игры и \"перегенерирует\" некоторые данные.", 720f) {
            val pPlayer = instance.player
            if (pPlayer != null) {
              val playerName = pPlayer.name.string
              if (playerName == "uertyk_" || playerName == "Uertyk_") {
                val bestLinks = arrayOf(
                  "https://www.youtube.com/watch?v=5ftZWTceOPQ",
                  "https://www.youtube.com/watch?v=dQw4w9WgXcQ",
                  "https://www.youtube.com/clip/UgkxFPcy6fyWMHhTQFPuDHsNVrzQPR6C3i91",
                  "https://www.youtube.com/clip/Ugkx3BB-K7krmNz_2o7GwVpzBP8etXz0dHMB",
                  "https://www.youtube.com/clip/UgkxgDdL5ItLxE8fneUO9YQwSe1_tmJ-5kER",
                  "https://www.youtube.com/clip/UgkxD1l6BjJGEGyxYpTGNy8BBiL19DGaBSXx",
                  "https://www.youtube.com/clip/UgkxDP4Le5niDxwQBQQ3zFHHkxYVODemi70b",
                  "https://www.youtube.com/clip/UgkxRyfJJp8446BJmOZyXAuOmwhBHnRnKaZR",
                  "https://www.youtube.com/clip/UgkxtFlDR3Bd84xmaOBH-ttXIjzCMAr-iKx2",
                  "https://www.youtube.com/clip/UgkxdyBXr40tn7vdUClxHWIgii96bmPocc5M",
                  "https://www.youtube.com/clip/UgkxhxD0j44HsD6v8NYOR36jv3DvTNpQc8gl",
                  "https://www.youtube.com/clip/UgkxqCJnjKfZoBqYwrtVKP_kZmMKmxktLUXB",
                  "https://www.youtube.com/clip/UgkxJnKk-VPb1TRZpKQf4BOjHuXVj4Jks9F-",
                  "https://www.youtube.com/clip/Ugkx19AprTfxctf3fJwJNHGpuPPYxswSYgsg",
                  "https://www.youtube.com/clip/UgkxrjKI2KDQKAONgGHF9VP83szARVNyietB",
                  "https://www.youtube.com/clip/UgkxDK57CSmo4mlvoP4PVgpEyNBV4JZLqhMO",
                  "https://www.youtube.com/clip/Ugkx0ZwO7O1C5u1EiAwRiN-YEMi6L7CHgYB5",
                  "https://www.youtube.com/clip/Ugkx0q7exaQssQuKtWAxhOa2UEUpPN6dBJbx"
                )
                DocsUtils.openUrl(bestLinks.random())
                instance.delayCrash(CrashReport("The link has opened. Did you like it?)", RuntimeException("Did you suck?)))")))
              } else if (playerName in specialUsers) {
                pPlayer.sendToast("Простите $playerName, но я не могу...".literal)
              } else {
                instance.delayCrash(CrashReport("playerName goof", RuntimeException("Well, anyone can make a mistake with a button, even you, $playerName.")))
              }
            }
          }
          ImGui.newLine()
          text("По умолчанию её нет, так что вам нужно создать её самостоятельно.")

          ImGui.newLine()
          ImGui.separator()
          ImGui.newLine()

          ImGui.setCursorPosX(ImGui.getWindowSizeX() / 2f - 512f / 2f)
          button(
            "Открыть папку \"assets\"",
            "Открывает папку `assets` в директории мода. На случай если она отсутствует - создаёт её",
            512f,
            buttonType = DocsUtils.ButtonType.DIR
          ) {
            DocsUtils.openDir(DirectoryManager.HOLLOW_ENGINE.resolve("assets").pathString)
          }
          ImGui.setCursorPos(ImGui.getWindowSizeX() / 2f - (512f + 128f) / 2f, -8f)
          Graphics.entity(
            icon3DModel,
            128f, 128f,
            red = 224f, green = 168f, blue = 36f,
            rotation = false,
            offsetY = 8f
          )
          ImGui.setCursorPos((ImGui.getWindowSizeX() / 2f - (512f + 128f) / 2f) + 512f - 32f, -8f)
          Graphics.entity(
            icon3DModel,
            128f, 128f,
            red = 224f, green = 168f, blue = 36f,
            rotation = false,
            offsetY = 8f
          )
          icon3DModel.apply {
            tickCount = ticks
            val cap = this[AnimatedEntityCapability::class.java]
            cap.textures["docs/icons/dirs/3d_icon_model/unnamed_texture_0"] = "$MODID:docs/icons/dirs/assets.png"
            cap.animations[AnimationType.IDLE] = "idle"
          }
        }
        2 -> {
          // CAMERA DIRECTORY //
          text("В ней хранятся данные о передвижении камеры для ваших кат-сцен.")
          ImGui.newLine()
          text("Она создаётся как только вы сохранили первый путь для передвижения камеры")

          ImGui.newLine()
          ImGui.separator()
          ImGui.newLine()

          ImGui.setCursorPosX(ImGui.getWindowSizeX() / 2f - 512f / 2f)
          button(
            "Открыть папку \"camera\"",
            "Открывает папку `camera` в директории мода. На случай если она отсутствует - создаёт её",
            512f,
            buttonType = DocsUtils.ButtonType.DIR
          ) {
            DocsUtils.openDir(DirectoryManager.HOLLOW_ENGINE.resolve("camera").pathString)
          }
          ImGui.setCursorPos(ImGui.getWindowSizeX() / 2f - (512f + 128f) / 2f, -8f)
          Graphics.entity(
            icon3DModel,
            128f, 128f,
            red = 224f, green = 168f, blue = 36f,
            rotation = false,
            offsetY = 8f
          )
          ImGui.setCursorPos((ImGui.getWindowSizeX() / 2f - (512f + 128f) / 2f) + 512f - 32f, -8f)
          Graphics.entity(
            icon3DModel,
            128f, 128f,
            red = 224f, green = 168f, blue = 36f,
            rotation = false,
            offsetY = 8f
          )
          icon3DModel.apply {
            tickCount = ticks
            val cap = this[AnimatedEntityCapability::class.java]
            cap.textures["docs/icons/dirs/3d_icon_model/unnamed_texture_0"] = "$MODID:docs/icons/dirs/camera.png"
            cap.animations[AnimationType.IDLE] = "idle"
          }
        }
        3 -> {
          // NPCs DIRECTORY //
          text("В ней хранится информация о всех персонажах в игре.")
          ImGui.newLine()
          text("ИНФОРМАЦИЯ О УСЛОВИИ СОЗДАНИЯ ДАННОЙ ДИРЕКТОРИИ НЕ НАЙДЕНА. ИГНОРИРОВАТЬ.")

          ImGui.newLine()
          ImGui.separator()
          ImGui.newLine()

          ImGui.setCursorPosX(ImGui.getWindowSizeX() / 2f - 512f / 2f)
          button(
            "Открыть папку \"npcs\"",
            "Открывает папку `npcs` в директории мода. На случай если она отсутствует - создаёт её",
            512f,
            buttonType = DocsUtils.ButtonType.DIR
          ) {
            DocsUtils.openDir(DirectoryManager.HOLLOW_ENGINE.resolve("npcs").pathString)
          }
          ImGui.setCursorPos(ImGui.getWindowSizeX() / 2f - (512f + 128f) / 2f, -8f)
          Graphics.entity(
            icon3DModel,
            128f, 128f,
            red = 224f, green = 168f, blue = 36f,
            rotation = false,
            offsetY = 8f
          )
          ImGui.setCursorPos((ImGui.getWindowSizeX() / 2f - (512f + 128f) / 2f) + 512f - 32f, -8f)
          Graphics.entity(
            icon3DModel,
            128f, 128f,
            red = 224f, green = 168f, blue = 36f,
            rotation = false,
            offsetY = 8f
          )
          icon3DModel.apply {
            tickCount = ticks
            val cap = this[AnimatedEntityCapability::class.java]
            cap.textures["docs/icons/dirs/3d_icon_model/unnamed_texture_0"] = "$MODID:docs/icons/dirs/npcs.png"
            cap.animations[AnimationType.IDLE] = "idle"
          }
        }
        4 -> {
          // REPLAYS DIRECTORY //
          text("В ней хранятся все записи ваших движений и действий.")
          ImGui.newLine()
          text("Появляется как только вы сохранили свою первую запись.")

          ImGui.newLine()
          ImGui.separator()
          ImGui.newLine()

          ImGui.setCursorPosX(ImGui.getWindowSizeX() / 2f - 512f / 2f)
          button(
            "Открыть папку \"replays\"",
            "Открывает папку `replays` в директории мода. На случай если она отсутствует - создаёт её",
            512f,
            buttonType = DocsUtils.ButtonType.DIR
          ) {
            DocsUtils.openDir(DirectoryManager.HOLLOW_ENGINE.resolve("replays").pathString)
          }
          ImGui.setCursorPos(ImGui.getWindowSizeX() / 2f - (512f + 128f) / 2f, -8f)
          Graphics.entity(
            icon3DModel,
            128f, 128f,
            red = 224f, green = 168f, blue = 36f,
            rotation = false,
            offsetY = 8f
          )
          ImGui.setCursorPos((ImGui.getWindowSizeX() / 2f - (512f + 128f) / 2f) + 512f - 32f, -8f)
          Graphics.entity(
            icon3DModel,
            128f, 128f,
            red = 224f, green = 168f, blue = 36f,
            rotation = false,
            offsetY = 8f
          )
          icon3DModel.apply {
            tickCount = ticks
            val cap = this[AnimatedEntityCapability::class.java]
            cap.textures["docs/icons/dirs/3d_icon_model/unnamed_texture_0"] = "$MODID:docs/icons/dirs/replays.png"
            cap.animations[AnimationType.IDLE] = "roll"
          }
        }
        5 -> {
          // SCRIPTS DIRECTORY //
          text("В ней хранятся все скрипты, которые вы создали.")
          ImGui.newLine()
          text("По умолчанию её нет, так что вам нужно создать её самостоятельно.")

          ImGui.newLine()
          ImGui.separator()
          ImGui.newLine()

          ImGui.setCursorPosX(ImGui.getWindowSizeX() / 2f - 512f / 2f)
          button(
            "Открыть папку \"scripts\"",
            "Открывает папку `scripts` в директории мода. На случай если она отсутствует - создаёт её",
            512f,
            buttonType = DocsUtils.ButtonType.DIR
          ) {
            DocsUtils.openDir(DirectoryManager.HOLLOW_ENGINE.resolve("scripts").pathString)
          }
          ImGui.setCursorPos(ImGui.getWindowSizeX() / 2f - (512f + 128f) / 2f, -8f)
          Graphics.entity(
            icon3DModel,
            128f, 128f,
            red = 224f, green = 168f, blue = 36f,
            rotation = false,
            offsetY = 8f
          )
          ImGui.setCursorPos((ImGui.getWindowSizeX() / 2f - (512f + 128f) / 2f) + 512f - 32f, -8f)
          Graphics.entity(
            icon3DModel,
            128f, 128f,
            red = 224f, green = 168f, blue = 36f,
            rotation = false,
            offsetY = 8f
          )
          icon3DModel.apply {
            tickCount = ticks
            val cap = this[AnimatedEntityCapability::class.java]
            cap.textures["docs/icons/dirs/3d_icon_model/unnamed_texture_0"] = "$MODID:docs/icons/dirs/scripts.png"
            cap.animations[AnimationType.IDLE] = "idle"
          }
        }
        6 -> {
          // STORYTELLER WORLD DIRECTORY //
          text("В ней хранятся все скрипты, которые вы создали.")
          ImGui.newLine()
          text("Создаётся автоматически.")

          ImGui.newLine()
          ImGui.separator()
          ImGui.newLine()

          ImGui.setCursorPosX(ImGui.getWindowSizeX() / 2f - 512f / 2f)
          button(
            "Открыть папку \"scripts\"",
            "Открывает папку `scripts` в директории мода. На случай если она отсутствует - создаёт её",
            512f,
            buttonType = DocsUtils.ButtonType.DIR
          ) {
            DocsUtils.openDir(DirectoryManager.HOLLOW_ENGINE.resolve("scripts").pathString)
          }
          ImGui.setCursorPos(ImGui.getWindowSizeX() / 2f - (512f + 128f) / 2f - 128f, -8f)
          Graphics.entity(
            icon3DModel,
            128f, 128f,
            red = 224f, green = 168f, blue = 36f,
            rotation = false,
            offsetY = 8f
          )
          ImGui.setCursorPos((ImGui.getWindowSizeX() / 2f - (512f + 128f) / 2f) + 512f + 128f - 24f, -8f)
          Graphics.entity(
            icon3DModel,
            128f, 128f,
            red = 224f, green = 168f, blue = 36f,
            rotation = false,
            offsetY = 8f
          )
          icon3DModel.apply {
            tickCount = ticks
            val cap = this[AnimatedEntityCapability::class.java]
            cap.textures["docs/icons/dirs/3d_icon_model/unnamed_texture_0"] = "$MODID:docs/icons/dirs/storyteller_world.png"
            cap.animations[AnimationType.IDLE] = "fair"
          }
        }
        else -> {
          text("Ошибка")
          ImGui.newLine()
          text("Выберите другой раздел!")
        }
      }
    }
  }
}