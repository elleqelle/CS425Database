/*
Larry Layne
CS 425 Final Project
DeletePayment class
*/

import java.sql.*;

public class DeletePayment {
	
	DBManager dbmanager;
	
	public DeletePayment(DBManager dbmanager) {
		this.dbmanager = dbmanager;
	}
	
	public boolean delete(int custID, String cardID) throws SQLException {
		String sqlUpdate = "DELETE FROM payment WHERE CardID = \'" + cardID + "\' AND custID = " + custID;
		
		return dbmanager.executeUpdate(sqlUpdate);
	}
}