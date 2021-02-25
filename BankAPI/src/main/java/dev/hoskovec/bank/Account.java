package dev.hoskovec.bank;


public class Account {

    int id;
    double balance;

    public Account(){
        this.balance = 0;
    }

    public Account(double balance){
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
