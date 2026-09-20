package com.customauth;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;

public final class CustomAuth extends JavaPlugin implements Listener, CommandExecutor {

    private final Set<String> loggedIn = new HashSet<>();

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getCommand("kayit").setExecutor(this);
        getCommand("giris").setExecutor(this);
        getLogger().info("CustomAuth aktif edildi!");
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        loggedIn.remove(player.getName());
        player.sendMessage(ChatColor.RED + "Lütfen /kayit <sifre> ile kayıt olun veya /giris <sifre> ile giriş yapın!");
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (!loggedIn.contains(player.getName())) {
            event.setTo(event.getFrom());
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Bu komut sadece oyun içinde kullanılabilir.");
            return true;
        }

        Player player = (Player) sender;

        if (command.getName().equalsIgnoreCase("kayit")) {
            if (args.length > 0) {
                loggedIn.add(player.getName());
                player.sendMessage(ChatColor.GREEN + "Başarıyla kayıt oldunuz ve giriş yaptınız!");
            } else {
                player.sendMessage(ChatColor.YELLOW + "Kullanım: /kayit <sifre>");
            }
            return true;
        }

        if (command.getName().equalsIgnoreCase("giris")) {
            if (args.length > 0) {
                loggedIn.add(player.getName());
                player.sendMessage(ChatColor.GREEN + "Başarıyla giriş yaptınız!");
            } else {
                player.sendMessage(ChatColor.YELLOW + "Kullanım: /giris <sifre>");
            }
            return true;
        }

        return false;
    }
}
