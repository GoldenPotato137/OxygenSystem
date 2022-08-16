package cn.goldenpotato.oxygensystem.Util;

import cn.goldenpotato.oxygensystem.Config.MessageManager;
import cn.goldenpotato.oxygensystem.Item.*;
import cn.goldenpotato.oxygensystem.Oxygen.*;
import cn.goldenpotato.oxygensystem.OxygenSystem;
import com.jeff_media.customblockdata.CustomBlockData;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class OxygenUtil
{
    public static void ShowOxygen(Player player)
    {
        float per = (float) OxygenSystem.playerOxygen.get(player.getUniqueId()) / OxygenCalculator.GetMaxOxygen(player);
        int cnt = Math.round(per * 20);
        if(SealedRoomCalculator.GetBelong(player.getLocation())!=0 && cnt==20)
            return;

        StringBuilder result = new StringBuilder(MessageManager.msg.Oxygen);
        for(int i=1;i<=cnt;i++)
            result.append(MessageManager.msg.OxygenAvailable);
        for(int i=cnt+1;i<=20;i++)
            result.append(MessageManager.msg.OxygenUsed);
        Util.SendActionBar(player, result.toString());
    }

    public static boolean CheckOxygenGenerator(Block block)
    {
        PersistentDataContainer data = new CustomBlockData(block, OxygenSystem.instance);
        return data.has(OxygenGenerator.oxygenGeneratorKey, PersistentDataType.INTEGER);
    }

    public static boolean CheckOxygenStation(Block block)
    {
        PersistentDataContainer data = new CustomBlockData(block, OxygenSystem.instance);
        return data.has(OxygenStation.oxygenStationKey, PersistentDataType.INTEGER);
    }

    /**
     * Try to get value from PersistentDataContainer.
     * @param block The block to get value from.
     * @param key Key
     * @return Value,if not found,return -1
     */
    public static int GetKey(Block block,NamespacedKey key)
    {
        PersistentDataContainer data = new CustomBlockData(block, OxygenSystem.instance);
        if (!data.has(key, PersistentDataType.INTEGER)) return -1;
        //noinspection ConstantConditions
        return data.get(key, PersistentDataType.INTEGER);
    }

    public static void SetKey(Block block, NamespacedKey key, int value)
    {
        PersistentDataContainer data = new CustomBlockData(block, OxygenSystem.instance);
        data.set(key,PersistentDataType.INTEGER,value);
    }

    public static void RemoveKey(Block block, NamespacedKey key)
    {
        PersistentDataContainer data = new CustomBlockData(block, OxygenSystem.instance);
        data.remove(key);
    }
}
