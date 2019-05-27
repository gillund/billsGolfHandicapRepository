package com.argonne.playerService;


import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
*
* @author William Gillund
* @since 11/9/2008
* @version 1.0
*/

public class Player {
	private int playerId;
    public String firstName;
    public String eMail;
    public String phoneNumber;
    private String userId;
    
	private String password;
   
    public Player(int PlayerId, String aFirstName, String aeMail, String aphoneNumber,String auserId,String apassWord) {
        playerId = playerId;
    	firstName = aFirstName;
        eMail = aeMail;
        phoneNumber = aphoneNumber;
        userId=auserId;
        password=apassWord;
        
        
        
        
    }
    public Player()
    {
    	
    }
    public int getPlayerId()
    {
    	return playerId;
    }
    public void setPlayerId(int aplayerId)
    {
    	 playerId = aplayerId;
    }
    public String getfirstName()
    {
    	return firstName;
    }
    public void  setfirstName(String afirstName)
    {
    	firstName = afirstName;
    }
    public String getEmail()
    {
    	return eMail;
    }
    public void  setEmail(String aemail)
    {
    	 eMail=aemail;
    }
    public String getPhoneNumber()
    {
    	return phoneNumber;
    }
    public void setPhoneNumber(String aphoneNumber)
    {
    	phoneNumber=aphoneNumber;
    }
    public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
    public static class AllTests extends TestCase {

        public AllTests(String name) {
            super(name);
        }

        protected void setUp() {
        }

        protected void tearDown() {
        }

        public static void main (String[] args) {
            junit.textui.TestRunner.run(suite());
        }

        public static Test suite() {
            return new TestSuite(AllTests.class);
        }

        public void testConstructor() {
            }
    }
}
