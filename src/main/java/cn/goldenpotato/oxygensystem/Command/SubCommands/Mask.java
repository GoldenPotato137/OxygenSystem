package cn.goldenpotato.oxygensystem.Command.SubCommands;

import cn.goldenpotato.oxygensystem.Command.SubCommand;
import cn.goldenpotato.oxygensystem.Config.MessageManager;
import cn.goldenpotato.oxygensystem.Oxygen.PlayerManager;
import cn.goldenpotato.oxygensystem.Util.Util;
import org.bukkit.entity.Player;

import java.util.List;

public class Mask extends SubCommand
{
    public Mask()
    {
        name = "mask";
        permission = "oxygen.admin";
        usage = MessageManager.msg.SubCommand_Mask_Usage;
    }

    @Override
    public void onCommand(Player player, String[] args)
    {
        if(player.getInventory().getHelmet()==null)
        {
            Util.Message(player, MessageManager.msg.SubCommand_Mask_NoHelmet);
        }
        int result = PlayerManager.SetMaskTier(player.getInventory().getHelmet(), PlayerManager.GetMaskTier(player)+1);
        if(result==-1)
            Util.Message(player, MessageManager.msg.SubCommand_Mask_FullLevel);
        else
            Util.Message(player, MessageManager.msg.Success);
    }

    @Override
    public List<String> onTab(Player player, String[] args)
    {
        return null;
    }
}
