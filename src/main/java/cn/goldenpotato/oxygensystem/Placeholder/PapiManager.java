package cn.goldenpotato.oxygensystem.Placeholder;

import cn.goldenpotato.oxygensystem.Oxygen.PlayerManager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PapiManager extends PlaceholderExpansion
{
    @Override
    public @NotNull String getIdentifier()
    {
        return "oxygen";
    }

    @Override
    public @NotNull String getAuthor()
    {
        return "https://github.com/GoldenPotato137/OxygenSystem/graphs/contributors";
    }

    @Override
    public @NotNull String getVersion()
    {
        return "${project.version}";
    }

    @Override
    public boolean persist()
    {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer offlinePlayer, @NotNull String params)
    {
        if(offlinePlayer == null || offlinePlayer.getPlayer()==null) return null;
        Player player = offlinePlayer.getPlayer();
        switch (params)
        {
            case "oxygen":
                return String.valueOf((int) PlayerManager.GetOxygen(player));
            case "maxOxygen":
                return String.valueOf(PlayerManager.GetMaxOxygen(player));
            case "oxygenPercent":
                if(PlayerManager.GetMaxOxygen(player)==0) return "0";
                return String.valueOf((int)(PlayerManager.GetOxygen(player)*100/ PlayerManager.GetMaxOxygen(player)));
            case "room":
            {
                String result = "Not In Room";
                int tmp = Math.abs(PlayerManager.GetRoom(player));
                if(tmp!=0)
                    result = String.valueOf(tmp);
                return result;
            }
            default:
                return null;
        }
    }
}
