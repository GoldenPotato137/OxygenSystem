package cn.goldenpotato.oxygensystem.Config;

import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Config
{
    public static String Language;
    public static boolean IsPaper = false;

    public static void GetServerType()
    {
        try
        {
            Class.forName("com.destroystokyo.paper.ParticleBuilder");
            IsPaper = true;
        }
        catch (ClassNotFoundException ignored)
        {
        }
    }

    public static boolean ItemsAdder = false; //Whether ItemsAdder is installed
    public static void CheckItemsAdder()
    {
        if(Bukkit.getPluginManager().getPlugin("ItemsAdder")!=null)
            ItemsAdder = true;
    }
    public static boolean ItemsAdderLoaded = false; //Whether ItemsAdder has loaded all resources

    public static List<String> EnableWorlds;
    public static List<String> EnableCaveNonOxygenWorlds;
    private static Map<String, WorldType> worldType;

    public static WorldType GetWorldType(World world)
    {
        if (worldType == null) worldType = new HashMap<>();
        if (!worldType.containsKey(world.getName()))
        {
            if (EnableWorlds.contains(world.getName()))
                worldType.put(world.getName(), WorldType.NON_OXYGEN);
            else if (EnableCaveNonOxygenWorlds.contains(world.getName()))
                worldType.put(world.getName(), WorldType.CAVE_NON_OXYGEN);
            else
                worldType.put(world.getName(), WorldType.NORMAL);
        }
        return worldType.get(world.getName());
    }

    public static void SetWorldType(World world, WorldType type)
    {
        EnableWorlds.remove(world.getName());
        EnableCaveNonOxygenWorlds.remove(world.getName());
        if (type == WorldType.NON_OXYGEN)
            EnableWorlds.add(world.getName());
        else if (type == WorldType.CAVE_NON_OXYGEN)
            EnableCaveNonOxygenWorlds.add(world.getName());
        worldType.put(world.getName(), type);
    }

    public static List<Integer> OxygenMask;
    public static List<String> CaveBlockList;

    public static boolean EnableIngredient;
    public static List<String> OxygenMaskT1Ingredient;
    public static List<String> OxygenMaskT2Ingredient;
    public static List<String> OxygenMaskT3Ingredient;
    public static List<String> OxygenGeneratorIngredient;
    public static List<String> OxygenStationIngredient;
    public static List<String> BootStoneIngredient;
    public static boolean Debug;
    public static List<String> OxygenTankProembryoIngredient;
    public static int OxygenTank;
    public static int RoomOxygenAdd;
    public static int OxygenStationOxygenAdd;
    public static boolean PlayMachineStartUpSound;
    public static boolean PlayEnterRoomSound;
    public static boolean PlayAirLockBreakSound;
    public static boolean PlayItemUpgradeSound;
    public static boolean PlayOxygenTankUseSound;
    public static boolean PlayRefillOxygenSound;
    public static double OxygenReducedOnDamagedOthers;
    public static double OxygenReducedOnRunning;
    public static double OxygenReducedOnJumping;
    public static int CheckCaveSize;
    public static double CaveP;
    public static int CaveCheckYShift;
    public static boolean OxygenBar;
    public static boolean RoomMessage;
    public static boolean IA_Hud_OxygenHudType1;
    public static boolean IA_Hud_OxygenHudType2;
}
