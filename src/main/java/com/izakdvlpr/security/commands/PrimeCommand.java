package com.izakdvlpr.security.commands;

import com.izakdvlpr.security.ZKSecurityMain;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PrimeCommand implements CommandExecutor {
  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (!(sender instanceof Player)) {
      sender.sendMessage("§cComando apenas para jogadores dentro do jogo!");

      return true;
    }

    Player player = (Player) sender;

    boolean needLogged = ZKSecurityMain.usersLogged.contains(player.getName());

    if (!needLogged) {
      sender.sendMessage("§cVocê precisa estar logado.");

      return true;
    }

    if (!(args.length == 1)) {
      sender.sendMessage(new String[]{
        "",
        "§aPara vincular um número de celular, use:",
        "§e/celular <código do país> <número com DDD>",
        ""
      });

      player.playSound(player.getLocation(), Sound.VILLAGER_YES, 1.0F, 0.5F);

      return true;
    }


    return true;
  }
}
