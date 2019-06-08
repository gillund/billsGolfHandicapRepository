package com.argonne.courseServices;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;

import com.argonne.utils.Log;

/**
*
* This class manages a collection of courses stored in the courses.date file
*
* Example: (name,rating,slope,yardage,comments)
*
*   Carillon RW,68.7,117,6400,Tough 9th on White
*   Arrowhead SE,70.4,127,6800,Good
*
*
* @author Mark Woyna
* @since 11/9/2004
* @version 1.0
*/

public class CourseServiceImpl implements CourseService
{
    public static String DEFAULT_FILENAME  = System.getProperty("user.home") + File.separator + "Documents" + File.separator + "courses.dat";
	
    
    public static Hashtable tempCourses = new Hashtable();
    private String filename;
    private Hashtable courses ;

    public CourseServiceImpl() {
        this(DEFAULT_FILENAME);
    }

    public CourseServiceImpl(String aFilename) {
        filename = aFilename;
    }

    public Hashtable getAllCourses() {
        return (Hashtable) getCourses().clone();
    }
    
    public Course getCourse(int courseId) {
        return (Course) getCourses().get(courseId);
    }

    public Hashtable addCourse(String aName, float aRating, int aSlope, int theYardage, String theComments) throws Exception {
        if ( aName == null || aName.equals("") ) {
            throw new Exception("Illegal Course Name: Course Name Missing");
        }
        if ( courses.containsKey(aName) ) {
            throw new Exception("Illegal Course Name: Course Already Exists");
        }
        if ( theComments.equals("") ) {
            theComments = " ";
        }
        
        Course newCourse = new Course(aName, aRating, aSlope, theYardage, theComments);
       
        Course c = writeCourses(getFilename(), courses,newCourse, false);
        if (c !=null)
        {
        	courses.put(c.getCourseId(), c);
        }
        else
        {
        	courses.put(newCourse.getName(),newCourse);
        }
        return getCourses();
    }
    public Hashtable removeCourse(String courseName) throws Exception {
    	
    	if ( courseName == null || courseName.equals("") ) {
            throw new Exception("Illegal Course Name: Course Name Missing");
        }
    	if ( ! courses.containsKey(courseName) ) {
             throw new Exception("Illegal Course Name: Not in DB");
    	}
    	
    	Course course = (Course) courses.get(courseName);
    	if (course == null)
    	{
    		System.err.println("Invalid Course should be in hashmap ");
    		throw new Exception ("Can't delete course does not exist");
    	}
    	
       
        Course c = writeCourses(getFilename(), courses,course, true);
        courses.remove(c.getCourseId());
        return getCourses();
    
    }
    protected String getFilename() {
         return filename;
    }
    
    protected Hashtable getCourses() {
    
       if ( courses == null ) {
            courses = readCourses(getFilename());
         }
        return courses;
     
    	
    }

    protected Hashtable readCourses(String aFilename) {
    	File dir = new File(aFilename);
    	
    	System.out.println("Current directory = " + dir );
		
        BufferedReader input = null;
        String line = null;

        try {
            input = getInputStream(aFilename);

            // read the first record of the file
            while ( (line = input.readLine()) != null ) {
                try {
                    Course course = createCourse(line);
                    tempCourses.put(course.name, course);
                    Log.debug(line);
                }
                catch(NumberFormatException exp) {
                   Log.warning(exp + " Illegal Numeric Value on line: " + line);
                }
             }
         }
         catch (IOException e) {
        	
        	 	System.out.println("Current directory = " + System.getProperty("user.dir"));
				e.printStackTrace();
			    
             Log.warning("IOException error: " + e.getMessage());
        }
        finally {
            // if the file opened, close it
            if ( input != null ) {
                try {
                     input.close();
                }
                catch (IOException ioe) {
                    Log.warning("IOException error trying to close the file: " + ioe.getMessage());
                }
            }
        }

        return tempCourses;
    }

    protected BufferedReader getInputStream(String aFilename) throws IOException {
        return new BufferedReader(new FileReader(aFilename));
    }

    protected Course createCourse(String line) throws NumberFormatException {
        StringTokenizer st = new StringTokenizer(line, ",");
        String name = st.nextToken();
        String rating = st.nextToken();
        String slope = st.nextToken();
        String yardage = st.nextToken();
        String comments = st.nextToken();

        return new Course(name, (float) Double.parseDouble(rating), Integer.parseInt(slope), Integer.parseInt(yardage), comments);
    }

    protected Course writeCourses(String aFilename, Hashtable aCourseCollection, Course c, boolean remove) {
    	
       BufferedWriter writer = null;

        try {
            writer = getOutputStream(aFilename);
            Enumeration enumeration = aCourseCollection.elements();
            while ( enumeration.hasMoreElements() ) {
                Course course = (Course) enumeration.nextElement();
                writeCourse(course, writer);
            }

            writer.close();
        }
        catch (IOException ioe) {
             Log.warning("IOException error: " + ioe.getMessage());
        }
        finally {
            // if the file opened, close it
            if ( writer != null ) {
                try {
                     writer.close();
                }
                catch (Exception ioe) {
                    Log.warning("Exception error trying to close the file: " + ioe.getMessage());
                }
            }
        }
        return null;
    }

    protected BufferedWriter getOutputStream(String aFilename) throws IOException {
        return new BufferedWriter(new FileWriter(aFilename));
    }
    
    protected void writeCourse(Course course, BufferedWriter writer) throws IOException {
        StringBuffer line = new StringBuffer();
        line.append(course.name); line.append(",");
        line.append(course.rating); line.append(",");
        line.append(course.slope); line.append(",");
        line.append(course.yardage); line.append(",");
        line.append(course.comments);

        writer.write(line.toString());
        writer.newLine();
    }

    public void printCourses(Hashtable aCourseCollection) {
        Log.debug("Courses: ");
        Enumeration enumeration = courses.elements();
        while ( enumeration.hasMoreElements() ) {
            Course course = (Course) enumeration.nextElement();
            Log.debug(course.name + ", " + course.slope + ", " + course.rating + ", " + course.yardage + ", " + course.comments);
        }
        Log.debug("");
    }

    public static void main(String[] args) {
        try {
            CourseServiceImpl courseService = new CourseServiceImpl();
            Hashtable courses = courseService.getAllCourses();
            courseService.printCourses(courses);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}