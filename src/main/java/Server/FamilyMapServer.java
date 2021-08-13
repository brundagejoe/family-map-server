package Server;

import DataAccess.DataAccessException;
import DataAccess.Database;
import Handler.*;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.Connection;

public class FamilyMapServer  {

    public static void main(String[] args) throws IOException {
        FamilyMapServer server = new FamilyMapServer();
        server.startServer(8080);
    }

    private void startServer(int port) throws IOException {
        InetSocketAddress serverAddress = new InetSocketAddress(port);
        HttpServer server = HttpServer.create(serverAddress, 10);
        registerHandlers(server);
        server.start();
        System.out.println("FamilyMapServer listening on port " + port);
    }

    private void registerHandlers(HttpServer server) {
        server.createContext("/", new FileHandler());
        server.createContext("/user/register", new RegisterHandler());
        server.createContext("/user/login", new LoginHandler());
        server.createContext("/clear", new ClearHandler());
        server.createContext("/fill", new FillHandler());
        server.createContext("/load", new LoadHandler());
        server.createContext("/person/", new SinglePersonHandler());
        server.createContext("/person", new PersonHandler());
        server.createContext("/event/", new SingleEventHandler());
        server.createContext("/event", new EventHandler());
    }
}
