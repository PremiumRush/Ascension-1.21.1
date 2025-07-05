package github.premiumrush.ascension.common.event.block;

import github.premiumrush.ascension.common.world.entity.custom.FerrotitaniumGolemEntity;
import github.premiumrush.ascension.common.world.entity.custom.TitaniumGolemEntity;
import github.premiumrush.ascension.common.init.BlockInit;
import github.premiumrush.ascension.common.init.EntityInit;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.level.BlockEvent;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.function.Predicate;

public class GolemSpawnBlockEvent {
    @SubscribeEvent
    public void spawnGolemsEvent(BlockEvent.EntityPlaceEvent event) {
        if(event.getPlacedBlock().is(Blocks.CARVED_PUMPKIN)) {
            this.trySpawnGolems(event.getEntity().level(), event.getPos());
        }
    }

    @Nullable
    private BlockPattern titaniumGolemBase;
    @Nullable
    private BlockPattern titaniumGolemFull;
    @Nullable
    private BlockPattern ferrotitaniumGolemBase;
    @Nullable
    private BlockPattern ferrotitaniumGolemFull;
    private static final Predicate<BlockState> PUMPKINS_PREDICATE;

    public void trySpawnGolems(Level level, BlockPos pos) {
        BlockPattern.BlockPatternMatch blockpattern$blockpatternmatch = this.getOrCreateTitaniumGolemFull().find(level, pos);
        if (blockpattern$blockpatternmatch != null) {
            TitaniumGolemEntity titaniumGolem = (TitaniumGolemEntity) EntityInit.TITANIUM_GOLEM.get().create(level);
            if (titaniumGolem != null) {
                spawnGolemInWorld(level, blockpattern$blockpatternmatch, titaniumGolem, blockpattern$blockpatternmatch.getBlock(1, 2, 0).getPos());
            }
        } else {
            BlockPattern.BlockPatternMatch blockpattern$blockpatternmatch2 = this.getOrCreateFerrotitaniumGolemFull().find(level, pos);
            if (blockpattern$blockpatternmatch2 != null) {
                FerrotitaniumGolemEntity ferrotitaniumGolem = (FerrotitaniumGolemEntity) EntityInit.FERROTITANIUM_GOLEM.get().create(level);
                if (ferrotitaniumGolem != null) {
                    spawnGolemInWorld(level, blockpattern$blockpatternmatch2, ferrotitaniumGolem, blockpattern$blockpatternmatch2.getBlock(1, 2, 0).getPos());
                }
            }
        }
    }

    public boolean canSpawnGolems(LevelReader level, BlockPos pos) {
        return this.getOrCreateTitaniumGolemBase().find(level, pos) != null || this.getOrCreateFerrotitaniumGolemBase().find(level, pos) != null;
    }

    private static void spawnGolemInWorld(Level level, BlockPattern.BlockPatternMatch patternMatch, Entity golem, BlockPos pos) {
        clearPatternBlocks(level, patternMatch);
        golem.moveTo((double)pos.getX() + 0.5, (double)pos.getY() + 0.05, (double)pos.getZ() + 0.5, 0.0F, 0.0F);
        level.addFreshEntity(golem);
        Iterator var4 = level.getEntitiesOfClass(ServerPlayer.class, golem.getBoundingBox().inflate(5.0)).iterator();

        while(var4.hasNext()) {
            ServerPlayer serverplayer = (ServerPlayer)var4.next();
            CriteriaTriggers.SUMMONED_ENTITY.trigger(serverplayer, golem);
        }

        updatePatternBlocks(level, patternMatch);
    }

    public static void clearPatternBlocks(Level level, BlockPattern.BlockPatternMatch patternMatch) {
        for(int i = 0; i < patternMatch.getWidth(); ++i) {
            for(int j = 0; j < patternMatch.getHeight(); ++j) {
                BlockInWorld blockinworld = patternMatch.getBlock(i, j, 0);
                level.setBlock(blockinworld.getPos(), Blocks.AIR.defaultBlockState(), 2);
                level.levelEvent(2001, blockinworld.getPos(), Block.getId(blockinworld.getState()));
            }
        }
    }

    public static void updatePatternBlocks(Level level, BlockPattern.BlockPatternMatch patternMatch) {
        for(int i = 0; i < patternMatch.getWidth(); ++i) {
            for(int j = 0; j < patternMatch.getHeight(); ++j) {
                BlockInWorld blockinworld = patternMatch.getBlock(i, j, 0);
                level.blockUpdated(blockinworld.getPos(), Blocks.AIR);
            }
        }
    }

    private BlockPattern getOrCreateTitaniumGolemBase() {
        if (this.titaniumGolemBase == null) {
            this.titaniumGolemBase = BlockPatternBuilder.start().aisle(new String[]{"~ ~", "###", "~#~"})
                    .where('#', BlockInWorld.hasState(BlockStatePredicate.forBlock(BlockInit.TITANIUM_BLOCK.get())))
                    .where('~', (p_284869_) -> p_284869_.getState().isAir())
                    .build();
        }
        return this.titaniumGolemBase;
    }

    private BlockPattern getOrCreateFerrotitaniumGolemBase() {
        if (this.ferrotitaniumGolemBase == null) {
            this.ferrotitaniumGolemBase = BlockPatternBuilder.start().aisle(new String[]{"~ ~", "###", "~#~"})
                    .where('#', BlockInWorld.hasState(BlockStatePredicate.forBlock(BlockInit.FERROTITANIUM_BLOCK.get())))
                    .where('~', (p_284869_) -> p_284869_.getState().isAir())
                    .build();
        }
        return this.ferrotitaniumGolemBase;
    }

    private BlockPattern getOrCreateTitaniumGolemFull() {
        if (this.titaniumGolemFull == null) {
            this.titaniumGolemFull = BlockPatternBuilder.start().aisle(new String[]{"~^~", "###", "~#~"})
                    .where('^', BlockInWorld.hasState(PUMPKINS_PREDICATE))
                    .where('#', BlockInWorld.hasState(BlockStatePredicate.forBlock(BlockInit.TITANIUM_BLOCK.get())))
                    .where('~', (p_284868_) -> p_284868_.getState().isAir())
                    .build();
        }
        return this.titaniumGolemFull;
    }

    private BlockPattern getOrCreateFerrotitaniumGolemFull() {
        if (this.ferrotitaniumGolemFull == null) {
            this.ferrotitaniumGolemFull = BlockPatternBuilder.start().aisle(new String[]{"~^~", "###", "~#~"})
                    .where('^', BlockInWorld.hasState(PUMPKINS_PREDICATE))
                    .where('#', BlockInWorld.hasState(BlockStatePredicate.forBlock(BlockInit.FERROTITANIUM_BLOCK.get())))
                    .where('~', (p_284868_) -> p_284868_.getState().isAir())
                    .build();
        }
        return this.ferrotitaniumGolemFull;
    }

    static {
        PUMPKINS_PREDICATE = (p_51396_) -> {
            return p_51396_ != null && (p_51396_.is(Blocks.CARVED_PUMPKIN) || p_51396_.is(Blocks.JACK_O_LANTERN));
        };
    }
}
