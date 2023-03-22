package cn.goldenpotato.oxygensystem.Listener;

import cn.goldenpotato.oxygensystem.Config.Config;
import cn.goldenpotato.oxygensystem.Item.ItemsAdder.IAItemsManager;
import cn.goldenpotato.oxygensystem.Item.Vanilla.OxygenGenerator;
import cn.goldenpotato.oxygensystem.Item.Vanilla.OxygenStation;
import cn.goldenpotato.oxygensystem.Util.OxygenUtil;
import dev.lone.itemsadder.api.Events.CustomBlockPlaceEvent;
import dev.lone.itemsadder.api.Events.ItemsAdderLoadDataEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

/**
 * Listener for ItemsAdder
 */
public class ItemsAdderListener implements Listener
{
    @EventHandler
    public void OnItemsAdderLoaded(ItemsAdderLoadDataEvent e)
    {
        Config.ItemsAdderLoaded = true;
        if(Config.IA_Items)
            IAItemsManager.Init();
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void OnIABlockBuild(CustomBlockPlaceEvent event)
    {
        switch (event.getNamespacedID())
        {
            case "oxygensystem:oxygen_generator":
                OxygenUtil.SetKey(event.getBlock(), OxygenGenerator.oxygenGeneratorKey, 1);
                break;
            case "oxygensystem:oxygen_station":
                OxygenUtil.SetKey(event.getBlock(), OxygenStation.oxygenStationKey, 1);
                break;
        }
    }
}
