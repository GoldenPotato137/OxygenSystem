package cn.goldenpotato.oxygensystem.Oxygen;

import cn.goldenpotato.oxygensystem.Config.Config;
import cn.goldenpotato.oxygensystem.Config.MessageManager;
import cn.goldenpotato.oxygensystem.Config.WorldType;
import cn.goldenpotato.oxygensystem.Item.ItemsAdder.IAItemsManager;
import cn.goldenpotato.oxygensystem.Item.Vanilla.OxygenTank;
import cn.goldenpotato.oxygensystem.Item.Vanilla.OxygenTankProembryo;
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
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class PlayerManager
{
    public static final Map<UUID, PlayerData> playerData = new HashMap<>();
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

    /**
     * Try to get the item stack of oxygen tank in player's inventory
     * @param player the player
     * @return the item stack of oxygen tank, null if not found
     */
    public static ItemStack GetOxygenTank(Player player)
    {
        Inventory inv = player.getInventory();
        for(ItemStack item : inv.getContents())
        {
            if(item == null) continue;
            if (!Config.IA_DisableVanillaItems && item.isSimilar(OxygenTank.GetItem()))
                return item;
            if (Config.IA_Items && IAItemsManager.CheckItem("oxygensystem:oxygen_tank", item))
                return item;
        }
        return null;
    }

    /**
     * Try to get the item stack of empty oxygen tank in player's inventory
     * @param player the player
     * @return the item stack of oxygen tank, null if not found
     */
    public static ItemStack GetEmptyOxygenTank(Player player)
    {
        Inventory inv = player.getInventory();
        for(ItemStack item : inv.getContents())
        {
            if(item == null) continue;
            if (!Config.IA_DisableVanillaItems && item.isSimilar(OxygenTankProembryo.GetItem()))
                return item;
            if (Config.IA_Items && IAItemsManager.CheckItem("oxygensystem:oxygen_tank_proembryo", item))
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

    /**
     * Get the room player in
     * @param player player
     * @return room id (0 if not in room, minus if in room's wall)
     */
    public static int GetRoom(@NotNull Player player)
    {
        return SealedRoomCalculator.GetBelong(player.getLocation().getBlock());
    }

    /**
     * Get player's data
     * @param player player
     * @return player's data, if player's data not exist, create a new one (with full oxygen)
     */
    public static PlayerData GetPlayerData(@NotNull Player player)
    {
        if(!playerData.containsKey(player.getUniqueId()))
            playerData.put(player.getUniqueId(), new PlayerData(GetMaxOxygen(player), player));
        return playerData.get(player.getUniqueId());
    }

    /**
     * Get player's oxygen
     * @param player player
     * @return oxygen, 0 if player's oxygen not found
     */
    public static double GetOxygen(@NotNull Player player)
    {
        return GetPlayerData(player).oxygen;
    }

    /**
     * Get player's oxygen percentage
     * @param player player
     * @return oxygen percentage (0-100), 0 if player's oxygen not found
     */
    public static double GetOxygenPercent(@NotNull Player player)
    {
        if(GetOxygen(player)==0) return 0;
        return GetOxygen(player)/GetMaxOxygen(player)*100.0;
    }

    /**
     * Add player's oxygen
     * @param player player
     * @param delta oxygen increase, negative if decrease
     * @return whether oxygen after set is grater than 0
     */
    public static boolean AddOxygen(Player player, double delta)
    {
        PlayerData data = GetPlayerData(player);
        double oxygen = data.oxygen;
        oxygen += delta;
        oxygen = Math.max(oxygen, 0);
        oxygen = Math.min(oxygen, GetMaxOxygen(player));
        data.oxygen = oxygen;
        return oxygen>0;
    }

    /**
     * Add player's oxygen like consuming oxygen tank. <br>
     * <b>This method will NOT check whether player has oxygen tank, and will NOT remove oxygen tank</b>
     * @param player player
     */
    public static void ConsumeOxygenTank(Player player)
    {
        AddOxygen(player, Config.OxygenTank);
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

                    boolean needOxygen = PlayerManager.NeedOxygen(player.getLocation());
                    if(needOxygen)
                    {
                        boolean result = PlayerManager.AddOxygen(player, -1);
                        if (!result)
                        {
                            ItemStack oxygenTank = PlayerManager.GetOxygenTank(player);
                            if (oxygenTank == null)
                                player.damage(2);
                            else
                            {
                                oxygenTank.setAmount(oxygenTank.getAmount() - 1);
                                PlayerManager.ConsumeOxygenTank(player);
                                player.getInventory().addItem(Config.IA_Items && Config.ItemsAdderLoaded ?
                                        IAItemsManager.oxygenTankProembryo : OxygenTankProembryo.GetItem());
                            }
                        }
                    }
                    else
                        PlayerManager.AddOxygen(player,Config.RoomOxygenAdd);

                    OxygenUtil.ShowOxygen(player);
                    if(Config.ItemsAdder)
                    {
                        PlayerData data = GetPlayerData(player);
                        if(Config.IA_Hud_OxygenHudType1 && data.oxygenHudType1.exists())
                            data.oxygenHudType1.setFloatValue((float) GetOxygenPercent(player) / 10);
                        if(Config.IA_Hud_OxygenHudType2 && data.oxygenHudType2.exists())
                            data.oxygenHudType2.setFloatValue((float) GetOxygenPercent(player) / 100 * 25);
                        data.hudHolder.sendUpdate();
                    }
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
