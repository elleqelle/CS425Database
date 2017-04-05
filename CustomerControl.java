/*
Larry Layne
CS 425 Final Project
CustomerControl class
*/

import java.sql.*;
import java.util.Scanner;

public class CustomerControl {
	
	private DBManager dbmanager = null;
	private boolean login = false;
	private CustomerLogin custLogin;
	private CustomerLogout custLogout;
	private CreateOrder cOrder;
	private ModifyOrder modOrder;
	private AddPayment aPayment;
	private ModifyPayment modPayment;
	private DeletePayment delPayment;
	private AddAddress aAddress;
	private ModifyAddress modAddress;
	private DeleteAddress delAddress;
	private ProductFinder finder;
	private int customerID;
	private String sqlQuery;
	private DBTablePrinter printer;

	public CustomerControl(){} // 
	
	public void connect() { dbmanager = DBManager.getInstance(); } 
	
	public void login() throws SQLException {
		if(dbmanager == null) {
			System.out.println("You must connect to the server before logging in.");
		}
		else {
			Scanner scanner = new Scanner(System.in);
			System.out.println("Enter your id: ");
			customerID = scanner.nextInt();
			scanner.nextLine();
			
			custLogin = new CustomerLogin(dbmanager);
			login = custLogin.login(customerID);
			if(login == false) {
				System.out.println("Login failed. Try again.");
			}
			else {
				System.out.println("You are now logged in as a customer.");
				printBalance();
			}
		}
	}
	
	public void logout() throws SQLException {
		if (login == false) {
			System.out.println("You need to login to logout.");
		}
		else {
			custLogout = new CustomerLogout(dbmanager);
			custLogout.logout();
			login = false;
			System.out.println("You have been logged out.");
		}
	}
	
	public void printBalance() throws SQLException {
		ResultSet rs;

		sqlQuery = "SELECT balance FROM customer WHERE custID = " + customerID;
		rs = dbmanager.executeQuery(sqlQuery);
		rs.next();
		double balance = rs.getDouble(1);

		System.out.println("Your current balance is " + balance + ".\n");
	}

	public void createOrder() throws SQLException {
		if(login) {
			ResultSet rs;
			Scanner scanner = new Scanner(System.in);
			printer = new DBTablePrinter();

			printBalance();

			sqlQuery = "SELECT cardid, cctype FROM payment WHERE custID = " + customerID;
			rs = dbmanager.executeQuery(sqlQuery);
			printer.printResultSet(rs);

			System.out.println("Please enter a cardID (id is case-sensitive.)");
			String cardID = scanner.nextLine();

			sqlQuery = "SELECT addressid, streetaddress, city, state, zip FROM deliveryaddress WHERE customerID = "
					+ customerID;
			rs = dbmanager.executeQuery(sqlQuery);
			printer.printResultSet(rs);

			System.out.println("Please enter an addressID (id is case-sensitive.)");
			String addressID = scanner.nextLine();
			
			sqlQuery = "Select state FROM deliveryaddress WHERE customerID = " + customerID 
						+ " AND addressID = \'" + addressID + "\'";
			rs = dbmanager.executeQuery(sqlQuery);
			rs.next();
			String state = rs.getString(1);
			
			sqlQuery = "SELECT MAX(orderID) FROM orders"; // make sure there is at least a 0 order in order table
			rs = dbmanager.executeQuery(sqlQuery);
			rs.next();
			int orderID = rs.getInt(1) + 1;
			
			cOrder = new CreateOrder(dbmanager, customerID, cardID, addressID, state, orderID);
			cOrder.order();

			printBalance();
	
		}
		else {
			System.out.println("You need to login to create an order.");
		}
	} 
	
	public void modifyOrder() throws SQLException { // make so can only modify order of customerID
		if(login) {
			int choice;
			Scanner scanner = new Scanner(System.in);
			boolean exit = false;

			printBalance();

			System.out.println("Enter the orderID to motify: "); //needs error check
			int orderID = scanner.nextInt();
			
			System.out.println("Enter the product id to be"); //needs error check
			int productID = scanner.nextInt();
			
			modOrder = new ModifyOrder(dbmanager, orderID, productID);
			
			while(!exit) {
				System.out.println("Do you want to 1) modify the number of items or 2) cancel the order for the item?: ");
				choice = scanner.nextInt();
				scanner.nextLine();
			
				if(choice == 1) {
					modOrder.modify();
					exit = true;
				}
				else if(choice == 2) {
					modOrder.delete();
					exit = true;
				}
				else {
					System.out.println("Invalid selection \n");
				}
			}

			printBalance();
		}
		else {
			System.out.println("You need to login to modify an order.");
		}
	}
	
