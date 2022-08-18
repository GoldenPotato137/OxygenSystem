package cn.goldenpotato.oxygensystem.Oxygen;

import cn.goldenpotato.oxygensystem.Util.Util;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public class SealedCaveCalculator {
    private static final Material[] caveBlockList = new Material[]{Material.STONE, Material.GRANITE};

    public static boolean checkIsOnCave(Location center) {
        int count = 0;
        World w = center.getWorld();
        float version = Util.GetServerVersion();
        int min = version < 1.18 ? 0 : -64; //1.14的api没看到World.getMinHeight的实现
        int cx = center.getBlockX(), cy = center.getBlockY(), cz = center.getBlockZ();
        for(int i = 0;i <= 1330;i++) {
            int x = i%11 + cx - 5, y = i/11%11 + cy -5, z = i/11/11%11 +cz -5;
            if(y > w.getMaxHeight() || y < -64 || y < min) continue;
            Location lo = new Location(w,x,y,z);
            Block ch = lo.getBlock();
            int hy = w.getHighestBlockAt(lo).getY();
            if(ch.isEmpty()) continue;
            if(ch.getLightFromSky() == 15) continue;
            if(lo.getBlock().getType() == Material.STONE && hy - y > 5) count++;
            if(count >= 333) return true;
        }
        return false;
    }
}
