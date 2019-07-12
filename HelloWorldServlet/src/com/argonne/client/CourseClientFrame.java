package com.argonne.client;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.table.AbstractTableModel;

import com.argonne.courseServices.Course;
import com.argonne.courseServices.CourseService;
import com.argonne.courseServices.CourseServiceImpl;
import com.argonne.courseServices.CourseUpdateConsumer;
import com.argonne.courseServices.CourseUpdateSupplier;
import com.argonne.utils.Log;

/**
*   This is the Web based Jhandicap ....
*/

public class CourseClientFrame extends JFrame implements CourseUpdateSupplier {

    JPanel contentPane;
    JPanel jPanel1 = new JPanel();
    JPanel jPanel2 = new JPanel();
    JPanel jPanel3 = new JPanel();
    JPanel jPanel4 = new JPanel();
    JPanel jPanel5 = new JPanel();

    JTextField course = new JTextField(12);
    JTextField slope = new JTextField(3);
    JTextField rating = new JTextField(5);
    JTextField yardage = new JTextField(5);
    JTextField comments = new JTextField(20);

    JLabel courseLabel = new JLabel();
    JLabel slopeLabel = new JLabel();
    JLabel ratingLabel = new JLabel();
    JLabel yardageLabel = new JLabel();
    JLabel commentsLabel = new JLabel();

    JButton addCourseButton = new JButton();
    JButton closeButton = new JButton();
    JButton removeCourseButton = new JButton();
    
    // storage for courses data
    JTable courses;
    private AbstractTableModel coursesDataTableModel;
    private Object[][] coursesData = {};
    private String[] coursesDataColumnNames = { "Course Name", "Rating", "Slope", "Yardage", "Comments" };
    
    CourseService courseService = null;
    Hashtable courseUpdateConsumers = new Hashtable();

    // Construct the frame
    public CourseClientFrame(String[] args) {
        try {
            init();
            initializeService(args);
            loadCoursesData();
        }
        catch(Exception e) {
            Log.warning(e);
        }
    }
    
    public CourseClientFrame(CourseService aCourseService) {
        try {
            init();
            initializeService(aCourseService);
            loadCoursesData();
        }
        catch(Exception e) {
            Log.warning(e);
        }
    }

