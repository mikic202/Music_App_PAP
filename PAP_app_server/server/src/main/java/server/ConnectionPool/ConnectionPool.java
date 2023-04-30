package server.ConnectionPool;

import server.DatabaseInteractors.DatabseInformation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class ConnectionPool {
    private static ArrayList<Connection> availableConnections = new ArrayList<>();
    private static ArrayList<Connection> usedConnections = new ArrayList<>();
    final private int MAX_POOL_SIZE = 5;
    {
        System.out.println("Activating connections");
        try {
            for (int i = 0; i < MAX_POOL_SIZE; i++) {
                availableConnections.add(DriverManager.getConnection(DatabseInformation.URL.value(),
                        DatabseInformation.USER.value(), DatabseInformation.PASSWORD.value()));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    };

    public ConnectionPool() {
        System.out.println("Connection Pool Active");
    }

    public static synchronized Connection getConnection() {
        Connection connection = availableConnections
                .remove(availableConnections.size() - 1);
        usedConnections.add(connection);
        return connection;
    }

    public static synchronized boolean releaseConnection(Connection connection) {
        availableConnections.add(connection);
        return usedConnections.remove(connection);
    }

    public static int availableConnections() {
        return availableConnections.size();
    }
}
