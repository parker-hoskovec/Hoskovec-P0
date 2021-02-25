package dev.hoskovec.bank;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;


public class Client {

    int id;
    String clientName;
    Map<Integer, Account> clientAccounts = new HashMap<Integer, Account>();
    private int numOfAccts = 0;

    public Client(){

    }

    public Client(int id, String clientName) {
        this.id = id;
        this.clientName = clientName;
        this.clientAccounts = clientAccounts;
    }

    public HashMap<Integer, Account> getClientAccounts() {
        return (HashMap<Integer, Account>) this.clientAccounts;
    }

    public void setClientAccounts(HashMap<Integer, Account> clientAccounts) {
        this.clientAccounts = clientAccounts;
    }

    public boolean addClientAccount(Account acct){
        acct.setId(++numOfAccts);
        clientAccounts.put(numOfAccts, acct);
        if(this.clientAccounts.containsValue(acct)){
            return true;
        }else{
            return false;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public int getNumOfAccts() {
        return numOfAccts;
    }

    public void setNumOfAccts(int numOfAccts) {
        this.numOfAccts = numOfAccts;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", clientName=" + clientName +
                ", clientAccounts=" + clientAccounts +
                '}';
    }
}
