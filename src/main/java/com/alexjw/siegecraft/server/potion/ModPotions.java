package com.alexjw.siegecraft.server.potion;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.potion.Potion;

import java.util.ArrayList;

public class ModPotions {
    public static ArrayList<Potion> POTIONS = new ArrayList<>();
    public Potion potionRookArmor;

    {
        potionRookArmor = new PotionRookArmor(false, 8171462).registerPotionAttributeModifier(SharedMonsterAttributes.MAX_HEALTH, "e7234b0a-7ba0-4c56-98de-f7c2b6d61fa3", 4.0D, 0).setBeneficial();
    }
}
