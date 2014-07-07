package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class select {

    public static ResultSet execute(String query) throws Exception
    {
        ResultSet resultSet = null;

        Connection conn = DatabaseConnection.connect();
        Statement statement = conn.createStatement();
        resultSet = statement.executeQuery(query);

        return resultSet;
    }

}
