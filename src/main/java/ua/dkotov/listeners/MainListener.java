package ua.dkotov.listeners;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import ua.dkotov.HologramChatPlugin;

public class MainListener implements Listener {

    private HologramChatPlugin main;
    private long visibleTime;
    private String holoChatFormat;

    public MainListener(HologramChatPlugin hologramChatPlugin) {
        this.main = hologramChatPlugin;
        this.visibleTime = main.getConfig().getLong("visibleTime", 5000);
        this.holoChatFormat = main.getConfig().getString("holoChatFormat", "{playername}: {message}");
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onChat(AsyncPlayerChatEvent event) {
        chat(event.getPlayer(), event.getMessage());
    }

    private void chat(Player p, String msg){
        ArmorStand entity =
                (ArmorStand) p.getWorld().spawnEntity(p.getLocation(), EntityType.ARMOR_STAND);
        entity.setVisible(false);
        entity.setCustomName(holoChatFormat
                .replace("{message}", msg)
                .replace("{playername}", p.getDisplayName())
                .replace("&", "§")
        );
        entity.setCustomNameVisible(true);
        p.addPassenger(entity);
        main.getServer().getScheduler().runTaskLaterAsynchronously(
                main,
                task -> {
                    entity.remove();
                },
                visibleTime);
    }
}
