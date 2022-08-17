package cn.goldenpotato.oxygensystem.Listener;

import cn.goldenpotato.oxygensystem.Config.Config;
import cn.goldenpotato.oxygensystem.Config.MessageManager;
import cn.goldenpotato.oxygensystem.Item.*;
import cn.goldenpotato.oxygensystem.Oxygen.OxygenCalculator;
import cn.goldenpotato.oxygensystem.Oxygen.SealedRoomCalculator;
import cn.goldenpotato.oxygensystem.OxygenSystem;
import cn.goldenpotato.oxygensystem.Util.OxygenUtil;
import cn.goldenpotato.oxygensystem.Util.Util;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class PlayerInteractListener implements Listener
{
    @EventHandler (ignoreCancelled = true)
    public void OnPlayerInteract(PlayerInteractEvent event)
    {
        if (event.getAction()==Action.PHYSICAL) return;
        if (event.getHand() != EquipmentSlot.HAND || event.getItem() == null) return;
        if (!Config.EnableWorlds.contains(event.getPlayer().getWorld().getName())) return;

        if (event.getItem().isSimilar(RoomDetector.GetItem()))
        {
            int belong = SealedRoomCalculator.GetBelong(event.getClickedBlock());
            if (belong != 0)
                Util.Message(event.getPlayer(), MessageManager.msg.Detector_GetRoom + Math.abs(belong) + (belong < 0 ? MessageManager.msg.Detector_GetRoom_Wall : ""));
            else
                Util.Message(event.getPlayer(), MessageManager.msg.Detector_GetRoom_NoRoom);
        }
        else if(event.getItem().isSimilar(MaskUpgradeT1.GetItem()))
        {
            if(event.getPlayer().getInventory().getHelmet()==null || OxygenCalculator.GetMaskTier(event.getPlayer())!=0)
                Util.Message(event.getPlayer(),MessageManager.msg.MaskUpgrade_WrongTier);
            else
            {
                OxygenCalculator.SetMaskTier(event.getPlayer().getInventory().getHelmet(),1);
                event.getItem().add(-1);
                Util.Message(event.getPlayer(),MessageManager.msg.Success);
                if(Config.PlayItemUpgradeSound)
                    Util.PlaySound(event.getPlayer(), Sound.ENTITY_PLAYER_LEVELUP);
            }
        }
        else if(event.getItem().isSimilar(MaskUpgradeT2.GetItem()))
        {
            if(OxygenCalculator.GetMaskTier(event.getPlayer())!=1)
                Util.Message(event.getPlayer(),MessageManager.msg.MaskUpgrade_WrongTier);
            else
            {
                OxygenCalculator.SetMaskTier(event.getPlayer().getInventory().getHelmet(),2);
                event.getItem().add(-1);
                Util.Message(event.getPlayer(),MessageManager.msg.Success);
                if(Config.PlayItemUpgradeSound)
                    Util.PlaySound(event.getPlayer(), Sound.ENTITY_PLAYER_LEVELUP);
            }
        }
        else if(event.getItem().isSimilar(MaskUpgradeT3.GetItem()))
        {
            if(OxygenCalculator.GetMaskTier(event.getPlayer())!=2)
                Util.Message(event.getPlayer(),MessageManager.msg.MaskUpgrade_WrongTier);
            else
            {
                OxygenCalculator.SetMaskTier(event.getPlayer().getInventory().getHelmet(),3);
                event.getItem().add(-1);
                Util.Message(event.getPlayer(),MessageManager.msg.Success);
                if(Config.PlayItemUpgradeSound)
                    Util.PlaySound(event.getPlayer(), Sound.ENTITY_PLAYER_LEVELUP);
            }
        }
        else if(event.getItem().isSimilar(BootStone.GetItem()))
        {
            if(OxygenUtil.CheckOxygenGenerator(event.getClickedBlock())) //OxygenGenerator
            {
                if (SealedRoomCalculator.GetBelong(event.getClickedBlock()) != 0)
                {
                    Util.Message(event.getPlayer(), MessageManager.msg.AlreadySealed);
                    return;
                }
                if (OxygenSystem.roomCalculator.AddSealedRoom(Objects.requireNonNull(event.getClickedBlock()).getLocation(), 0) == 0)
                {
                    event.getItem().add(-1);
                    Util.Message(event.getPlayer(), MessageManager.msg.Success);
                    if(Config.PlayMachineStartUpSound)
                        Util.PlaySound(event.getPlayer(), Sound.BLOCK_BEACON_ACTIVATE);
                }
                else
                    Util.Message(event.getPlayer(), MessageManager.msg.UnableToAddRoom);
            }
            else if(OxygenUtil.CheckOxygenStation(event.getClickedBlock())) //OxygenStation
            {
                if (SealedRoomCalculator.GetBelong(event.getClickedBlock()) == 0)
                {
                    Util.Message(event.getPlayer(),MessageManager.msg.OxygenStation_NotInRoom);
                    return;
                }
                if(OxygenUtil.GetKey(event.getClickedBlock(),OxygenStation.oxygenStationKey)==1)
                {
                    event.getItem().add(-1);
                    OxygenUtil.SetKey(event.getClickedBlock(),OxygenStation.oxygenStationKey,2);
                    Util.Message(event.getPlayer(), MessageManager.msg.Success);
                    if(Config.PlayMachineStartUpSound)
                        Util.PlaySound(event.getPlayer(), Sound.BLOCK_BEACON_ACTIVATE);
                }
                else
                    Util.Message(event.getPlayer(), MessageManager.msg.AlreadySealed);
            }
        }
    }

    @EventHandler (priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void OnPlayerInteractEmptyHand(PlayerInteractEvent event)
    {
        if (event.getAction()==Action.PHYSICAL) return;
        if (event.getItem() != null) return;
        if (!Config.EnableWorlds.contains(event.getPlayer().getWorld().getName())) return;

        if(OxygenUtil.CheckOxygenStation(event.getClickedBlock())) //Refill Oxygen
        {
            if (SealedRoomCalculator.GetBelong(event.getClickedBlock()) == 0)
            {
                Util.Message(event.getPlayer(),MessageManager.msg.OxygenStation_NotInRoom);
                return;
            }
            if(OxygenUtil.GetKey(event.getClickedBlock(),OxygenStation.oxygenStationKey)!=2)
                Util.Message(event.getPlayer(),MessageManager.msg.NotEnabled);
            else
            {
                OxygenCalculator.SetOxygen(event.getPlayer(), Config.OxygenStationOxygenAdd);
                OxygenUtil.ShowOxygen(event.getPlayer());
                if(Config.PlayRefillOxygenSound)
                    Util.PlaySound(event.getPlayer(), Sound.ENTITY_GENERIC_BURN);
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void OnPlayerMove(PlayerMoveEvent event)
    {
        if (event.getFrom().toBlockLocation() == event.getTo().toBlockLocation()) return;
        if (!Config.EnableWorlds.contains(event.getTo().getWorld().getName())) return;

        int belongFrom = SealedRoomCalculator.GetBelong(event.getFrom());
        int belongTo = SealedRoomCalculator.GetBelong(event.getTo());
        if (Math.abs(belongFrom) != Math.abs(belongTo) && belongTo != 0)
        {
            Util.SendActionBar(event.getPlayer(), MessageManager.msg.EnteringRoom + " " + Math.abs(belongTo));
            if(Config.PlayEnterRoomSound)
                Util.PlaySound(event.getPlayer(), Sound.BLOCK_REDSTONE_TORCH_BURNOUT);
        }
        else if (belongFrom != 0 && belongTo == 0)
        {
            Util.SendActionBar(event.getPlayer(), MessageManager.msg.LeavingRoom);
            if(Config.PlayEnterRoomSound)
                Util.PlaySound(event.getPlayer(), Sound.BLOCK_REDSTONE_TORCH_BURNOUT);
        }
        if(event.getPlayer().isSprinting()) {
            OxygenCalculator.SetOxygen(event.getPlayer(), -(float) Config.OxygenReducedOnRunning / 20);
        }
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void OnPlayerConsume(PlayerItemConsumeEvent event)
    {
        if(event.getItem().isSimilar(OxygenTankProembryo.GetItem())) event.setCancelled(true);
        if (!Config.EnableWorlds.contains(event.getPlayer().getWorld().getName())){
            if(event.getItem().isSimilar(OxygenTank.GetItem())) event.setCancelled(true);
        }
        if(event.getItem().isSimilar(OxygenTank.GetItem())) {
            OxygenCalculator.ConsumeOxygenTank(event.getPlayer());
            event.setReplacement(OxygenTankProembryo.GetItem());
        }
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void OnPlaceBlock(BlockPlaceEvent event)
    {
        ItemStack item = event.getItemInHand().clone();
        if(item.isSimilar(MaskUpgradeT1.GetItem()) || item.isSimilar(MaskUpgradeT2.GetItem()) || item.isSimilar(MaskUpgradeT3.GetItem()))
        {
            Util.Message(event.getPlayer(), MessageManager.msg.CantPlace);
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void OnCraftItem(CraftItemEvent event)
    {
        Inventory inv = event.getInventory();
        for (ItemStack item : inv)
        {
            if(item != null && !item.isSimilar(event.getInventory().getResult())) {
                if (item.isSimilar(MaskUpgradeT1.GetItem())
                        || item.isSimilar(MaskUpgradeT2.GetItem())
                        || item.isSimilar(MaskUpgradeT3.GetItem())
                        || item.isSimilar(RoomDetector.GetItem())
                        || item.isSimilar(BootStone.GetItem())
                ) {

                    Util.Message(event.getWhoClicked(), MessageManager.msg.CantCraft);
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void OnBrewDone(BrewEvent event)
    {
        Inventory inv = event.getContents();
        for (ItemStack item : inv)
        {
            if(item != null) {
                if (item.isSimilar(OxygenTank.GetItem()) || item.isSimilar(OxygenTankProembryo.GetItem()))
                {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent e) {
        if(e.getInventory().getType() == InventoryType.ANVIL) {
            if(e.getCurrentItem() != null) {
                ItemStack item = e.getCurrentItem();
                if(item.isSimilar(MaskUpgradeT1.GetItem())
                        || item.isSimilar(MaskUpgradeT2.GetItem())
                        || item.isSimilar(MaskUpgradeT3.GetItem())
                        || item.isSimilar(RoomDetector.GetItem())
                        || item.isSimilar(BootStone.GetItem())
                        || item.isSimilar(OxygenTank.GetItem())
                        || item.isSimilar(OxygenTankProembryo.GetItem())
                        || item.isSimilar(OxygenGenerator.GetItem())
                        || item.isSimilar(OxygenStation.GetItem()))
                    e.setCancelled(true);
            }
        }
    }
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void OnPlayerDamaged(EntityDamageByEntityEvent event) {
        if(event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            OxygenCalculator.SetOxygen(player, -(float) Config.OxygenReducedOnDamagedOthers);
        }
    }
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void OnPlayerShootArrow(EntityShootBowEvent event) {
        if(event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            OxygenCalculator.SetOxygen(player, -(float) Config.OxygenReducedOnDamagedOthers);
        }
    }
}
