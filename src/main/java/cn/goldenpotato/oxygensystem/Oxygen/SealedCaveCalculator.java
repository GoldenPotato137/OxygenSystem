package cn.goldenpotato.oxygensystem.Oxygen;

import cn.goldenpotato.oxygensystem.Config.Config;
import cn.goldenpotato.oxygensystem.Util.Util;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.LinkedList;

public class SealedCaveCalculator {
    private static final LinkedList<Material> CaveBlockList = new LinkedList<>();
    public static boolean checkIsOnCave(Location center) {
        int count = 0;
        World w = center.getWorld();
        float version = Util.GetServerVersion();
        int min = version < 1.18 ? 0 : -64; //1.14的api没看到World.getMinHeight的实现
        int cx = center.getBlockX(), cy = center.getBlockY(), cz = center.getBlockZ();
        int s = Config.CheckCaveSize;
        int s1 = s/2;
        int s2 = (int) Math.pow(s, 3);
        for(int i = 0;i <= s2;i++) {
            int x = i%s+cx-s1, y = i/s%s+cy-Config.CaveCheckYShift, z =i/s/s%s+cz-s1;
            if(y > w.getMaxHeight() || y < min) continue;
            Location lo = new Location(w,x,y,z);
            Block ch = lo.getBlock();
            int hy = w.getHighestBlockAt(lo).getY();
            if(ch.isEmpty()) continue;
            if(ch.getLightFromSky() == 15) continue;
            for(Material m : CaveBlockList) {if(lo.getBlock().getType() == m && hy - y > 5) count++; break;}
            if(count >= s2*Config.CaveP) return true;
        }
        return false;
    }

    public static void Init(){
        for(String s : Config.CaveBlockList) CaveBlockList.add(Material.matchMaterial(s));
    }
}
