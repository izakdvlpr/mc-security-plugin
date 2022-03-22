package com.izakdvlpr.security.commands;

import com.connorlinfoot.titleapi.TitleAPI;

import com.izakdvlpr.security.repositories.IpRepository;
import com.izakdvlpr.security.ZKSecurityMain;
import com.izakdvlpr.security.helpers.BCryptHelper;
import com.izakdvlpr.security.repositories.UserRepository;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class RegisterCommand implements CommandExecutor {
  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (!(sender instanceof Player)) {
      sender.sendMessage("§cComando apenas para jogadores dentro do jogo!");

      return true;
    }

    Player player = (Player) sender;
    String userId = player.getUniqueId().toString();

    boolean userAlreadyCreated = UserRepository.verifyUser(userId);

    if (userAlreadyCreated) {
      sender.sendMessage("§cVocê já está registrado!");

      player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1.0F, 0.5F);

      return true;
    }

    String ip = player.getAddress().getHostString();

    boolean ipAlreadyRegistered = IpRepository.findIp(ip);
    int MAX_REGISTER_IP = 1;

    if (ipAlreadyRegistered && MAX_REGISTER_IP == 1) {
      player.kickPlayer("§cLimite de contas criadas neste ip excedido!");

      return true;
    }

    if (!(args.length == 2)) {
      sender.sendMessage("§eFalta argumentos! /registrar <senha> <senha>");

      player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1.0F, 0.5F);

      return true;
    }

    String password = args[0];
    String confirm_password = args[1];

    if (!Objects.equals(confirm_password, password)) {
      sender.sendMessage("§cAs senhas não coincidem.");

      player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1.0F, 0.5F);

      return true;
    }

    int MIN_PASSWORD_SIZE = 4;

    if (password.length() < MIN_PASSWORD_SIZE || confirm_password.length() < MIN_PASSWORD_SIZE) {
      sender.sendMessage("§fSua senha é §c§lFRACA§f tente outra!");

      return true;
    }

    String nickname = player.getName();
    String encryptedPassword = BCryptHelper.generate(password, 10);

    IpRepository.addIp(ip);
    UserRepository.createUser(userId, nickname, encryptedPassword);

    ZKSecurityMain.usersLogged.add(player.getName());

    player.playSound(player.getLocation(), Sound.LEVEL_UP, 1.0F, 0.5F);

    TitleAPI.sendTitle(player, "§a§lRegistrado!", "Bom jogo :D", 20, 100, 20);

    sender.sendMessage(new String[]{
      "",
      "§aRegistrado com sucesso!",
      "",
      "§6§lDICA§f: Nunca passe sua senha para ninguém.",
      ""
    });

    if (password.length() >= 5) {
      sender.sendMessage("§fSua senha é §e§lMEDIO§f! Muito bom.");

      return true;
    }

    if (password.length() >= 8 && password.matches("[0-9]")) {
      sender.sendMessage("§fSua senha é §a§lFORTE§f! Bom jogo :D");

      return  true;
    }

    return true;
  }
}
