package github.premiumrush.ascension.common.world.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;

public class AddItem extends LootModifier {

    public static final MapCodec<AddItem> CODEC = RecordCodecBuilder.mapCodec(instance ->
            LootModifier.codecStart(instance).and(instance.group(
                    Codec.STRING.fieldOf("field1").forGetter(e -> e.name),
                    Codec.INT.fieldOf("field2").forGetter(e -> e.amount),
                    BuiltInRegistries.ITEM.byNameCodec().fieldOf("field3").forGetter(e -> e.item)
            )).apply(instance, AddItem::new));

    private final String name;
    private final int amount;
    private final Item item;

    public AddItem(LootItemCondition[] conditionsIn, String name, int amount, Item item) {
        super(conditionsIn);
        this.name = name;
        this.amount = amount;
        this.item = item;
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }

    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        for(LootItemCondition condition : this.conditions) {
            if(!condition.test(context)) {
                return generatedLoot;
            }
        }
        generatedLoot.add(new ItemStack(this.item));
        return generatedLoot;
    }
}
