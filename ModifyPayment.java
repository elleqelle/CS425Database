/*
Larry Layne
CS 425 Final Project
ModifyPayment class
*/

import java.util.Scanner;
import java.sql.*;

public class ModifyPayment {
	
	private DBManager dbmanager;
	private boolean exit;
	private Scanner scanner;
	private String attr;
	private int selection;

	public ModifyPayment(DBManager dbmanager) {
		this.dbmanager = dbmanager;
		scanner = new Scanner(System.in);
	}
	
	public boolean modify(String id) throws SQLException {
		exit = false;

		while(!exit) {
			System.out.println("");
			System.out.println(" 1. Card ID");
			System.out.println(" 2. Credit Card Type");
			System.out.println(" 3. Credit Card Number");
			System.out.println(" 4. Street Address");
			System.out.println(" 5. City");
			System.out.println(" 6. State");
			System.out.println(" 7. Zip");
			System.out.println("0. Exit");
			System.out.println("What would you like to modify?: ");
			selection = scanner.nextInt();

			if(selection < 0 || selection > 7) {
				System.out.println("Invalid selection");
			}
			else if(selection == 0){
				exit = true;
				return false;
			}
			else if(selection == 1){
				attr = "cardID";
				return modifyString(id, attr);
			}
			else if(selection == 2){
				attr = "cctype";
				return modifyString(id, attr);
			}
			else if(selection == 3){
				attr = "ccnum";
				return modifyString(id, attr);
			}
			else if(selection == 4){
				attr = "streetaddress";
				return modifyString(id, attr);
			}
			else if(selection == 5){
				attr = "city";
				return modifyString(id, attr);
			}
			else if(selection == 6){
				attr = "state";
				return modifyString(id, attr);
			}
			else if(selection == 7){
				attr = "zip";
				return modifyString(id, attr);
			}
		}

		return false;
	}

	private boolean modifyString(String id, String attr) {
		System.out.println("Please enter the new value: ");
		String value = scanner.next();
		String sqlUpdate = "UPDATE payment SET " + attr + " = \'" + value + "\' WHERE CardID = \'" + id + "\'";

		return dbmanager.executeUpdate(sqlUpdate);
	}


}
