package cn.goldenpotato.oxygensystem.Util;

import cn.goldenpotato.oxygensystem.Config.Config;
import cn.goldenpotato.oxygensystem.OxygenSystem;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class Util
{
    public static void Message(CommandSender player, String s)
    {
        if (player == null) return;
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', s));
    }

    public static void Message(List<UUID> players, String s)
    {
        for (UUID uuid : players)
        {
            Player player = Bukkit.getPlayer(uuid);
            if (player == null) continue;
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', s));
        }
    }

    public static void Title(Player player, String s, int stay)
    {
        player.sendTitle(ChatColor.translateAlternateColorCodes('&', s), "", 0, stay, 0);
    }

    public static void PlaySound(Player player, Sound sound)
    {
        player.playSound(player.getLocation(), sound, 1, 1);
    }

    public static void Log(String s)
    {
        OxygenSystem.instance.getLogger().info(ChatColor.translateAlternateColorCodes('&', s));
    }

    static ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();

    public static void Command(String command, List<UUID> players)
    {
        if (command.equals("[null]")) return;
        if (command.contains("[player]"))
        {
            for (UUID uuid : players)
            {
                Player player = Bukkit.getPlayer(uuid);
                if (player == null) continue;
                String com = command;
                com = com.replace("[player]", player.getName());
                Bukkit.dispatchCommand(console, com);
            }
        }
        else
            Bukkit.dispatchCommand(console, command);
    }

    /**
     * Execute a command as console
     * @param command Command to execute
     */
    public static void Command(String command)
    {
        Bukkit.dispatchCommand(console, command);
    }

    public static String TickToTime(long tick)
    {
        long hour = (tick / 1000 + 7) % 24;
        long minute = (int) ((float) (tick % 1000) / (1000.0 / 60));
        return String.format("%02d:%02d", hour, minute);
    }

    public static void SendActionBar(Player player,String s)
    {
        if(Config.IsPaper)
            player.sendActionBar(ChatColor.translateAlternateColorCodes('&', s));
        else
            //noinspection deprecation
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', s)));
    }

    public static float GetServerVersion()
    {
        String[] a = Bukkit.getServer().getBukkitVersion().split("-");
        String[] ver = a[0].split("\\."); //1.x.x
        return Float.parseFloat(ver[0]+'.'+ver[1]);
    }

    //Add due to compatibility with spigot
    public static Location ToBlockLocation(Location loc)
    {
        return new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
    }
}