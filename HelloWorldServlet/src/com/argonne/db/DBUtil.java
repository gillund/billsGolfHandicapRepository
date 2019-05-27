package com.argonne.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

	private static final String USERNAME = "dbuser";
	private static final String PASSWORD = "dbpassword";
	private static final String H_CONN_STRING =
			"jdbc:hsqldb:data/explorecalifornia";
	private static final String M_CONN_STRING_PLAYERHANDICAPDB =
			"jdbc:mysql://localhost/playerhandicapdb";
	private static final String M_CONN_STRING_explorecalifornia =
			"jdbc:mysql://localhost/explorecalifornia";
	public static Connection getConnection(DBType dbType) throws SQLException {
		
		switch (dbType) {
		case MYSQL:
			return DriverManager.getConnection(M_CONN_STRING_PLAYERHANDICAPDB, USERNAME, PASSWORD);
		case HSQLDB:
			return DriverManager.getConnection(H_CONN_STRING, USERNAME, PASSWORD);
		default:
			return null;
		}
		
	}
	public static String getDataBasePlayerHandicap()
	{
		return M_CONN_STRING_PLAYERHANDICAPDB;
	}
	
	public static void processException(SQLException e) {
		System.err.println("Error message: " + e.getMessage());
		System.err.println("Error code: " + e.getErrorCode());
		System.err.println("SQL state: " + e.getSQLState());
	}
	
}
