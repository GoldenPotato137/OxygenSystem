package cn.goldenpotato.oxygensystem.Item;

import cn.goldenpotato.oxygensystem.Config.Config;
import cn.goldenpotato.oxygensystem.Config.MessageManager;
import cn.goldenpotato.oxygensystem.OxygenSystem;
import cn.goldenpotato.oxygensystem.Util.ItemUtil;
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
        return ItemUtil.MakeRecipe("oxygen_generator", item, Config.OxygenGeneratorIngredient);
    }
}
