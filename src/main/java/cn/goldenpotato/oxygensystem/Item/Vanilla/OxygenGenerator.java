package cn.goldenpotato.oxygensystem.Item.Vanilla;

import cn.goldenpotato.oxygensystem.Config.Config;
import cn.goldenpotato.oxygensystem.Config.MessageManager;
import cn.goldenpotato.oxygensystem.OxygenSystem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class OxygenGenerator
{
    static ItemStack item;
    public static NamespacedKey oxygenGeneratorKey = new NamespacedKey(OxygenSystem.instance, "oxygen_generator");

    static void Init()
    {
        item = new ItemStack(Material.SMOKER);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(MessageManager.msg.Item_OxygenGenerator);
        meta.setLore(MessageManager.msg.Item_OxygenGenerator_Lore);
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
        NamespacedKey key = new NamespacedKey(OxygenSystem.instance, "oxygen_generator");
        ShapedRecipe recipe = new ShapedRecipe(key, item);
        recipe.shape("ABC", "DEF", "GHI");
        for(int i=0;i<9;i++)
        {
            Material material = Material.matchMaterial(Config.OxygenGeneratorIngredient.get(i));
            if(material==null) material = Material.AIR;
            recipe.setIngredient((char)('A'+i), material);
        }
        return recipe;
    }
}
