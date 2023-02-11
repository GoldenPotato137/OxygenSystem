package cn.goldenpotato.oxygensystem.Command;

import org.bukkit.command.CommandSender;

import java.util.List;

public abstract  class SubCommandConsole {
    public abstract void onCommand(CommandSender sender, String[] args);
    public abstract List<String> onTab(CommandSender sender, String[] args);
    public String name;
    public String permission;
    public String usage;
}