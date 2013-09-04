package de.autoit4you.bankaccount.api.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Fired when any BankAccount event is fired.
 * <p>
 * Note the only purpose of this class is that not every event must implement {@code getHandlers()} and {@code getHandlers()}.
 * </p>
 */
public class BankAccountEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
