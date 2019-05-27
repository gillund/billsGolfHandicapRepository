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
    }

}