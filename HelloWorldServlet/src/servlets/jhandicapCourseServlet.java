package servlets;

import java.io.*;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.argonne.courseServices.Course;
import com.argonne.courseServices.CourseService;
import com.argonne.utils.JhandicapFactory;

/** Servlet that creates Excel spreadsheet comparing
 *  apples and oranges.
 *  <P>
 *  Taken from Core Servlets and JavaServer Pages 2nd Edition
 *  from Prentice Hall and Sun Microsystems Press,
 *  http://www.coreservlets.com/.
 *  &copy; 2003 Marty Hall; may be freely used or adapted.
 */
@WebServlet("/jhandicapCourseServlet")
public class jhandicapCourseServlet extends HttpServlet {
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {

	  
	  String addOrDelete = request.getParameter("function");
	  String url = null;
	  
	  if (addOrDelete.equals("ADD")) 
	  {
		  Course c = getCourse(request);  
		  CourseService courseService 			= JhandicapFactory.getCourseService();
		  Hashtable courses 					= courseService.getAllCourses();
		  Vector sortedCourses 					= getCourseList(courses);
		  request.setAttribute("Courses", sortedCourses);
		  request.setAttribute("Course", c);
		  url = "/addAcourseMVC.jsp";
      }
	  else if (addOrDelete.equals("DELETE"))
	  {
		  String[] selectedCourses =  request.getParameterValues("courseList");
		  String c = request.getParameter("courseList");
		  if (selectedCourses !=null){
			  deleteCourses(selectedCourses);
		  }
		  CourseService courseService 			= JhandicapFactory.getCourseService();
		  Hashtable courses 					= courseService.getAllCourses();
		  Vector sortedCourses 					= getCourseList(courses);
		  request.setAttribute("Courses", sortedCourses);
		  url = "/deleteCourse.jsp";  
	  }
	  
	  RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
	  dispatcher.forward(request, response);
	 
	  
	    
  }
  
  public void deleteCourses(String[] selectedCourses){
	int numCourses = selectedCourses.length; 
	 
	CourseService courseService = JhandicapFactory.getCourseService();
	
	numCourses--;
	
	while(numCourses >= 0){
		 String course = selectedCourses[numCourses];
		 try{
			 String deletedCourse = course.replace('+', ' ');
		     courseService.removeCourse(deletedCourse);
		 }
		 catch (Exception e){
			 
		 }
		numCourses--;
	}
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
  public Course getCourse(HttpServletRequest request){
	  
	  String courseName 	= request.getParameter("CourseName");
      String comments		= request.getParameter("comments");
      String slope 		    = request.getParameter("slope");
      String rating 		= request.getParameter("rating");
      String yardage 		= request.getParameter("yardage");
      String message        = "";
      Course c = null;
      float ratingF = 0;
      int   intS =0;
      int   intY=0;
      
      if (courseName != null){
    	 try {
    	   Integer yI    = new Integer(yardage);
    	   intY = yI.intValue();
    	   if (!(intY > 0)){
    		  message = "Yardage must be greater than 0";
    	   }
    	  }
    	  catch(Exception e){
        	  intY=0;
    		  message = "Yardage needs to be numeric";	  
        }
        try {
    		  Float rF  = new Float(rating);
    		  ratingF = rF.floatValue();
    		  if (!(ratingF > 0)){
    			  message = "Rating must be greater than 0";
    		  }
    	  }
    	  catch(Exception e){
        	  ratingF =0;
    		  message = "Rating needs to be numeric";	  
         
    	  }
     	  try {
    		  Integer sI   = new Integer(slope);
    		  intS = sI.intValue();
    		  if (!(intS > 0)){
    			  message = "Slope must be greater than 0";
    		  }
    		 
 	      }
    	  
    	  catch(Exception e){
      	      intS =0;
    		  message = "Slope needs to be numeric";	  
    	  }
    	  finally{
    		  if (courseName.length() == 0 ){
  	    	     message = "Course name is Required ";
    		  }   
    		  if (message == "")
    		  {
    			  CourseService courseService = JhandicapFactory.getCourseService();
    			  try {
    	        	   courseService.addCourse(courseName,ratingF,intS,intY,comments);
    	        	}
    	        	catch(Exception e){
    	        		message = "Course not Added - Possiple Duplicate";
    	        	}  
    		     message = "Course Added";
    		     courseName = ""; ratingF=0; intS=0; intY=0; comments="";
    		  }
    		  
    		  c = courseFactory(courseName,ratingF,intS,intY,comments);
    		  setMessage(request,message);
    		    
    	  }
      }
      else
      {
    	  c= courseFactory("", 0,0,0,"");
          message ="";
          setMessage(request,message);
    	  
      }
      return c;
	  
  }
  
  private void setMessage (HttpServletRequest request, String message){
	  request.setAttribute("Message", message);      
		 
  }
  private Course courseFactory(String courseName,float rating, int slope, int yardage, String comments ){
	  Course c = new Course(courseName,rating,slope,yardage,comments);
	  return c;
  }
  
    
  
}

