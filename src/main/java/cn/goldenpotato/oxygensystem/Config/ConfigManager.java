package cn.goldenpotato.oxygensystem.Config;

import cn.goldenpotato.oxygensystem.OxygenSystem;
import cn.goldenpotato.oxygensystem.Util.Util;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager
{
    static boolean init = false;

    static void Init()
    {
        OxygenSystem.instance.saveDefaultConfig();
        init = true;
    }

    static public void LoadConfig()
    {
        if (!init) Init();
        OxygenSystem.instance.reloadConfig();
        FileConfiguration reader = OxygenSystem.instance.getConfig();
        //Language
        Config.Language = reader.getString("Language", "zh-CN");
        Util.Log("Using locale: " + Config.Language);
        //Worlds
        Config.EnableWorlds = reader.getStringList("Worlds");
        //Oxygen
        Config.OxygenMask = reader.getIntegerList("OxygenMask");
        Config.OxygenTank = reader.getInt("OxygenTank",300);
        Config.RoomOxygenAdd = reader.getInt("RoomOxygenAdd",5);
        Config.OxygenStationOxygenAdd = reader.getInt("OxygenStationOxygenAdd",300);
        Config.OxygenReducedOnDamagedOthers = reader.getDouble("OxygenReducedOnDamagedOthers", 0.5);
        Config.OxygenReducedOnRunning = reader.getDouble("OxygenReducedOnRunning",0.2);

        //Sound
        Config.PlayMachineStartUpSound = reader.getBoolean("PlayMachineStartUpSound",true);
        Config.PlayEnterRoomSound = reader.getBoolean("PlayEnterRoomSound",true);
        Config.PlayAirLockBreakSound = reader.getBoolean("PlayAirLockBreakSound",true);
        Config.PlayItemUpgradeSound = reader.getBoolean("PlayItemUpgradeSound",true);
        Config.PlayOxygenTankUseSound = reader.getBoolean("PlayOxygenTankUseSound",true);
        Config.PlayRefillOxygenSound = reader.getBoolean("PlayRefillOxygenSound",true);

        //Ingredient
        Config.EnableIngredient = reader.getBoolean("EnableIngredient", true);
        Config.OxygenMaskT1Ingredient = reader.getStringList("OxygenMaskT1Ingredient");
        Config.OxygenMaskT2Ingredient = reader.getStringList("OxygenMaskT2Ingredient");
        Config.OxygenMaskT3Ingredient = reader.getStringList("OxygenMaskT3Ingredient");
        Config.OxygenGeneratorIngredient = reader.getStringList("OxygenGeneratorIngredient");
        Config.OxygenStationIngredient = reader.getStringList("OxygenStationIngredient");
        Config.BootStoneIngredient = reader.getStringList("BootStoneIngredient");
        Config.OxygenTankProembryoIngredient = reader.getStringList("OxygenTankProembryoIngredient");

        //Debug
        Config.Debug = reader.getBoolean("Debug", false);
    }

    static public void Save()
    {
        FileConfiguration writer = OxygenSystem.instance.getConfig();
        //Worlds:
        writer.set("Worlds", Config.EnableWorlds);
        OxygenSystem.instance.saveConfig();
    }
}