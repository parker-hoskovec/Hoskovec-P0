package dev.hoskovec.DAOTests;


import dev.hoskovec.bank.Client;
import dev.hoskovec.daos.BankDAO;
import dev.hoskovec.daos.BankDAOLocal;
import dev.hoskovec.daos.BankDAOPostgres;
import org.junit.jupiter.api.*;

import java.util.Set;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BankDAOTest {

    private static BankDAO bdao = new BankDAOPostgres();
    private static Client testClient = null;

    @Test
    @Order(1)
    void create_client(){
        Client dave = new Client(0, "Dave West");
        bdao.createClient(dave);
        testClient = dave;
        Assertions.assertNotEquals(0, dave.getId());
    }

    @Test
    @Order(2)
    void get_all_clients(){
        Client dave = new Client(0, "Lebron James");
        Client mario = new Client(0, "Mario Lopez");
        Client philip = new Client(0, "Philip Gomez");
        bdao.createClient(dave);
        bdao.createClient(mario);
        bdao.createClient(philip);
        Set<Client> allClients = bdao.getAllClients();
        Assertions.assertTrue(allClients.size() == 4);

    }

    @Test
    @Order(3)
    void get_client_by_id(){
        int id = testClient.getId();
        Client client = bdao.getClientById(id);
        Assertions.assertEquals(testClient.getClientName(), client.getClientName());
        System.out.println("The client retireved was "+ client);
    }

    @Test
    @Order(4)
    void update_client(){
        int id = testClient.getId();
        Client newClient = new Client(id, "Dave East");
        bdao.updateClient(newClient, id);
        Assertions.assertTrue(bdao.getClientById(id).getClientName().equals(newClient.getClientName()));
    }

    @Test
    @Order(5)
    void delete_client(){
        int id = testClient.getId();
        boolean result = bdao.deleteClient(id);
        Assertions.assertTrue(result);
    }
}
