package cn.goldenpotato.oxygensystem.Util;

import cn.goldenpotato.oxygensystem.OxygenSystem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import java.util.ArrayList;
import java.util.List;


public class ItemUtil {
    public static ShapedRecipe MakeRecipe(String itemKey, ItemStack item, List<String> ingredients)
    {
        NamespacedKey key = new NamespacedKey(OxygenSystem.instance, itemKey);
        ShapedRecipe recipe = new ShapedRecipe(key, item);
        List<Character> shape = new ArrayList<>();
        for(int i=0;i<9;i++)
        {
            Material material = Material.matchMaterial(ingredients.get(i));
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
            Material material = Material.matchMaterial(ingredients.get(i));
            if(material==null) material = Material.AIR;
            recipe.setIngredient(shape.get(i), material);
        }
        return recipe;
    }
}
