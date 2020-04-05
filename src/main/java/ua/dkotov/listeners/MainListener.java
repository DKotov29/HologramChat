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
        Player player = event.getPlayer();
        ArmorStand entity = (ArmorStand) player.getWorld().spawnEntity(player.getLocation(), EntityType.ARMOR_STAND);
        entity.setVisible(false);
        entity.setCustomName(holoChatFormat
                .replace("{message}", event.getMessage())
                .replace("{playername}", player.getDisplayName())
                .replace("&", "§")
        );
        entity.setCustomNameVisible(true);
        player.addPassenger(entity);
        main.getServer().getScheduler().runTaskLaterAsynchronously(
                main,
                task -> {
                    entity.remove();
                },
                visibleTime);
    }
}
