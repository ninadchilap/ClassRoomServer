package database;

import java.sql.Connection;
import java.sql.Statement;

public class update {

    public static int execute(String query) throws Exception
    {
        int rows = 0;
        Connection conn = DatabaseConnection.connect();
        Statement statement = conn.createStatement();
        rows = statement.executeUpdate(query);
        return rows;
    }

}
