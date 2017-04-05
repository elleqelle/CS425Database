/* 
Larry Layne
CS 425 Final Project
CustomerLogin class
*/

import java.sql.*;

public class CustomerLogin {
	
	DBManager dbmanager;
	
	public CustomerLogin(DBManager dbmanager) {
		this.dbmanager = dbmanager;
	}
	
	public boolean login(int custID) throws SQLException {
		boolean result = false;
		
		String sqlQuery = "SELECT * FROM customer WHERE (custID = " + custID + ")";
		ResultSet rs = dbmanager.executeQuery(sqlQuery);
		
		if(rs.next() == true) {
			result = true;
		}
		
		return result;
	}
}