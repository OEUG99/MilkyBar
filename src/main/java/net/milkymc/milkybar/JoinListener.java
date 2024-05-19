package net.milkymc.milkybar;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

import static org.bukkit.Bukkit.getLogger;
import static org.bukkit.Bukkit.getServer;

import me.clip.placeholderapi.PlaceholderAPI;



public class JoinListener implements Listener {

    private final List<String> list;
    private int currentIndex = 0;


    private final JavaPlugin plugin;
    private final BossBar bossBar;

    private boolean isPlaceholderEnabled = false;

    public JoinListener(JavaPlugin plugin, CSV_Interface csv) {
        this.plugin = plugin;
        this.list = csv.readConfig();

        // create a single instance of boss bar that we will just update.
        // todo add the ability to change bar color and style.
        this.bossBar = Bukkit.createBossBar("", BarColor.PINK, BarStyle.SEGMENTED_6);
    }

    public void createBossBar(Player player) {

        // Determine if we have the placeholder API before trying to create boss bar.
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {

            Bukkit.getPluginManager().registerEvents(this, this.plugin);
            isPlaceholderEnabled = true;
        } else {
            /*
             * We inform about the fact that PlaceholderAPI isn't installed and then
             * disable this plugin to prevent issues.
             */
            getLogger().warning("Could not find PlaceholderAPI! This plugin is required.");
        }




        // Add the boss bar to the inputed player.
        this.bossBar.addPlayer(player);



        // Schedule a task to update the boss bar
        getServer().getScheduler().runTaskTimer(plugin, () -> {


            String preformattedOutput = this.list.get(currentIndex);
            String formattedOutput;

            // We check if placeholders are enable to determine if we need to parse them out.
            if (isPlaceholderEnabled) {
               formattedOutput = PlaceholderAPI.setPlaceholders(player, preformattedOutput); // Pass the player and string
            } else {
                formattedOutput = preformattedOutput;
            }


            // Update the boss bar's text
            this.bossBar.setTitle(formattedOutput);




            // Increment index or loop back to the beginning if we reached the end of the list
            currentIndex = (currentIndex + 1) % this.list.size();

            // You can also update other properties of the boss bar here
        }, 0L, 20L * 10); // Update every second (20 ticks)

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        createBossBar(player);


    }
}
