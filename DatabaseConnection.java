import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection 
{
	// here username and password of our mysql workbench
	private static final String URL      = "jdbc:mysql://localhost:3306/BankDB";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "root";
	
	// Establish the Database connection 
	public static Connection getConnection()
	{
		try 
		{
			return DriverManager.getConnection(URL , USERNAME , PASSWORD);
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			throw new RuntimeException("Database Connection Failed");
		}
	}
}
