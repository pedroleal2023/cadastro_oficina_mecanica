package DataBaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {
    private static final String URL = "jdbc:mysql://127.0.0.1:3307/oficina"; 
    private static final String USER = "root"; 
    private static final String PASSWORD = ""; 
    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Erro ao conectar ao banco de dados", e);
            }
        }
        return connection;
    }
}



