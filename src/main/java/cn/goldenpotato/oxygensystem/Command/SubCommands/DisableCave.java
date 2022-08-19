package cn.goldenpotato.oxygensystem.Command.SubCommands;

import cn.goldenpotato.oxygensystem.Command.SubCommand;
import cn.goldenpotato.oxygensystem.Config.Config;
import cn.goldenpotato.oxygensystem.Config.MessageManager;
import cn.goldenpotato.oxygensystem.OxygenSystem;
import org.bukkit.entity.Player;

import java.util.List;

public class DisableCave extends SubCommand {
    public DisableCave()
    {
        name = "disableCave";
        permission = "oxygen.admin";
        usage = MessageManager.msg.SubCommand_DisableCave_Usage;
    }


    @Override
    public void onCommand(Player player, String[] args)
    {
        if(Config.EnableCaveNonOxygenWorlds.contains(player.getWorld().getName()))
        {
            if(Config.EnableWorlds.contains(player.getWorld().getName()))
            {
                Config.EnableWorlds.remove(player.getWorld().getName());
            }
            Config.EnableCaveNonOxygenWorlds.remove(player.getWorld().getName());
            OxygenSystem.Save();
            player.sendMessage(MessageManager.msg.Success);
        }
        else
            player.sendMessage(MessageManager.msg.SubCommand_DisableCave_NotEnabled);
    }

    @Override
    public List<String> onTab(Player player, String[] args)
    {
        return null;
    }
}
