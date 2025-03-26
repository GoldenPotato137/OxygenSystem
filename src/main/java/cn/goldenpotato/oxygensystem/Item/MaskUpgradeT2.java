package cn.goldenpotato.oxygensystem.Item;

import cn.goldenpotato.oxygensystem.Config.Config;
import cn.goldenpotato.oxygensystem.Config.MessageManager;
import cn.goldenpotato.oxygensystem.Util.ItemUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class MaskUpgradeT2
{
    static ItemStack item;

    static void Init()
    {
        item = new ItemStack(Material.CORNFLOWER);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(MessageManager.msg.Item_MaskUpgradeT2);
        meta.setLore(MessageManager.msg.Item_MaskUpgradeT2_Lore);
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
        return ItemUtil.MakeRecipe("mask-upgrade-t2", item, Config.OxygenMaskT2Ingredient);
    }
}
