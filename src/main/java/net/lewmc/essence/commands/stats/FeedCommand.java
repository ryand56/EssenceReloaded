package net.lewmc.essence.commands.stats;

import net.lewmc.essence.Essence;
import net.lewmc.essence.utils.MessageUtil;
import net.lewmc.essence.utils.PermissionHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

public class FeedCommand implements CommandExecutor {
    private final Essence plugin;

    /**
     * Constructor for the FeedCommand class.
     * @param plugin References to the main plugin class.
     */
    public FeedCommand(Essence plugin) {
        this.plugin = plugin;
    }

    /**
     * @param commandSender Information about who sent the command - player or console.
     * @param command Information about what command was sent.
     * @param s Command label - not used here.
     * @param args The command's arguments.
     * @return boolean true/false - was the command accepted and processed or not?
     */
    @Override
    public boolean onCommand(
        @NotNull CommandSender commandSender,
        @NotNull Command command,
        @NotNull String s, String[] args
    ) {
        MessageUtil message = new MessageUtil(commandSender, this.plugin);
        if (!(commandSender instanceof Player) && args.length == 0) {
            message.PrivateMessage("Usage: /feed <player>",true);
            return true;
        }
        PermissionHandler permission = new PermissionHandler(commandSender, message);

        if (command.getName().equalsIgnoreCase("feed")) {
            if (args.length > 0) {
                if (permission.has("essence.stats.feed.other")) {
                    String pName = args[0];
                    Player p = Bukkit.getPlayer(pName);
                    if (p != null) {
                        message.PrivateMessage("You fed "+p.getName(), false);
                        if (!(commandSender instanceof Player)) {
                            message.SendTo(p, "You've been fed by a magical force.", false);
                        } else {
                            message.SendTo(p, "You've been fed by "+commandSender.getName(), false);
                        }
                        p.setFoodLevel(20);
                    } else {
                        message.PrivateMessage("Player not found.", true);
                    }
                    return true;
                } else {
                    return permission.not();
                }
            } else {
                if (permission.has("essence.stats.feed")) {
                    Player player = (Player) commandSender;
                    player.setFoodLevel(20);
                    message.PrivateMessage("You've been fed!", false);
                    return true;
                } else {
                    return permission.not();
                }
            }
        }

        return true;
    }
}