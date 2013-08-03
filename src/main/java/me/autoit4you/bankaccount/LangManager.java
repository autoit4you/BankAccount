package me.autoit4you.bankaccount;

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

    LangManager(String lang, JavaPlugin plugin) {
        for(String file : langs) {
            if(file == lang)
                this.lang = lang;
        }

        if(this.lang == "dr.who") {
            plugin.getLogger().warning("[BankAccount] Language not found using english instead.");
            this.lang = "en";
        }

        langConf = YamlConfiguration.loadConfiguration(plugin.getResource("lang/" + this.lang + ".yml"));
    }

    public String localString(String id) {
        return langConf.getString(id);
    }
}
