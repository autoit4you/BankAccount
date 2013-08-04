package me.autoit4you.bankaccount;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

/**
 * Project: BankAccount
 * Author: autoit4you
 */
public class LangManager {
    private String[] langs = new String[] {"en"};

    private String lang = "dr.who";
    private FileConfiguration langConf;
    private FileConfiguration enConf;

    LangManager(JavaPlugin plugin) {
        String lang = plugin.getConfig().getString("general.language", "en");
        for(String file : langs) {
            if(file == lang)
                this.lang = lang;
        }

        if(this.lang == "dr.who") {
            plugin.getLogger().warning("[BankAccount] Language not found using english instead.");
            this.lang = "en";
        }

        langConf = YamlConfiguration.loadConfiguration(plugin.getResource("lang/" + this.lang + ".yml"));
        enConf = YamlConfiguration.loadConfiguration(plugin.getResource("lang/en.yml"));
    }

    public String getLocalString(String id) {
        return langConf.getString(id, getString(id));
    }

    public String getString(String id) {
        return enConf.getString(id);
    }
}
