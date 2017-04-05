
/*
Larry Layne
CS 425 Final Project
productFinder class
*/

import java.sql.*;
import java.util.Scanner;

public class ProductFinder {

    private DBManager dbmanager;
    private DBTablePrinter printer;
    private Scanner scanner;
    private boolean exit;
    private int selection;

    public ProductFinder(DBManager dbmanager){
        this.dbmanager = dbmanager;
        printer = new DBTablePrinter();
        scanner = new Scanner(System.in);
        exit = false;
    }

    public void productMenu() throws SQLException {
        while(!exit) {
            System.out.println("");
            System.out.println("1) Browse Products by Category");
            System.out.println("2) Search For Products By Name");
            System.out.println("0) Return To Menu");
            System.out.println("What would you like to do?: ");
            selection = scanner.nextInt();

            if(selection < 0 || selection > 2) {
                System.out.println("Invalid selection");
            }
            else if(selection == 0){
                exit = true;
            }
            else if(selection == 1){
                scanner.nextLine();
                browse();
            }
            else if(selection == 2){
                scanner.nextLine();
                search();
            }

        }
    }

    public void search() throws SQLException {
        // search by product name
        System.out.println("");
        System.out.println("What product would you like to search for? ");
        String search = scanner.nextLine();

        String sqlQuery = "SELECT * FROM product WHERE productname LIKE \'%" + search + "%\'";
        ResultSet rs = dbmanager.executeQuery(sqlQuery);
        printer.printResultSet(rs);
    }

    public void browse() throws SQLException {
        // browse by category
        String sqlQuery = "SELECT DISTINCT category FROM product";
        ResultSet rs = dbmanager.executeQuery(sqlQuery);
        printer.printResultSet(rs);

        System.out.println("");
        System.out.println("Select a category from above: ");
        String catChoice = scanner.nextLine();

        sqlQuery = "SELECT * FROM product WHERE category = \'" + catChoice + "\'";
        rs = dbmanager.executeQuery(sqlQuery);
        printer.printResultSet(rs);
    }
}
