package net.lewmc.essence.commands.teleportation.tp;

import net.lewmc.essence.Essence;
import net.lewmc.essence.utils.FileUtil;
import net.lewmc.essence.utils.LogUtil;
import net.lewmc.essence.utils.MessageUtil;
import net.lewmc.essence.utils.PermissionHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * /tpaccept command.
 */
public class TptoggleCommand implements CommandExecutor {
    private final Essence plugin;
    private final LogUtil log;

    /**
     * Constructor for the TptoggleCommand class.
     *
     * @param plugin References to the main plugin class.
     */
    public TptoggleCommand(Essence plugin) {
        this.plugin = plugin;
        this.log = new LogUtil(plugin);
    }

    /**
     * @param commandSender Information about who sent the command - player or console.
     * @param command       Information about what command was sent.
     * @param s             Command label - not used here.
     * @param args          The command's arguments.
     * @return boolean true/false - was the command accepted and processed or not?
     */
    @Override
    public boolean onCommand(
            @NotNull CommandSender commandSender,
            @NotNull Command command,
            @NotNull String s,
            String[] args
    ) {
        if (!(commandSender instanceof Player)) {
            this.log.noConsole();
            return true;
        }

        Player player = (Player) commandSender;

        if (command.getName().equalsIgnoreCase("tptoggle")) {
            MessageUtil message = new MessageUtil(commandSender, this.plugin);
            PermissionHandler permission = new PermissionHandler(commandSender, message);

            if (permission.has("essence.teleport.request.toggle")) {
                FileUtil file = new FileUtil(this.plugin);
                file.load(file.playerDataFile(player.getUniqueId()));

                if (file.getBoolean("user.accepting-teleport-requests")) {
                    file.set("user.accepting-teleport-requests", false);
                    message.PrivateMessage("teleport", "toggled", "disabled");
                } else {
                    file.set("user.accepting-teleport-requests", true);
                    message.PrivateMessage("teleport", "toggled", "enabled");
                }

                file.save();
            } else {
                return permission.not();
            }
        }
        return false;
    }
}