package github.premiumrush.ascension.common.world.entity.custom;

import github.premiumrush.ascension.common.world.entity.ai.goal.GhostAttackGoal;
import github.premiumrush.ascension.common.init.EntityInit;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class GhostEntity extends AbstractGhostEntity {

    public GhostEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new GhostAttackGoal(this, 1.7, true));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.9));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, true, false));
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (player.getItemInHand(hand).is(Items.JACK_O_LANTERN)) {
            if (!this.level().isClientSide() && !(this instanceof TamedGhostEntity)) {
                player.getItemInHand(hand).consume(1, player);
                this.setTame(true);
                this.setPersistenceRequired();
            }
        }
        return super.mobInteract(player, hand);
    }

    public void setTame(boolean tame) {
        if (tame) {
            this.setTarget(null);
            TamedGhostEntity newGhost = new TamedGhostEntity(EntityInit.TAMED_GHOST.get(), this.level());
            newGhost.moveTo(this.position());
            newGhost.setRot(this.getYRot(), this.getXRot());
            newGhost.setYHeadRot(this.getYHeadRot());
            //newGhost.scareTimeout = 1200;
            this.level().addFreshEntity(newGhost);
            this.discard();
        }
    }
}
