/*
Larry Layne
CS 425 Final Project
Customer class
*/

import java.sql.*;
import java.util.Scanner;

public class Customer {
	
	public static void main(String[] args) throws SQLException {
		
		Scanner scanner = new Scanner(System.in);
		CustomerControl cc = new CustomerControl();
		boolean exit = false;
		int selection;
		
		cc.connect();
		
		while(!exit) {
			System.out.println("");
			System.out.println(" 1. Customer Login");
			System.out.println(" 2. Find products");
			System.out.println(" 3. Create order");
			System.out.println(" 4. Modify order");
			System.out.println(" 5. Add payment option");
			System.out.println(" 6. Modify payment option");
			System.out.println(" 7. Delete payment option");
			System.out.println(" 8. Add delivery address");
			System.out.println(" 9. Modify delivery address");
			System.out.println("10. Delete delivery address");
			System.out.println("11. Customer Logout");
			System.out.println("0. Exit");
			System.out.println("What would you like to do?: ");
			selection = scanner.nextInt();
			
			if(selection < 0 || selection > 11) {
				System.out.println("Invalid selection");
			}
			else if(selection == 0){
				System.out.println("Goodbye!");
				exit = true;
			}
			else if(selection == 1){
				cc.login();
			}
			else if(selection == 2){
				cc.findProduct();
			}
			else if(selection == 3){
				cc.createOrder();
			}
			else if(selection == 4){
				cc.modifyOrder();
			}
			else if(selection == 5){
				cc.addPayment();
			}
			else if(selection == 6){
				cc.modifyPayment();
			}
			else if(selection == 7){
				cc.deletePayment();
			}
			else if(selection == 8){
				cc.addAddress();
			}
			else if(selection == 9){
				cc.modifyAddress();
			}
			else if(selection == 10){
				cc.deleteAddress();
			}
			else if(selection == 11){
				cc.logout();
			}
		}
	}
}