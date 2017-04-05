/* 
Larry Layne
CS 425 Final Project
CreateOrder class
*/

// needs to update supply quantity
	// needs to update warehouse quantities

import java.util.Scanner;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CreateOrder {
	
	private DBManager dbmanager;
	private double orderTotal;
	private int customerID;
	private int orderID; 
	private String state;
	private String cardID;
	private String addressID;
	private Scanner scanner;
	private int warehouseID;
	private ResultSet rs;
	private boolean first;
	private char newItem;
	
	public CreateOrder(DBManager dbmanager, int customerID, String cardID, String addressID, String state,
					   int orderID) throws SQLException {
		first = true;
		this.dbmanager = dbmanager;
		orderTotal = 0.0;
		this.orderID = orderID; 
		this.state = state;
		this.customerID = customerID;
		this.cardID = cardID;
		this.addressID = addressID;
		scanner = new Scanner(System.in);

		String sqlQuery = "SELECT warehouse FROM warehouse WHERE state = \'" + state + "\'";
		rs = dbmanager.executeQuery(sqlQuery);
		rs.next();
		warehouseID = rs.getInt(1);
	}
	
	public void order() throws SQLException {
		boolean valid = false;
		
		orderItem();
		while(!valid) {
			System.out.println("Would you like to order another item? (y, n): ");
			newItem = scanner.findInLine(".").charAt(0); // will non-char entry cause error?
			if(newItem == 'y') {
				orderItem();
			}
			else if(newItem == 'n') {
				if(!first) {
					updateOrder();
				}
				valid = true;
			}
			else {
				System.out.println("Invalid entry. \n");
			}
			scanner.nextLine();
		}
	}
	
	private void updateOrder() {

        String sqlAdd = "UPDATE customer SET balance = balance + " + orderTotal + " WHERE custID = " + customerID;
        dbmanager.executeUpdate(sqlAdd);
        sqlAdd = "UPDATE orders SET ordertotal = " + orderTotal + " WHERE orderID = " + orderID;
		dbmanager.executeUpdate(sqlAdd);
	}
	
	private void orderItem() throws SQLException {
		String sqlAdd;
		boolean valid = false;


		System.out.println("Please enter product id: "); //needs an error checker
		int pid = scanner.nextInt();
		scanner.nextLine();

		System.out.println("Please enter quantity desired: ");
		int quantity = scanner.nextInt();
		scanner.nextLine();

		// print line of product and quantity
		String sqlQuery = "SELECT productname FROM product WHERE productid = " + pid;
		rs = dbmanager.executeQuery(sqlQuery);
		rs.next();
		String checkOrder = rs.getString(1);

		while(!valid) {
			System.out.println("You would like to order " + quantity + " " + checkOrder + ". Is that correct (y/n)?");
			char correct = scanner.findInLine(".").charAt(0);
			scanner.nextLine();

			if(correct == 'y') {
				valid = true;
				int stockQuantity = checkStock(pid, quantity);
				int remainStock = stockQuantity - quantity;

				if (remainStock < 0) {
					System.out.println("Order cannot be placed. Quantity desired less than quantity on hand.");
				} else {
					String sqlUpdate = "UPDATE stock SET amount = " + remainStock + " WHERE productID = " + pid
							+ " AND warehouse = " + warehouseID;
					dbmanager.executeUpdate(sqlUpdate);

					if (first) {
						// TO_DATE('########', 'YYYYMMDD')
						LocalDate date = LocalDate.now(); // how to make a LocalDate object
						DateTimeFormatter formatter = DateTimeFormatter.BASIC_ISO_DATE;

						sqlAdd = "INSERT INTO orders VALUES (" + orderID + "," + customerID + ",TO_DATE(\'" + date.format(formatter)
								+ "\',\'YYYYMMDD\'),\'RECEIVED\',\'" + cardID + "\',\'" + addressID + "\',4,0)";

						dbmanager.executeUpdate(sqlAdd);
						first = false;

					}

					sqlAdd = "INSERT INTO orderstatement VALUES (" + orderID + "," + pid + ","
							+ quantity + "," + "\'" + state + "\')";
					dbmanager.executeUpdate(sqlAdd);

					orderTotal += (quantity * checkPrice(pid));

					System.out.println("Order for " + quantity + " of " + checkOrder + " placed.");
				}
			}
			else if(correct == 'n'){
				valid = true;
				return;
			}
			else {
				System.out.println("Incorrect selection. Please enter \'y'\' or \'n\'.");
			}
		}
	}
	
	private int checkStock(int pid, int quantity) throws SQLException {
		//needs to check against warehouse in state
		String sqlQuery = "Select amount FROM stock WHERE productID = " + pid + " AND warehouse = " + warehouseID;
		rs = dbmanager.executeQuery(sqlQuery);
		rs.next();
		return rs.getInt(1);
	}
	
	private double checkPrice(int pid) throws SQLException {
		
		String sqlQuery = "Select price FROM pricing WHERE state = \'" + state 
							+ "\' AND productID = " + pid;
		rs = dbmanager.executeQuery(sqlQuery);
		rs.next();
		return rs.getDouble(1);
	} 
		
	
}