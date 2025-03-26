package cn.goldenpotato.oxygensystem.Item;

import cn.goldenpotato.oxygensystem.Config.Config;
import cn.goldenpotato.oxygensystem.Config.MessageManager;
import cn.goldenpotato.oxygensystem.Util.ItemUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class MaskUpgradeT1
{
    static ItemStack item;

    static void Init()
    {
        item = new ItemStack(Material.PINK_TULIP);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(MessageManager.msg.Item_MaskUpgradeT1);
        meta.setLore(MessageManager.msg.Item_MaskUpgradeT1_Lore);
        item.setItemMeta(meta);
    }

    public static ItemStack GetItem()
    {
        if(item==null)
            Init();
        return item;
    }

    public static ShapedRecipe GetRecipe()
    {
        Init();
        return ItemUtil.MakeRecipe("mask-upgrade-t1", item, Config.OxygenMaskT1Ingredient);
    }
}
