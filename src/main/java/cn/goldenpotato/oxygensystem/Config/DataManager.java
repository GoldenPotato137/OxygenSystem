package cn.goldenpotato.oxygensystem.Config;

import cn.goldenpotato.oxygensystem.Oxygen.PlayerData;
import cn.goldenpotato.oxygensystem.Oxygen.PlayerManager;
import cn.goldenpotato.oxygensystem.OxygenSystem;
import com.google.gson.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.*;
import java.nio.file.Files;
import java.util.UUID;

public class DataManager {
    private static final File PlayerDataFolder = new File(OxygenSystem.instance.getDataFolder(), "data");

    public static void LoadData() {
        //load player data
        try {
            JsonArray arr = LoadJson("player.json");
            for (JsonElement object : arr) {
                JsonObject json = object.getAsJsonObject();
                Player player = Bukkit.getPlayer(UUID.fromString(json.get("uuid").getAsString()));
                if(player!=null)
                    PlayerManager.GetPlayerData(player).oxygen = json.get("oxygen").getAsDouble();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public static void Save() {
        JsonArray array = new JsonArray();
        for(UUID uuid : PlayerManager.playerData.keySet()) {
            JsonObject jo = new JsonObject();
            jo.addProperty("uuid", uuid.toString());
            PlayerData data = PlayerManager.playerData.get(uuid);
            jo.addProperty("oxygen",data.oxygen);
            array.add(jo);
        }
        try {
            SaveJson("player.json", array);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static JsonArray LoadJson(String FileName) throws IOException {
        if(!PlayerDataFolder.exists()) {
            PlayerDataFolder.mkdirs();
        }
        File f = new File(PlayerDataFolder, FileName);
        if(!f.exists()) f.createNewFile();
        Reader reader = new InputStreamReader(Files.newInputStream(f.toPath()), "Utf-8");
        int ch = 0;
        StringBuffer sb = new StringBuffer();
        while ((ch = reader.read()) != -1) {
            sb.append((char) ch);
        }
        reader.close();
        String jsonStr = sb.toString();
        JsonArray jo = null;
        try {
            jo = new JsonParser().parse(jsonStr).getAsJsonArray();
        } catch (IllegalStateException e) {
            jo = new JsonArray();
        }
        return jo;
    }

    private static void SaveJson(String FileName, JsonArray jsonObject) throws IOException {
        GsonBuilder gb = new GsonBuilder();
        gb.setPrettyPrinting();
        String jsonString = gb.create().toJson(jsonObject);
        if(!PlayerDataFolder.exists()) {
            PlayerDataFolder.mkdirs();
        }
        File f = new File(PlayerDataFolder, FileName);
        if(!f.exists()) f.createNewFile();
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(Files.newOutputStream(f.toPath()));
        outputStreamWriter.write(jsonString);
        outputStreamWriter.close();
    }
}
