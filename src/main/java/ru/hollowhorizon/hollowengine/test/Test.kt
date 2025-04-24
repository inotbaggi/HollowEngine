package ru.hollowhorizon.hollowengine.test

import net.minecraft.core.BlockPos
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.Vec3
import ru.hollowhorizon.hc.common.registry.HollowRegistry
import ru.hollowhorizon.hollowengine.HollowEngine
import ru.hollowhorizon.hollowengine.common.scripting.story.functions.npcs.npc

object TestNpcCreatorSpawner: HollowRegistry(HollowEngine.MODID) {
  val TNCS_Block by register("test_npc_creator_block_spawner_xddds") {
    object: Block(Properties.of()) {
      override fun use(
        state: BlockState,
        level: Level,
        pos: BlockPos,
        player: Player,
        hand: InteractionHand,
        hit: BlockHitResult
      ): InteractionResult {
        val modelType = "${HollowEngine.MODID}:test/test_${if(player.isCrouching) "glb.glb" else "gltf.gltf"}"

        npc(
          pos = Vec3(pos.x+0.5, pos.y+1.0, pos.z+0.5),
          name = "Testing model Bebebee :P | ${if(player.isCrouching) "GLTF" else "GLB"}",
          model = modelType
        )

        return super.use(state, level, pos, player, hand, hit)
      }
    }
  }
  val TNCS_Item by register("test_npc_creator_block_spawner_xddds_item") {
    object: BlockItem(TNCS_Block, Item.Properties()) {}
  }
}