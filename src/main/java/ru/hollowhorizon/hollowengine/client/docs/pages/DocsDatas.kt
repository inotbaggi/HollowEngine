package ru.hollowhorizon.hollowengine.client.docs.pages

import ru.hollowhorizon.hc.client.models.internal.manager.AnimatedEntityCapability
import ru.hollowhorizon.hc.client.utils.get
import ru.hollowhorizon.hc.client.utils.mc
import ru.hollowhorizon.hc.common.events.SubscribeEvent
import ru.hollowhorizon.hc.common.events.tick.TickEvent
import ru.hollowhorizon.hollowengine.common.entities.NPCEntity
/*
/*
 * Уважаемый `TheHollowHorizon`. Давайте вы не будете меня за такой костыль *гладить*ю Прошу вас, это единственно до чего я, *тупой*, смог додуматься.
 * Гражданин `Uertyk_`. Если вы каким-то боком прочитали это. то прекратите читать это, т.к. это адресовано даже не вам (я про сообщение выше).
 * ----
 * С желанием ещё пожить: `_BENDY659_`
*/

val nModel by lazy {
  NPCEntity(mc.level ?: error("Level ${mc.level} not found!")).apply {
    val cap = this[AnimatedEntityCapability::class.java]
    cap.model = "hollowengine:docs/title_model.gltf"
  }
}

enum class DocsPages {
  NONE, TEST_PAGE,
  WELCOME
}

var docsPages = DocsPages.NONE
*/

// Уважаемый, или не очень Bendy659, не изобретайте велосипед, HollowCore уже имеет буквально такой же класс TickHandler, возьмите данные оттуда. А велосипед отдайте Данбату или Уёртику, вдруг он им пригодится?
var ticks: Int = 0
@SubscribeEvent
fun onTick(event: TickEvent.Client) {
  if(event.minecraft.player != null) ticks++
}
