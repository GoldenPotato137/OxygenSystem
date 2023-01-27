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
        Config.GetServerType();
        Config.CheckItemsAdder();
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
        //For compatibility with older versions, the world type storage method remains unchanged
        Config.EnableWorlds = reader.getStringList("Worlds");
        Config.EnableCaveNonOxygenWorlds = reader.getStringList("CaveNonOxygenWorlds");
        Config.CheckCaveSize = reader.getInt("CaveCheckSize",11);
        Config.CaveCheckYShift = reader.getInt("CaveCheckYShift",1);
        Config.CaveP = reader.getDouble("CaveP",0.4);
        Config.CaveBlockList = reader.getStringList("CaveBlockList");
        //Oxygen
        Config.OxygenMask = reader.getIntegerList("OxygenMask");
        Config.OxygenTank = reader.getInt("OxygenTank",300);
        Config.RoomOxygenAdd = reader.getInt("RoomOxygenAdd",5);
        Config.OxygenStationOxygenAdd = reader.getInt("OxygenStationOxygenAdd",300);
        Config.OxygenReducedOnDamagedOthers = reader.getDouble("OxygenReducedOnDamagedOthers", 0.5);
        Config.OxygenReducedOnRunning = reader.getDouble("OxygenReducedOnRunning",0.2);
        Config.OxygenReducedOnJumping = reader.getDouble("OxygenReducedOnJumping",1.0);

        //Display
        Config.OxygenBar = reader.getBoolean("OxygenBar",true);
        Config.RoomMessage = reader.getBoolean("RoomMessage",true);

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

        //ItemsAdder
        Config.IA_Hud_OxygenHudType1 = reader.getBoolean("IA.Hud.OxygenHudType1",false);
        Config.IA_Hud_OxygenHudType2 = reader.getBoolean("IA.Hud.OxygenHudType2",false);

        //Debug
        Config.Debug = reader.getBoolean("Debug", false);


        //Check Config
        if(!Config.ItemsAdder)
        {
            if(Config.IA_Hud_OxygenHudType1 || Config.IA_Hud_OxygenHudType2)
                Util.Warning("ItemsAdder is not installed, but the configuration file is set to use ItemsAdder. Please check the configuration file.");
        }
        if(Config.IA_Hud_OxygenHudType1 || Config.IA_Hud_OxygenHudType2)
        {
            if(Config.RoomMessage || Config.OxygenBar)
                Util.Warning("You have enabled IA-Hud, but built-in HUD is still enabled. Please check the configuration file.");
        }
    }

    static public void Save()
    {
        //Save all config in case of plugin update

        FileConfiguration writer = OxygenSystem.instance.getConfig();
        //Worlds:
        writer.set("Worlds", Config.EnableWorlds);
        writer.set("CaveNonOxygenWorlds", Config.EnableCaveNonOxygenWorlds);
        writer.set("CaveCheckSize",Config.CheckCaveSize);
        writer.set("CaveCheckYShift",Config.CaveCheckYShift);
        writer.set("CaveP",Config.CaveP);
        writer.set("CaveBlockList",Config.CaveBlockList);
        //Oxygen
        writer.set("OxygenMask",Config.OxygenMask);
        writer.set("OxygenTank",Config.OxygenTank);
        writer.set("RoomOxygenAdd",Config.RoomOxygenAdd);
        writer.set("OxygenStationOxygenAdd",Config.OxygenStationOxygenAdd);
        writer.set("OxygenReducedOnDamagedOthers",Config.OxygenReducedOnDamagedOthers);
        writer.set("OxygenReducedOnRunning",Config.OxygenReducedOnRunning);
        writer.set("OxygenReducedOnJumping",Config.OxygenReducedOnJumping);
        //Display
        writer.set("OxygenBar",Config.OxygenBar);
        writer.set("RoomMessage",Config.RoomMessage);
        //Sound
        writer.set("PlayMachineStartUpSound",Config.PlayMachineStartUpSound);
        writer.set("PlayEnterRoomSound",Config.PlayEnterRoomSound);
        writer.set("PlayAirLockBreakSound",Config.PlayAirLockBreakSound);
        writer.set("PlayItemUpgradeSound",Config.PlayItemUpgradeSound);
        writer.set("PlayOxygenTankUseSound",Config.PlayOxygenTankUseSound);
        writer.set("PlayRefillOxygenSound",Config.PlayRefillOxygenSound);
        //Ingredient
        writer.set("EnableIngredient",Config.EnableIngredient);
        writer.set("OxygenMaskT1Ingredient",Config.OxygenMaskT1Ingredient);
        writer.set("OxygenMaskT2Ingredient",Config.OxygenMaskT2Ingredient);
        writer.set("OxygenMaskT3Ingredient",Config.OxygenMaskT3Ingredient);
        writer.set("OxygenGeneratorIngredient",Config.OxygenGeneratorIngredient);
        writer.set("OxygenStationIngredient",Config.OxygenStationIngredient);
        writer.set("BootStoneIngredient",Config.BootStoneIngredient);
        writer.set("OxygenTankProembryoIngredient",Config.OxygenTankProembryoIngredient);
        //ItemsAdder
        writer.set("IA.Hud.OxygenHudType1",Config.IA_Hud_OxygenHudType1);
        writer.set("IA.Hud.OxygenHudType2",Config.IA_Hud_OxygenHudType2);
        //Debug
        writer.set("Debug",Config.Debug);

        OxygenSystem.instance.saveConfig();
    }
}