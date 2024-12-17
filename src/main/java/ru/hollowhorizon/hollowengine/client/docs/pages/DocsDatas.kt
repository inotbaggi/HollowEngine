package ru.hollowhorizon.hollowengine.client.docs.pages

import ru.hollowhorizon.hc.client.handlers.TickHandler
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

var ticks: Int = TickHandler.currentTicks