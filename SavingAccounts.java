import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class SavingAccounts 
{
	public Double getBalance(String AccountNumber) 
	{
		String getBalanceQuery    = "SELECT Balance FROM accounts WHERE AccountNumber = ?";
		try
		(Connection conn = DatabaseConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(getBalanceQuery))
		{
			pstmt.setString(1, AccountNumber);
			ResultSet rs = pstmt.executeQuery();

			if(rs.next())
			{
				double currentBalance = rs.getDouble("Balance");
				return currentBalance;
			}
			else
			{
				System.out.println("ACCOUNT NOT FOUND");
				return 0d;
			}
		}
		catch(SQLException e)
		{
			System.out.println("FAILED TO SHOW BALANCE");
			e.printStackTrace();
		}
		return null;
	}
//-------------------------------------------------------------------------------------------------------
	public void saveAccountToDatabase(String AccountNumber , String AccountHolder , Double initialBalance) 
	{
		try(Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement
						("INSERT INTO accounts (AccountNumber, AccountHolder, Balance) VALUES (?, ?, ?)"))
		{
			stmt.setString(1, AccountNumber);
			stmt.setString(2, AccountHolder);
			stmt.setDouble(3, initialBalance);
			stmt.executeUpdate();
			System.out.println("ACCOUNT SUCCESSFULLY SAVED TO DATABASE");
		}
		catch (SQLException e)
		{
			System.out.println("ERROR SAVING ACCOUNT");
			e.printStackTrace();
		}
	}
	
	public void deleteAccountFromDatabase(String accnum) 
	{
		String query = "DELETE FROM Accounts WHERE AccountNumber = ?";
		try
		(Connection conn = DatabaseConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(query))
		{
			pstmt.setString(1, accnum);
			int rowAffected = pstmt.executeUpdate();
			if(rowAffected > 0)
			{
				System.out.println("ACCOUNT DELETED FROM DATABASE");
			}
			else 
			{
				System.out.println(">>>>ACCOUNT NOT FOUND<<<<");
			}
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
			System.out.println("ERROR DELETING ACCOUNT ");
		}
	}
}
