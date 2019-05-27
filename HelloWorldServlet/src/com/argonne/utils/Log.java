/*
 * ====================================================================
 *
 * Copyright (c) 2004 Argonne Technologies.  All rights reserved.
 *
 * This file is part of JHandicap.
 *
 * JHandicap is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 * JHandicap is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 *  along with JHandicap; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 *
 * Contact Mark Woyna at woyna at argonne dot com
 *
 */

package com.argonne.utils;

import java.io.*;

/**
*
* Log is a utility class that allows debugging, information, warning, and
* error messages to be directed to an appropriate output stream. The current
* implementation simply outputs the messages to System.out and System.err.
* Future enhancements could direct specific types of messages to a file,
* central repository, or a system management console.
*
* @author Mark Woyna
* @since 7/10/2000
* @version 1.0
* @version 1.1  Added static initializer to set debug level
*/

public class Log {

    private static PrintStream output = System.out;
    private static PrintStream error = System.err;
    private static boolean debugOn; // used to globally turn debug messages on and off

    static{

        String debugOnPropertyValue = System.getProperty("LogDebugOn");
        if ( debugOnPropertyValue != null && debugOnPropertyValue.equals("false") ) {
            debugOn = false;
        }
        else {
            debugOn = true;
        }

        System.out.println("LogDebugOn = " + debugOn);
    }

    /**
    *
    * Construct an instance. Constructor is private to force the use as a
    * static class
    */

    private Log() {
    }

    /**
    *
    * setDebug is used to globally turn debug messages on and off
    *
    * @param debugFlag boolean
    */

    public static void setDebug(boolean debugFlag) {
        debugOn = debugFlag;
    }

    /**
    *
    * debug is used to print a debug message to System.out
    *
    * @param debugMessage String the debug message
    */

    public static void debug(String debugMessage) {
        if ( debugOn ) {
            output.println(debugMessage);
        }
    }

    /**
    *
    * information is used to print an information message to System.out
    *
    * @param informationMessage String the information message
    */

    public static void information(String informationMessage) {
        output.println(informationMessage);
    }

    /**
    *
    * warning is used to print a warning message to System.err
    *
    * @param warningMessage String the warning message
    */

    public static void warning(String warningMessage) {
        error.println(warningMessage);
    }

    public static void warning(Exception e) {
        error.println(e);
    }

    /**
    *
    * alarm is used to print an alarm message to System.err
    *
    * @param alarmMessage String the alarm message
    */

    public static void alarm(String alarmMessage) {
        error.println(alarmMessage);
    }

    /**
    *
    * alarm is used to print an Exception to System.err
    *
    * @param e Exception the Exception
    */

    public static void alarm(Exception e) {
        e.printStackTrace(error);
        error.println(e);
    }
}
