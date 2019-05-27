
package com.argonne.utils;

/**
*
* ShutdownHandler allows the ORB to shut down cleanly when the JVM exits
*
* Thanks to ORBACUS, at http://www.ooc.com/faq/orbacus_sect_2_3.html
*
* @author Mark Woyna
* @since 5/20/2002
* @version 1.0
*/

public class ShutdownHandler extends java.lang.Thread {

    private final org.omg.CORBA.ORB orb;

    /**
    *
    * Construct an instance.
    *
    */

    public ShutdownHandler(org.omg.CORBA.ORB anOrb) {
        orb = anOrb;
    }

    /**
    *
    * register is used to register the handler with the runtime
    *
    */

    public void register() {
        Log.information("Registering Shutdown Hook...");
        Runtime.getRuntime().addShutdownHook(this);
    }

    /**
    *
    * unregister is used to unregister the handler with the runtime
    *
    */

    public void unregister() {
        Log.information("Unregistering Shutdown Hook...");
        Runtime.getRuntime().removeShutdownHook(this);
    }

    /**
    *
    * run is used to trigger the ORB shutdown
    *
    */

    public void run() {
        Log.information("ORB shutting down...");
        orb.shutdown(false);
    }
}
