package business;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;
public class DateTest
{
   public static boolean validateJavaDate(String strDate)
   {
	/* Check if date is 'null' */
	if (strDate.trim().equals(""))
	{
		System.out.println(strDate+" is Invalid Date format");
	    return false;
	  
	}
	/* Date is not 'null' */
	else
	{
	    /*
	     * Set preferred date format,
	     * For example MM-dd-yyyy, MM.dd.yyyy,dd.MM.yyyy etc.*/
	    SimpleDateFormat sdfrmt = new SimpleDateFormat("yyyy-MM-dd");
	    sdfrmt.setLenient(false);
	    /* Create Date object
	     * parse the string into date 
             */
	    try
	    {
	        Date javaDate = sdfrmt.parse(strDate); 
	        System.out.println(strDate+" is valid date format");
	    }
	    /* Date format is invalid */
	    catch (ParseException e)
	    {
	        System.out.println(strDate+" is Invalid Date format");
	        return false;
	    }
	   
	    return true;
	}
   }
   public static void main(String args[]){
	//validateJavaDate("");
	//validateJavaDate("2016-29-5");
	//validateJavaDate("2019-10-35");
	   
	   System.out.println("Loading driver...");
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("driver loaded..");
			System.out.println(System.getProperty("java.class.path"));
			
		}
		catch(ClassNotFoundException e)
		{
			System.out.println("driver NOT found");
			e.printStackTrace();
		}

	   
	   
	   
	validateJavaDate("2019-10-30");
	validateJavaDate("2019-12-01");
   }
}