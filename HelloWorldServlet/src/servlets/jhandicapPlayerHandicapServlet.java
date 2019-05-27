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

import com.argonne.playerService.Player;
import com.argonne.playerService.PlayerHandicapService;
import com.argonne.playerService.PlayerService;
import com.argonne.utils.JhandicapFactory;
import com.argonne.courseServices.Course;
import com.argonne.courseServices.CourseService;
import com.argonne.playerService.Player;
import com.argonne.playerService.PlayerService;
import com.argonne.utils.JhandicapFactory;

/**
 * Servlet implementation class jhandicapPlayerHandicapServlet
 */
@WebServlet("/jhandicapPlayerHandicapServlet")       
public class jhandicapPlayerHandicapServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public jhandicapPlayerHandicapServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  
		 String function = request.getParameter("function");
		 String url = null;
		 
		 if (function.equals("SELPLAYER")) 
		 {
			  PlayerService playerService 			= JhandicapFactory.getPlayerService();
			  Hashtable players 					= playerService.getAllPlayers();
			  Vector sortedPlayers					= getPlayerList(players);
			  request.setAttribute("Players", sortedPlayers);
			  url = "/selectAplayerMVC.jsp";
	      }
		 else if (function.equals("PLAYERSELECTED"))
		 {
			 // write code to get player Calcualte handicap.. pass to jsp 
			 // then get all courses - pass in attribute
			 // then call /jhandicapEnterHandicap.jsp
			 String[] selectedPlayers =  request.getParameterValues("playerList");
			  if (selectedPlayers !=null){
				  getPlayerHandicap(selectedPlayers);
			  }
			 
			 url = "/jhandicap.html";
		 }
		 else
		 {
			 url="/jhandicap.html";
		 }
		   
		  RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
		  dispatcher.forward(request, response);	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
	 public Vector getPlayerList(Hashtable players) {
	      
	      Vector sortedCourses 					= new Vector();
	      Enumeration enumeration 				= players.elements();
	      
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
	 public void getPlayerHandicap(String[]playerList){
		 PlayerHandicapService phs = JhandicapFactory.getPlayerHandicapService();
		 String player = playerList[0];
		 if (player !=null){
			 float handicap = phs.getHandicap(player);
			 float avg      = phs.getAverage(player);
			 float avgDelta = phs.getAverageDelta(player);
		 }
		 // create player object with these values pass jsp.
		 
	 }


}
