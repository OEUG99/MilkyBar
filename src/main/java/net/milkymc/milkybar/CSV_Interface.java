package net.milkymc.milkybar;

import org.bukkit.plugin.Plugin;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.bukkit.Bukkit.getLogger;

public class CSV_Interface {

    private final Plugin plugin;

    public CSV_Interface(Plugin plugin) {
        this.plugin = plugin;
        createFolder();
    }

    public List<String> readConfig() {
        List<String> lines = new ArrayList<>();
        File folder = new File(this.plugin.getDataFolder().toURI());
        File file = new File(folder, "config.csv");

        // Check if the file exists, if not, create it and write default content
        if (!file.exists()) {
            try {
                // Ensure the folder exists before creating the file
                if (!folder.exists() && !folder.mkdirs()) {
                    getLogger().warning("Failed to create MilkyBar folder. Cannot create config.csv.");
                    return lines;
                }

                if (file.createNewFile()) {
                    // File created successfully, now write the header
                    try (FileWriter writer = new FileWriter(file)) {
                        writer.write("Welcome to our server!\n");
                        writer.write("Don't forget to join our Discord server: discord.gg/example\n");
                        writer.write("Follow us on Twitter: @example_server\n");
                        writer.write("Visit our website: example.com\n");
                    }
                    getLogger().info("Empty config.csv generated successfully.");
                } else {
                    getLogger().warning("Failed to create config.csv file.");
                }
            } catch (IOException e) {
                getLogger().warning("An error occurred while creating config.csv: " + e.getMessage());
            }
        }

        // Read the lines from the file
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            getLogger().warning("An error occurred while reading config.csv: " + e.getMessage());
        }

        return lines;
    }

    private void createFolder() {
        File folder = new File(this.plugin.getDataFolder().toURI());

        // Check if the folder exists, if not, create it
        if (!folder.exists()) {
            if (folder.mkdirs()) {
                getLogger().info("MilkyBar folder created successfully.");
            } else {
                getLogger().warning("Failed to create MilkyBar folder.");
            }
        } else {
            getLogger().info("MilkyBar folder already exists.");
        }
    }

}
