package cn.goldenpotato.oxygensystem.Command.SubCommands;

import cn.goldenpotato.oxygensystem.Command.SubCommand;
import cn.goldenpotato.oxygensystem.Config.MessageManager;
import cn.goldenpotato.oxygensystem.OxygenSystem;
import cn.goldenpotato.oxygensystem.Util.Util;
import org.bukkit.entity.Player;

import java.util.List;

public class Add extends SubCommand
{
    public Add()
    {
        this.name = "add";
        this.permission = "oxygen.admin";
        this.usage = MessageManager.msg.SubCommand_Add_Usage;
    }
    @Override
    public void onCommand(Player player, String[] args)
    {
        if(OxygenSystem.roomCalculator.AddSealedRoom(player.getLocation(),0)==0)
            Util.Message(player, MessageManager.msg.Success);
        else
            Util.Message(player, MessageManager.msg.UnableToAddRoom);
    }

    @Override
    public List<String> onTab(Player player, String[] args)
    {
        return null;
    }
}
