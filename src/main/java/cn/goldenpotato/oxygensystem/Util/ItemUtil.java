package cn.goldenpotato.oxygensystem.Util;

import cn.goldenpotato.oxygensystem.OxygenSystem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import java.util.List;


public class ItemUtil {
    public static ShapedRecipe MakeRecipe(String itemKey, ItemStack item, List<String> ingredients)
    {
        NamespacedKey key = new NamespacedKey(OxygenSystem.instance, itemKey);
        ShapedRecipe recipe = new ShapedRecipe(key, item);
        recipe.shape("ABC", "DEF", "GHI");
        for(int i=0;i<9;i++)
        {
            Material material = Material.matchMaterial(ingredients.get(i));
            if(material==null) material = Material.AIR;
            recipe.setIngredient((char)('A'+i), material);
        }
        return recipe;
    }
}
