package cn.goldenpotato.oxygensystem;

import cn.goldenpotato.oxygensystem.Command.CommandManager;
import cn.goldenpotato.oxygensystem.Config.Config;
import cn.goldenpotato.oxygensystem.Config.ConfigManager;
import cn.goldenpotato.oxygensystem.Config.DataManager;
import cn.goldenpotato.oxygensystem.Config.MessageManager;
import cn.goldenpotato.oxygensystem.Item.*;
import cn.goldenpotato.oxygensystem.Listener.BlockListener;
import cn.goldenpotato.oxygensystem.Listener.ItemsAdderListener;
import cn.goldenpotato.oxygensystem.Listener.PlayerInteractListener;
import cn.goldenpotato.oxygensystem.Listener.PlayerJumpListener;
import cn.goldenpotato.oxygensystem.Metrics.Metrics;
import cn.goldenpotato.oxygensystem.Oxygen.PlayerManager;
import cn.goldenpotato.oxygensystem.Oxygen.SealedCaveCalculator;
import cn.goldenpotato.oxygensystem.Oxygen.SealedRoomCalculator;
import cn.goldenpotato.oxygensystem.Placeholder.PapiManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class OxygenSystem extends JavaPlugin
{
    public static OxygenSystem instance;
    public static SealedRoomCalculator roomCalculator;

    @Override
    public void onEnable()
    {
        instance = this;
        Load();

        //register commands
        CommandManager.Init();
        Objects.requireNonNull(Bukkit.getPluginCommand("oxygen")).setExecutor(new CommandManager());

        //register events
        Bukkit.getPluginManager().registerEvents(new BlockListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(), this);
        if(Config.IsPaper)
            Bukkit.getPluginManager().registerEvents(new PlayerJumpListener(),this);
        if(Config.ItemsAdder)
            Bukkit.getPluginManager().registerEvents(new ItemsAdderListener(),this);

        //Init
        roomCalculator = new SealedRoomCalculator();
        SealedCaveCalculator.Init();
        AddRecipe();
        PlayerManager.StartCalculate();

        //Placeholder
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI")!=null)
            new PapiManager().register();

        //Metrics
        int pluginId = 16115;
        Metrics metrics = new Metrics(this, pluginId);
        metrics.addCustomChart(new Metrics.SimplePie("locale", () -> Config.Language));
    }

    void AddRecipe()
    {
        if(!Config.EnableIngredient) return;
        Bukkit.addRecipe(MaskUpgradeT1.GetRecipe());
        Bukkit.addRecipe(MaskUpgradeT2.GetRecipe());
        Bukkit.addRecipe(MaskUpgradeT3.GetRecipe());
        Bukkit.addRecipe(OxygenGenerator.GetRecipe());
        Bukkit.addRecipe(OxygenStation.GetRecipe());
        Bukkit.addRecipe(BootStone.GetRecipe());
        Bukkit.addRecipe(OxygenTankProembryo.GetRecipe());
        Bukkit.addRecipe(OxygenTank.GetRecipe());
    }

    @Override
    public void onDisable()
    {
        PlayerManager.StopCalculate();
        Save();
        Bukkit.resetRecipes();
    }

    public static void Load()
    {
        ConfigManager.LoadConfig();
        MessageManager.LoadMessage();
        DataManager.LoadData();
    }

    public static void Save()
    {
        ConfigManager.Save();
        DataManager.Save();
    }
}