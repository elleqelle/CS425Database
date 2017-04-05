/*
Larry Layne
CS 425 Final Project
AddAddress class
*/

import java.util.Scanner;
import java.sql.*;

public class AddAddress {
	
	DBManager dbmanager;
	
	public AddAddress(DBManager dbmanager) {
		this.dbmanager = dbmanager;
	}
	
	public boolean addAddress(int custid) throws SQLException {
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Enter address id: ");
		String addid = scanner.nextLine();
		
		System.out.println("Enter delivery address: ");
		String address = scanner.nextLine();
		
		System.out.println("Enter delivery city: ");
		String city = scanner.nextLine();
		
		System.out.println("Enter delivery state: ");
		String state = scanner.nextLine();
		
		System.out.println("Enter delivery zip code: ");
		String zip = scanner.nextLine();
		
		String sqlUpdate;
		
		sqlUpdate = "INSERT INTO deliveryaddress VALUES " + "(" + custid + ",\'" + addid + "\',\'" + address + "\',\'" + city + "\',\'" + state + "\'," + zip + ")";


		return dbmanager.executeUpdate(sqlUpdate);
		
	}
}
