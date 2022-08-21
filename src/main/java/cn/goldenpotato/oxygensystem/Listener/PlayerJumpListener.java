package cn.goldenpotato.oxygensystem.Listener;

import cn.goldenpotato.oxygensystem.Config.Config;
import cn.goldenpotato.oxygensystem.Oxygen.OxygenCalculator;
import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class PlayerJumpListener implements Listener
{
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void OnPlayerJump(PlayerJumpEvent event)
    {
        if(OxygenCalculator.NeedOxygen(event.getFrom()))
            OxygenCalculator.SetOxygen(event.getPlayer(), -(float) Config.OxygenReducedOnJumping);
    }
}
