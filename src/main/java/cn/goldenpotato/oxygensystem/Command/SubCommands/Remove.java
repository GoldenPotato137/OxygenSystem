package cn.goldenpotato.oxygensystem.Command.SubCommands;

import cn.goldenpotato.oxygensystem.Command.SubCommand;
import cn.goldenpotato.oxygensystem.Config.MessageManager;
import cn.goldenpotato.oxygensystem.Oxygen.SealedRoomCalculator;
import cn.goldenpotato.oxygensystem.OxygenSystem;
import cn.goldenpotato.oxygensystem.Util.Util;
import org.bukkit.entity.Player;

import java.util.List;

public class Remove extends SubCommand
{
    public Remove()
    {
        name = "remove";
        permission = "oxygen.admin";
        usage = MessageManager.msg.SubCommand_Remove_Usage;
    }

    @Override
    public void onCommand(Player player, String[] args)
    {
        if (SealedRoomCalculator.GetBelong(player.getLocation()) == 0)
        {
            Util.Message(player, MessageManager.msg.NotInRoom);
            return;
        }
        OxygenSystem.roomCalculator.RemoveSealedRoom(player.getLocation());
        Util.Message(player, MessageManager.msg.Success);
    }

    @Override
    public List<String> onTab(Player player, String[] args)
    {
        return null;
    }
}
