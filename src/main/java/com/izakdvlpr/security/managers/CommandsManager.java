package com.izakdvlpr.security.managers;

import com.izakdvlpr.security.ZKSecurityMain;
import com.izakdvlpr.security.commands.LoginCommand;
import com.izakdvlpr.security.commands.RegisterCommand;
import com.izakdvlpr.security.commands.EmailCommand;
import com.izakdvlpr.security.commands.VerifyCommand;
import com.izakdvlpr.security.commands.PrimeCommand;

public class CommandsManager {
  public CommandsManager(ZKSecurityMain main) {
    LoginCommand login = new LoginCommand();
    RegisterCommand register = new RegisterCommand();
    EmailCommand email = new EmailCommand();
    VerifyCommand verify = new VerifyCommand();
    PrimeCommand prime = new PrimeCommand();

    main.getCommand("login").setExecutor(login);
    main.getCommand("register").setExecutor(register);
    main.getCommand("email").setExecutor(email);
    main.getCommand("verify").setExecutor(verify);
    main.getCommand("prime").setExecutor(prime);
  }
}
