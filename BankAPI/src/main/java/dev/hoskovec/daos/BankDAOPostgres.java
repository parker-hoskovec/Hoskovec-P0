package dev.hoskovec.daos;

import dev.hoskovec.bank.Account;
import dev.hoskovec.bank.Client;
import dev.hoskovec.services.AccountService;
import dev.hoskovec.utils.ConnectionUtil;

import java.sql.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class BankDAOPostgres implements BankDAO, AccountService {
    @Override
    public Client createClient(Client client) {
        try(Connection conn = ConnectionUtil.createConnection()){

            String sql = "insert into Client (client_name) values (?)";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, client.getClientName());
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int key = rs.getInt("client_id");
            client.setId(key);
            return client;

        }catch (SQLException sqlException){
            sqlException.printStackTrace();
            return null;
        }
    }

    @Override
    public Set<Client> getAllClients() {

        try(Connection conn = ConnectionUtil.createConnection()){

            String sql = "select * from Client";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            Set<Client> allClients = new HashSet<Client>();
            while (rs.next()){
                Client client = new Client();
                client.setId(rs.getInt("client_id"));
                client.setClientName(rs.getString("client_name"));
                allClients.add(client);
            }
            return allClients;

        }catch (SQLException sqlException){
            sqlException.printStackTrace();
            return null;
        }

    }

    @Override
    public Client getClientById(int id) {
        try(Connection conn = ConnectionUtil.createConnection()){

            String sql = "select * from Client where client_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            Client client = new Client();
            client.setId(rs.getInt("client_id"));
            client.setClientName(rs.getString("client_name"));
            return client;

        }catch (SQLException sqlException){
            sqlException.printStackTrace();
            return null;
        }
    }

    @Override
    public Client updateClient(Client client, int id) {
        try(Connection conn = ConnectionUtil.createConnection()){

            String sql = "update Client set client_name = ? where client_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, client.getClientName());
            ps.setInt(2, id);
            ps.executeUpdate();
            return client;

        }catch (SQLException sqlException){
            sqlException.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteClient(int id) {
        try(Connection conn = ConnectionUtil.createConnection()){

            String sql = "delete from Client where client_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
            return true;

        }catch (SQLException sqlException){
            sqlException.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean createAccount(int clientId) {

        try(Connection conn = ConnectionUtil.createConnection()) {

            String sql = "insert into Account (client_id, balance) values (?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, clientId);
            ps.setFloat(2, 0);
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int key = rs.getInt("account_id");
            if (key != 0){
                return true;
            }else {
                return false;
            }
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
            return false;
        }

    }

    @Override
    public HashMap<Integer, Account> getClientAccounts(int id) {
        try(Connection conn = ConnectionUtil.createConnection()) {

            String sql = "select * from Account where client_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            HashMap<Integer, Account> accounts = new HashMap<Integer, Account>();
            while(rs.next()){
                Account acct = new Account();
                acct.setId(rs.getInt("account_id"));
                acct.setBalance(rs.getInt("balance"));
                accounts.put(acct.getId(), acct);
            }
            return accounts;

        }catch (SQLException sqlException){
            sqlException.printStackTrace();
            return null;
        }

    }

    @Override
    public Set<Account> getClientAccountByBalance(int clientId, double minBalance, double maxBalance) {
        try(Connection conn = ConnectionUtil.createConnection()) {

            String sql = "select * from Account where client_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, clientId);
            ResultSet rs = ps.executeQuery();
            HashSet<Account> accounts = new HashSet<Account>();
            while(rs.next()){
                Account acct = new Account();
                acct.setId(rs.getInt("account_id"));
                acct.setBalance(rs.getInt("balance"));
               if (acct.getBalance() >= minBalance && acct.getBalance() <= maxBalance){
                   accounts.add(acct);
               }
            }
            return accounts;

        }catch (SQLException sqlException){
            sqlException.printStackTrace();
            return null;
        }

    }

    @Override
    public Account getAccountById(int clientId, int id) {
        try(Connection conn = ConnectionUtil.createConnection()) {
            String sql = "select * from Account where client_id = ? and account_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, clientId);
            ps.setInt(2, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            Account account = new Account();
            account.setId(rs.getInt("account_id"));
            account.setBalance(rs.getFloat("balance"));
            return account;
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
            return null;
        }
    }

    @Override
    public Account updateAccountById(Account acct, int clientId, int id) {
        try(Connection conn = ConnectionUtil.createConnection()) {
            String sql = "update Account set balance = ? where client_id = ? and account_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setFloat(1, (float) acct.getBalance());
            ps.setInt(2, clientId);
            ps.setInt(3, id);
            ps.executeUpdate();
            return acct;

        }catch (SQLException sqlException){
            sqlException.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteAccountById(int clientId, int id) {
        try(Connection conn = ConnectionUtil.createConnection()) {

            String sql = "delete from Account where client_id = ? and account_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, clientId);
            ps.setInt(2, id);
            ps.execute();
            return true;

        }catch (SQLException sqlException){
            sqlException.printStackTrace();
            return false;
        }
    }
}
