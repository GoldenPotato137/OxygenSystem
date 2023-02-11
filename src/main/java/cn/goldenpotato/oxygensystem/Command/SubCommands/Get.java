package cn.goldenpotato.oxygensystem.Command.SubCommands;

import cn.goldenpotato.oxygensystem.Command.SubCommand;
import cn.goldenpotato.oxygensystem.Config.MessageManager;
import cn.goldenpotato.oxygensystem.Item.Vanilla.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Get extends SubCommand
{
    Map<String, ItemStack> items = new HashMap<>();
    public Get()
    {
        name = "get";
        permission = "oxygen.admin";
        usage = MessageManager.msg.SubCommand_Get_Usage;
        items.put("RoomDetector", RoomDetector.GetItem());
        items.put("BootStone", BootStone.GetItem());
        items.put("OxygenTank", OxygenTank.GetItem());
        items.put("OxygenTankProembryo", OxygenTankProembryo.GetItem());
        items.put("MaskUpgradeT1", MaskUpgradeT1.GetItem());
        items.put("MaskUpgradeT2", MaskUpgradeT2.GetItem());
        items.put("MaskUpgradeT3", MaskUpgradeT3.GetItem());
        items.put("OxygenGenerator", OxygenGenerator.GetItem());
        items.put("OxygenStation", OxygenStation.GetItem());
    }

    @Override
    public void onCommand(Player player, String[] args)
    {
        List<ItemStack> toPut = new ArrayList<>();
        if(args.length==0)
            toPut = new ArrayList<>(items.values());
        for(String str : args)
        {
            if(items.containsKey(str))
                toPut.add(items.get(str));
        }
        for(ItemStack item : toPut)
            player.getInventory().addItem(item);
    }

    @Override
    public List<String> onTab(Player player, String[] args)
    {
        return new ArrayList<>(items.keySet());
    }
}
