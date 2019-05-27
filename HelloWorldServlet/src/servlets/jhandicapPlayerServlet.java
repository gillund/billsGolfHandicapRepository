package servlets;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.argonne.courseServices.Course;
import com.argonne.courseServices.CourseService;
import com.argonne.playerService.Player;
import com.argonne.playerService.PlayerService;
import com.argonne.utils.JhandicapFactory;

/** Servlet that creates Excel spreadsheet comparing
 *  apples and oranges.
 *  <P>
 *  Taken from Core Servlets and JavaServer Pages 2nd Edition
 *  from Prentice Hall and Sun Microsystems Press,
 *  http://www.coreservlets.com/.
 *  &copy; 2003 Marty Hall; may be freely used or adapted.
 */
@WebServlet("/jhandicapPlayerServlet")   
public class jhandicapPlayerServlet extends HttpServlet {
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {

	  
	  String addOrDelete = request.getParameter("function");
	  String url = null;
	  
	  if (addOrDelete.equals("ADD")) 
	  {
		  Player p = getPlayer(request);  
		  PlayerService playerService 			= JhandicapFactory.getPlayerService();
		  Hashtable players 					= playerService.getAllPlayers();
		  Vector sortedPlayers					= getPlayerList(players);
		  request.setAttribute("Players", sortedPlayers);
		  request.setAttribute("Player", p);
		  url = "/addAPlayerMVC.jsp";
      }
	  else if (addOrDelete.equals("DELETE"))
	  {
		  String[] selectedPlayers =  request.getParameterValues("playerList");
		  if (selectedPlayers !=null){
			  deletePlayers(selectedPlayers);
		  }
		  PlayerService playerService 			= JhandicapFactory.getPlayerService();
		  Hashtable players 					= playerService.getAllPlayers();
		  Vector sortedPlayers 					= getPlayerList(players);
		  request.setAttribute("Players", sortedPlayers);
		  url = "/deletePlayer.jsp";  
	  }
	  
	  RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
	  dispatcher.forward(request, response);
	 
	  
	    
  }
  
  public void deletePlayers(String[] selectedPlayers){
	int numCourses = selectedPlayers.length; 
	 
	PlayerService playersService = JhandicapFactory.getPlayerService();
	
	numCourses--;
	
	while(numCourses >= 0){
		 String player = selectedPlayers[numCourses];
		 try{
			 String deletedPlayer = player.replace('+', ' ');
		    playersService.removePlayer(deletedPlayer);
		 }
		 catch (Exception e){
			 
		 }
		numCourses--;
	}
  }
  
  
  
  
  public Vector getPlayerList(Hashtable courses) {
      
      Vector sortedCourses 					= new Vector();
      Enumeration enumeration 				= courses.elements();
      
      while ( enumeration.hasMoreElements() ) {
          Player player1 = (Player) enumeration.nextElement();
          boolean notInserted = true;
          for ( int i = 0 ; i < sortedCourses.size(); i++ ) {
              Player player2 = (Player) sortedCourses.elementAt(i);
              if ( player1.firstName.compareTo(player2.firstName) < 0 ) {
                  sortedCourses.add(i, player1);
                  notInserted = false;
                  break;
              }
          }
          if ( notInserted ) {
              sortedCourses.add(player1);
          }
      }
      return sortedCourses;
  
  } 
  public Player getPlayer(HttpServletRequest request){
	  
	  Player p =null;
	  String playerName 	= request.getParameter("playerName");
      String email			= request.getParameter("email");
      String phone 		    = request.getParameter("phone");
      String userid			= request.getParameter("userid");
      String password 	    = request.getParameter("password");
      String message		= "";
      
      if (playerName != null){
    	 if (playerName.equals("")) {
    	    message = "Player name must exist";
    	   }
      
         if (email != null){
     	    if (email.equals("")) {
     	       message = "email must exist";
     	      }
          }
         if (userid != null){
      	    if (userid.equals("")) {
      	       message = "UserId requiredt";
      	      }
           }
         if (password != null){
      	    if (password.equals("")) {
      	       message = "password must exist";
      	      }
           }

         if (message.equals("") )
         {
            PlayerService playerService = JhandicapFactory.getPlayerService();
    	    try {
    	       playerService.addPlayer(playerName, email, phone,userid,password);
    	    }
    	    catch(Exception e){
    	    	message = "Player not Added - Possiple Duplicate";
    	    }  
    	    message = "Player Added";
    	    playerName = ""; email=""; phone=""; userid="";password="";
    	    
    	    p = playerFactory(playerName,email,phone,userid,password);
     	    setMessage(request,message);
    	 }
         else{  
    	   p = playerFactory(playerName,email,phone,userid,password);
    	   setMessage(request,message);
         }	    
    	  
      }
      else
      {
    	  p= playerFactory("","","","","");
          message ="";
          setMessage(request,message);
    	  
      }
      return p;
	  
  }
  
  private void setMessage (HttpServletRequest request, String message){
	  request.setAttribute("Message", message);      
		 
  }
  private Player playerFactory(String aname,String aemail, String aphone,String auserid,String apassword){
	  
	  Player p = new Player();
	  p.setfirstName(aname);
	  p.setEmail(aemail);
	  p.setPhoneNumber(aphone);
	  p.setUserId(auserid);
	  p.setPassword(apassword);
	  
	  return p;
  }
  
    
  
}

