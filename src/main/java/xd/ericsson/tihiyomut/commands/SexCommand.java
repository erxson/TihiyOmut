package xd.ericsson.tihiyomut.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import xd.ericsson.tihiyomut.config.Config;

import java.util.ArrayList;
import java.util.List;

public class SexCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        String subCommand = args[0].toLowerCase();

        if (subCommand.equals("reload") && sender.hasPermission("tihiyomut.sex.reload")) {
            Config.reloadConfig();
            sender.sendMessage(ChatColor.LIGHT_PURPLE + "Конфиг был перезагружен!");
            return true;
        } else if (subCommand.equals("toggle") && sender.hasPermission("tihiyomut.sex.toggle")) {
            Config.setConfigValue("enabled", !Config.isEnabled);
            Config.saveConfig();
            Config.reloadConfig();
            sender.sendMessage(ChatColor.LIGHT_PURPLE + "Секс был " + (Config.isEnabled ? "разрешен." : ChatColor.RED + "ЗАПРЕЩЁН!"));
            return true;
        } else return false;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            completions.add("reload");
            completions.add("toggle");
        }

        return completions;
    }

}