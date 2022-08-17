package cn.goldenpotato.oxygensystem.Oxygen;

import cn.goldenpotato.oxygensystem.Config.Config;
import cn.goldenpotato.oxygensystem.Config.MessageManager;
import cn.goldenpotato.oxygensystem.Item.OxygenTank;
import cn.goldenpotato.oxygensystem.OxygenSystem;
import cn.goldenpotato.oxygensystem.Util.OxygenUtil;
import cn.goldenpotato.oxygensystem.Util.Util;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;

import java.util.ArrayList;
import java.util.List;

public class OxygenCalculator
{
    public static NamespacedKey maskTierKey = new NamespacedKey(OxygenSystem.instance, "mask-tier");

    public static int SetMaskTier(ItemStack itemStack,int tier)
    {
        if(tier>Config.OxygenMask.size()-1) return -1;
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> lore = itemMeta.getLore();
        if(lore==null) lore = new ArrayList<>();
        boolean found = false;
        for (int i=0;i<lore.size();i++)
            if (lore.get(i).contains(MessageManager.msg.OxygenMaskLore))
            {
                lore.set(i, MessageManager.msg.OxygenMaskLore + tier);
                found = true;
                break;
            }
        if(!found)
            lore.add(MessageManager.msg.OxygenMaskLore + tier);
        itemMeta.setLore(lore);
        itemMeta.getPersistentDataContainer().set(maskTierKey,org.bukkit.persistence.PersistentDataType.INTEGER,tier);
        itemStack.setItemMeta(itemMeta);
        return 0;
    }

    public static ItemStack GetOxygenTank(Player player)
    {
        Inventory inv = player.getInventory();
        for(ItemStack item : inv.getContents())
        {
            if (item != null && item.isSimilar(OxygenTank.GetItem()))
                return item;
        }
        return null;
    }

    public static int GetMaskTier(ItemStack itemStack)
    {
        ItemMeta itemMeta = itemStack.getItemMeta();
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();
        if(!container.has(maskTierKey, org.bukkit.persistence.PersistentDataType.INTEGER))
            return 0;
        //noinspection ConstantConditions
        return container.get(maskTierKey, org.bukkit.persistence.PersistentDataType.INTEGER);
    }

    public static int GetMaskTier(Player player)
    {
        if(player.getInventory().getHelmet()==null) return 0;
        return GetMaskTier(player.getInventory().getHelmet());
    }

    public static int GetMaxOxygen(Player player)
    {
        return Config.OxygenMask.get(Math.min(Config.OxygenMask.size()-1,GetMaskTier(player)));
    }

    public static boolean SetOxygen(Player player, int delta)
    {
        int oxygen = 0;
        if (OxygenSystem.playerOxygen != null) oxygen = OxygenSystem.playerOxygen.get(player.getUniqueId());
        oxygen += delta;
        oxygen = Math.max(oxygen, 0);
        oxygen = Math.min(oxygen, GetMaxOxygen(player));
        OxygenSystem.playerOxygen.put(player.getUniqueId(), oxygen);
        return oxygen>0;
    }

    public static void ConsumeOxygenTank(Player player)
    {
        SetOxygen(player, Config.OxygenTank);
        OxygenUtil.ShowOxygen(player);
        if(Config.PlayOxygenTankUseSound)
            Util.PlaySound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
    }
}
