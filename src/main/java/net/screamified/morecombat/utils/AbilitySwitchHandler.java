package net.screamified.morecombat.utils;

import net.screamified.morecombat.Abilities.BowAblities.BowAbilities;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class AbilitySwitchHandler {

    public static void NextAbility(ItemStack bow) {
        ItemMeta bowItemMeta = bow.getItemMeta();
        if (!(bowItemMeta.hasLore())) { return; }
        if (!(bowItemMeta.hasDisplayName())) {
            bowItemMeta.setDisplayName("Bow");
        }

        List<String> abilities = bowItemMeta.getLore();
        String currentAbility = bowItemMeta.getDisplayName();
        int abilitiesLength = abilities.size();
        int indexOfCurrentAbility = abilities.indexOf(currentAbility);

        if ((indexOfCurrentAbility+1) == abilitiesLength) {
            bowItemMeta.setDisplayName("Bow");
        }
        else if (bowItemMeta.getDisplayName().equals("Bow")) {
            bowItemMeta.setDisplayName(abilities.get(0));
        }
        else {
            bowItemMeta.setDisplayName(abilities.get(indexOfCurrentAbility+1));
        }
        bow.setItemMeta(bowItemMeta);
    }

    public static void PreviousAbility(ItemStack bow) {
        ItemMeta bowItemMeta = bow.getItemMeta();
        if (!(bowItemMeta.hasLore())) { return; }
        if (!(bowItemMeta.hasDisplayName())) {
            bowItemMeta.setDisplayName("Bow");
        }

        List<String> abilities = bowItemMeta.getLore();
        String currentAbility = bowItemMeta.getDisplayName();
        int abilitiesLength = abilities.size();
        int indexOfCurrentAbility = abilities.indexOf(currentAbility);

        if ((indexOfCurrentAbility) == 0) {
            bowItemMeta.setDisplayName("Bow");
        }
        else if (bowItemMeta.getDisplayName().equals("Bow")) {
            bowItemMeta.setDisplayName(abilities.get(abilitiesLength-1));
        }
        else {
            bowItemMeta.setDisplayName(abilities.get(indexOfCurrentAbility-1));
        }
        bow.setItemMeta(bowItemMeta);
    }
}
