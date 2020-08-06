package org.pronze.hypixelify.api.events;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.screamingsandals.bedwars.api.RunningTeam;

public class PlayerToolUpgradeEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private boolean isCancelled = false;
    private ItemStack stack;
    private String name;
    private  RunningTeam team;


    public PlayerToolUpgradeEvent(Player player, ItemStack stack, String name, RunningTeam team){
        this.player = player;
        this.stack = stack;
        this.name = name;
        this.team = team;
    }

    public RunningTeam getTeam(){
        return team;
    }


    public String getName(){
        return name;
    }

    public ItemStack getUpgradedItem(){
        return stack;
    }

    public Player getPlayer(){
        return player;
    }



    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        isCancelled = b;
    }

    @Override
    public HandlerList getHandlers() {
        return PlayerToolUpgradeEvent.handlers;
    }

    public static HandlerList getHandlerList() {
        return PlayerToolUpgradeEvent.handlers;
    }
}
