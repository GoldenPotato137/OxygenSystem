package cn.goldenpotato.oxygensystem.Listener;

import cn.goldenpotato.oxygensystem.Config.Config;
import cn.goldenpotato.oxygensystem.Item.OxygenTankProembryo;
import cn.goldenpotato.oxygensystem.Oxygen.OxygenCalculator;
import cn.goldenpotato.oxygensystem.Oxygen.SealedRoomCalculator;
import cn.goldenpotato.oxygensystem.OxygenSystem;
import cn.goldenpotato.oxygensystem.Util.OxygenUtil;
import com.destroystokyo.paper.event.server.ServerTickEndEvent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class TickListener implements Listener
{
    @EventHandler
    public void OnTickEnd(ServerTickEndEvent e)
    {
        if (e.getTickNumber() % 20 != 0) return;
        for (Player player : Bukkit.getOnlinePlayers())
        {
            if(!Config.EnableWorlds.contains(player.getWorld().getName())) continue;
            if(player.getGameMode()!= GameMode.SURVIVAL) continue;
            if(!OxygenSystem.playerOxygen.containsKey(player.getUniqueId()))
                OxygenSystem.playerOxygen.put(player.getUniqueId(), (double) OxygenCalculator.GetMaxOxygen(player));
            if(SealedRoomCalculator.GetBelong(player.getLocation())==0)
            {
                boolean result = OxygenCalculator.SetOxygen(player, -1);
                if(!result)
                {
                    ItemStack oxygenTank = OxygenCalculator.GetOxygenTank(player);
                    if(oxygenTank==null)
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
}
