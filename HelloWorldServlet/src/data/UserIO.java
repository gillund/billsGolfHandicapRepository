/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package data;

import java.io.*;
import business.User;
import java.util.*;

public class UserIO
{
    public static void add(User user, String filepath) 
    throws IOException
    {
        File file = new File(filepath);
        PrintWriter out = new PrintWriter(
                new FileWriter(file, true));
        out.println(user.getEmailAddress()+ "|"
                + user.getFirstName() + "|"
                + user.getLastName());        
        out.close();
    }
    
    public static List<String> read(String filepath) {
      try {
        BufferedReader r = new BufferedReader(new FileReader(filepath));
        List<String> answer = new ArrayList<String>( );
        String user = null;
        while ((user = r.readLine( )) != null)
            answer.add(user);
        return answer;
      } catch (Exception e) { return null; }
    }
}

