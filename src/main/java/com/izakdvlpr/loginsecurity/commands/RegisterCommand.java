package com.izakdvlpr.loginsecurity.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RegisterCommand implements CommandExecutor {
  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (!(sender instanceof Player) && args.length == 1) {
      sender.sendMessage("§cApenas jogadores in-game!");

      return true;
    }

    Player player = null;

    if (sender instanceof Player) {
      player = (Player) sender;
    }

    assert player != null;

    System.out.print(player.getUniqueId().toString());

    String password = args[0];

    System.out.print(password);

    sender.sendMessage("§2Login");

    return true;
  }
}
