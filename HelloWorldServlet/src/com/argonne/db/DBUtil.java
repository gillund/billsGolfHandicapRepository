package com.argonne.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DBUtil {

	private static final String USERNAME = "dbuser";
	private static final String PASSWORD = "dbpassword";
	private static final String H_CONN_STRING =
			"jdbc:hsqldb:data/explorecalifornia";
	private static final String M_CONN_STRING_PLAYERHANDICAPDB =
			"jdbc:mysql://localhost/playerhandicapdb";
	private static final String M_CONN_STRING_explorecalifornia =
			"jdbc:mysql://localhost/explorecalifornia";
	
	private static final String AWS_USERNAME = System.getProperty("RDS_USERNAME");
	private static final String AWS_PASSWORD = System.getProperty("RDS_PASSWORD");
	private static final String hostname = System.getProperty("RDS_HOSTNAME");
	private static final String port = System.getProperty("RDS_PORT");
	private static final String jdbcUrl = "jdbc:mysql://" + hostname + ":" +
	    port + "/" + "playerHandicapDb" + "?user=" + AWS_USERNAME + "&password=" + AWS_PASSWORD;
	
	public static Connection getConnection(DBType dbType) throws SQLException {
		
		loadDababaseConnector();
		
		String value = System.getenv("LOCAL");
		
		boolean runLocal = Boolean.valueOf(value);
		if (runLocal)
		{
			dbType=DBType.MYSQL;
		}
		switch (dbType) {
		case MYSQL:
			return DriverManager.getConnection(M_CONN_STRING_PLAYERHANDICAPDB, USERNAME, PASSWORD);
		case HSQLDB:
			return DriverManager.getConnection(H_CONN_STRING, USERNAME, PASSWORD);
		case AWS_MYSQL:
			System.out.println("RETURNING CONNECTION = " +jdbcUrl );
			return DriverManager.getConnection(jdbcUrl);
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
	public static boolean validateJavaDate(String strDate)
	   {
		 if (strDate == null)
		 {
			 return false;
		 }
	   
		/* Check if date is 'null' */
		if (strDate.trim().equals(""))
		{
		    return false;
		}
		/* Date is not 'null' */
		else
		{
		    /*
		     * Set preferred date format,
		     * For example MM-dd-yyyy, MM.dd.yyyy,dd.MM.yyyy etc.*/
		    SimpleDateFormat sdfrmt = new SimpleDateFormat("yyyy-MM-dd");
		    sdfrmt.setLenient(false);
		    /* Create Date object
		     * parse the string into date 
	             */
		    try
		    {
		        Date javaDate = sdfrmt.parse(strDate); 
		      }
		    /* Date format is invalid */
		    catch (ParseException e)
		    {
		        return false;
		    }
		   
		    return true;
		}
	   }
	private static void loadDababaseConnector()
	
	{
		System.out.println("Printing java classpath");
		System.out.println(System.getProperty("java.class.path"));
	
		try 
		{
			System.out.println("Loading driver...");
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch(ClassNotFoundException e)
		{
			System.out.println("driver NOT found");
			e.printStackTrace();
		}
	}
	
}
