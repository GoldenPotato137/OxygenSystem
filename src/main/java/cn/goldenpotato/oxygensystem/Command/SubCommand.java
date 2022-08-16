package cn.goldenpotato.oxygensystem.Command;

import org.bukkit.entity.Player;

import java.util.List;

public abstract class SubCommand
{
    public abstract void onCommand(Player player, String[] args);
    public abstract List<String> onTab(Player player, String[] args);
    public String name;
    public String permission;
    public String usage;
}