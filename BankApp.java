import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class BankApp 
{
	private String AccountNumber;
	private String AccountHolder;
	private static double initialBalance;
	
	static HashMap<String , BankApp> accounts = new HashMap<String , BankApp>(); //For store all accounts
	
	
					// Create Constructor
	public BankApp(String AccountNumber , String AccountHolder , double initialBalance)
	{
		this.AccountNumber = AccountNumber;
		this.AccountHolder = AccountHolder;
		this.initialBalance = initialBalance;
	} 

	           // Main Method
    public static void main(String[] args) 
    {
    	Scanner sc = new Scanner (System.in);
    	DisplayAccounts da = new DisplayAccounts();
    	da.loadAccountsFromDatabase();
    	int choice;
    	
        do 
        {
        	System.out.println("-----------------------------");
        	System.out.println("   BANK APPLICATION MENU   ");
        	System.out.println("-----------------------------");
            System.out.println("1:- ADD NEW ACCOUNT");
            System.out.println("2:- DELETE ACCOUNT");
            System.out.println("3:- VIEW TRANSACTION HISTORY");
            System.out.println("4:- DEPOSIT MONEY");
            System.out.println("5:- WITHDRAW MONEY");
            System.out.println("6:- CHECK BALANCE");
            System.out.println("7:- DISPLAY ACCOUNT DETAILS");
            System.out.println("8:- EXIT");
            System.out.print("-----------------------------\nPLEASE ENTER YOUR CHOICE =  ");

            choice = sc.nextInt();

            switch (choice) 
            {
            case 1:
            {
            	System.out.println("ENTER ACCOUNT NUMBER = ");
            	String accnum = sc.next();
            	if(accounts.containsKey(accnum))
            	{
            		System.out.println("ACCOUNT ALREADY EXISTS");
            		break;
            	}
            	System.out.println("ENTER ACCOUNT HOLDER NAME = ");
            	String holnam = sc.next();  // for string -- nextLine()
            	System.out.println("ENTER INITIAL BALANCE = ");
            	double initialBalance = sc.nextDouble();  // for double -- nextDouble()
            	
            	BankApp newAccount = new BankApp(accnum, holnam, initialBalance);
            	accounts.put(accnum, newAccount);
            	BankApp bapp = accounts.get(accnum);
            	System.out.println("**ACCOUNT CREATED SUCCESSFULLY**");
            	TransactionHistory tr1 = new TransactionHistory();
            	tr1.addAccountTransaction(String.valueOf(initialBalance));
            	SavingAccounts sva = new SavingAccounts();
            	sva.saveAccountToDatabase(accnum ,holnam ,initialBalance);  
            	break;
            }
            case 2:
            	System.out.println("ENTER ACCOUNT NUMBER TO DELETE YOUR ACCOUNT = ");
            	String delacc = sc.next();
            	
            	if(accounts.containsKey(delacc))
            	{
            		SavingAccounts svac = new SavingAccounts();
            		svac.deleteAccountFromDatabase(delacc);  //4
            		accounts.remove(delacc);
            		System.out.println("##ACCOUNT DELETE SUCCESSFULLY##");
            	}
            	else 
            	{
            		System.out.println(">>>>ACCOUNT NOT FOUND<<<<");
				}
            	break;
            	
            case 3:
            	System.out.println("ENTER ACCOUNT NUMBER = ");
            	String acchistory = sc.next();
            	
            	TransactionHistory trh = new TransactionHistory();
            	trh.loadTransactionHistory(acchistory);
            	break;
            case 4:
            	System.out.println("ENTER ACCOUNT NUMBER = ");
            	String depositacc = sc.next();
            	BankApp depacc = accounts.get(depositacc);
            	Deposit dep = new Deposit();
            	if(depacc != null)
            	{
            		System.out.println("ENTER AMOUNT TO DEPOSIT = ");
            		double depositAmount = sc.nextDouble();
            		dep.deposit(depositAmount, depositacc);
            		TransactionHistory th = new TransactionHistory();
            		th.saveTransactionToDatabase(depositacc,"Deposit" ,depositAmount);
            	}
            	else
            	{
            		System.out.println(">>>>ACCOUNT NOT FOUNT<<<<");
            	}
            	break;
            	
            case 5:
            	System.out.println("ENTER ACCOUNT NUMBER = ");
            	String withdraacc = sc.next();
            	BankApp withamo = accounts.get(withdraacc);
            	if(withamo != null)  
            	{
            		System.out.println("ENTER WITHDRAW AMOUNT = ");
            		double withdrawamount = sc.nextDouble();
            		Withdraw wd = new Withdraw();
            		wd.withdrawMoney(withdraacc, withdrawamount);
            		TransactionHistory th = new TransactionHistory(); //5
            		th.saveTransactionToDatabase(withdraacc,"Withdraw" ,withdrawamount);
            	}
            	else
            	{
            		System.out.println(">>>>ACCOUNT NOT FOUNTD<<<<");
            	}
            	break;
            	
            case 6:
            	System.out.println("ENTER ACCOUNT NUMBER = ");
            	String balanceamo = sc.next();
            	BankApp balacco = accounts.get(balanceamo);
            	if(balacco != null)
            	{
            		SavingAccounts sa = new SavingAccounts();
            		System.out.println("CURRENT BALANCE = "+sa.getBalance(balanceamo));
            	}
            	else
            	{
            		System.out.println(">>>>ACCOUNT NOT FOUND<<<<");
            	}
            	break;
            	
            case 7:
            	System.out.println("ENTER ACCOUNT NUMBER = ");
            	String accdetail = sc.next();
            	BankApp detaiapp = accounts.get(accdetail);
            	if(detaiapp != null)
            	{
            		DisplayAccounts dsa = new DisplayAccounts();
           		dsa.displayAccountDetails(accdetail);  //1
            	}
            	else 
            	{
            		System.out.println(">>>>ACCOUNT NOT FOUND<<<<");
            	}
            	break;
            	
            case 8:
            	System.out.println(">>>>EXISTING<<<<");
            	System.exit(0);
            }
        } 
        while (true);
        
    }
    private double getBalance() 
    {
    	
    	return initialBalance;
	}
}
