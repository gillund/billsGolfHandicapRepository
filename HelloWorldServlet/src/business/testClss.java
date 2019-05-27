package business;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class testClss {

    public static void main(String[] args) {
    	    String myDate = "05/1/2018";
    	    String year = myDate.substring(myDate.length()-4, myDate.length());
		      int slsh = myDate.indexOf("/");
		    String day = myDate.substring(0,slsh);
		    String month = myDate.substring(slsh+1, myDate.length()-5);
		    
		    String newDate = year + "-" + month  + "-" +day;
		
			SimpleDateFormat sfd = new SimpleDateFormat("hh:mm:ss");
			Calendar cal = Calendar.getInstance();
		    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		    String mytime =  sdf.format(cal.getTime()) ;
			String newDteTime = newDate + " " + mytime;
			
			System.out.println(newDteTime);
			
			
	    	    String myDate1= "5/10/2018";
	    	    String year1 = myDate1.substring(myDate.length()-4, myDate1.length());
			      int slsh1 = myDate1.indexOf("/");
			    String day1 = myDate1.substring(0,slsh1);
			    String month1 = myDate1.substring(slsh1+1, myDate1.length()-5);
			    
			    String newDate1 = year1 + "-" + month1  + "-" +day1;
			
				SimpleDateFormat sfd1 = new SimpleDateFormat("hh:mm:ss");
				Calendar cal1 = Calendar.getInstance();
			    SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
			    String mytime1 =  sdf.format(cal1.getTime()) ;
				String newDteTime1 = newDate1 + " " + mytime1;
				
				System.out.println(newDteTime1);
				System.out.println(newDate1);
    }  

}