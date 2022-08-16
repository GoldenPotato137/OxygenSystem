package cn.goldenpotato.oxygensystem.Oxygen;

import cn.goldenpotato.oxygensystem.Config.Config;
import cn.goldenpotato.oxygensystem.OxygenSystem;
import com.jeff_media.customblockdata.CustomBlockData;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class SealedRoomCalculator
{
    public static NamespacedKey borderKey = new NamespacedKey(OxygenSystem.instance, "border");
    public static NamespacedKey belongKey = new NamespacedKey(OxygenSystem.instance, "belong");

    public static int GetBelong(Block block)
    {
        PersistentDataContainer data = new CustomBlockData(block, OxygenSystem.instance);
        if (!data.has(belongKey, PersistentDataType.INTEGER)) return 0;
        int flag = data.has(borderKey, PersistentDataType.INTEGER)?-1:1;
        //noinspection ConstantConditions
        return data.get(belongKey, PersistentDataType.INTEGER) * flag;
    }

    public static int GetBelong(Location location)
    {
        return GetBelong(location.getBlock());
    }

    public static void RemoveBelong(Location location)
    {
        PersistentDataContainer data = new CustomBlockData(location.getBlock(), OxygenSystem.instance);
        data.remove(borderKey);
        data.remove(belongKey);
    }

    public static void SetBelong(Location location,int no,boolean isBorder)
    {
        PersistentDataContainer data = new CustomBlockData(location.getBlock(), OxygenSystem.instance);
        data.set(belongKey,PersistentDataType.INTEGER,no);
        data.remove(borderKey);
        if(isBorder)
            data.set(borderKey,PersistentDataType.INTEGER, 1);
    }

    int centerX, centerY, centerZ;
    boolean[][][] vis;
    boolean Check(int x, int y, int z)
    {
        return x >= centerX - 45 && x <= centerX + 45 && y >= centerY - 45 && y <= centerY + 45 && z >= centerZ - 45 && z <= centerZ + 45;
    }

    boolean CheckInner(int x,int y,int z)
    {
        int[] dx = {-1, 1, 0, 0, 0, 0}, dz = {0, 0, -1, 1, 0, 0}, dy = {0, 0, 0, 0, -1, 1};
        for(int i=0;i<6;i++)
            if(!vis[x-centerX+50+dx[i]][y-centerY+50+dy[i]][z-centerZ+50+dz[i]])
                return false;
        return true;
    }

    Material[] glasses = {Material.RED_STAINED_GLASS,Material.ORANGE_STAINED_GLASS,Material.YELLOW_STAINED_GLASS,Material.GREEN_STAINED_GLASS,Material.BLUE_STAINED_GLASS};
    public int AddSealedRoom(Location location,int no)
    {
        if(no!=0) SetBelong(location,no,false);
        no = Math.abs(no);
        vis = new boolean[100][100][100];
        Queue<Location> toAdd = new LinkedList<>();
        centerX = location.getBlockX();
        centerY = location.getBlockY();
        centerZ = location.getBlockZ();
        Queue<Location> toCheck = new LinkedList<>();
        toCheck.add(location);
        vis[50][50][50] = true;
        boolean nearByAnotherRoom = false;
        while(!toCheck.isEmpty() && toAdd.size()<=10000 && !nearByAnotherRoom)
        {
            Location now = toCheck.poll();
            toAdd.add(now);
            if (now.getBlock().getType().isSolid() && now!=location)
                continue;
            int[] dx = {-1, 1, 0, 0, 0, 0}, dz = {0, 0, -1, 1, 0, 0}, dy = {0, 0, 0, 0, -1, 1};
            for (int i = 0; i < 6; i++)
                if (Check(now.getBlockX() + dx[i], now.getBlockY() + dy[i], now.getBlockZ() + dz[i])
                    && !vis[now.getBlockX() + dx[i] - centerX + 50][now.getBlockY() + dy[i] - centerY + 50][now.getBlockZ() + dz[i] - centerZ + 50])
                {
                    Location next = now.clone().add(dx[i], dy[i], dz[i]);
                    int tmp = GetBelong(next);
                    if(tmp!=0 && (no==0 || Math.abs(tmp)!=no))
                        nearByAnotherRoom = true;
                    vis[next.getBlockX() - centerX + 50][next.getBlockY() - centerY + 50][next.getBlockZ() - centerZ + 50] = true;
                    if(tmp==0)
                        toCheck.add(next);
                }
        }
        if(toAdd.size()>10000 || nearByAnotherRoom)
        {
            if(no!=0)
            {
                RemoveSealedRoom(location);
                return 2;
            }
            return 1;
        }
//        Util.Log("size:"+toAdd.size());
        if(no==0)
            no = new Random().nextInt(1919810)+1;
        while(!toAdd.isEmpty())
        {
            Location now = toAdd.poll();
            if(now.getBlock().getType().isSolid() && !CheckInner(now.getBlockX(),now.getBlockY(),now.getBlockZ()))
            {
                if(Config.Debug && !now.getBlock().getType().toString().contains("DOOR"))
                    now.getBlock().setType(glasses[no%glasses.length]);
                SetBelong(now,no,true);
            }
            else
                SetBelong(now,no,false);
        }
        return 0;
    }

    public void RemoveSealedRoom(Location location)
    {
        location = location.toBlockLocation();
        Queue<Location> queue = new LinkedList<>();
        queue.add(location);
        int target = Math.abs(GetBelong(location));
        if(target==0) return;
        RemoveBelong(location);
        while(!queue.isEmpty())
        {
            Location now = queue.poll();
            int[] dx = {-1, 1, 0, 0, 0, 0}, dz = {0, 0, -1, 1, 0, 0}, dy = {0, 0, 0, 0, -1, 1};
            for (int i = 0; i < 6; i++)
            {
                Location next = now.clone().add(dx[i], dy[i], dz[i]);
                if (Math.abs(GetBelong(next)) == target)
                {
                    if(Config.Debug && GetBelong(next)<0 && !next.getBlock().getType().toString().contains("DOOR"))
                        next.getBlock().setType(Material.GLASS);
                    RemoveBelong(next);
                    queue.add(next);
                }
            }
        }
    }
}
