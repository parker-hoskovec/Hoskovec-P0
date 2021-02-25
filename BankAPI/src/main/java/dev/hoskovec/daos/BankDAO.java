package dev.hoskovec.daos;

import dev.hoskovec.bank.Client;

import java.util.List;
import java.util.Set;

public interface BankDAO {

    Client createClient(Client client);
    Set<Client> getAllClients();
    Client getClientById(int id);
    Client updateClient(Client client, int id);
    boolean deleteClient(int id);


}
