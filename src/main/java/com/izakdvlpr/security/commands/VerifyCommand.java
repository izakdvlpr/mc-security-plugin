package com.izakdvlpr.security.commands;

import com.izakdvlpr.security.ZKSecurityMain;
import com.izakdvlpr.security.repositories.CodeRepository;
import com.izakdvlpr.security.repositories.UserRepository;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VerifyCommand implements CommandExecutor {
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

    if (!codeAlreadyCreated) {
      sender.sendMessage("§dVocê não possui um pedido de verificação. Use /email <seuemail> para pedir.");

      return true;
    }

    if (!(args.length == 1)) {
      sender.sendMessage("§bPara verificar, use: /verify <code>");

      player.playSound(player.getLocation(), Sound.VILLAGER_YES, 1.0F, 0.5F);

      return true;
    }

    String code = args[0];

    boolean codeIsInvalid = CodeRepository.verifyCode(code);

    if (!codeIsInvalid) {
      sender.sendMessage("§cCódigo não inválido.");

      return true;
    }

    // String codeCreatedAt = CodeRepository.getCodeCreatedTimestamp(userId);

    // Date today = new Date();
    // Date time = new Date(Objects.requireNonNull(codeCreatedAt));

    // time.setHours(2);

    // https://github.com/TiagooAndrade/GoStack/blob/master/level-04/01-arquitetura-e-testes-no-nodejs/src/modules/users/services/ResetPasswordService.ts

    // if (today.after(time)) {
      // sender.sendMessage("§cCódigo expirado. Use /email <seuemail> para gerar um novo.");

      // return true;
    // }

    CodeRepository.updateCodeUsed(code, "1");
    UserRepository.updateUserVerify(userId, "1");

    sender.sendMessage("§aPerfil verificado com sucesso!.");

    return  true;
  }
}
