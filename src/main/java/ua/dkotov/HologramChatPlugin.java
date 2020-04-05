package ua.dkotov;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.plugin.ApiVersion;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.author.Author;
import ua.dkotov.listeners.MainListener;

@Plugin(name = "HologramChat", version = "1.0")
@Author("DKotov")
@ApiVersion
public class HologramChatPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new MainListener(this), this);
    }

    @Override
    public void onDisable() {
        saveConfig();
    }
}
