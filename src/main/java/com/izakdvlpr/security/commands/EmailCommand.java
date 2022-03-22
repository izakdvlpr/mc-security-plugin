package com.izakdvlpr.security.commands;

import com.izakdvlpr.security.ZKSecurityMain;
import com.izakdvlpr.security.services.MailerService;
import com.izakdvlpr.security.repositories.CodeRepository;
import com.izakdvlpr.security.repositories.UserRepository;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EmailCommand implements CommandExecutor {
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

    String userId = player.getUniqueId().toString();

    boolean codeAlreadyCreated = CodeRepository.verifyCodeByUserId(userId);

    if (codeAlreadyCreated) {
      sender.sendMessage("§dVocê já possui um pedido em andamento. Verifique seu e-mail.");

      return true;
    }

    if (!(args.length == 1)) {
      sender.sendMessage("§ePara cadastrar seu email, use: /email <seu email>");

      player.playSound(player.getLocation(), Sound.VILLAGER_YES, 1.0F, 0.5F);

      return true;
    }

    String from = "no-reply@servidor.com";
    String to = args[0];

    String code = CodeRepository.createCode(userId);

    UserRepository.updateUserEmail(userId, to);

    MailerService mailerService = new MailerService(ZKSecurityMain.getInstance());

    mailerService.sendEmail(
      from,
      to,
      "[Servidor] Verificação de E-mail",
      "Olá usuário este é seu código " + code + ", use o comando /verify " + code + " para validar este código e verificar seu perfil como seguro."
    );

    sender.sendMessage("§9Verifque seu e-mail e caixa de spam, você receberá um e-mail com instruções para verificar sua conta e deixá-la segura.");

    return true;
  }
}
