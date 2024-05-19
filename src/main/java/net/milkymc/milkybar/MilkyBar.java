package net.milkymc.milkybar;

import org.bukkit.Bukkit;
import org.bukkit.boss.BossBar;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.EventHandler;

public final class MilkyBar extends JavaPlugin {

    private BossBar bossBar;

    @Override
    public void onEnable() {



        CSV_Interface csv = new CSV_Interface(this);


        getServer().getPluginManager().registerEvents(new JoinListener(this, csv), this);

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Bukkit.broadcastMessage("Welcome to the server!");
    }

    @Override
    public void onDisable() {
        // Remove the boss bar when the plugin is disabled
        bossBar.removeAll();
    }
}
