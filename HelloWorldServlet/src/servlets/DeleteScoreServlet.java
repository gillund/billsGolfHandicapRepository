package servlets;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.argonne.handicapServices.Round;
import com.argonne.playerService.PlayerHandicapService;
import com.argonne.playerService.PlayerHandicapServiceDBImpl;
import com.argonne.utils.JhandicapFactory;

/**
 * Servlet implementation class eleteScoreServlet
 */
@WebServlet("/DeleteScoreServlet")
public class DeleteScoreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteScoreServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  Vector ps =null;
		  String sessionPlayer = null;
    	  HttpSession session = request.getSession(true);
    	  sessionPlayer = (String)session.getAttribute("sessionPlayer");
    	  SelectedPlayer sp = null;
    	  String url = null;
    	  String function = request.getParameter("function");
    	  if (sessionPlayer == null)
    	  {
    		  url = "Login.jsp";
    	  }
    	  else if (function == null)
    	  {
    		  ps = getPlayerScores(sessionPlayer);
    		  sp =  getPlayerHandicap(sessionPlayer);
    		  request.setAttribute("playerScores",ps);
    		  request.setAttribute("Player",sp);
    		  url = "/deletePlayerScores.jsp";
    	  }
    	else if (function.equals("DELETE"))
    	{
    		deleteTheScores(request,sessionPlayer);
    		url = "/deletePlayerScores.jsp";
    	}
    	else
    	{
    		url = "/index.html";
    	}
		
    	  RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
    	  dispatcher.forward(request, response);
		
	
		
		
		
		
		
		
	}
	
	 public Vector getPlayerScores(String player ){
	    	
		 PlayerHandicapService phs = JhandicapFactory.getPlayerHandicapService();
		 Vector playerScores = phs.getScoresForPlayer(player);
		
		 return playerScores;
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
 public void deleteTheScores(HttpServletRequest request , String sessionPlayer )
 {
 	 
 	String[] deleteTheseScores = null;
 	Vector newScores = null;       
 	deleteTheseScores =   request.getParameterValues("playerScoresToDelete");
	String message = "";
		
 	if (deleteTheseScores !=null)
 	{
			message = goDeleteScores(sessionPlayer, deleteTheseScores, request);
		//	message = "delete not supported -" ;
		}
 	else
 	{
 		message = "No Player Scores Selected";
 	}
 	
 		request.setAttribute("Message", message);
 		newScores = getPlayerScores(sessionPlayer);
 		request.setAttribute("playerScores", newScores);
 		SelectedPlayer sp =  getPlayerHandicap(sessionPlayer);
 		request.setAttribute("Player",sp);
	}
 public String goDeleteScores(String player, String[] deleteTheseScores, HttpServletRequest request) 
 {
	 PlayerHandicapServiceDBImpl phs = (PlayerHandicapServiceDBImpl) JhandicapFactory.getPlayerHandicapService();
 	
 	String message = "";
 	String s= null;
 	
 	for (int i=0; i<deleteTheseScores.length; i++){
 		  s =  deleteTheseScores[i] ;
 		 
 		  int firstDel = s.indexOf(',');
 		  String roundId = s.substring(0,firstDel);
 		  Integer I = new Integer(roundId);
	      int intRoundId = I.intValue();
 		  Round r = phs.getRoundUsingroundId(intRoundId);
 		  		  
 		  try{
 		     phs.deleteScore(r.getPlayer(), r);  // don't c
 		  }
 		  catch (Exception e)
 		  {
 			  message = "Score Not Deleted = " + r.date  + "," + r.player + "," + r.course;
		  }
		  
	}
   	return message;
	 
 } 
	 
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
