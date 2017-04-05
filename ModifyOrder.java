/*
Larry Layne
CS 425 Final Project
ModifyOrder class
*/

import java.util.Scanner;
import java.sql.*;

public class ModifyOrder {
	
	private DBManager dbmanager;
	private int orderID;
	private int productID;
	private int warehouseID;
	private ResultSet rs;
	private String state;
	private String modifyCheck;

	public ModifyOrder(DBManager dbmanager, int orderID, int productID) throws SQLException {
		this.dbmanager = dbmanager;
		this.orderID = orderID;
		this.productID = productID;

		String sqlQuery = "SELECT productname FROM product where productID = " + productID;
		rs = dbmanager.executeQuery(sqlQuery);
		rs.next();
		modifyCheck = rs.getString(1);

	}
	
	public void modify() throws SQLException {
		Scanner scanner = new Scanner(System.in);
		boolean valid = false;

		System.out.println("What is the new order quantity for the product?: ");
		int newValue = scanner.nextInt();
		scanner.nextLine();

		while(!valid) {
			System.out.println("You would like to change your order for " + modifyCheck + " to " + newValue + ". Is that correct(y/n)?");
			char correct = scanner.findInLine(".").charAt(0);

			if(correct == 'y') {
				valid = true;
				String sqlUpdate = "UPDATE orderstatement SET quantity = " + newValue + " WHERE orderID = "
						+ orderID + " AND productID = " + productID;

				dbmanager.executeUpdate(sqlUpdate);

				System.out.println("The order is updated.");
				updateOrder(newValue);
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
	
	public void delete() throws SQLException {
		Scanner scanner = new Scanner(System.in);
		boolean valid = false;

		while(!valid) {
			System.out.println("You would like to delete your order for " + modifyCheck + ". Is that correct(y/n)?");
			char correct = scanner.findInLine(".").charAt(0);

			if(correct == 'y') {
				valid = true;
				String sqlUpdate = "DELETE FROM orderstatement WHERE orderID = " + orderID + " AND productID = " + productID;
				dbmanager.executeUpdate(sqlUpdate);

				System.out.println("The order is updated.");
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
	
	private void updateOrder(int newQuantity) throws SQLException {
		//get order total
		String sqlQuery = "SELECT ordertotal FROM orders WHERE orderID = " + orderID;
		rs = dbmanager.executeQuery(sqlQuery);
		rs.next();
		double total = rs.getDouble(1);
		
		sqlQuery = "SELECT quantity, state FROM orderstatement WHERE orderID = " + orderID + " AND productID = " + productID;
		rs = dbmanager.executeQuery(sqlQuery);
		rs.next();
		double oldQuantity = rs.getDouble(1);
		state = rs.getString(2);
		
		total -= (oldQuantity - newQuantity) * checkPrice(productID, state);
		
		String sqlUpdate = "UPDATE orders SET ordertotal = " + total + " WHERE orderID = "
							+ orderID;
		
		dbmanager.executeUpdate(sqlUpdate);
	}
	
	private double checkPrice(int pid, String state) throws SQLException {
		
		String sqlQuery = "Select price FROM pricing WHERE state = \'" + state 
							+ "\' AND productID = " + pid;
		rs = dbmanager.executeQuery(sqlQuery);
		rs.next();

		return rs.getDouble(1);
	} 
}