package dev.hoskovec.services;

import dev.hoskovec.bank.Account;
import dev.hoskovec.bank.Client;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public interface AccountService {

    boolean createAccount(int clientId);
    HashMap<Integer, Account> getClientAccounts(int id);
    Set<Account> getClientAccountByBalance(int clientId, double minBalance, double maxBalance);
    Account getAccountById(int clientId, int id);
    Account updateAccountById(Account acct, int clientId, int id);
    boolean deleteAccountById(int clientId, int id);


}
