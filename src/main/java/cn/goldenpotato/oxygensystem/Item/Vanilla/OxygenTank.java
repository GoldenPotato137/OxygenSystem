package cn.goldenpotato.oxygensystem.Item.Vanilla;

import cn.goldenpotato.oxygensystem.Config.MessageManager;
import cn.goldenpotato.oxygensystem.OxygenSystem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.SmokingRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class OxygenTank
{
    static ItemStack item;

    static void Init()
    {
        item = new ItemStack(Material.POTION);
        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(Enchantment.DURABILITY, 1, false);
        meta.setDisplayName(MessageManager.msg.Item_OxygenTank);
        meta.setLore(MessageManager.msg.Item_OxygenTank_Lore);
        item.setItemMeta(meta);
    }

    public static ItemStack GetItem()
    {
        if(item==null)
            Init();
        return item;
    }

    public static SmokingRecipe GetRecipe()
    {
        Init();
        NamespacedKey key = new NamespacedKey(OxygenSystem.instance, "oxygen-tank");
        return new SmokingRecipe(key, item, new RecipeChoice.ExactChoice(OxygenTankProembryo.GetItem()),0,20);
    }
}
