package dev.hoskovec.daos;

import dev.hoskovec.bank.Client;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BankDAOLocal implements BankDAO{

    private static Map<Integer, Client> bankClients = new HashMap<Integer, Client>();
    private static int clientId = 0;

    @Override
    public Client createClient(Client client) {
        client.setId(++clientId);
        bankClients.put(client.getId(), client);
        return client;
    }

    @Override
    public Set<Client> getAllClients() {
        Set<Client> allClients = new HashSet<Client>(bankClients.values());
        return allClients;
    }

    @Override
    public Client getClientById(int id) {

        return bankClients.get(id);

    }

    @Override
    public Client updateClient(Client client, int id) {
        return bankClients.put(id, client);

    }

    @Override
    public boolean deleteClient(int id) {

        Client client = bankClients.remove(id);
        if(client == null){
            return false;
        }else{
            return true;
        }
    }
}
