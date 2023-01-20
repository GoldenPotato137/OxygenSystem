package cn.goldenpotato.oxygensystem.Command.SubCommands;

import cn.goldenpotato.oxygensystem.Command.SubCommand;
import cn.goldenpotato.oxygensystem.Config.MessageManager;
import cn.goldenpotato.oxygensystem.OxygenSystem;
import cn.goldenpotato.oxygensystem.Util.Util;
import org.bukkit.entity.Player;

import java.util.List;

public class Reload extends SubCommand
{
    public Reload()
    {
        this.name = "reload";
        this.permission = "oxygen.admin";
        this.usage = MessageManager.msg.SubCommand_Reload_Usage;
    }
    @Override
    public void onCommand(Player player, String[] args)
    {
        try
        {
            OxygenSystem.Load();
            Util.Message(player,MessageManager.msg.Success);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> onTab(Player player, String[] args)
    {
        return null;
    }
}
