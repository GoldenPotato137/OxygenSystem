package cn.goldenpotato.oxygensystem.Oxygen;

import dev.lone.itemsadder.api.FontImages.PlayerHudsHolderWrapper;
import dev.lone.itemsadder.api.FontImages.PlayerQuantityHudWrapper;
import org.bukkit.entity.Player;

public class PlayerData
{
    public double oxygen; //Player's oxygen
    public PlayerHudsHolderWrapper hudHolder;
    public PlayerQuantityHudWrapper oxygenHudType1;
    public PlayerQuantityHudWrapper oxygenHudType2;

    public PlayerData(int MaxOxygen, Player player)
    {
        oxygen = MaxOxygen;
        hudHolder = new PlayerHudsHolderWrapper(player);
        oxygenHudType1 = new PlayerQuantityHudWrapper(hudHolder, "oxygensystem:oxygen_hud_type1");
        oxygenHudType2 = new PlayerQuantityHudWrapper(hudHolder, "oxygensystem:oxygen_hud_type2");
    }
}
