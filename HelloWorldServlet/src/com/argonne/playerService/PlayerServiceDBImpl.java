package com.argonne.playerService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;

import com.argonne.db.DBType;
import com.argonne.db.DBUtil;
import com.argonne.handicapServices.Round;

public class PlayerServiceDBImpl extends PlayerServiceImpl {
	
	private static String dbConnectionString = DBUtil.getDataBasePlayerHandicap();
	public PlayerServiceDBImpl()
	{
		super(dbConnectionString);
	}
	
	 protected Hashtable readPlayers(String aFilename) 
	 {
		 Hashtable<String, Player>  tempHash = new Hashtable<String, Player>();
		 try 
		 (
				 	Connection conn = DBUtil.getConnection(DBType.MYSQL);
					Statement stmt = conn.createStatement();
					ResultSet rs = stmt.executeQuery(
							"select * from players " );
		) 
		{
			 while (rs.next()) {
				 Player p = new Player();
				 p.setPlayerId(rs.getInt("playerId"));
				 p.setfirstName(rs.getString("name"));
				 p.setEmail(rs.getString("email"));
				 p.setPhoneNumber(rs.getString("phonenumber"));
				 p.setUserId(rs.getString("userid"));
				 p.setPassword(rs.getString("password"));
				 tempHash.put(p.getfirstName() , p);
			 }
			 
		} 
		catch (SQLException e) 
		{
			System.err.println(e);
		}
			return tempHash;
	
	 }
	 
	 protected void writePlayers(String aFilename, Hashtable aPlayerCollection,Player p,boolean remove)  {
		 boolean result = false;
		 if (remove == false)
		 {
			 result = insert(p);
		 }
		 else 
		 {
			 result = delete(p.getPlayerId());
		 }
		 
		 if (result)
		 {
			 System.out.println("Successfull update for " + p.getfirstName() + "With playerId = " + p.getPlayerId());
		 }
		 else
		 {
			 System.err.println("UnSuccessfull :( update for " + p.getfirstName() + "With playerId = " + p.getPlayerId());
				 
		 }
	 }
	 
	 protected boolean insert (Player p)  
	 {
		 String sql = "INSERT into players (name, email,phonenumber,userid,password) " +
					"VALUES (?, ?, ?, ? ,? )";
			
			ResultSet keys = null;
			try (
					Connection conn = DBUtil.getConnection(DBType.MYSQL);
					PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					) {
				stmt.setString(1, p.getfirstName());
				stmt.setString(2, p.getEmail());
				stmt.setString(3, p.getPhoneNumber());
				stmt.setString(4, p.getUserId());
				stmt.setString(5, p.getPassword());
				int affected =stmt.executeUpdate();
				if (affected ==1)
				{
					keys = stmt.getGeneratedKeys();
					keys.next();
					int newKey = keys.getInt(1);
					p.setPlayerId(newKey);
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
	 
	 protected boolean delete (int playerId)  
	 {
			String sql = "DELETE FROM players WHERE playerId = ?";
			try (
					Connection conn = DBUtil.getConnection(DBType.MYSQL);
					PreparedStatement stmt = conn.prepareStatement(sql);
					)
			{
			   stmt.setInt(1, playerId);
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
	 public int getPlayerId(String playerName)  
	 {
			String sql = "select * FROM players WHERE name = ?";
			int playerId =0;
			try 
			(
					Connection conn = DBUtil.getConnection(DBType.MYSQL);
					PreparedStatement stmt = conn.prepareStatement(sql);
							
			)
			{
					stmt.setString(1, playerName);
					    
					System.out.println("executing Query = " + sql);
					System.out.println("executing stmt = " + stmt.toString());
					  
					ResultSet rs = stmt.executeQuery();
					 while (rs.next()) {
						  playerId =   rs.getInt("playerId");
					 }
				  
					return playerId;
			
			}
			catch(SQLException e) {
				System.err.println(e);
				
			}
		return	playerId;
	 }
		

}
