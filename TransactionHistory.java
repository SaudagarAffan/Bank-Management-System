import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class TransactionHistory 
{
	private ArrayList<String> transactionHistory = new ArrayList<>();
	
	public void saveTransactionToDatabase(String accountNumber , String transactionType , double amount)
	{
		String query = "INSERT into transactions (AccountNumber , TransactionType , Amount)VALUES (? , ? , ?)";
		try 
		(Connection conn = DatabaseConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(query))
		{
			pstmt.setString(1, accountNumber);
			pstmt.setString(2, transactionType);
			pstmt.setDouble(3, amount);
			
			pstmt.executeUpdate();
			System.out.println("TRANSACTION SAVED SUCCESSFULLY");
		}
		catch (SQLException e) 
		{
			System.out.println("ERROR SAVING TRANSACTION");
			e.printStackTrace();
		}
	}
//--------------------------------------------------------------------------------------------------------	
	public void loadTransactionHistory(String accountNumber) 
	{
		String query = "SELECT TransactionID , AccountNumber , TransactionType, Amount, Date_Time FROM transactions WHERE AccountNumber = ?";
		try
		(Connection conn = DatabaseConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(query))
		{
			pstmt.setString(1, accountNumber);
			ResultSet rs = pstmt.executeQuery();

			System.out.println("\nTRANSACTION HISTORY FOR ACCOUNT.NO--> " + accountNumber);
			while(rs.next())   // checking at list one transaction need to available otherwise skip this loop
			{
				Timestamp timestamp = rs.getTimestamp("Date_Time");
				System.out.println(rs.getString("TransactionType") + " --> " +
					    rs.getDouble("Amount") + " ON " +timestamp);
			}
		}
		catch(SQLException e)
		{
			System.out.println("ERROR LOADING TRANSACTION HISTORY");
			e.printStackTrace();
		}
	}
//--------------------------------------------------------------------------------------------------------	
	public void transactionHistory(String AccountNumber) 
    {
    	if(transactionHistory.isEmpty())
    	{
    		System.out.println(">>>>NO TRANSACTION AVAILABLE<<<<");
    	}
    	else
    	{
    		System.out.println("\n TRANSACTION HISTORY FOR ACCOUNT.NO-->" + AccountNumber);
    		for(String transaction : transactionHistory)
    		{
    			System.out.println(transaction);
    		}
    	}
    }
	
	public void addAccountTransaction(String initialBalance) 
    {
    	transactionHistory.add("DEPOSITED "+initialBalance);
    }
}
