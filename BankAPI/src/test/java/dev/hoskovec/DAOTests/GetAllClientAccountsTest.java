package dev.hoskovec.DAOTests;


import dev.hoskovec.bank.Account;
import dev.hoskovec.bank.Client;
import dev.hoskovec.daos.BankDAO;
import dev.hoskovec.services.AccountService;
import dev.hoskovec.services.AccountServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;


@ExtendWith(MockitoExtension.class)
public class GetAllClientAccountsTest {

    @Mock
    BankDAO bankDAO = null;

    private AccountService accountService = null;

    @BeforeEach
    void setUp(){
        Client dave = new Client(1, "Dave West");
        Account a1 = new Account();
        Account a2 = new Account();
        Account a3 = new Account();

        dave.addClientAccount(a1);
        dave.addClientAccount(a2);
        dave.addClientAccount(a3);

        Mockito.when(bankDAO.getClientById(dave.getId())).thenReturn(dave);

        this.accountService = new AccountServiceImpl(this.bankDAO);
    }

    @Test
    void get_all_client_acccounts(){
        Set<Account> accounts = new HashSet<Account>(this.accountService.getClientAccounts(1).values());
        Assertions.assertEquals(3, accounts.size());
    }
}