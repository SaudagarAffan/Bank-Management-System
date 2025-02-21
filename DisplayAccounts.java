import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DisplayAccounts
{
	public void loadAccountsFromDatabase() 
	{
		String query = "SELECT * FROM ACCOUNTS";
		try                                    // try with resources
		(Connection conn = DatabaseConnection.getConnection();
				Statement stmt = conn.createStatement();  // for run mysql query
				ResultSet rs =stmt.executeQuery(query))  // to see or store which data come from mysql
		{
			while (rs.next())
			{
				String accNum = rs.getString("AccountNumber");
				String holder = rs.getString("AccountHolder");
				double balance = rs.getDouble("Balance");

				BankApp account = new BankApp(accNum, holder, balance);
				account.accounts.put(accNum, account);  
			}
			System.out.println("ACCOUNT LOADED FROM DATABASE");
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
			System.out.println("ERROR LOADING ACCOUNTS");
		}
	}
//--------------------------------------------------------------------------------------------------------	
	public void displayAccountDetails(String accountNumber) 
	{
		String query = "SELECT AccountNumber, AccountHolder, Balance FROM accounts WHERE AccountNumber = ?";
		try 
		(Connection conn = DatabaseConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(query)) 
		{
			pstmt.setString(1, accountNumber);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) 
			{                                  // Check if account exists
				System.out.println("\nACCOUNT NUMBER  = " + rs.getString("AccountNumber"));
				System.out.println("ACCOUNT HOLDER  = " + rs.getString("AccountHolder"));
				System.out.println("CURRENT BALANCE = " + rs.getDouble("Balance")); // Ensure it fetches latest balance
			} 
			else 
			{
				System.out.println(">>>>ACCOUNT NOT FOUND<<<<");
			}
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
    }
//-------------------------------------------------------------------------------------------------------	
	public void updateBalanceInDatabase(Double initialBalance , String AccountNumber) 
	{
		String query ="UPDATE Accounts SET Balance =? WHERE AccountNumber = ?";
		try
		(Connection conn = DatabaseConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(query))
		{
			pstmt.setDouble(1, initialBalance);
			pstmt.setString(2, AccountNumber);  //6
			pstmt.executeUpdate();
			System.out.println("BALANCE UPDATED IN DATABASE");
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
			System.out.println("ERROR UPDATING BALANCE");
		}
	}
}