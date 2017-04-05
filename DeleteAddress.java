/*
Larry Layne
CS 425 Final Project
DeleteAddress class
*/

import java.sql.*;

public class DeleteAddress {
	
	DBManager dbmanager;
	int customerID;
	
	public DeleteAddress(DBManager dbmanager, int customerID) {
		this.dbmanager = dbmanager;
		this.customerID = customerID;
	}
	
	public boolean delete(String id) throws SQLException {
		String sqlUpdate = "DELETE FROM deliveryaddress WHERE AddressID = \'" + id + "\' AND customerid = "
							+ customerID;
		
		return dbmanager.executeUpdate(sqlUpdate);
	}
}