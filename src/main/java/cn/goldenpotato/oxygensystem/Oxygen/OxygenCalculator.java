package cn.goldenpotato.oxygensystem.Oxygen;

import cn.goldenpotato.oxygensystem.Config.Config;
import cn.goldenpotato.oxygensystem.Config.MessageManager;
import cn.goldenpotato.oxygensystem.Config.WorldType;
import cn.goldenpotato.oxygensystem.Item.OxygenTank;
import cn.goldenpotato.oxygensystem.Item.OxygenTankProembryo;
import cn.goldenpotato.oxygensystem.OxygenSystem;
import cn.goldenpotato.oxygensystem.Util.OxygenUtil;
import cn.goldenpotato.oxygensystem.Util.Util;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

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

    /**
     * Check whether player in this location need oxygen
     * @param loc location
     */
    public static boolean NeedOxygen(Location loc)
    {
        if(Config.GetWorldType(loc.getWorld())== WorldType.NORMAL)
            return false;
        else if(SealedRoomCalculator.GetBelong(loc.getBlock())==0)
        {
            if(Config.GetWorldType(loc.getWorld())== WorldType.NON_OXYGEN)
                return true;
            else if(Config.GetWorldType(loc.getWorld())==WorldType.CAVE_NON_OXYGEN)
                return SealedCaveCalculator.checkIsOnCave(loc);
        }
        //else: in room
        return false;
    }

    public static boolean SetOxygen(Player player, double delta)
    {
        double oxygen = 0;
        if (OxygenSystem.playerOxygen != null) try{oxygen = OxygenSystem.playerOxygen.get(player.getUniqueId());} catch (NullPointerException ignored){}
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

    private static BukkitTask task;
    public static void StartCalculate()
    {
        if(task!=null) return;
        task =  new BukkitRunnable()
        {
            @Override
            public void run()
            {
                for (Player player : Bukkit.getOnlinePlayers())
                {
                    if(Config.GetWorldType(player.getWorld())== WorldType.NORMAL) continue;
                    if(player.getGameMode()!= GameMode.SURVIVAL) continue;

                    if(!OxygenSystem.playerOxygen.containsKey(player.getUniqueId()))
                        OxygenSystem.playerOxygen.put(player.getUniqueId(), (double) OxygenCalculator.GetMaxOxygen(player));

                    boolean needOxygen = OxygenCalculator.NeedOxygen(player.getLocation());
                    if(needOxygen)
                    {
                        boolean result = OxygenCalculator.SetOxygen(player, -1);
                        if (!result)
                        {
                            ItemStack oxygenTank = OxygenCalculator.GetOxygenTank(player);
                            if (oxygenTank == null)
                                player.damage(2);
                            else
                            {
                                oxygenTank.add(-1);
                                OxygenCalculator.ConsumeOxygenTank(player);
                                player.getInventory().addItem(OxygenTankProembryo.GetItem());
                            }
                        }
                    }
                    else
                        OxygenCalculator.SetOxygen(player,Config.RoomOxygenAdd);
                    OxygenUtil.ShowOxygen(player);
                }
            }
        }.runTaskTimer(OxygenSystem.instance, 10, 20);
    }

    public static void StopCalculate()
    {
        if(task==null || task.isCancelled()) return;
        task.cancel();
        task = null;
    }
}
