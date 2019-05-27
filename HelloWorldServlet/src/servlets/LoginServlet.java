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
import javax.servlet.http.HttpSession;

import com.argonne.courseServices.Course;
import com.argonne.courseServices.CourseService;
import com.argonne.handicapServices.Round;
import com.argonne.playerService.Player;
import com.argonne.playerService.PlayerHandicapService;
import com.argonne.playerService.PlayerService;
import com.argonne.utils.JhandicapFactory;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	PlayerService playerService = JhandicapFactory.getPlayerService();
	Hashtable<String, Player>  playerCacheByName = null;
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Add code to see if someone is logged in.
		 String message = (String) request.getAttribute("message");
		
		 String function = request.getParameter("function");
		 String url = null;
		 if (function != null && function.equals("Login")) 
		 {
			String userid			= request.getParameter("username");
		    String password 	    = request.getParameter("password");
		    
		    Player player = null;
		    String validUser = userid+password;
		  	SelectedPlayer sp  = null;
	    	Vector ps =null;
	
		    boolean result =  playerService.isValidUserId(validUser);
		    if (result )
		    {
		    	 player = playerService.getPlayerByUserId(validUser);
		    	 setSessionPlayer(request, player.getfirstName());
		    	 processVldLogin(request, player );
		    	 url = "/selectedPlayerAddHandicapMVC.jsp";
				
		    }
		    else
		    {
		    	message = "INVALID USER";
		    	request.setAttribute("message", message);
		  		url = "/Login.jsp";	
		    }
		}
		 else
		 {
			 String name = getSessionPlayer(request);
			 if (name != null)
			 {
				 Player p = playerService.getPlayer(name); 
			 	 processVldLogin(request, p );
				 message = "Highlight Course and Enter a Score";
				 request.setAttribute("Message", message);
				 url = "/selectedPlayerAddHandicapMVC.jsp";
			 }
			 else
			 {
			    if (message == "" || message == null)
			    {	
			    	message = "Please Login";
			    }
			    request.setAttribute("message", message);
			  	url = "/Login.jsp";	
			  }
			 
		 }
	   
	   
	    request.getRequestDispatcher(url).forward(request, response);
	
	}
	
	private void processVldLogin(HttpServletRequest request,Player player )
	{
	  	SelectedPlayer sp  = null;
    	Vector ps =null;
    	String message =null;
		 sp =  getPlayerHandicap(player.getfirstName());
		 ps = getPlayerScores(player.getfirstName());
			 
		  
		  
		  if (sp != null)
		  {
			  request.setAttribute("Player", sp);
			  request.setAttribute("playerScores", ps);
			  Round round = new Round("", "", "", 0, 0, 0);
			  request.setAttribute("newRound", round);
			 
			  CourseService courseService 			= JhandicapFactory.getCourseService();
			  Hashtable courses 					= courseService.getAllCourses();
			  Vector sortedCourses 					= getCourseList(courses);
			  request.setAttribute("Courses", sortedCourses);
			
		 }
			  
	}
 public SelectedPlayer getPlayerHandicap(String player){
		 
		 PlayerHandicapService phs = JhandicapFactory.getPlayerHandicapService();
		 SelectedPlayer sp = null;
		 
		 
		 if (player !=null){
			 float handicap = phs.getHandicap(player);
			 float avg      = phs.getAverage(player);
			 float avgDelta = phs.getAverageDelta(player);
			 sp = new SelectedPlayer ( player, handicap,avg,avgDelta); 
		 }
		
		 return sp;
		
		 
	 }
	
 public Vector getPlayerScores(String player ){
 	
	 PlayerHandicapService phs = JhandicapFactory.getPlayerHandicapService();
	 Vector playerScores = phs.getScoresForPlayer(player);
	
	 return playerScores;
 }

 public Vector getCourseList(Hashtable courses) {
     
     Vector sortedCourses 					= new Vector();
     Enumeration enumeration 				= courses.elements();
     
     while ( enumeration.hasMoreElements() ) {
         Course course1 = (Course) enumeration.nextElement();
         boolean notInserted = true;
         for ( int i = 0 ; i < sortedCourses.size(); i++ ) {
             Course course2 = (Course) sortedCourses.elementAt(i);
             if ( course1.name.compareTo(course2.name) < 0 ) {
                 sortedCourses.add(i, course1);
                 notInserted = false;
                 break;
             }
         }
         if ( notInserted ) {
             sortedCourses.add(course1);
         }
     }
     return sortedCourses;
 
 } 
 private void setSessionPlayer(HttpServletRequest request, String aplayer) {
 	
 	 HttpSession session = request.getSession(true);
     session.setAttribute("sessionPlayer", aplayer);
     System.out.println("Saving Session ID =  " + session.getId() + " for Player " + aplayer);
     
     
     
  	 }
 private String getSessionPlayer(HttpServletRequest request) {
		
	  String savedPlayer = null;
	  HttpSession session = request.getSession(true);
	  savedPlayer = (String)session.getAttribute("sessionPlayer");
	  System.out.println("returning Session ID =  " + session.getId() + " for Player " + savedPlayer);
	 return savedPlayer;
}
 
 

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String message = (String) request.getAttribute("message");
			
		 String function = request.getParameter("function");
		 String url = null;
		 if (function != null && function.equals("Login")) 
		 {
			String userid			= request.getParameter("username");
		    String password 	    = request.getParameter("password");
		    
		    Player player = null;
		    String validUser = userid+password;
		  	SelectedPlayer sp  = null;
	    	Vector ps =null;
	
		    boolean result =  playerService.isValidUserId(validUser);
		    if (result )
		    {
		    	 player = playerService.getPlayerByUserId(validUser);
		    	 setSessionPlayer(request, player.getfirstName());
		    	 processVldLogin(request, player );
		    	 url = "/selectedPlayerAddHandicapMVC.jsp";
				
		    }
		    else
		    {
		    	message = "INVALID USER";
		    	request.setAttribute("message", message);
		  		url = "/Login.jsp";	
		    }
		}
		 else
		 {
			 String name = getSessionPlayer(request);
			 if (name != null)
			 {
				 Player p = playerService.getPlayer(name); 
			 	 processVldLogin(request, p );
				 message = "Highlight Course and Enter a Score";
				 request.setAttribute("Message", message);
				 url = "/selectedPlayerAddHandicapMVC.jsp";
			 }
			 else
			 {
			    if (message == "" || message == null)
			    {	
			    	message = "Please Login";
			    }
			    request.setAttribute("message", message);
			  	url = "/Login.jsp";	
			  }
			 
		 }
	   
	   
	    request.getRequestDispatcher(url).forward(request, response);
	}
	
	public void init() throws ServletException {
		// this cache needs to be built and maintained in PlayerService
			
		}
		
		
		  
	}


