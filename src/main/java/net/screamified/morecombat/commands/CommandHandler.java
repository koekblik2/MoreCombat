package net.screamified.morecombat.commands;


import net.screamified.morecombat.MoreCombat;
import org.bukkit.command.PluginCommand;

public class CommandHandler {
    private static void registerAddPotionEffectCommand() {
        PluginCommand command = MoreCombat.p.getCommand("addpotioneffect");
        command.setExecutor(new AddPotionEffect());
    }

    private static void registerAddBowAbilityCommand() {
        PluginCommand command = MoreCombat.p.getCommand("addbowability");
        command.setExecutor(new AddBowAbility());
    }
    private static void registerRemovePotionEffectCommand() {
        PluginCommand command = MoreCombat.p.getCommand("removepotioneffect");
        command.setExecutor(new RemovePotionEffect());
    }

//    private static void registerRemoveBowAbilityCommand() {
//        PluginCommand command = MoreCombat.p.getCommand("removebowability");
//        command.setExecutor(new AddBowAbility());
//    }

    public static void registerCommands() {
        registerAddPotionEffectCommand();
        registerRemovePotionEffectCommand();
        registerAddBowAbilityCommand();
    }

}
