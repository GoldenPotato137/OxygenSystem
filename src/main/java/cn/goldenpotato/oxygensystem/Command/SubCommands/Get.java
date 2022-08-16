package cn.goldenpotato.oxygensystem.Command.SubCommands;

import cn.goldenpotato.oxygensystem.Command.SubCommand;
import cn.goldenpotato.oxygensystem.Config.MessageManager;
import cn.goldenpotato.oxygensystem.Item.*;
import org.bukkit.entity.Player;

import java.util.List;

public class Get extends SubCommand
{
    public Get()
    {
        name = "get";
        permission = "oxygen.admin";
        usage = MessageManager.msg.SubCommand_Get_Usage;
    }

    @Override
    public void onCommand(Player player, String[] args)
    {
        player.getInventory().addItem(RoomDetector.GetItem());
        player.getInventory().addItem(BootStone.GetItem());
        player.getInventory().addItem(OxygenTank.GetItem());
        player.getInventory().addItem(OxygenTankProembryo.GetItem());
        player.getInventory().addItem(MaskUpgradeT1.GetItem());
        player.getInventory().addItem(MaskUpgradeT2.GetItem());
        player.getInventory().addItem(MaskUpgradeT3.GetItem());
        player.getInventory().addItem(OxygenGenerator.GetItem());
        player.getInventory().addItem(OxygenStation.GetItem());
    }

    @Override
    public List<String> onTab(Player player, String[] args)
    {
        return null;
    }
}
