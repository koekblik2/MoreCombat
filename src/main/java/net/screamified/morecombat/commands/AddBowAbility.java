package net.screamified.morecombat.commands;

import net.screamified.morecombat.Abilities.BowAblities.BowAbilities;
import net.screamified.morecombat.utils.BowAbilityHandler;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AddBowAbility implements TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Hey you can not hold an item! Only players can use this command.");
            return true;
        }
        Player player = (Player) sender;
        ItemStack getHeldItem = player.getInventory().getItem(EquipmentSlot.HAND);

        if (command.getName().equalsIgnoreCase("addbowability")) {
            if (args.length >= 1 && (getHeldItem.getType() == Material.BOW)) {
                BowAbilityHandler.setBowAbility(getHeldItem, args[0], player);
                return true;
            } else {
                player.sendMessage(ChatColor.RED + "[/addBowAbility <Ability>]");
                return true;
            }
        }

        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if(!(sender instanceof Player)) { return Collections.emptyList(); }
        List<String> completions = new ArrayList<>();


        if(command.getName().equalsIgnoreCase("addbowability")) {
            if (args.length <= 1) {
                StringUtil.copyPartialMatches(args[0], BowAbilities.getAbilityNames(), completions);
                return completions;
            }
        }


        return Collections.emptyList();
    }
}
