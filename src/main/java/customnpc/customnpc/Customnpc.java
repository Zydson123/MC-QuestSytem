package customnpc.customnpc;

import config.CustomConfig;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Customnpc extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        CustomConfig.setup();
        CustomConfig.get().addDefault("test", "pies");
        CustomConfig.get().options().copyDefaults(true);
        CustomConfig.reload();
        this.getServer().getPluginManager().registerEvents(new Join(),this);
    }
    @Override
    public void onDisable() {
        CustomConfig.save();
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(label.equalsIgnoreCase("createnpc")){
            if(sender instanceof Player){
                Player pl = (Player) sender;
                if(args.length==0){
                    NPC.createNPC(pl, pl.getName());
                    pl.sendMessage("NPC CREATED");
                }
                else{
                    NPC.createNPC(pl, args[0]);
                    pl.sendMessage("NPC CREATED");
                }
            }
        }
        if(label.equalsIgnoreCase("newGetQuest")){
            if(sender instanceof Player){
                FileConfiguration config = CustomConfig.get();
                Player pl = (Player) sender;
                if(pl.isOp()){
                    if(args[0]!=null && args[1]!=null){
                        String what = args[0];
                        int amount = Integer.parseInt(args[1]);
                        Material material = Material.getMaterial(what);
                        if(material != null){
                            String questName = ("get" + what);
                            pl.sendMessage(questName);
                            config.createSection(pl.getName() + ".Quests." + questName + ".amount");
                            config.createSection(pl.getName() + ".Quests." + questName + ".progress");
                            config.set(pl.getName() + ".Quests." + questName + ".amount", amount);
                            config.set(pl.getName() + ".Quests." + questName + ".progress", 0);
                            config.addDefault(pl.getName() + ".Quests." + questName + ".amount", amount);
                            config.addDefault(pl.getName() + ".Quests." + questName + ".progress", 0);
                            int pies = config.getInt(pl.getName() + ".Quests." + questName + ".amount");
                            int kot = config.getInt(pl.getName() + ".Quests." + questName + ".progress");
                            pl.sendMessage(String.valueOf(pies));
                            pl.sendMessage(String.valueOf(kot));
                            CustomConfig.save();
                            CustomConfig.reload();
                        }
                        else{
                            pl.sendMessage("That is not a proper item!");
                        }
                    }
                    else{
                        pl.sendMessage("Please put in 2 arguments!");
                    }
                }
                else{
                    pl.sendMessage("You're not an operator!!");
                }
            }
        }
        else if(command.getName().equalsIgnoreCase("getCustomConfig")){
            if (sender instanceof Player) {
                FileConfiguration config = CustomConfig.get();
                Player pl = (Player) sender;
                String what = "ARROW";
                String questName = ("get" + what);
                String nameOfPath = pl.getName() + ".Quests." + questName + ".amount";
                String nameOfPath2 = pl.getName() + ".Quests." + questName + ".progress";
                pl.sendMessage("amount: " + config.getString(nameOfPath));
                pl.sendMessage("progress: " + config.getString(nameOfPath2));
            }
        }
        return false;
    }
}
