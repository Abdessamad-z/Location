package Location.Config;

import java.sql.*;

public class Database {
    static String url = "jdbc:mysql://localhost:3306/java?allowMultiQueries=true", //Database Name
            name = "root",  //UserName
            password = "Aa987654321";   //Password

    static public ResultSet ExecureQ(String Q) {
        try {
            Connection connection = DriverManager.getConnection(url, name, password);
            Statement statement = connection.createStatement();
            return statement.executeQuery(Q);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    static public void ExecureUp(String Q) {
        try {
            Connection connection = DriverManager.getConnection(url, name, password);
            Statement statement = connection.createStatement();
            statement.executeUpdate(Q);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static public PreparedStatement getStatement(String Q) {
        try {
            Connection connection = DriverManager.getConnection(url, name, password);
            return connection.prepareStatement(Q);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
