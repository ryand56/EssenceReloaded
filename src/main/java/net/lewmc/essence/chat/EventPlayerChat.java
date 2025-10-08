package net.lewmc.essence.chat;

import net.lewmc.essence.Essence;
import net.lewmc.essence.core.UtilPlaceholder;
import net.lewmc.essence.core.UtilPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.List;

import org.bukkit.plugin.RegisteredServiceProvider;

import org.bukkit.entity.Player;
import org.bukkit.World;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;

/**
 * PlayerChatEvent fires when a player sends a message in chat.
 */
public class EventPlayerChat implements Listener {
    private final Essence plugin;
    private LuckPerms lp = null;

    /**
     * Constructs the class.
     * @param plugin Reference to the main Essence class.
     */
    public EventPlayerChat(Essence plugin) {
        this.plugin = plugin;
        RegisteredServiceProvider<LuckPerms> lpProvider = this.plugin.getServer().getServicesManager().getRegistration(LuckPerms.class);

        if (lpProvider != null)
            lp = lpProvider.getProvider();
    }

    /**
     * Fires when a player sends a message in chat.
     * @param event The AsyncPlayerChatEvent event.
     */
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if ((boolean) this.plugin.config.get("chat.manage-chat")) {
            String msg = event.getMessage();

            Player player = event.getPlayer();
            World world = player.getWorld();

            if (lp != null)
            {
              User user = lp.getPlayerAdapter(Player.class).getUser(player);
              String prefix = user.getCachedData().getMetaData().getPrefix();

              msg = new UtilPlaceholder(this.plugin, event.getPlayer()).replaceAll(prefix + " %essence_player%: " + msg);
            }
            else
              msg = new UtilPlaceholder(this.plugin, event.getPlayer()).replaceAll(this.plugin.config.get("chat.name-format") + " " + msg);

            if ((boolean) this.plugin.config.get("chat.allow-message-formatting")) {
                msg = ChatColor.translateAlternateColorCodes('&', msg);
            }

            // Escape %
            msg = msg.replace("%", "%%");

            event.setMessage(msg);
            event.setFormat(msg);

            for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                List<String> ignoring = (List<String>) new UtilPlayer(this.plugin).getPlayer(p.getUniqueId(), UtilPlayer.KEYS.USER_IGNORING_PLAYERS);
                if (!ignoring.contains(p.getUniqueId().toString())) {
                    p.sendMessage(msg);
                }
            }
            event.setCancelled(true);
        }
    }
}
