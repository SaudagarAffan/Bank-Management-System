import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Withdraw 
{
	public void withdrawMoney(String accountNumber, double withdrawAmount) 
	{
	    String getBalanceQuery    = "SELECT Balance FROM accounts WHERE AccountNumber = ?";
	    String updateBalanceQuery = "UPDATE accounts SET Balance = ? WHERE AccountNumber = ?";

	    try (Connection conn = DatabaseConnection.getConnection();
	         PreparedStatement getBalanceStmt = conn.prepareStatement(getBalanceQuery);
	         PreparedStatement updateBalanceStmt = conn.prepareStatement(updateBalanceQuery)) 
	    {

	        // Fetch current balance
	        getBalanceStmt.setString(1, accountNumber);
	        ResultSet rs = getBalanceStmt.executeQuery();

	        if (rs.next())  // check the account is exist or not 
	        {
	            double currentBalance = rs.getDouble("Balance");

	            if (withdrawAmount > currentBalance) 
	            {
	                System.out.println("INSUFFICIENT BALANCE");
	                return;
	            }

	            // Calculate new balance
	            double newBalance = currentBalance - withdrawAmount;

	            // Update balance in database
	            updateBalanceStmt.setDouble(1, newBalance);
	            updateBalanceStmt.setString(2, accountNumber);
	            int rowsAffected = updateBalanceStmt.executeUpdate();

	            if (rowsAffected > 0) 
	            {
	                System.out.println("WITHDRAWAL SUCCESSFUL YOUR AVAILABLE BALANCE = " + newBalance);
	            } 
	            else 
	            {
	                System.out.println("FAILED TO UPDATE BALANCE");
	            }
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
//----------------------------------------------------------------------------------------------------------	
	public void withdraw(double withdrawAmount , String AccountNumber , double initialBalance) 
    {
    	if(initialBalance >= withdrawAmount)
    	{
    		initialBalance -= withdrawAmount;
    		withdrawMoney(AccountNumber, withdrawAmount);
    		TransactionHistory th = new TransactionHistory();
    		th.saveTransactionToDatabase(AccountNumber, "Withdraw", withdrawAmount);
    	}
    	else 
    	{
    		System.out.println(">>>>INSUFFICIENT FUNDS<<<<");
		}
	}
}
