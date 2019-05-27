package com.argonne.playerService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import com.argonne.courseServices.CourseService;
import com.argonne.db.DBType;
import com.argonne.db.DBUtil;
import com.argonne.handicapServices.Round;
import com.argonne.utils.JhandicapFactory;

public class PlayerHandicapServiceDBImpl extends PlayerHandicapServiceImpl {
	
	
	private static String dbConnectionString = DBUtil.getDataBasePlayerHandicap();
	String fileznme =null;

	 public PlayerHandicapServiceDBImpl(CourseService aCourseService, PlayerService aPlayerService) {
	    	
	        this(dbConnectionString, aCourseService, aPlayerService);
	    }
    public PlayerHandicapServiceDBImpl(String fileznme, CourseService aCourseService,PlayerService aPlayerService)
    {
       super(dbConnectionString,aCourseService,aPlayerService);
    }
	
	 protected Vector readPlayerScores(String aPlayerName) 
	 {
		  Vector<Round> scores = new Vector();
		
		  String sql = "select * from round where player = ?";
			try 
			(
					Connection conn = DBUtil.getConnection(DBType.MYSQL);
					PreparedStatement stmt = conn.prepareStatement(sql);
					
			)
			{
				stmt.setString(1, aPlayerName);
			    
				System.out.println("executing Query = " + sql);
				System.out.println("executing stmt = " + stmt.toString());
				 	
				ResultSet rs = stmt.executeQuery();
			  
				
				
				while (rs.next()) {
					 Round r = new Round();
					 r.setRoundId(rs.getInt( "roundId"));
					 r.setPlayerId(rs.getInt("playerId"));
					 Date d = rs.getDate("rounddatetime");
					 String date = d.toString();
					 r.setDate(date);
					 
					 r.setPlayer(rs.getString("player"));
					 r.setCourse(rs.getString("course"));
				
					 r.setScore(rs.getInt( "score"));
					 r.setSlope(rs.getInt("slope"));
					
					 r.setRating(rs.getFloat("rating"));
					 r.setDelta(rs.getFloat("delta"));
							
					 scores.add(r);
				 }
			   
			  
			}
			catch(SQLException e) {
				System.err.println(e);
	
			}
			return scores;
	
	 }
	 
	 	protected void writePlayerScores(String aPlayer , Vector rounds, int roundId)  {
		 boolean result = false;
		
		 PlayerService psDbImpl =  JhandicapFactory.getPlayerService();
		 int plyId = psDbImpl.getPlayerId(aPlayer);
		 
		 if (roundId == 0)
		 {
			 Round r = (Round)rounds.get(0);
			 result = insert(aPlayer,r,plyId);
		 }
		 else 
		 {
			 result = delete(roundId);
		 }
		 
		 if (result)
		 {
			 System.out.println("Successfull update for " +  aPlayer );
		 }
		 else
		 {
			 System.err.println("UnSuccessfull :( update for " + aPlayer);
				 
		 }
	 }
	 
	 protected boolean insert (String p, Round r,int playerId)  
	 {
		 String sql = "INSERT into round (playerId,rounddatetime, player, course, score, slope,rating, delta ) " 
				 +
					"VALUES (?, ?, ?, ? ,? ,?,?,?)";
			
			ResultSet keys = null;
			try (
					Connection conn = DBUtil.getConnection(DBType.MYSQL);
					PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					) {
				stmt.setInt(1, playerId );
				
				stmt.setString(2, fixDate(r.getDate()));
				stmt.setString(3, r.getPlayer());
				stmt.setString(4, r.getCourse());
				stmt.setInt(5, r.getScore());
				stmt.setInt(6, r.getSlope());
				stmt.setFloat(7, r.getRating());
				stmt.setFloat(8, r.getDelta());
				
				int affected =stmt.executeUpdate();
				if (affected ==1)
				{
					keys = stmt.getGeneratedKeys();
					keys.next();
					int newKey = keys.getInt(1);
					r.setRoundId(newKey);
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
	 
	 protected boolean delete (int roundId)  
	 {
			String sql = "DELETE FROM round WHERE roundId = ?";
			try (
					Connection conn = DBUtil.getConnection(DBType.MYSQL);
					PreparedStatement stmt = conn.prepareStatement(sql);
					)
			{
			   stmt.setInt(1, roundId);
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
		

	 public String fixDate(String myDate){
		 // '2019-10-25 10:00:00 from 25/10/2019
	        String year = myDate.substring(myDate.length()-4, myDate.length());
		    int slsh = myDate.indexOf("/");
		    String month = myDate.substring(0,slsh);
		    String day = myDate.substring(slsh+1, myDate.length()-5);
		    
		    String newDate = year + "-" + month  + "-" +day;
		
			SimpleDateFormat sfd = new SimpleDateFormat("hh:mm:ss");
			Calendar cal = Calendar.getInstance();
		    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		    String mytime =  sdf.format(cal.getTime()) ;
			String newDteTime = newDate + " " + mytime;
			return newDteTime;
		}
	 
	 public Round getRoundUsingroundId(int roundId) 
	 {
		   String sql = "select * from round where roundId = ?";
		   Round r= null;;
			try 
			(
					Connection conn = DBUtil.getConnection(DBType.MYSQL);
					PreparedStatement stmt = conn.prepareStatement(sql);
					
			)
			{
				stmt.setInt(1, roundId);
			    
				System.out.println("executing Query = " + sql);
				System.out.println("executing stmt = " + stmt.toString());
				 	
				ResultSet rs = stmt.executeQuery();
			  
				
				
				while (rs.next()) {
					 r = new Round();
					 r.setRoundId(rs.getInt( "roundId"));
					 r.setPlayerId(rs.getInt("playerId"));
					 Date d = rs.getDate("rounddatetime");
					 String date = d.toString();
					 r.setDate(date);
					 
					 r.setPlayer(rs.getString("player"));
					 r.setCourse(rs.getString("course"));
				
					 r.setScore(rs.getInt( "score"));
					 r.setSlope(rs.getInt("slope"));
					
					 r.setRating(rs.getFloat("rating"));
					 r.setDelta(rs.getFloat("delta"));
							
				 }
			   
			  
			}
			catch(SQLException e) {
				System.err.println(e);
	
			}
			return r;
	
	 }
	 
	 
	 
	 
	 
}
