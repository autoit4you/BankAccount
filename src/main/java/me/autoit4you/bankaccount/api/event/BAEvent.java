package me.autoit4you.bankaccount.api.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Project: BankAccount
 * Author: autoit4you
 */
public class BAEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
