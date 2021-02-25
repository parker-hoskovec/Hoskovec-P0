package dev.hoskovec.services;

import dev.hoskovec.bank.Account;
import dev.hoskovec.bank.Client;
import dev.hoskovec.daos.BankDAO;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AccountServiceImpl implements AccountService{

    private BankDAO bdao;

    public AccountServiceImpl(BankDAO bdao) {
        this.bdao = bdao;
    }

    @Override
    public boolean createAccount(int clientId) {
        Account acct = new Account();
        boolean result = this.bdao.getClientById(clientId).addClientAccount(acct);
        if (result){
            return true;
        }else{
            return false;
        }

    }

    @Override
    public HashMap<Integer, Account> getClientAccounts(int id) {
        return this.bdao.getClientById(id).getClientAccounts();
    }

    @Override
    public Set<Account> getClientAccountByBalance(int clientId, double minBalance, double maxBalance) {
        Set<Account> desiredAccounts = new HashSet<Account>();
        Set<Account> clientAccounts = new HashSet<Account>(this.bdao.getClientById(clientId).getClientAccounts().values());
        for(Account currentAccount: clientAccounts){
            if (currentAccount.getBalance() <= maxBalance && currentAccount.getBalance()>= minBalance){
                desiredAccounts.add(currentAccount);
            }
        }
        return desiredAccounts;
    }

    @Override
    public Account getAccountById(int clientId, int id) {
        Account acct = this.bdao.getClientById(clientId).getClientAccounts().get(id);
        return acct;
    }

    @Override
    public Account updateAccountById(Account acct, int clientId, int id) {
        return this.bdao.getClientById(clientId).getClientAccounts().put(id, acct);
    }

    @Override
    public boolean deleteAccountById(int clientId, int id){
        Account acct;
        acct = this.bdao.getClientById(clientId).getClientAccounts().remove(id);
        if(acct == null){
            return false;
        }else{
            return true;
        }
    }
}
