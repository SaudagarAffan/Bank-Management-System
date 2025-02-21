import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CheckAutoCommit 
{
	public static void main(String[] args) 
	{
        try 
        {
            String url = "jdbc:mysql://localhost:3306/bankdb";
            String user = "root";
            String password = "root";

            
            // Establish the connection with mysql using JDBC
            Connection conn = DriverManager.getConnection(url, user, password);

            // Checking our Auto-Commit Status
            boolean autoCommit = conn.getAutoCommit();
            System.out.println("Auto-commit status: " + autoCommit);

            // Close connection
            conn.close();
        } 
        
        // here we are catching our sql Exception
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
    }
}
