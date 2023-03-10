package link.ryhn.jpt;

import java.io.ObjectInputFilter.Config;
import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatColor;

public class JustPlaceholderTab extends JavaPlugin implements Listener {
	String tabFormat;

	@Override
	public void onEnable() {
		loadConfig();

		Bukkit.getScheduler().runTaskLater(this, new Runnable() {
			// When reloading, PAPI does not load expansions in onEnable, resulting in being
			// unable to use PAPI in onEnable
			// Wait 3 seconds and hope everything has loaded
			// If not, use the /reloadtablist command
			public void run() {
				for (Player p : Bukkit.getOnlinePlayers()) {
					SetTabName(p, tabFormat);
				}
			}
		}, 3 * 20);

		getCommand("reloadtablist").setExecutor(new CommandExecutor() {
			public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
				loadConfig();

				for (Player p : Bukkit.getOnlinePlayers()) {
					SetTabName(p, tabFormat);
				}
				return true;
			}
		});
		Bukkit.getPluginManager().registerEvents(this, this);
	}

	void loadConfig() {
		FileConfiguration c = getConfig();
		c.addDefault("format", "%player_displayname%");
		c.options().copyDefaults(true);
		saveConfig();

		tabFormat = c.getString("format");
	}

	@Override
	public void onDisable() {
		for (Player p : Bukkit.getOnlinePlayers())
			p.setPlayerListName(null);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	void on(PlayerJoinEvent ev) {
		SetTabName(ev.getPlayer(), tabFormat);
	}

	void SetTabName(Player p, String format) {
		String s = ChatColor.translateAlternateColorCodes('&', PlaceholderAPI.setPlaceholders(p, format));
		getLogger().info(format);
		getLogger().info(s);
		p.setPlayerListName(s);
	}
}
