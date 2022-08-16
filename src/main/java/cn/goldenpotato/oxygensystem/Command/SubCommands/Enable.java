package cn.goldenpotato.oxygensystem.Command.SubCommands;

import cn.goldenpotato.oxygensystem.Command.SubCommand;
import cn.goldenpotato.oxygensystem.Config.Config;
import cn.goldenpotato.oxygensystem.Config.Message;
import cn.goldenpotato.oxygensystem.Config.MessageManager;
import cn.goldenpotato.oxygensystem.Util.Util;
import org.bukkit.entity.Player;

import java.util.List;

public class Enable extends SubCommand
{
    public Enable()
    {
        name = "enable";
        permission = "oxygen.admin";
        usage = MessageManager.msg.SubCommand_Enable_Usage;
    }

    @Override
    public void onCommand(Player player, String[] args)
    {
        String world = player.getWorld().getName();
        if(!Config.EnableWorlds.contains(world))
        {
            Config.EnableWorlds.add(world);
            Util.Message(player, MessageManager.msg.Success);
        }
        else
            Util.Message(player, MessageManager.msg.SubCommand_Enable_AlreadyEnabled);
    }

    @Override
    public List<String> onTab(Player player, String[] args)
    {
        return null;
    }
}
