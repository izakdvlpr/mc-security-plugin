package com.izakdvlpr.loginsecurity.commands;

import com.connorlinfoot.titleapi.TitleAPI;
import com.izakdvlpr.loginsecurity.LoginSecurityMain;

import com.izakdvlpr.loginsecurity.helpers.PasswordHelper;
import com.izakdvlpr.loginsecurity.repositories.UserRepository;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

// TO DO:
// verificar se a senha é a mesma do banco; msg
// adicionar 3 tentativas de erro; msg/kick
// verificar se já tem um usuário online; kick

public class LoginCommand implements CommandExecutor {
  private static final HashMap usersWhoTried = new HashMap<String, Integer>();

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (!(sender instanceof Player)) {
      sender.sendMessage("§cComando apenas para jogadores dentro do jogo!");

      return true;
    }

    Player player = (Player) sender;
    String uuid = player.getUniqueId().toString();

    boolean userAlreadyNotCreated = UserRepository.verifyUser(uuid);

    if (!userAlreadyNotCreated) {
      sender.sendMessage("§cVocê precisa se registrar primeiro!");

      player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1.0F, 0.5F);

      return true;
    }

    boolean userAlreadyLogged = LoginSecurityMain.usersLogged.contains(player.getName());

    if (userAlreadyLogged) {
      sender.sendMessage("§eVocê já está logado!");

      player.playSound(player.getLocation(), Sound.VILLAGER_YES, 1.0F, 0.5F);

      return true;
    }

    if (!(args.length == 1)) {
      sender.sendMessage("§eFalta argumentos! /login <senha>");

      player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1.0F, 0.5F);

      return true;
    }

    String password = args[0];
    String userPassword = UserRepository.getUserPassword(uuid);

    boolean verifyPassword = PasswordHelper.compare(password, userPassword);

    int LIMIT_COUNT = 2;

    if (!verifyPassword) {
      if (usersWhoTried.containsKey(player.getName()) && ((Integer) usersWhoTried.get(player.getName())).equals(LIMIT_COUNT)) {
        player.kickPlayer("§cVocê excedeu o número limite de " + (LIMIT_COUNT + 1) + " tentativas de login, reconecte e tente novamente.");

        usersWhoTried.remove(player.getName());

        return true;
      }

      if (usersWhoTried.containsKey(player.getName())) {
        usersWhoTried.put(player.getName(), (Integer) usersWhoTried.get(player.getName()) + 1);
      } else {
        usersWhoTried.put(player.getName(), 1);
      }

      int attempts = (Integer) usersWhoTried.get(player.getName()) == 1 ? LIMIT_COUNT : LIMIT_COUNT - 1;

      sender.sendMessage("§cSenha incorreta! Você tem mais " + attempts + " tentativas.");

      return true;
    }

    LoginSecurityMain.usersLogged.add(player.getName());

    player.playSound(player.getLocation(), Sound.LEVEL_UP, 1.0F, 0.5F);

    TitleAPI.sendTitle(player, "§a§lAutenticado!", "Bom jogo :D", 20, 100, 20);

    sender.sendMessage(new String[]{
      "",
      "§aVocê logou com sucesso!",
      "",
      "§6§lDICA§f: Nunca passe sua senha para ninguém.",
      ""
    });

    return true;
  }
}
