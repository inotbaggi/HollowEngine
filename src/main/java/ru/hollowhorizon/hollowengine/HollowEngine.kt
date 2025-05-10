package ru.hollowhorizon.hollowengine

import com.mojang.blaze3d.systems.RenderSystem
import de.fabmax.kool.util.launchOnMainThread
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.minecraft.Util
import org.apache.logging.log4j.LogManager
import ru.hollowhorizon.hc.api.Init
import ru.hollowhorizon.hc.client.kool.KoolManager
import ru.hollowhorizon.hc.common.config.HollowConfig
import ru.hollowhorizon.hc.common.config.hollowConfig
import ru.hollowhorizon.hollowengine.client.gui.overlay.BetaWarning
import ru.hollowhorizon.hollowengine.client.gui.overlay.CompilationStatus
import ru.hollowhorizon.hollowengine.common.scripting.core.ScriptingCompiler
import ru.hollowhorizon.hollowengine.common.scripting.core.example.HollowScript
import ru.hollowhorizon.hollowengine.common.scripting.core.setupScripting
import ru.hollowhorizon.hollowengine.common.scripting.events.loadEvents
//? if forge
/*import ru.hollowhorizon.hollowengine.client.render.setupCamera*/

import ru.hollowhorizon.hollowengine.docs.OPEN_URL
import ru.hollowhorizon.hollowengine.docs.loadResources

@Init
object HollowEngine {
    const val MODID = "hollowengine"
    val LOGGER = LogManager.getLogger()
    val config by hollowConfig(::EngineConfig, "hollowengine")

    init {
        launchOnMainThread { loadResources() }

        OPEN_URL = { Util.getPlatform().openUri(it) }

        setupScripting()

        runBlocking {
            ScriptingCompiler.compileText<HollowScript>("ru.hollowhorizon.hc.LOGGER.info(\"Scripting engine loaded!\")")
                .execute()
        }

        LOGGER.info("Initializing Hollow Engine 2.0!")

        loadEvents()

        RenderSystem.recordRenderCall {
            //KoolManager.context.addScene(CompilationStatus.overlay)
            KoolManager.context.addScene(BetaWarning.overlay)
        }

        //? if forge {
        /*setupCamera()
        *///?}
    }
}


@Serializable
class EngineConfig : HollowConfig() {
    @SerialName("ide_config")
    var ideConfig = IDEConfig()

    @SerialName("docs_config")
    var docsDebug = DocsConfig()

    @SerialName("is_beta")
    var beta = BetaConfig()

    @Serializable
    class IDEConfig {
        var tabSpace = 4
        var fontSize = 30
        var enableSound = false
    }

    @Serializable
    class DocsConfig {
        var debugPages = false
    }

    @Serializable
    class BetaConfig {
        var notifiedIsNotStableBeta = false
    }
}