package ru.hollowhorizon.hollowengine.client.docs.pages

import imgui.ImGui
import imgui.flag.ImGuiStyleVar
import ru.hollowhorizon.hc.client.imgui.Graphics.entity
import ru.hollowhorizon.hc.client.models.internal.Transform
import ru.hollowhorizon.hc.client.models.internal.animations.AnimationType
import ru.hollowhorizon.hc.client.models.internal.manager.AnimatedEntityCapability
import ru.hollowhorizon.hc.client.utils.get
import ru.hollowhorizon.hc.client.utils.mc
import ru.hollowhorizon.hc.common.events.SubscribeEvent
import ru.hollowhorizon.hc.common.events.tick.TickEvent
import ru.hollowhorizon.hollowengine.client.docs.DocsPage
import ru.hollowhorizon.hollowengine.client.docs.DocsRenderer
import ru.hollowhorizon.hollowengine.client.docs.DocsUtils.text
import ru.hollowhorizon.hollowengine.common.entities.NPCEntity

const val a = "0_welcome"

var ticks: Int = 0

@DocsPage(a)
fun DocsRenderer.heWelcome() {
  text("Добро пожаловать", 70, true, true)
  ImGui.newLine()
  text("на документацию по HollowEngine!", 50, true, true)
  ImGui.newLine()
  ImGui.separator()

  ImGui.setCursorPosX(ImGui.getWindowSizeX() / 2f - (ImGui.getWindowSize().x * 0.75f) / 2f)
  ImGui.pushStyleVar(ImGuiStyleVar.WindowRounding, 16f)
  ImGui.beginChild("title", ImGui.getWindowSize().x * 0.75f, 512f)
    val hollow = NPCEntity(mc.level ?: error("Level ${mc.level} not found!")).apply {
      val cap = this[AnimatedEntityCapability::class.java]
      cap.model = "hollowengine:models/entity/player_model_slim.gltf"
      cap.textures["models/entity/player_model_slim/unnamed_texture_0"] = "hollowengine:docs/skins/hollowhorizon.png"
      cap.transform = Transform(rY = 20f)
      cap.animations[AnimationType.IDLE] = "dance2"
      this.tickCount = ticks
    }
    val bendy = NPCEntity(mc.level ?: error("Level ${mc.level} not found!")).apply {
      val cap = this[AnimatedEntityCapability::class.java]
      cap.model = "hollowengine:models/entity/player_model.gltf"
      cap.textures["models/entity/player_model/unnamed_texture_0"] = "hollowengine:docs/skins/bendy659.png"
      cap.transform = Transform(rY = -20f)
      cap.animations[AnimationType.IDLE] = "dance2"
      this.tickCount = ticks
    }

    val center = (ImGui.getWindowWidth() / 2f - 512f / 2f)
    ImGui.setCursorPos(center + 256f, -16f)
    entity(hollow, 512f, 512f, rotation = false, offsetY = 448f, scale = 1.5f )
    ImGui.setCursorPos(center - 256f, -16f)
    entity(bendy, 512f, 512f, rotation = false, offsetY = 448f, scale = 1.5f )
  ImGui.endChild()
  ImGui.popStyleVar()
}

/*
 * Уважаемый `TheHollowHorizon`. Давайте вы не будете меня за такой костыль *гладить*ю Прошу вас, это единственно до чего я, *тупой*, смог додуматься.
 * Гражданин `Uertyk_`. Если вы каким-то боком прочитали это. то прекратите читать это, т.к. это адресовано даже не вам (я про сообщение выше).
 * ----
 * С желанием ещё пожить: `_BENDY659_`
*/
@SubscribeEvent
fun onTick(event: TickEvent.Client) {
  if(event.minecraft.player != null) ticks++
}