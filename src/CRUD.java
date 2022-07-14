import java.sql.*;
import java.util.Scanner;

public class CRUD {
    // This method to create table
    void createTable(Connection connection, Scanner scanner, Statement statement) throws SQLException {
        scanner = new Scanner(System.in);
        System.out.println("Enter the table name\n");
        String tableName = scanner.next();

        String sqlComand = String.format(
                "CREATE TABLE %s" +
                        "(Id INT PRIMARY KEY AUTO_INCREMENT," +
                        "ProductName VARCHAR(20)," +
                        "Price INT)",
                tableName);

        statement = connection.createStatement();
        statement.executeUpdate(sqlComand);
        scanner.close();
    }

    // Inset data to table
    void insertData(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        int rows = statement.executeUpdate("INSERT Products(ProductName, Price) VALUES ('iPhone X', 76000)," +
                "('Galaxy S9', 45000), ('Nokia 9', 36000)");
        System.out.printf("Added %d rows", rows);
    }

    /**
     * @param connection
     * @param scanner
     * @param statement
     * @throws SQLException
     */
    void deleteTable(Connection connection, Scanner scanner, Statement statement) throws SQLException {
        scanner = new Scanner(System.in);
        System.out.println("Enter table name to delete\n");
        String tableName = scanner.nextLine();
        String sqlComand = String.format("DROP TABLE %s", tableName);
        try {
            statement = connection.createStatement();
            statement.executeUpdate(sqlComand);
        } catch (Exception exception) {
            System.out.println("Failed to delete the table: " + exception);

        }

    }
}
