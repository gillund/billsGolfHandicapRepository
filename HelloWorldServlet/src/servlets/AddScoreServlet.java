package servlets;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.argonne.db.DBUtil;
import com.argonne.handicapServices.Round;
import com.argonne.playerService.PlayerHandicapService;
import com.argonne.utils.DuplicateException;
import com.argonne.utils.JhandicapFactory;

/**
 * Servlet implementation class AddScoreServlet
 */
@WebServlet("/AddScoreServlet")
public class AddScoreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddScoreServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String function = request.getParameter("function");
		 String url = null;
		 
		 if (function.equals("ADDSCORE")) 
		 {
				String message = "";
				
				CourseService courseService 			= JhandicapFactory.getCourseService();
				Hashtable courses 						= courseService.getAllCourses();
				Vector sortedCourses 					= getCourseList(courses);
				request.setAttribute("Courses", sortedCourses);
			
		    	message = validateRequest(request,message);
			
		    	request.setAttribute("Message", message);

		    	SelectedPlayer  sp =null;
		    	Vector ps =null;
		    	sp = getPlayerHandicap(getSessionPlayer(request));
		    	ps = getPlayerScores(getSessionPlayer(request));
			
		    	request.setAttribute("Player", sp);
		    	request.setAttribute("playerScores", ps);	
		    	url = "/selectedPlayerAddHandicapMVC.jsp";
		 
		 }
		 else
		 {
			 url="/jhandicap.html";
		 }
		   
		  RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
		  dispatcher.forward(request, response);	

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
   
	
public String validateRequest(HttpServletRequest request, String message){
    	
    	String selPlayer = getSessionPlayer(request);
		String addDate =  request.getParameter("Date"); 
		String addCourse[] =  request.getParameterValues("courses"); 
		String addScore =  request.getParameter("Score");
		int newScore =0;
		try{
		   newScore = Integer.parseInt(addScore);
		}
		catch(Exception e){
		  message = "Invalid Score";
		}
		
	    if (DBUtil.validateJavaDate(addDate)  == false)
		{
				message = "Date Required";
		}
		if (addCourse == null || addCourse == null)
		{
			message = "Course needs to be Highlighted";
		}
		if (newScore == 0){
			message = "Score must be greater than Zero ";
		}
		
		// try to add 
		if ((message.equals(""))) 
		{
		   PlayerHandicapService phs	= JhandicapFactory.getPlayerHandicapService();
		
		   try {
			 phs.addScore(addDate, getSessionPlayer(request), addCourse[0], newScore);
		   } catch (Exception e) {
			  message = "Score Not Added - Bad input or Database problem";
			   e.printStackTrace();
		   } catch (DuplicateException e) {
              message = "Score Already added";
		   }
		
		}
		
		if (message.equals("")){
		   Round round = new Round("", "", "", 0, 0, 0);
		   request.setAttribute("newRound", round);
		   message = getSessionPlayer(request) + " Shot " + addScore + " at " + addCourse[0] ;
		}
		else{
			Round round = new Round("", "", "", 0, 0, 0);
			request.setAttribute("newRound", round);
		}
		
		return message;
    }

private String getSessionPlayer(HttpServletRequest request) {
	
	  String savedPlayer = null;
	  HttpSession session = request.getSession(true);
	  savedPlayer = (String)session.getAttribute("sessionPlayer");
	  System.out.println("returning Session ID =  " + session.getId() + " for Player " + savedPlayer);
	 return savedPlayer;
}
private void setSessionPlayer(HttpServletRequest request, String aplayer) {
	
	HttpSession session = request.getSession(true);
	session.setAttribute("sessionPlayer", aplayer);
	System.out.println("Saving Session ID =  " + session.getId() + " for Player " + aplayer);



	 }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	 
}
