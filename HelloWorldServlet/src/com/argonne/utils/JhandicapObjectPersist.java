package com.argonne.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.argonne.tournamentService.RyderCupPlayers;

public class JhandicapObjectPersist {
	
	JhandicapObjectPersist(){
		
	}

	public void serializeObject(Object o, String fileName) {
		ObjectOutputStream oos = null;
		FileOutputStream fout = null;
		try 
		{
			fout = new FileOutputStream(fileName);
			oos = new ObjectOutputStream(fout);
			oos.writeObject(o);
			oos.close();
			
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
	    catch (IOException e) 
	    {
		  e.printStackTrace();
	    }
		
   	}
	
	public Object deserializeObject (String fileName){
		
		Object o = new Object();
		
		try {
		    FileInputStream fin = new FileInputStream(fileName);
		    ObjectInputStream ois = new ObjectInputStream(fin);
		    o = (Object) ois.readObject();
		    ois.close();
		    }
		   catch (Exception e) {   return null; }
		     
       return o;
       
	}
    public boolean areRyderCupPlaersPersisted (String fileName){
		
		Object o = new Object();
		
		try {
		    FileInputStream fin = new FileInputStream(fileName);
		    ObjectInputStream ois = new ObjectInputStream(fin);
		    o = (Object) ois.readObject();
		    ois.close();
		    }
		
		   catch (Exception e) {   return false; }
		     
       RyderCupPlayers rcp = (RyderCupPlayers) o;
       
       if (rcp.getBlueNbrPlayers() + rcp.getRedNbrPlayers() > 0){
    	   return true;
       }
       else
       {   
    	   return false;
       }
       
	}
    
    public boolean purgedRyderCupPlyers (String fileName){
		
    	File f = new File(fileName);
        if (f.exists()){
        	f.delete();
        	return true;
        }
        return false;
       
	}
    
    
    public String getRyderCupPlayerFile (){
		return "ShagnastyRyderCup.dat";
       
	}
    
   public boolean purgedTournament (String fileName){
		
    	File f = new File(fileName);
        if (f.exists()){
        	f.delete();
        	return true;
        }
        return false;
       
	}
    
   public String getTournamentFile (){
		return "tournament.dat";
      
	}
   
}
