package cn.goldenpotato.oxygensystem.Item;

import cn.goldenpotato.oxygensystem.Config.Config;
import cn.goldenpotato.oxygensystem.Config.MessageManager;
import cn.goldenpotato.oxygensystem.OxygenSystem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

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
        NamespacedKey key = new NamespacedKey(OxygenSystem.instance, "mask-upgrade-t2");
        ShapedRecipe recipe = new ShapedRecipe(key, item);
        List<Character> shape = new ArrayList<>();
        for(int i=0;i<9;i++)
        {
            Material material = Material.matchMaterial(Config.OxygenMaskT2Ingredient.get(i));
            if(material==null) material = Material.AIR;
            if (material == Material.AIR) shape.add(' ');
            else shape.add((char)('A'+i));
        }
        recipe.shape("" + shape.get(0) + shape.get(1) + shape.get(2),
            "" + shape.get(3) + shape.get(4) + shape.get(5),
            "" + shape.get(6) + shape.get(7) + shape.get(8));

        for(int i=0;i<9;i++)
        {
            if (shape.get(i) == ' ') continue;
            Material material = Material.matchMaterial(Config.OxygenMaskT2Ingredient.get(i));
            if(material==null) material = Material.AIR;
            recipe.setIngredient(shape.get(i), material);
        }
        return recipe;
    }
}
