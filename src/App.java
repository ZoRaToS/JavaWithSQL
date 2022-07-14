import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

public class App {
    static ArrayList<String> listOfCommand = new ArrayList<String>();

    static {
        listOfCommand.add("create table");
        listOfCommand.add("delete table");
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = null;
        Statement statement = null;
        Connection connection = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            connection = getConnection();
            System.out.println("Connection to Store DB succesfull!");
            // System.out.println(connection);
            String cmd = consoleCommandController(scanner);
            consoleCommandExecuter(cmd, connection, scanner, statement);

        } catch (Exception e) {
            System.out.println("Connection failed...");
            System.out.println(e);
        }
    }

    // Get connection from data base
    public static Connection getConnection() throws SQLException, IOException {
        //
        String url = "";
        String username = "";
        String password = "";
        Properties properties = new Properties();
        try (InputStream inputStream = Files.newInputStream(Paths.get("database.ini"))) {
            properties.load(inputStream);

            url = properties.getProperty("url");
            username = properties.getProperty("username");
            password = properties.getProperty("password");

        } catch (IOException ioException) {
            System.out.println(ioException);
        }
        ;
        return DriverManager.getConnection(url, username, password);

    }

    // Console comand controller
    public static String consoleCommandController(Scanner scanner) throws SQLException {
        System.out.println("What do you want?");
        scanner = new Scanner(System.in);
        String command = "";
        command = scanner.nextLine();
        scanner.close();
        while (!listOfCommand.contains(command)) {

            System.out.println("Command does not exist");
            command = scanner.nextLine();

        }
        return command;
    }

    public static void consoleCommandExecuter(String comand, Connection connection, Scanner scanner,
            Statement statement) throws SQLException {
        switch (comand) {
            case "create table":
                new CRUD().createTable(connection, scanner, statement);
                System.out.println("Table sucsesful created");
                break;
            case "delete table":
                new CRUD().deleteTable(connection, scanner, statement);
                System.out.println("Table sucsesful deleted");
        }
    }

}
