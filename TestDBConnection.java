import java.sql.Connection;
import java.sql.SQLException;

public class TestDBConnection 
{
	public static void main(String[] args) 
	{
		
		//this is the try-with-resources block which automatically close the connection 
		try (Connection conn = DatabaseConnection.getConnection()) 
		{
			// if db connection return not null it means the it is connected the the database 
            if (conn != null) 
            {
                System.out.println("✅ Connected to Database.");
            } 
            else 
            {
                System.out.println("❌ Connection Failed.");
            }
        } 
		// catching the exception 
		catch (SQLException e) 
		{
            e.printStackTrace();
        }
	}
}
