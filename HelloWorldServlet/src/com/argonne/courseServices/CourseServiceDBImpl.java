package com.argonne.courseServices;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;

import com.argonne.db.DBType;
import com.argonne.db.DBUtil;

public class CourseServiceDBImpl extends CourseServiceImpl {
	
	private static String dbConnectionString = DBUtil.getDataBasePlayerHandicap();
	public CourseServiceDBImpl()
	{
		super(dbConnectionString);
	}
	
	
	 protected Course writeCourses(String aFilename, Hashtable aCourseCollection,Course c, boolean remove) {
	 
		 boolean result = false;
		 if (remove == false)
		 {
			 result = insert(c);
		 }
		 else 
		 {
			 result = delete(c.getCourseId());
		 }
		 
		 if (result)
		 {
			 System.out.println("Successfull update for " + c.getName() + "With courseId = " + c.getComments());
		 }
		 else
		 {
			 System.err.println("UnSuccessfull :( update for " + c.getName() + "With courseId = " + c.getCourseId());
				 
		 }
	     return c;
	 
	 }
	 
	 
	 
	 
	 protected Hashtable readCourses(String aFilename) {

		 Hashtable  tempHash = new Hashtable();
		 try 
		 (
				 	Connection conn = DBUtil.getConnection(DBType.MYSQL);
					Statement stmt = conn.createStatement();
					ResultSet rs = stmt.executeQuery(
							"select * from courses " );
		) 
		{
			 while (rs.next()) {
				 Course c = new Course();
			
				 c.setCourseId(rs.getInt("courseId"));
				 c.setName(rs.getString("coursename"));
				 c.setSlope(rs.getInt("slope"));
				 c.setRating(rs.getFloat("rating"));
				 c.setYardage(rs.getInt("yardage"));
				 c.setComments(rs.getString("comment"));
				 tempHash.put(c.getCourseId() , c);
			 }
			 
		} 
		catch (SQLException e) 
		{
			System.err.println(e);
		}
			return tempHash;
	
	 
	  }
	 
	 protected boolean insert (Course c)  
	 {
		 String sql = "INSERT into courses (coursename,slope,rating,yardage,comment) " +
					"VALUES (?, ?, ? ,? ,?  )";
			
			ResultSet keys = null;
			try (
					Connection conn = DBUtil.getConnection(DBType.MYSQL);
					PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					) 
			    {
				 stmt.setString(1,c.getName());
				 stmt.setInt(2, c.getSlope());
				 stmt.setFloat(3,c.getRating());
				 stmt.setInt(4, c.getYardage());
				 stmt.setString(5, c.getComments());
				
				 int affected =stmt.executeUpdate();
				if (affected ==1)
				{
					keys = stmt.getGeneratedKeys();
					keys.next();
					int newKey = keys.getInt(1);
					c.setCourseId(newKey);
				}
				else
				{
					System.err.println("no rows affected");
				}
				
				
			} catch (SQLException e) {
				System.err.println(e);
			} finally{
				if (keys !=null) 
				{
					try {
						keys.close();
					} catch (SQLException e) {
						System.err.println( e);
					}
				}
			}
			return true;
			 
	 }
	 
	 protected boolean delete (int courseId)  
	 {
			String sql = "DELETE FROM courses WHERE courseId = ?";
			try (
					Connection conn = DBUtil.getConnection(DBType.MYSQL);
					PreparedStatement stmt = conn.prepareStatement(sql);
					)
			{
			   stmt.setInt(1, courseId);
			   int affected = stmt.executeUpdate();
			   if (affected == 1)
			   {
				   return true;
			   }
			   else
			   {
				   return false;
			   }
			}
			catch(SQLException e) {
				System.err.println(e);
				return false;
			}
	 }
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
}
