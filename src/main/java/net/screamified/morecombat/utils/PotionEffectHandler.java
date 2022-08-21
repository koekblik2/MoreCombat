package net.screamified.morecombat.utils;

import net.screamified.morecombat.MoreCombat;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public class PotionEffectHandler {

    private static final HashMap<String, PotionEffectType> potionEffects = new HashMap<>();

    public static void LoadPotionEffects(){
        for (PotionEffectType potion : PotionEffectType.values()) {
            potionEffects.put(potion.getName(), potion);
        }
    }

    public static HashMap<String, PotionEffectType> getPotionKeysValues() {
        return potionEffects;
    }

    public static void GiveEntityPotionEffect(ItemStack heldItem, LivingEntity targetEntity) {
        List<String> heldItemKeys = NBTHandler.getAllKeys(heldItem);
        List<PotionEffect> potionEffects = new ArrayList<>();

        for (String key : heldItemKeys) {
            String strippedKey = key.replace("morecombat:", "");
            String nbtValue = NBTHandler.getNBT(heldItem, strippedKey);

            assert nbtValue != null;
            HashMap<String, Integer> seperatedAmplifyAndTime = SeperateAmplifyAndTime(nbtValue);

            if (!(seperatedAmplifyAndTime.isEmpty())){
                potionEffects.add(new PotionEffect(Objects.requireNonNull(PotionEffectType.getByName(strippedKey.toUpperCase())),
                        seperatedAmplifyAndTime.get("time"), seperatedAmplifyAndTime.get("amplify")));
            }
        }
        targetEntity.addPotionEffects(potionEffects);
    }

    public static void AddPotionEffect(Player player, ItemStack heldItem, String[] args) {
        try {
            if (CheckTime(args[2]) == null) {
                player.sendMessage(ChatColor.RED + "The given time was not right.");
                return;
            }
            if (!(getPotionKeysValues().containsKey(args[0]))) {
                player.sendMessage(ChatColor.RED + "[" + MoreCombat.getPlugin().getName() + "] Invalid potion effect.");
                return;
            }

            String effect = Objects.requireNonNull(PotionEffectType.getByName(args[0])).getName();
            String amplify = args[1];
            String duration = CheckTime(args[2]);

            // Sets the Item lore with the given effect, amplifier, time.
            setPotionItemLore(heldItem, args);
            NBTHandler.addNBT(heldItem, effect, (amplify + "-" + duration));

        } catch (IllegalArgumentException e) {
            player.sendMessage(ChatColor.RED + "[" + MoreCombat.getPlugin().getName() + "] That is not right.");
        }
        player.sendMessage(ChatColor.GREEN + "[" + MoreCombat.getPlugin().getName() + "] Item successfully luuubed.");

    }

    public static void RemovePotionEffect(Player player, ItemStack heldItem, String effect) {
        try {
            List<String> heldItemKeys = NBTHandler.getAllKeys(heldItem);
            for (String key : heldItemKeys) {
                String strippedKey = key.replace("morecombat:", "");
                if (strippedKey.contains(effect.toLowerCase())) {
                    NBTHandler.removeNBT(heldItem, strippedKey);
                }
            }
            removePotionItemLore(heldItem, effect);

        } catch (IllegalArgumentException e) {
            player.sendMessage(ChatColor.RED + "[" + MoreCombat.getPlugin().getName() + "] That is not right.");
        }
        player.sendMessage(ChatColor.GREEN + "[" + MoreCombat.getPlugin().getName() + "] Item successfully Unluuubed.");
    }

    private static HashMap<String, Integer> SeperateAmplifyAndTime(String s) {
        // s = "1-1:30"
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        int seperator = s.indexOf("-");
        int amplify = Integer.parseInt(s.substring(0, seperator));
        int time = ConvertToTicks(s.substring(seperator+1));
        if (!(amplify > 255) || time == -1) {
            map.put("amplify", amplify);
            map.put("time", time);
        } else {
            return null;
        }
        return map;
    }

    private static void setPotionItemLore(ItemStack heldItem, String[] args) {
        List<String> itemLore = new ArrayList<>();
        ItemMeta heldItemMeta = heldItem.getItemMeta();
        if((heldItem.getItemMeta().hasLore())) {
            itemLore = heldItemMeta.getLore();
        }
        itemLore.add(String.format(ChatColor.BOLD + "" + ChatColor.DARK_PURPLE + "%1$s" + ChatColor.GOLD + " %2$s " + ChatColor.GREEN+ "Time: %3$s", args[0], args[1], CheckTime(args[2])));
        heldItemMeta.setLore(itemLore);
        heldItem.setItemMeta(heldItemMeta);
    }

    private static void removePotionItemLore(ItemStack heldItem, String effect) {
        ItemMeta heldItemMeta = heldItem.getItemMeta();
        List<String> itemLore = heldItemMeta.getLore();

        if(!(heldItem.getItemMeta().hasLore())) {return;}
        for (int i = 0; i < itemLore.size(); i++) {
            if (itemLore.get(i).contains(effect)) {
                itemLore.remove(i);
            }
        }
        heldItemMeta.setLore(itemLore);
        heldItem.setItemMeta(heldItemMeta);
    }

    private static int ConvertToTicks(String time) {
        int minutes;
        int seconds;
        int seperator = time.indexOf(":");

        minutes = Integer.parseInt(time.substring(0, seperator));
        seconds = Integer.parseInt(time.substring(seperator+1));

        return (((minutes * 60) + seconds) * 20);
    }

    private static String CheckTime(String time) {
        if (!(time.indexOf(":") == 1 || time.indexOf(":") == 2)) { return null; }
        if ((time.length() < 3) || (time.length() > 5)) { return null; }
        if (!(time.contains(":"))) { return null; }

        int seconds;
        int minutes;
        int seperator = time.indexOf(":");


        minutes = Integer.parseInt(time.substring(0, seperator));
        seconds = Integer.parseInt(time.substring(seperator+1));

        if (seconds >= 60) {
            minutes = minutes + (int)(seconds / 60);
            seconds = seconds % 60;
        }

        time = String.valueOf(minutes) + ":" + String.valueOf(seconds);

        return time;
    }
}
