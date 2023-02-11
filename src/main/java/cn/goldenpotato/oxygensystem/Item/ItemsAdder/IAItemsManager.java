package cn.goldenpotato.oxygensystem.Item.ItemsAdder;

import cn.goldenpotato.oxygensystem.Util.Util;
import dev.lone.itemsadder.api.CustomStack;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class IAItemsManager
{
    public static ItemStack oxygenTank;
    public static ItemStack oxygenTankProembryo;

    /**
     * Check this item is a custom item from ItemsAdder
     * @param namespaceAndId namespace:id
     * @param itemStack item
     */
    public static boolean CheckItem(@NotNull String namespaceAndId, @NotNull ItemStack itemStack)
    {
        CustomStack stack = CustomStack.byItemStack(itemStack);
        if(stack==null) return false;
        return stack.getNamespacedID().equals(namespaceAndId);
    }


    /**
     * Try to get ItemsAdder's item stack
     * @param nameSpaceAndId The nameSpace and name of the item
     * @return The item stack, if not found, return null
     */
    private static ItemStack TryGetItem(String nameSpaceAndId)
    {
        CustomStack stack = CustomStack.getInstance(nameSpaceAndId);
        if(stack==null)
        {
            Util.Warning("Can't find item: " + nameSpaceAndId + ", please check this plugin's ItemsAdder config.");
            return null;
        }
        return stack.getItemStack();
    }

    public static void Init()
    {
        //Items
        TryGetItem("oxygensystem:room_detector");
        TryGetItem("oxygensystem:boot_stone");
        oxygenTank = TryGetItem("oxygensystem:oxygen_tank");
        oxygenTankProembryo = TryGetItem("oxygensystem:oxygen_tank_proembryo");
        TryGetItem("oxygensystem:mask_upgrade_t1");
        TryGetItem("oxygensystem:mask_upgrade_t2");
        TryGetItem("oxygensystem:mask_upgrade_t3");
        //Blocks
        TryGetItem("oxygensystem:oxygen_generator");
        Util.Log("ItemsAdder items are loaded.");
    }
}
