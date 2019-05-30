package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.argonne.playerService.Player;
import com.argonne.playerService.PlayerService;
import com.argonne.utils.JhandicapFactory;

/**
 * Servlet implementation class RegisterAPlayer
 */
@WebServlet("/RegisterAPlayerServlet")
public class RegisterAPlayerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterAPlayerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		  String url =null;
		  Player p = registerPlayer(request);
		
			url = "/registerPlayer.jsp";
		   request.getRequestDispatcher(url).forward(request, response);
		
	}
	
	 public Player registerPlayer(HttpServletRequest request){
		  
		 
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
	    	    	message = e.getLocalizedMessage();
	    	    	if (message =="")
	    	    	{
	    	    		message = "Player not Added - Possiple Duplicate";
	    	    	}
	    	    }  
	    	  
	    	    if (message == "")
	    	    {
	    	    	message = "Player Added";
	    	    	playerName = ""; email=""; phone=""; userid="";password="";
	    	    }
	    	   
	    	    
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
		  request.setAttribute("message", message);      
			 
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
