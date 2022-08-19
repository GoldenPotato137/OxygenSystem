package cn.goldenpotato.oxygensystem.Command;

import cn.goldenpotato.oxygensystem.Command.SubCommands.*;
import cn.goldenpotato.oxygensystem.Config.MessageManager;
import cn.goldenpotato.oxygensystem.Util.Util;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandManager implements CommandExecutor, TabCompleter
{
    public static ArrayList<SubCommand> subCommands;

    public static void Init()
    {
        subCommands = new ArrayList<>();
        subCommands.add(new Add());
        subCommands.add(new Disable());
        subCommands.add(new Enable());
        subCommands.add(new Get());
        subCommands.add(new Help());
        subCommands.add(new Mask());
        subCommands.add(new Remove());
        subCommands.add(new SetWorldType());
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args)
    {
        if(!(sender instanceof Player))
        {
            sender.sendMessage(MessageManager.msg.NoCommandInConsole);
            return true;
        }
        if(args.length==0)
        {
            Util.Message(sender,MessageManager.msg.SubCommand_Help_Usage);
            return true;
        }
        for (SubCommand subCommand : subCommands)
            if(subCommand.name.equals(args[0]))
            {
                if(!sender.hasPermission(subCommand.permission))
                    Util.Message(sender,MessageManager.msg.NoPermission);
                else
                    subCommand.onCommand((Player) sender, Arrays.copyOfRange(args,1,args.length));
                return true;
            }
        Util.Message(sender,MessageManager.msg.SubCommand_Help_NoSuchCommand);
        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args)
    {
        if(!(sender instanceof Player))
            return null;
        if(args.length==1)
        {
            List<String> result = new ArrayList<>();
            for (SubCommand subCommand : subCommands)
                if(sender.hasPermission(subCommand.permission))
                    result.add(subCommand.name);
            return result;
        }
        for (SubCommand subCommand : subCommands)
            if(subCommand.name.equals(args[0]) && sender.hasPermission(subCommand.permission))
                return subCommand.onTab((Player) sender, Arrays.copyOfRange(args,1,args.length));
        return null;
    }
}