/* 
Larry Layne
CS 425 Final Project
CustomerLogout class
*/

import java.sql.*;

public class CustomerLogout {
	
	DBManager dbmanager;
	
	public CustomerLogout(DBManager dbmanager) {
		this.dbmanager = dbmanager;
	}
	
	public void logout() throws SQLException {
		dbmanager.close();
	}
	
}