    // Component initialization
    private void init() throws Exception  {
        contentPane = (JPanel) this.getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        contentPane.setBorder(BorderFactory.createEtchedBorder());
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(new Dimension(620 /*width*/, 485));
        this.setTitle("Course Service Client");

        jPanel3.setLayout(new FlowLayout(FlowLayout.CENTER));
        jPanel4.setLayout(new FlowLayout(FlowLayout.CENTER));

        courseLabel.setText("Course Name:");
        courseLabel.setBounds(new Rectangle(28, 50, 140, 25));
        course.setBounds(new Rectangle(173, 50, 213, 29));
 
        ratingLabel.setText("Rating:");
        ratingLabel.setBounds(new Rectangle(28, 50, 140, 25));
        rating.setBounds(new Rectangle(173, 50, 213, 29));

        slopeLabel.setText("Slope:");
        slopeLabel.setBounds(new Rectangle(28, 50, 140, 25));
        slope.setBounds(new Rectangle(173, 50, 213, 29));

        yardageLabel.setText("Yardage:");
        yardageLabel.setBounds(new Rectangle(28, 50, 140, 25));
        yardage.setBounds(new Rectangle(173, 50, 213, 29));

        commentsLabel.setText("Comments:");
        commentsLabel.setBounds(new Rectangle(28, 50, 140, 25));
        commentsLabel.setBounds(new Rectangle(173, 50, 213, 29));

        coursesDataTableModel = new CoursesDataTableModel();
        courses = new JTable(coursesDataTableModel);
        courses.setPreferredScrollableViewportSize(new Dimension(525, 319));
        courses.setShowGrid(true);
        JScrollPane coursesScrollPane = new JScrollPane(courses);

        addCourseButton.setText("Add Course");
        addCourseButton.setBounds(new Rectangle(49, 160, 150, 35));
        addCourseButton.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(ActionEvent e) {
                addCourseButton_actionPerformed(e);
            }
        });
        
       removeCourseButton.setText("Remove Course");
       removeCourseButton.setBounds(new Rectangle(49, 160, 150, 35));
       removeCourseButton.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(ActionEvent e) {
                removeCourseButton_actionPerformed(e);
              
            }
        });
        
        closeButton.setText("Close");
        closeButton.setBounds(new Rectangle(229, 160, 150, 35));
        closeButton.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(ActionEvent e) {
                closeButton_actionPerformed(e);
            }
        });

        jPanel3.setToolTipText("");
        contentPane.add(jPanel3, null);
        jPanel3.add(coursesScrollPane, null);

        jPanel1.setToolTipText("");
        contentPane.add(jPanel1, null);
        jPanel1.add(courseLabel, null);
        jPanel1.add(course, null);

        jPanel1.setToolTipText("");
        jPanel1.add(ratingLabel, null);
        jPanel1.add(rating, null);

        jPanel1.setToolTipText("");
        jPanel1.add(slopeLabel, null);
        jPanel1.add(slope, null);

        jPanel1.setToolTipText("");
        jPanel1.add(yardageLabel, null);
        jPanel1.add(yardage, null);

        jPanel2.setToolTipText("");
        contentPane.add(jPanel2, null);
        jPanel2.add(commentsLabel, null);
        jPanel2.add(comments, null);

        jPanel4.setToolTipText("");
        contentPane.add(jPanel4, null);
        jPanel4.add(addCourseButton, null);
        jPanel4.add(removeCourseButton, null);
        jPanel4.add(closeButton, null);
    }

    private void initializeService(String[] args) {
        try {
            courseService = getCourseService();
            Log.debug("Resolved CourseService.");
        }
        catch(Exception e) {
            Log.warning(e);
        }
    }

    private void initializeService(CourseService aCourseService) {
        try {
            courseService = aCourseService;
            Log.debug("Resolved CourseService.");
        }
        catch(Exception e) {
            Log.warning(e);
        }
    }

    protected void loadCoursesData() {
        Hashtable tempCourses = courseService.getAllCourses();
        loadCoursesData(tempCourses);
    }

    protected void loadCoursesData(Hashtable tempCourses) {
        Vector sortedCourses = sortCourses(tempCourses);
        Log.debug("Displaying " + sortedCourses.size() + " courses.");

        // coursesDataColumnNames = { "Course Name", "Rating", "Slope", "Yardage", "Comments" };
        Object[][] newData = new Object[sortedCourses.size()][coursesDataColumnNames.length];
        for ( int i = 0; i < newData.length; i++ ) {
            Course aCourse = (Course) sortedCourses.elementAt(i);
            newData[i][0] = aCourse.name;
            newData[i][1] = new Float(aCourse.rating);
            newData[i][2] = new Integer(aCourse.slope);
            newData[i][3] = new Integer(aCourse.yardage);
            newData[i][4] = aCourse.comments;
        }

        // reset model
        coursesData = newData;

        // instruct table to refresh its view since the underlying model has changed
        coursesDataTableModel.fireTableDataChanged();
    }

    protected Vector sortCourses(Hashtable tempCourses) {
        Vector sortedCourses = new Vector();
        Enumeration enumeration = tempCourses.elements();
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
    
    void closeButton_actionPerformed(ActionEvent event) {
        Log.debug("Closing Client...");
        this.setVisible(false);
    }

    void addCourseButton_actionPerformed(ActionEvent event) {

        // get the values from the fields
        String slopeValue = slope.getText();
        String ratingValue = rating.getText();
        String yardageValue = yardage.getText();

        // check to see the slope field is not entered. it has to be checked here since
        // only the int value is taken as the argument in the server.
        int newSlope = 0;
        if( slopeValue.length() != 0 ) {
            try {
                newSlope = Integer.parseInt(slopeValue);
            }
            catch(NumberFormatException exp) {
                JOptionPane.showMessageDialog(this,"Illegal Value: Slope");
                slope.setText("0");
                return;
            }
        }
        else {
            JOptionPane.showMessageDialog(this,"Illegal Value: Slope");
            slope.setText("0");
            return;
        }

        // check to see the rating field is not entered. it has to be checked here since
        // only the float value is taken as the argument in the server.
        float newRating = 0.0f;
        if( ratingValue.length() != 0 ) {
            try {
                newRating = Float.parseFloat(ratingValue);
            }
            catch(NumberFormatException exp) {
                JOptionPane.showMessageDialog(this,"Illegal Value: Rating");
                rating.setText("0.0");
                return;
            }
        }
        else {
            JOptionPane.showMessageDialog(this,"Illegal Value: Rating");
            rating.setText("0.0");
            return;
        }

        // check to see the yardage field is not entered. it has to be checked here since
        // only the int value is taken as the argument in the server.
        int newYardage = 0;
        if( yardageValue.length() != 0 ) {
            try {
                newYardage = Integer.parseInt(yardageValue);
            }
            catch(NumberFormatException exp) {
                JOptionPane.showMessageDialog(this,"Illegal Value: Yardage");
                yardage.setText("0");
                return;
            }
        }
        else {
            JOptionPane.showMessageDialog(this,"Illegal Value: Yardage");
            yardage.setText("0");
            return;
        }

        try {
            Hashtable newCourses = courseService.addCourse(course.getText(), newRating, newSlope, newYardage, comments.getText());
            loadCoursesData(newCourses);
            publishCourseUpdates(newCourses);
        }
        catch(Exception e) {
            Log.warning(e);
        }
    }
    void removeCourseButton_actionPerformed(ActionEvent event) {
    	
   	 int rowNumberToDelete = courses.getSelectedRow();
   	 if (rowNumberToDelete < 0 ){
   	    JOptionPane.showMessageDialog(this,"Must select Row to remove Course");
   	    return;
   	 }
   	 String name = (String) courses.getValueAt(rowNumberToDelete,0);
   	 Hashtable newCourses = null;
        try {
           newCourses = courseService.removeCourse(name);
        }
        catch (Exception e)
        {
        	JOptionPane.showMessageDialog(this,"Course not in DB");
           return;
        }
        loadCoursesData(newCourses);
        publishCourseUpdates(newCourses);

 
   }
    protected CourseService getCourseService() {
        if ( courseService == null ) {
            Log.information("Using Default Course DataFile.");
            courseService = new CourseServiceImpl();
        }

        return courseService;
    }

    public void addConsumer(CourseUpdateConsumer consumer) {
        courseUpdateConsumers.put(consumer, consumer);
    }

    public void removeConsumer(CourseUpdateConsumer consumer) {
        courseUpdateConsumers.remove(consumer);
    }

    protected void publishCourseUpdates(Hashtable courseCollection) {
        Enumeration enumeration = courseUpdateConsumers.elements();
        while ( enumeration.hasMoreElements() ) {
            CourseUpdateConsumer consumer = (CourseUpdateConsumer) enumeration.nextElement();
            consumer.acceptCourses(courseCollection);
        }
    }

    /**
    * Represents the underlying data model for the table.
    */
    class CoursesDataTableModel extends AbstractTableModel {
        public int getRowCount() {
            return coursesData.length;
        }

        public int getColumnCount() {
            return coursesDataColumnNames.length;
        }

        public Object getValueAt(int row, int column) {
            return coursesData[row][column];
        }

        public String getColumnName(int columnIndex) {

            return coursesDataColumnNames[columnIndex];
        }
    }
    
    /**
    * The main driver program
    *
    */

    public static void main(String[] args) {
        boolean packFrame = false;
        try {
            printCopyright();
            
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            CourseClientFrame courseClientFrame = new CourseClientFrame(args);

            // Center the window
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            Dimension frameSize = courseClientFrame.getSize();
            if ( frameSize.height > screenSize.height ) {
                frameSize.height = screenSize.height;
            }
            if ( frameSize.width > screenSize.width ) {
                frameSize.width = screenSize.width;
            }

            courseClientFrame.setLocation(
                                    (screenSize.width - frameSize.width) / 2,
                                    (screenSize.height - frameSize.height) / 2);
            courseClientFrame.setVisible(true);
        }
        catch(Exception e) {
            Log.warning(e);
        }
    }
    
    protected static void printCopyright() {
        System.out.println("JHandicap V2.0 11/2008");
        System.out.println("JHandicap comes with ABSOLUTELY NO WARRANTY;");
        System.out.println("");
    }
}

