package com.pekar.nautilusvsmagma;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import com.pekar.nautilusvsmagma.config.ModConfigSpec;

public class Config
{
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue COPPER_NAUTILUS_ARMOR_PROTECTS_FROM_MAGMA;
    public static final ModConfigSpec.BooleanValue IRON_NAUTILUS_ARMOR_PROTECTS_FROM_MAGMA;
    public static final ModConfigSpec.BooleanValue GOLDEN_NAUTILUS_ARMOR_PROTECTS_FROM_MAGMA;
    public static final ModConfigSpec.BooleanValue DIAMOND_NAUTILUS_ARMOR_PROTECTS_FROM_MAGMA;
    public static final ModConfigSpec.BooleanValue NETHERITE_NAUTILUS_ARMOR_PROTECTS_FROM_MAGMA;

    public static final ModConfigSpec SPEC;

    static
    {
        BUILDER.push("nautilusArmorImmunity");

        // Keep prior behavior: only netherite armor grants magma immunity by default.
        COPPER_NAUTILUS_ARMOR_PROTECTS_FROM_MAGMA = BUILDER
                .comment("If true, Copper Nautilus Armor grants immunity to magma (hot floor) damage.")
                .define("copper", false);
        IRON_NAUTILUS_ARMOR_PROTECTS_FROM_MAGMA = BUILDER
                .comment("If true, Iron Nautilus Armor grants immunity to magma (hot floor) damage.")
                .define("iron", false);
        GOLDEN_NAUTILUS_ARMOR_PROTECTS_FROM_MAGMA = BUILDER
                .comment("If true, Golden Nautilus Armor grants immunity to magma (hot floor) damage.")
                .define("golden", true);
        DIAMOND_NAUTILUS_ARMOR_PROTECTS_FROM_MAGMA = BUILDER
                .comment("If true, Diamond Nautilus Armor grants immunity to magma (hot floor) damage.")
                .define("diamond", true);
        NETHERITE_NAUTILUS_ARMOR_PROTECTS_FROM_MAGMA = BUILDER
                .comment("If true, Netherite Nautilus Armor grants immunity to magma (hot floor) damage.")
                .define("netherite", true);

        BUILDER.pop();

        SPEC = BUILDER.build();
    }

    public static boolean isMagmaProtectionEnabledForArmor(ItemStack armorItem)
    {
        if (armorItem.is(Items.COPPER_NAUTILUS_ARMOR)) return COPPER_NAUTILUS_ARMOR_PROTECTS_FROM_MAGMA.get();
        if (armorItem.is(Items.IRON_NAUTILUS_ARMOR)) return IRON_NAUTILUS_ARMOR_PROTECTS_FROM_MAGMA.get();
        if (armorItem.is(Items.GOLDEN_NAUTILUS_ARMOR)) return GOLDEN_NAUTILUS_ARMOR_PROTECTS_FROM_MAGMA.get();
        if (armorItem.is(Items.DIAMOND_NAUTILUS_ARMOR)) return DIAMOND_NAUTILUS_ARMOR_PROTECTS_FROM_MAGMA.get();
        if (armorItem.is(Items.NETHERITE_NAUTILUS_ARMOR)) return NETHERITE_NAUTILUS_ARMOR_PROTECTS_FROM_MAGMA.get();
        return false;
    }
}
