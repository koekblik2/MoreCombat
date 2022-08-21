package net.screamified.morecombat;

import net.screamified.morecombat.Listeners.PlayerListener;
import net.screamified.morecombat.commands.CommandHandler;
import net.screamified.morecombat.utils.BowAbilityHandler;
import net.screamified.morecombat.utils.PotionEffectHandler;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class MoreCombat extends JavaPlugin {

    private static Plugin plugin;

    public static MoreCombat p;

    public static Plugin getPlugin() {return plugin;}

    public MoreCombat() {
        p = this;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;


        // Initialize
        registerEvents();
        CommandHandler.registerCommands();
        PotionEffectHandler.LoadPotionEffects();




    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void registerEvents() {
        PluginManager pluginManager = getServer().getPluginManager();

        // Register events
        pluginManager.registerEvents(new PlayerListener(), this);

//        pluginManager.registerEvents(new CommandListener(this), this);
    }
}
