package config;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class CustomConfig {
    private static File file;
    private static FileConfiguration customConfig;
    public static void setup(){
        File CustomFile = new File(Bukkit.getServer().getPluginManager().getPlugin("Customnpc").getDataFolder(), "customconfig.yml");
        file = CustomFile;
        if(!CustomFile.exists()){
            try {
                CustomFile.createNewFile();
            }
            catch (IOException e){

            }
        }
        customConfig = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration get(){
        return customConfig;
    }

    public static void save(){
        try{
            customConfig.save(file);
        }
        catch (IOException e){
            System.out.println("cant save file!");
        }
    }

    public static File getFile(){
        return file;
    }

    public static void reload(){
        customConfig = YamlConfiguration.loadConfiguration(file);
    }

}
