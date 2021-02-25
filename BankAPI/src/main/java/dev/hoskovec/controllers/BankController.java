package dev.hoskovec.controllers;

import com.google.gson.Gson;
import dev.hoskovec.bank.Account;
import dev.hoskovec.bank.Client;
import dev.hoskovec.daos.BankDAO;
import dev.hoskovec.daos.BankDAOLocal;
import dev.hoskovec.daos.BankDAOPostgres;
import dev.hoskovec.services.AccountService;
import dev.hoskovec.services.AccountServiceImpl;
import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.util.HashMap;
import java.util.Set;

public class BankController {

    private BankDAO bankDAO = new BankDAOPostgres();
    //private AccountService bankServ = new AccountServiceImpl(bankDAO);
    private AccountService bankServ = new BankDAOPostgres();

    public Handler createClientHandler = (Context ctx) -> {
        String body = ctx.body();
        Gson gson = new Gson();
        Client client = gson.fromJson(body, Client.class);
        this.bankDAO.createClient(client);
        ctx.status(201);
    };

    public Handler getAllClientsHandler = (ctx) -> {
        Set<Client> allClients = this.bankDAO.getAllClients();
        Gson gson = new Gson();
        String bankJSON = gson.toJson(allClients);
        ctx.result(bankJSON);
        ctx.status(200);
    };

    public Handler getClientByIdHandler = (ctx) -> {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Client client = this.bankDAO.getClientById(id);
        Gson gson = new Gson();
        String bankJSON = gson.toJson(client);
        ctx.result(bankJSON);
        ctx.status(200);
    };

    public Handler updateClientHandler = (ctx) -> {
        int id = Integer.parseInt(ctx.pathParam("id"));
        String body = ctx.body();
        Gson gson = new Gson();
        Client client = gson.fromJson(body, Client.class);
        client.setId(id);
        this.bankDAO.updateClient(client, id);

    };

    public Handler deleteClientHandler = (ctx) -> {
        int id = Integer.parseInt(ctx.pathParam("id"));
        boolean result = this.bankDAO.deleteClient(id);

        if (result){
            ctx.result("Client deleted");
        }else{
            ctx.result("Client not successfully deleted");
        }

    };

    public Handler createClientAccountHandler = (ctx) -> {
        int id = Integer.parseInt(ctx.pathParam("id"));
        this.bankServ.createAccount(id);
        ctx.status(201);
    };

    public Handler getAllClientAccountsHandler = (ctx) -> {
        int id = Integer.parseInt(ctx.pathParam("id"));
        HashMap<Integer, Account> clientAccounts = this.bankServ.getClientAccounts(id);
        Gson gson = new Gson();
        String allAccounts = gson.toJson(clientAccounts);
        ctx.result(allAccounts);
        ctx.status(200);
    };

    public Handler getClientAccountsByBalanceHandler = (ctx) -> {
        int id = Integer.parseInt(ctx.pathParam("id"));
        int minBal = Integer.parseInt(ctx.pathParam("minBal"));
        int maxBal = Integer.parseInt(ctx.pathParam("maxBal"));
        Set<Account> balAccounts = this.bankServ.getClientAccountByBalance(id, minBal, maxBal);

        Gson gson = new Gson();
        String balancedAccounts = gson.toJson(balAccounts);
        ctx.result(balancedAccounts);
        ctx.status(200);
    };

    public Handler getClientAccountByIdHandler = (ctx) -> {
        int id = Integer.parseInt(ctx.pathParam("id"));
        int acctId = Integer.parseInt(ctx.pathParam("aid"));
        Account account = this.bankServ.getAccountById(id, acctId);
        Gson gson = new Gson();
        String acct = gson.toJson(account);
        ctx.result(acct);
        ctx.status(200);
    };

    public Handler updateClientAccountByIdHandler = (ctx) -> {
        int id = Integer.parseInt(ctx.pathParam("id"));
        int acctId = Integer.parseInt(ctx.pathParam("aid"));
        String body = ctx.body();
        Gson gson = new Gson();
        Account acct = gson.fromJson(body, Account.class);
        acct.setId(acctId);
        this.bankServ.updateAccountById(acct, id, acctId);

    };

    public Handler deleteClientAccountHandler = (ctx) -> {
        int id = Integer.parseInt(ctx.pathParam("id"));
        int acctId = Integer.parseInt(ctx.pathParam("aid"));
        boolean result = this.bankServ.deleteAccountById(id, acctId);
        if (result){
            ctx.result("Account deleted");
        }else{
            ctx.result("Failed to find or delete account");
        }

    };

}
