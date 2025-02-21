import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Deposit 
{
	public void depositMoney(String accountNumber, double depositAmount) 
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

	            // Calculate new balance
	            double newBalance = currentBalance + depositAmount;

	            // Update balance in database
	            updateBalanceStmt.setDouble(1, newBalance);
	            updateBalanceStmt.setString(2, accountNumber);
	            int rowsAffected = updateBalanceStmt.executeUpdate();

	            if (rowsAffected > 0)
	            {
	                System.out.println("DEPOSIT SUCCESSFUL YOUR NEW BALANCE = " +newBalance);
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
//--------------------------------------------------------------------------------------------------------
	public void deposit(double depositAmount , String AccountNumber)
    {
//    	 initialBalance = depositAmount;
//    	transactionHistory.add("DEPOSITED = " + depositAmount);
//    	System.out.println("DEPOSIT SUCCESSFULL - AVAILABLE BALANCE = "+initialBalance);
    	depositMoney(AccountNumber, depositAmount);
//    	saveTransactionToDatabase(AccountNumber, "Deposit", depositAmount);
    }
}


