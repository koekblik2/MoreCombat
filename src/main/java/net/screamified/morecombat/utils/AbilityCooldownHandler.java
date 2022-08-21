package net.screamified.morecombat.utils;

import java.util.*;

public class AbilityCooldownHandler {


    private final Map<UUID, Integer> cooldowns = new HashMap<>();

    public void setCooldown(UUID player, int time){
        if(time < 1) {
            cooldowns.remove(player);
        } else {
            cooldowns.put(player, time);
        }
    }

    public int getCooldown(UUID player){
        return cooldowns.getOrDefault(player, 0);
    }



}
