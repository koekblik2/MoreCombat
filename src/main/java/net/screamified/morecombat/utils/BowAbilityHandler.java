package net.screamified.morecombat.utils;

import net.screamified.morecombat.Abilities.Abilities;
import net.screamified.morecombat.MoreCombat;
import net.screamified.morecombat.Abilities.BowAblities.BowAbilities;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class BowAbilityHandler implements Listener {

    public static final AbilityCooldownHandler cooldownHandler = new AbilityCooldownHandler();

    public static void setBowAbility(ItemStack bow, String abilityName, Player player) {
        List<String> itemLore = new ArrayList<>();
        ItemMeta heldItemMeta = bow.getItemMeta();
        if((bow.getItemMeta().hasLore())) {
            itemLore = heldItemMeta.getLore();
            for (String ability : itemLore) {
                if (abilityName.equalsIgnoreCase(ChatColor.stripColor(ability))) {
                    player.sendMessage(ChatColor.RED + "[" + MoreCombat.getPlugin().getName() + "] That ability is already on this bow.");
                    return;
                }
            }
        }

        for (Abilities ability : BowAbilities.values()) {
            if (abilityName.equalsIgnoreCase(ChatColor.stripColor(ability.toString()))) {
                itemLore.add(ability.toString());
                break;
            }
        }
        heldItemMeta.setLore(itemLore);
        bow.setItemMeta(heldItemMeta);
//        NBTHandler.addNBT(bow, "bowability", abilityName.toUpperCase());
    }


}
