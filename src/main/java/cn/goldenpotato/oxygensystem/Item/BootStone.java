package cn.goldenpotato.oxygensystem.Item;

import cn.goldenpotato.oxygensystem.Config.Config;
import cn.goldenpotato.oxygensystem.Config.MessageManager;
import cn.goldenpotato.oxygensystem.OxygenSystem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class BootStone
{
    static ItemStack item;

    static void Init()
    {
        item = new ItemStack(Material.FLINT);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(MessageManager.msg.Item_BootStone);
        meta.setLore(MessageManager.msg.Item_BootStone_Lore);
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
        NamespacedKey key = new NamespacedKey(OxygenSystem.instance, "boot_stone");
        ShapedRecipe recipe = new ShapedRecipe(key, item);
        recipe.shape("ABC", "DEF", "GHI");
        for(int i=0;i<9;i++)
        {
            Material material = Material.matchMaterial(Config.BootStoneIngredient.get(i));
            if(material==null) material = Material.AIR;
            recipe.setIngredient((char)('A'+i), material);
        }
        return recipe;
    }
}
