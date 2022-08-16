package cn.goldenpotato.oxygensystem.Item;

import cn.goldenpotato.oxygensystem.Config.Config;
import cn.goldenpotato.oxygensystem.Config.MessageManager;
import cn.goldenpotato.oxygensystem.OxygenSystem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class OxygenStation
{
    static ItemStack item;
    public static NamespacedKey oxygenStationKey = new NamespacedKey(OxygenSystem.instance, "oxygen_station");

    static void Init()
    {
        item = new ItemStack(Material.LOOM);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(MessageManager.msg.Item_OxygenStation);
        meta.setLore(MessageManager.msg.Item_OxygenStation_Lore);
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
        NamespacedKey key = new NamespacedKey(OxygenSystem.instance, "oxygen_station");
        ShapedRecipe recipe = new ShapedRecipe(key, item);
        recipe.shape("ABC", "DEF", "GHI");
        for(int i=0;i<9;i++)
        {
            Material material = Material.matchMaterial(Config.OxygenStationIngredient.get(i));
            if(material==null) material = Material.AIR;
            recipe.setIngredient((char)('A'+i), material);
        }
        return recipe;
    }
}
