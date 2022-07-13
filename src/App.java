import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

public class App {
    public static Scanner scanner = null;
    public static Statement statement = null;
    public static Connection connection = null;
    public static ArrayList<String> listOfComand = new ArrayList<String>();

    static {
        listOfComand.add("create table");
        listOfComand.add("delete table");
    }

    public static void main(String[] args) throws Exception {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            connection = getConnection();
            System.out.println("Connection to Store DB succesfull!");
            // System.out.println(connection);

        } catch (Exception e) {
            System.out.println("Connection failed...");
            // System.out.println(e);
        }

        consoleComandController(connection, scanner);
    }

    // Get connection from data base
    public static Connection getConnection() throws SQLException, IOException {
        Properties properties = new Properties();
        try (InputStream inputStream = Files.newInputStream(Paths.get("database.ini"))) {
            properties.load(inputStream);
        }
        String url = properties.getProperty("url");
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");

        return DriverManager.getConnection(url, username, password);
    }

    // Console comand controller
    public static void consoleComandController(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("What do you want?\n");
        String comand = new Scanner(System.in).nextLine();

        while (scanner.hasNext()) {
            for (String cmd : listOfComand) {
                if (!comand.equals(cmd)) {
                    System.out.println("command does not exist");
                    scanner.next();
                } else
                    break;

            }

        }

        switch (comand) {
            case "create table":
                new CRUD().createTable(connection, scanner, statement);
                System.out.println("Table sucsesful created");
                break;
            case "delet table":
                new CRUD().deleteTable(connection, scanner, statement);
                System.out.println("Table sucsesful deleted");
        }
    }

}
