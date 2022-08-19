package cn.goldenpotato.oxygensystem.Config;

import cn.goldenpotato.oxygensystem.OxygenSystem;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class MessageManager
{
    public static Message msg;
    public static void LoadMessage()
    {
        msg = new Message();
        OxygenSystem.instance.saveResource("message_"+Config.Language +".yml",true);
        File messageFile = new File(OxygenSystem.instance.getDataFolder(), "message_"+Config.Language +".yml");
        FileConfiguration messageReader = YamlConfiguration.loadConfiguration(messageFile);

        msg.NoPermission = messageReader.getString("NoPermission","");
        msg.NoCommandInConsole =  messageReader.getString("NoCommandInConsole","");
        msg.WrongNum = messageReader.getString("WrongNum","");
        msg.Success = messageReader.getString("Success","");
        msg.BreakRoom = messageReader.getString("BreakRoom","");
        msg.UnableToAddRoom = messageReader.getString("UnableToAddRoom","");
        msg.NotInRoom = messageReader.getString("NotInRoom","");
        msg.Oxygen = messageReader.getString("Oxygen","");
        msg.OxygenAvailable = messageReader.getString("OxygenAvailable","");
        msg.OxygenUsed = messageReader.getString("OxygenUsed","");
        msg.EnteringRoom = messageReader.getString("EnteringRoom","");
        msg.LeavingRoom = messageReader.getString("LeavingRoom","");
        msg.OxygenMaskLore = messageReader.getString("OxygenMaskLore","");
        msg.Detector_GetRoom = messageReader.getString("Detector_GetRoom","");
        msg.Detector_GetRoom_NoRoom = messageReader.getString("Detector_GetRoom_NoRoom","");
        msg.Detector_GetRoom_Wall = messageReader.getString("Detector_GetRoom_Wall","");
        msg.AlreadySealed = messageReader.getString("AlreadySealed","");
        msg.NotEnabled = messageReader.getString("NotEnabled","");
        msg.OxygenStation_NotInRoom = messageReader.getString("OxygenStation_NotInRoom","");
        msg.CantPlace = messageReader.getString("CantPlace", "");
        msg.CantCraft = messageReader.getString("CantCraft", "");

        msg.MaskUpgrade_WrongTier = messageReader.getString("MaskUpgrade_WrongTier","");
        msg.Item_MaskUpgradeT1 = messageReader.getString("Item_MaskUpgradeT1","");
        msg.Item_MaskUpgradeT1_Lore = messageReader.getStringList("Item_MaskUpgradeT1_Lore");
        msg.Item_MaskUpgradeT2 = messageReader.getString("Item_MaskUpgradeT2","");
        msg.Item_MaskUpgradeT2_Lore = messageReader.getStringList("Item_MaskUpgradeT2_Lore");
        msg.Item_MaskUpgradeT3 = messageReader.getString("Item_MaskUpgradeT3","");
        msg.Item_MaskUpgradeT3_Lore = messageReader.getStringList("Item_MaskUpgradeT3_Lore");
        msg.Item_RoomDetector = messageReader.getString("Item_RoomDetector","");
        msg.Item_RoomDetector_Lore = messageReader.getStringList("Item_RoomDetector_Lore");
        msg.Item_OxygenGenerator = messageReader.getString("Item_OxygenGenerator","");
        msg.Item_OxygenGenerator_Lore = messageReader.getStringList("Item_OxygenGenerator_Lore");
        msg.Item_OxygenStation = messageReader.getString("Item_OxygenStation","");
        msg.Item_OxygenStation_Lore = messageReader.getStringList("Item_OxygenStation_Lore");
        msg.Item_BootStone = messageReader.getString("Item_BootStone","");
        msg.Item_BootStone_Lore = messageReader.getStringList("Item_BootStone_Lore");
        msg.Item_OxygenTank = messageReader.getString("Item_OxygenTank","");
        msg.Item_OxygenTank_Lore = messageReader.getStringList("Item_OxygenTank_Lore");
        msg.Item_OxygenTankProembryo = messageReader.getString("Item_OxygenTankProembryo","");
        msg.Item_OxygenTankProembryo_Lore = messageReader.getStringList("Item_OxygenTankProembryo_Lore");

        msg.SubCommand_Add_Usage = messageReader.getString("SubCommand_Add_Usage","");
        msg.SubCommand_Disable_Usage = messageReader.getString("SubCommand_Disable_Usage","");
        msg.SubCommand_Disable_NotEnabled = messageReader.getString("SubCommand_Disable_NotEnabled","");
        msg.SubCommand_Enable_Usage = messageReader.getString("SubCommand_Enable_Usage","");
        msg.SubCommand_Enable_AlreadyEnabled = messageReader.getString("SubCommand_Enable_AlreadyEnabled","");
        msg.SubCommand_Get_Usage = messageReader.getString("SubCommand_Get_Usage","");
        msg.SubCommand_Help_Usage = messageReader.getString("SubCommand_Help_Usage","");
        msg.SubCommand_Help_NoSuchCommand = messageReader.getString("SubCommand_Help_NoSuchCommand","");
        msg.SubCommand_Mask_Usage = messageReader.getString("SubCommand_Mask_Usage","");
        msg.SubCommand_Mask_NoHelmet = messageReader.getString("SubCommand_Mask_NoHelmet","");
        msg.SubCommand_Mask_FullLevel = messageReader.getString("SubCommand_Mask_FullLevel","");
        msg.SubCommand_Remove_Usage = messageReader.getString("SubCommand_Remove_Usage","");
        msg.SubCommand_EnableCave_Usage = messageReader.getString("SubCommand_EnableCave_Usage","");
        msg.SubCommand_EnableCave_AlreadyEnabled = messageReader.getString("SubCommand_EnableCave_AlreadyEnabled","");
        msg.SubCommand_DisableCave_Usage = messageReader.getString("SubCommand_DisableCave_Usage","");
        msg.SubCommand_DisableCave_NotEnabled = messageReader.getString("SubCommand_DisableCave_NotEnabled","");
        msg.SubCommand_SetWorldType_Usage = messageReader.getString("SubCommand_SetWorldType_Usage","");
        msg.SubCommand_SetWorldType_InvalidWorldType = messageReader.getString("SubCommand_SetWorldType_InvalidWorldType","");
    }
}