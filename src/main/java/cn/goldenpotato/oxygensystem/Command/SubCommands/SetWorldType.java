package cn.goldenpotato.oxygensystem.Command.SubCommands;

import cn.goldenpotato.oxygensystem.Command.SubCommand;
import cn.goldenpotato.oxygensystem.Config.Config;
import cn.goldenpotato.oxygensystem.Config.MessageManager;
import cn.goldenpotato.oxygensystem.Config.WorldType;
import cn.goldenpotato.oxygensystem.Util.Util;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SetWorldType extends SubCommand
{
    public SetWorldType()
    {
        name = "setWorldType";
        permission = "oxygen.admin";
        usage = MessageManager.msg.SubCommand_SetWorldType_Usage;
    }

    @Override
    public void onCommand(Player player, String[] args)
    {
        WorldType[] worldTypes = WorldType.values();
        if(args.length<1)
        {
            Util.Message(player,MessageManager.msg.SubCommand_SetWorldType_InvalidWorldType);
            return;
        }
        for(WorldType worldType : worldTypes)
            if(worldType.name().equalsIgnoreCase(args[0]))
            {
                Config.SetWorldType(player.getWorld(), worldType);
                player.sendMessage(MessageManager.msg.Success);
                return;
            }
        //No match found
        Util.Message(player,MessageManager.msg.SubCommand_SetWorldType_InvalidWorldType);
    }

    @Override
    public List<String> onTab(Player player, String[] args)
    {
        List<String> result = new ArrayList<>();
        if(args.length==1)
            for(WorldType worldType : WorldType.values())
                result.add(worldType.toString());
        return result;
    }
}
