package dev.hoskovec.app;

import dev.hoskovec.controllers.BankController;
import io.javalin.Javalin;

public class App {

    public static void main(String[] args) {

        Javalin app = Javalin.create();

        BankController bankController = new BankController();

        app.post("/clients", bankController.createClientHandler);
        app.get("/clients", bankController.getAllClientsHandler);
        app.get("/clients/:id", bankController.getClientByIdHandler);
        app.put("/clients/:id", bankController.updateClientHandler);
        app.delete("/clients/:id", bankController.deleteClientHandler);
        app.post("/clients/:id/accounts", bankController.createClientAccountHandler);
        app.get("/clients/:id/accounts", bankController.getAllClientAccountsHandler);
        app.get("/clients/:id/accounts/:minBal/:maxBal", bankController.getClientAccountsByBalanceHandler);
        app.get("/clients/:id/accounts/:aid", bankController.getClientAccountByIdHandler);
        app.put("/clients/:id/accounts/:aid", bankController.updateClientAccountByIdHandler);
        app.delete("/clients/:id/accounts/:aid", bankController.deleteClientAccountHandler);

        app.start(7001);

    }


}