	public void addPayment() throws SQLException {
		if(login) {
			boolean b;
			
			aPayment = new AddPayment(dbmanager);
			b = aPayment.add(customerID);
			
			if(b){
				System.out.println("New payment method added."); 
			}
			else {
				System.out.println("Payment addition failed.");
			}
		}
		else {
			System.out.println("You need to be logged in to add a payment method.");
		}
	}
	
	public void modifyPayment() throws SQLException {
		if(login) {

			ResultSet rs;
			printer = new DBTablePrinter();
			Scanner scanner = new Scanner(System.in);
			boolean b;

			sqlQuery = "SELECT cardid, cctype FROM payment WHERE custID = " + customerID;
			rs = dbmanager.executeQuery(sqlQuery);
			printer.printResultSet(rs);

			System.out.println("Please enter the card id to modify (id is case-sensitive). ");
			String id = scanner.next();
			
			modPayment = new ModifyPayment(dbmanager);
			b = modPayment.modify(id);
			
			if(b) {
				System.out.println("Payment attribute updated.");
			}
			else {
				System.out.println("Failed to update payment attribute.");
			}
		}
		else {
			System.out.println("You need to be logged in to modify a payment method.");
		}
	}
	
	public void deletePayment() throws SQLException {
		if(login) {
			ResultSet rs;
			printer = new DBTablePrinter();
			Scanner scanner = new Scanner(System.in);
			boolean b;

			sqlQuery = "SELECT cardid, cctype FROM payment WHERE custID = " + customerID;
			rs = dbmanager.executeQuery(sqlQuery);
			printer.printResultSet(rs);
			
			System.out.println("Please enter card id to delete (id is case-sensitive).");
			String id = scanner.next();
			
			delPayment = new DeletePayment(dbmanager);
			b = delPayment.delete(customerID, id);
			
			if(b) {
				System.out.println("Payment method deleted.");
			}
			else {
				System.out.println("Failed to delete payment method.");
			}
			
		}
		else {
			System.out.println("You need to be logged in to delete a payment method.");
		}
	}
	
	public void addAddress() throws SQLException {
		if(login) {
			boolean b;
			
			aAddress = new AddAddress(dbmanager);
			b = aAddress.addAddress(customerID);
			
			if(b){
				System.out.println("New delivery address added."); 
			}
			else {
				System.out.println("Delivery address addition failed.");
			}
		}
		else {
			System.out.println("You need to be logged in to add a delivery address.");
		}
	}
	
	public void modifyAddress() throws SQLException {
		if(login) {
			ResultSet rs;
			printer = new DBTablePrinter();
			Scanner scanner = new Scanner(System.in);
			boolean b;

			sqlQuery = "SELECT addressid, streetaddress, city, state, zip FROM deliveryaddress WHERE customerID = "
						+ customerID;
			rs = dbmanager.executeQuery(sqlQuery);
			printer.printResultSet(rs);
			
			System.out.println("Please enter address id to modify (id is case-sensitive).");
			String id = scanner.next();

			modAddress = new ModifyAddress(dbmanager);
			b = modAddress.modify(customerID, id);
			
			if(b) {
				System.out.println("Delivery address attribute updated.");
			}
			else {
				System.out.println("Failed to update delivery address attribute.");
			}
		}
		else {
			System.out.println("You need to be logged in to modify a delivery address.");
		}
	}
	
	public void deleteAddress() throws SQLException {
		if(login) {
			ResultSet rs;
			printer = new DBTablePrinter();
			Scanner scanner = new Scanner(System.in);
			boolean b;

			sqlQuery = "SELECT addressid, streetaddress, city, state, zip FROM deliveryaddress WHERE customerID = "
					+ customerID;
			rs = dbmanager.executeQuery(sqlQuery);
			printer.printResultSet(rs);
			
			System.out.println("Please enter address to delete (id is case-sensitive).");
			String id = scanner.next();
			
			delAddress = new DeleteAddress(dbmanager, customerID);
			b = delAddress.delete(id);
			
			if(b) {
				System.out.println("Delivery address deleted.");
			}
			else {
				System.out.println("Failed to delete delivery address method.");
			}
		}
		else {
			System.out.println("You need to be logged in to delete a delivery address.");
		}
	}

	public void findProduct() throws SQLException {
		finder = new ProductFinder(dbmanager);
		finder.productMenu();
	}

}