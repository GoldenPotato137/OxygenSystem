package cn.goldenpotato.oxygensystem.Listener;

import cn.goldenpotato.oxygensystem.Config.Config;
import dev.lone.itemsadder.api.Events.ItemsAdderLoadDataEvent;
import org.bukkit.event.EventHandler;
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
    }
}
