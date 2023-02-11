package cn.goldenpotato.oxygensystem.Item.Vanilla;

import cn.goldenpotato.oxygensystem.Config.MessageManager;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class RoomDetector
{
    static ItemStack item;

    static void Init()
    {
        item = new ItemStack(Material.STICK);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(MessageManager.msg.Item_RoomDetector);
        meta.setLore(MessageManager.msg.Item_RoomDetector_Lore);
        item.setItemMeta(meta);
    }

    public static ItemStack GetItem()
    {
        if(item==null)
            Init();
        return item;
    }
}
