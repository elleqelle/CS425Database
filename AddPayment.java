/*
Larry Layne
CS 425 Final Project
AddPayment class
*/

import java.util.Scanner;
import java.sql.*;

public class AddPayment {
	
	private DBManager dbmanager;
	
	public AddPayment(DBManager dbmanager) {
		this.dbmanager = dbmanager;
	}
	
	public boolean add(int id) throws SQLException {
		Scanner scanner = new Scanner(System.in);

		System.out.println("Enter credit card id: ");
		String ccid = scanner.nextLine();

		System.out.println("Enter card type: ");
		String type = scanner.nextLine();

		System.out.println("Enter credit card number: ");
		String ccnum = scanner.nextLine();

		System.out.println("Enter billing address: ");
		String address = scanner.nextLine();
		
		System.out.println("Enter billing city: ");
		String city = scanner.nextLine();
		
		System.out.println("Enter billing state: ");
		String state = scanner.nextLine();
		
		System.out.println("Enter billing zip code: ");
		String zip = scanner.nextLine();
		
		String sqlUpdate;
		
		sqlUpdate = "INSERT INTO payment VALUES " + "(\'" + ccid + "\',\'" + id + "\',\'" + type + "\',\'" + ccnum
					+ "\',\'" + address + "\',\'" + city + "\',\'" + state + "\'," + zip + ")";
	
		return dbmanager.executeUpdate(sqlUpdate);
		
	}
}
