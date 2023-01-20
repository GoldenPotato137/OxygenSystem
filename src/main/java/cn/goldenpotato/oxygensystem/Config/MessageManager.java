package cn.goldenpotato.oxygensystem.Config;

import cn.goldenpotato.oxygensystem.OxygenSystem;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MessageManager
{
    public static Message msg;

    private static FileConfiguration localeFileManager; //实际的本地化文件
    private static FileConfiguration messageLocaleFileManager; //翻译过后的本地化文件
    private static FileConfiguration messageFileManager; //本地化文件的原版（Chinglish）

    /**
     * 按照locale -> message_xx-xx -> message的优先级加载本地化文件字段,并将其存储在locale中
     * @param path 字段路径
     * @return 字段内容
     */
    private static String GetString(String path)
    {
        String result = localeFileManager.getString(path);
        if(result == null)
        {
            result = messageLocaleFileManager.getString(path);
            if(result == null)
            {
                result = messageFileManager.getString(path);
            }
        }
        localeFileManager.set(path, result);
        return result;
    }

    /**
     * 按照locale -> message_xx-xx -> message的优先级加载本地化文件字段,并将其存储在locale中
     * @param path 字段路径
     * @return 字段内容
     */
    private static List<String> GetStringList(String path)
    {
        List<String> result = localeFileManager.getStringList(path);
        if(result.isEmpty())
        {
            result = messageLocaleFileManager.getStringList(path);
            if(result.isEmpty())
            {
                result = messageFileManager.getStringList(path);
            }
        }
        localeFileManager.set(path, result);
        return result;
    }

    public static void LoadMessage()
    {
        msg = new Message();
        File localeFile = new File(OxygenSystem.instance.getDataFolder(), "locale.yml");
        File messageLocaleFile = new File(OxygenSystem.instance.getDataFolder(), "message_"+Config.Language +".yml");
        File messageFile = new File(OxygenSystem.instance.getDataFolder(), "message.yml");

        OxygenSystem.instance.saveResource("message_"+Config.Language +".yml",false); //临时保存翻译后的本地化文件原版
        if(!localeFile.exists()) //如果locale文件不存在，则创建一个
        {
            try
            {
                if(!localeFile.createNewFile())
                    throw new IOException("Failed to create locale.yml");
            }
            catch (IOException e)
            {
                throw new RuntimeException(e);
            }
        }
        OxygenSystem.instance.saveResource("message.yml",false); //临时保存一份Chinglish原版

        localeFileManager = YamlConfiguration.loadConfiguration(localeFile);
        messageLocaleFileManager = YamlConfiguration.loadConfiguration(messageLocaleFile);
        messageFileManager = YamlConfiguration.loadConfiguration(messageFile);

        Load(); //加载本地化文件

        //删除临时保存的文件并保存合成后的locale
        try
        {
            localeFileManager.save(localeFile);
            if(!messageFile.delete() || !messageLocaleFile.delete())
            {
                OxygenSystem.instance.getLogger().warning("Failed to delete temporary files!");
            }
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * 加载本地化文件
     */
    private static void Load()
    {
        msg.NoPermission = GetString("NoPermission");
        msg.NoCommandInConsole =  GetString("NoCommandInConsole");
        msg.WrongNum = GetString("WrongNum");
        msg.Success = GetString("Success");
        msg.BreakRoom = GetString("BreakRoom");
        msg.UnableToAddRoom = GetString("UnableToAddRoom");
        msg.NotInRoom = GetString("NotInRoom");
        msg.Oxygen = GetString("Oxygen");
        msg.OxygenAvailable = GetString("OxygenAvailable");
        msg.OxygenUsed = GetString("OxygenUsed");
        msg.EnteringRoom = GetString("EnteringRoom");
        msg.LeavingRoom = GetString("LeavingRoom");
        msg.OxygenMaskLore = GetString("OxygenMaskLore");
        msg.Detector_GetRoom = GetString("Detector_GetRoom");
        msg.Detector_GetRoom_NoRoom = GetString("Detector_GetRoom_NoRoom");
        msg.Detector_GetRoom_Wall = GetString("Detector_GetRoom_Wall");
        msg.AlreadySealed = GetString("AlreadySealed");
        msg.NotEnabled = GetString("NotEnabled");
        msg.OxygenStation_NotInRoom = GetString("OxygenStation_NotInRoom");
        msg.CantPlace = GetString("CantPlace");
        msg.CantCraft = GetString("CantCraft");

        msg.MaskUpgrade_WrongTier = GetString("MaskUpgrade_WrongTier");
        msg.Item_MaskUpgradeT1 = GetString("Item_MaskUpgradeT1");
        msg.Item_MaskUpgradeT1_Lore = GetStringList("Item_MaskUpgradeT1_Lore");
        msg.Item_MaskUpgradeT2 = GetString("Item_MaskUpgradeT2");
        msg.Item_MaskUpgradeT2_Lore = GetStringList("Item_MaskUpgradeT2_Lore");
        msg.Item_MaskUpgradeT3 = GetString("Item_MaskUpgradeT3");
        msg.Item_MaskUpgradeT3_Lore = GetStringList("Item_MaskUpgradeT3_Lore");
        msg.Item_RoomDetector = GetString("Item_RoomDetector");
        msg.Item_RoomDetector_Lore = GetStringList("Item_RoomDetector_Lore");
        msg.Item_OxygenGenerator = GetString("Item_OxygenGenerator");
        msg.Item_OxygenGenerator_Lore = GetStringList("Item_OxygenGenerator_Lore");
        msg.Item_OxygenStation = GetString("Item_OxygenStation");
        msg.Item_OxygenStation_Lore = GetStringList("Item_OxygenStation_Lore");
        msg.Item_BootStone = GetString("Item_BootStone");
        msg.Item_BootStone_Lore = GetStringList("Item_BootStone_Lore");
        msg.Item_OxygenTank = GetString("Item_OxygenTank");
        msg.Item_OxygenTank_Lore = GetStringList("Item_OxygenTank_Lore");
        msg.Item_OxygenTankProembryo = GetString("Item_OxygenTankProembryo");
        msg.Item_OxygenTankProembryo_Lore = GetStringList("Item_OxygenTankProembryo_Lore");

        msg.SubCommand_Add_Usage = GetString("SubCommand_Add_Usage");
        msg.SubCommand_Disable_Usage = GetString("SubCommand_Disable_Usage");
        msg.SubCommand_Disable_NotEnabled = GetString("SubCommand_Disable_NotEnabled");
        msg.SubCommand_Enable_Usage = GetString("SubCommand_Enable_Usage");
        msg.SubCommand_Enable_AlreadyEnabled = GetString("SubCommand_Enable_AlreadyEnabled");
        msg.SubCommand_Get_Usage = GetString("SubCommand_Get_Usage");
        msg.SubCommand_Help_Usage = GetString("SubCommand_Help_Usage");
        msg.SubCommand_Help_NoSuchCommand = GetString("SubCommand_Help_NoSuchCommand");
        msg.SubCommand_Mask_Usage = GetString("SubCommand_Mask_Usage");
        msg.SubCommand_Mask_NoHelmet = GetString("SubCommand_Mask_NoHelmet");
        msg.SubCommand_Mask_FullLevel = GetString("SubCommand_Mask_FullLevel");
        msg.SubCommand_Reload_Usage = GetString("SubCommand_Reload_Usage");
        msg.SubCommand_Remove_Usage = GetString("SubCommand_Remove_Usage");
        msg.SubCommand_EnableCave_Usage = GetString("SubCommand_EnableCave_Usage");
        msg.SubCommand_EnableCave_AlreadyEnabled = GetString("SubCommand_EnableCave_AlreadyEnabled");
        msg.SubCommand_DisableCave_Usage = GetString("SubCommand_DisableCave_Usage");
        msg.SubCommand_DisableCave_NotEnabled = GetString("SubCommand_DisableCave_NotEnabled");
        msg.SubCommand_SetWorldType_Usage = GetString("SubCommand_SetWorldType_Usage");
        msg.SubCommand_SetWorldType_InvalidWorldType = GetString("SubCommand_SetWorldType_InvalidWorldType");
    }
}