package ru.hollowhorizon.hollowengine.docs

import kotlin.random.Random

object ChildrenHooks {
    annotation class SubscribeEvent
    class ChildEvents {
        class ChildSpawn { lateinit var child: Children }
        class ChildDestroy { lateinit var child: Children }
    }
    class DayEvents {
        class NextDay { lateinit var context: DayContext }

        class DayContext
    }

    val list: MutableList<Children> = mutableListOf()

    @SubscribeEvent
    fun onChildSpawn(event: ChildEvents.ChildSpawn) {
        if(Random.nextBoolean())
            event.child.kaBoom()
        else
            list += event.child
    }

    @SubscribeEvent
    fun onNextDay(event: DayEvents.NextDay) { list.forEach { it.destroy() } }

    open class Children {
        open val name: String = ""
        open val date: String = "2025.01.01/00:00"

        fun register(name: String, date: String) = println()

        fun destroy() = println("Ребёнок \"$name\" был уничтожен :D (by HollowHorizon)")
        fun kaBoom() = println("Ребёнок \"$name\" был взорван :D (by _BENDY659_)")
    }
}