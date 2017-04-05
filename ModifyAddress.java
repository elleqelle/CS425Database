/*
Larry Layne
CS 425 Final Project
ModifyAddress class
*/

import java.util.Scanner;
import java.sql.*;

public class ModifyAddress {

	private DBManager dbmanager;
	private boolean exit;
	private Scanner scanner;
	private String attr;
	private int selection;

	public ModifyAddress(DBManager dbmanager) {

		scanner = new Scanner(System.in);
		this.dbmanager = dbmanager;
	}
	
	public boolean modify(int id, String addID) throws SQLException {
		exit = false;

		while(!exit) {
			System.out.println("");
			System.out.println(" 1. Address ID");
			System.out.println(" 2. Street Address");
			System.out.println(" 3. City");
			System.out.println(" 4. State");
			System.out.println(" 5. Zip");
			System.out.println("0. Exit");
			System.out.println("What would you like to modify?: ");
			selection = scanner.nextInt();

			if(selection < 0 || selection > 5) {
				System.out.println("Invalid selection");
			}
			else if(selection == 0){
				exit = true;
				return false;
			}
			else if(selection == 1){
				attr = "addressID";
				return modifyString(id, addID, attr);
			}
			else if(selection == 2){
				attr = "streetaddress";
				return modifyString(id, addID, attr);
			}
			else if(selection == 3){
				attr = "city";
				return modifyString(id, addID, attr);
			}
			else if(selection == 4){
				attr = "state";
				return modifyString(id, addID, attr);
			}
			else if(selection == 5){
				attr = "zip";
				return modifyString(id, addID, attr);
			}
		}

		return false;
	}

	private boolean modifyString(int id, String addID, String attr) {
		System.out.println("Please enter the new value: ");
		String value = scanner.next();
		String sqlUpdate = "UPDATE deliveryaddress SET " + attr + " = \'" + value
				+ "\' WHERE addressID = \'" + addID + "\' AND customerID = " + id;

		return dbmanager.executeUpdate(sqlUpdate);
	}

}