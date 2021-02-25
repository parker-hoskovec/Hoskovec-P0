package dev.hoskovec.servicetests;


import dev.hoskovec.bank.Account;
import dev.hoskovec.bank.Client;
import dev.hoskovec.daos.BankDAO;
import dev.hoskovec.daos.BankDAOLocal;
import dev.hoskovec.daos.BankDAOPostgres;
import dev.hoskovec.services.AccountService;
import dev.hoskovec.services.AccountServiceImpl;
import org.junit.jupiter.api.*;

import java.util.Set;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class BankServiceTest {

    private static BankDAO bdao = new BankDAOPostgres();
    //private static AccountService accountService = new AccountServiceImpl(bdao);
    private static AccountService accountService = new BankDAOPostgres();
    private static Client testClient;

    @Test
    @Order(1)
    void create_account_for_client(){
        Client dave = new Client(0, "Dave West");
        bdao.createClient(dave);
        testClient = dave;
        int clientId = dave.getId();
        boolean result = accountService.createAccount(clientId);
        Assertions.assertTrue(result);
    }

    @Test
    @Order(2)
    void get_all_accounts_for_client(){
        int clientId = testClient.getId();
        accountService.createAccount(clientId);
        accountService.createAccount(clientId);
        accountService.createAccount(clientId);
        Assertions.assertTrue(accountService.getClientAccounts(clientId).size() == 4);
    }

    @Test
    @Order(3)
    void update_account_for_client(){
        int clientId = testClient.getId();
        double balance = 2000;
        Account acct = new Account(balance);
        acct.setId(3);
        accountService.updateAccountById(acct, clientId, 3);
        Assertions.assertTrue(accountService.getClientAccounts(clientId).get(3).getBalance() == 2000);
    }

    @Test
    @Order(4)
    void get_account_by_id_for_client(){
        int clientId = testClient.getId();
        Assertions.assertTrue(accountService.getAccountById(clientId, 3).getId() == 3);
    }

    @Test
    @Order(5)
    void get_accounts_for_client_by_balance(){
        int clientId = testClient.getId();
        int minBalance = 1000;
        int maxBalance = 3000;
        Set<Account> returnedAccounts = accountService.getClientAccountByBalance(clientId, minBalance, maxBalance);
        Assertions.assertTrue(returnedAccounts.size()==1);
    }

    @Test
    @Order(6)
    void delete_account_by_id_for_client(){
        int clientId = testClient.getId();
        boolean result = accountService.deleteAccountById(clientId, 3);
        Assertions.assertTrue(result);
    }
}
