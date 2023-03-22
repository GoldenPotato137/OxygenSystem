package cn.goldenpotato.oxygensystem.Listener;

import cn.goldenpotato.oxygensystem.Config.Config;
import cn.goldenpotato.oxygensystem.Config.MessageManager;
import cn.goldenpotato.oxygensystem.Item.Vanilla.OxygenGenerator;
import cn.goldenpotato.oxygensystem.Item.Vanilla.OxygenStation;
import cn.goldenpotato.oxygensystem.Item.Vanilla.OxygenTankProembryo;
import cn.goldenpotato.oxygensystem.Oxygen.SealedRoomCalculator;
import cn.goldenpotato.oxygensystem.OxygenSystem;
import cn.goldenpotato.oxygensystem.Util.OxygenUtil;
import cn.goldenpotato.oxygensystem.Util.Util;
import dev.lone.itemsadder.api.CustomBlock;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class BlockListener implements Listener
{
    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void BlockBreak(final BlockBreakEvent event)
    {
        if (OxygenUtil.CheckOxygenGenerator(event.getBlock()))
        {
            //noinspection ConstantValue
            if(!Config.ItemsAdder || CustomBlock.byAlreadyPlaced(event.getBlock()) == null) //drop item only when this is vanilla block
            {
                event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), OxygenGenerator.GetItem());
                event.setDropItems(false);
            }
            OxygenUtil.RemoveKey(event.getBlock(),OxygenGenerator.oxygenGeneratorKey);
            if(SealedRoomCalculator.GetBelong(event.getBlock())!=0)
            {
                Util.Message(event.getPlayer(), MessageManager.msg.BreakRoom + " " + Math.abs(SealedRoomCalculator.GetBelong(event.getBlock())));
                OxygenSystem.roomCalculator.RemoveSealedRoom(event.getBlock().getLocation());
                if(Config.PlayAirLockBreakSound)
                    Util.PlaySound(event.getPlayer(), Sound.ENTITY_DRAGON_FIREBALL_EXPLODE);
            }
            return;
        }
        else if(OxygenUtil.CheckOxygenStation(event.getBlock()))
        {
            //noinspection ConstantValue
            if(!Config.ItemsAdder || CustomBlock.byAlreadyPlaced(event.getBlock()) == null) //drop item only when this is vanilla block
            {
                event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), OxygenStation.GetItem());
                event.setDropItems(false);
            }
            OxygenUtil.RemoveKey(event.getBlock(),OxygenStation.oxygenStationKey);
        }

        int belong = SealedRoomCalculator.GetBelong(event.getBlock());
        if (belong >= 0) return;

        int result=OxygenSystem.roomCalculator.AddSealedRoom(event.getBlock().getLocation(), belong);
        if(result == 2)
        {
            Util.Message(event.getPlayer(), MessageManager.msg.BreakRoom + " " + Math.abs(belong));
            if(Config.PlayAirLockBreakSound)
                Util.PlaySound(event.getPlayer(), Sound.ENTITY_DRAGON_FIREBALL_EXPLODE);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void OnBlockBuild(BlockCanBuildEvent event)
    {
        ItemStack toBuild = Objects.requireNonNull(event.getPlayer()).getInventory().getItemInMainHand();
        if(toBuild.isSimilar(OxygenGenerator.GetItem()) && !Config.IA_DisableVanillaItems)
        {
            OxygenUtil.SetKey(event.getBlock(), OxygenGenerator.oxygenGeneratorKey, 1);
        }
        else if(toBuild.isSimilar(OxygenStation.GetItem()) && !Config.IA_DisableVanillaItems)
        {
            OxygenUtil.SetKey(event.getBlock(), OxygenStation.oxygenStationKey, 1);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void OnEntityExplode(EntityExplodeEvent event)
    {
        for (Block block : event.blockList())
        {
//            Util.Log(block.getLocation().toString());
            int belong = SealedRoomCalculator.GetBelong(block);
            if (belong >= 0) return;
            OxygenSystem.roomCalculator.AddSealedRoom(block.getLocation(), belong);
        }
    }

    @EventHandler (ignoreCancelled = true)
    public void OnFurnaceSmelt(FurnaceSmeltEvent event)
    {
        if(event.getSource().isSimilar(OxygenTankProembryo.GetItem()) && SealedRoomCalculator.GetBelong(event.getBlock()) == 0)
            event.setCancelled(true);
    }
}
