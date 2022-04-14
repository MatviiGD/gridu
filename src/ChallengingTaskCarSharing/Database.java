package ChallengingTaskCarSharing;

import java.sql.*;


public class Database {
    public Connection connection;

    public Database(String[] args) {
        connectToDatabase(args.length > 1 && "-databaseFileName".equals(args[0]) ? args[1] : "ChallengingTaskCarSharing");
        createTables();
    }

    private void createTables() {
        try (var statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS company (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "name VARCHAR(60) NOT NULL UNIQUE);" +
                    "");
            statement.execute("CREATE TABLE IF NOT EXISTS car (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT," +
                    "name VARCHAR(60) NOT NULL UNIQUE," +
                    "company_id INT NOT NULL," +
                    "CONSTRAINT fk_car_companyId FOREIGN KEY(company_id) REFERENCES company(id));" +
                    "");
            statement.execute("CREATE TABLE IF NOT EXISTS customer (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT," +
                    "name VARCHAR(60) NOT NULL UNIQUE," +
                    "rented_car_id INT NULL," +
                    "CONSTRAINT fk_customer_carId FOREIGN KEY(rented_car_id) REFERENCES car(id));" +
                    "");
        } catch (SQLException e) {
            System.err.println("Error while creating tables: " + e.getMessage());
        }
    }

    private void connectToDatabase(String databaseName) {
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection("jdbc:h2:./src/ChallengingTaskCarSharing/db/carsharing.mv.db");
            connection.setAutoCommit(true);
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error while connecting with database: " + e.getMessage());
        }
    }

    protected void disconnect() {
        if (connection == null) return;
        try {
            connection.close();
        } catch (SQLException e) {
            System.err.println("Error while disconnecting: " + e.getMessage());
        }
        connection = null;
    }
}