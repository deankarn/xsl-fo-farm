/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cwc.util.os;

import cwc.util.exceptions.UnsupportedOperatingSystemException;
import java.io.IOException;
import java.util.Date;

/**
 * @version 1.0
 * @author karnd01
 */
public final class OS {

    /**
     *
     * @return number of available processor cores, including virtual cores
     */
    public static int getProsessorCount(){
        return Runtime.getRuntime().availableProcessors();
    }

    /**
     *
     * @return The System Timestamp
     */
    public static long getTimestamp(){
        return new Date().getTime();
    }

    /**
     *
     * @return The users home directory.
     * @throws SecurityException
     * @throws NullPointerException
     * @throws IllegalArgumentException
     */
    public static String getHomeDirectory()
            throws SecurityException,
                   NullPointerException,
                   IllegalArgumentException{
        return System.getProperty("user.home");
    }

    /**
     *
     * @param includeSeparator determines if the line separator is appended.
     * @return The users home directory, with or without the path separator
     *         appended.
     * @throws SecurityException
     * @throws NullPointerException
     * @throws IllegalArgumentException
     */
    public static String getHomeDirectory( boolean includeSeparator )
        throws SecurityException,
               NullPointerException,
               IllegalArgumentException{
        if(includeSeparator){
            return getHomeDirectory() + getPathSeparator();
        }
        else{
            return getHomeDirectory();
        }
    }

    /**
     *
     * @return The Operating Systems path separator.
     * @throws SecurityException
     * @throws NullPointerException
     * @throws IllegalArgumentException
     */
    public static String getPathSeparator()
        throws SecurityException,
               NullPointerException,
               IllegalArgumentException{
        return System.getProperty("file.separator");
    }

    /**
     *
     * @return The Operating Systems line separator.
     * @throws SecurityException
     * @throws NullPointerException
     * @throws IllegalArgumentException
     */
    public static String getLineSeparator()
        throws SecurityException,
               NullPointerException,
               IllegalArgumentException{
        return System.getProperty("line.separator");
    }

    /**
     * 
     * @return The Operating Systems name.
     * @throws SecurityException
     * @throws NullPointerException
     * @throws IllegalArgumentException
     */
    public static String getOSName()
        throws SecurityException,
               NullPointerException,
               IllegalArgumentException{
        return System.getProperty("os.name");
    }

    /**
     *
     * @param ev The Environment Variable to look for.
     * @return The specified Environment Variable value.
     * @throws NullPointerException
     * @throws SecurityException
     */
    public static String getEnvironmentVariable( String ev )
            throws NullPointerException,
                   SecurityException{
        
        return System.getenv(ev);
    }

    /**
     * 
     * @param cmds Commands to execute at the OS, NOT WINDOWMANAGER, LEVEL.
     * @return The Process associated to the Runtime command.
     * @throws IOException
     * @throws NullPointerException
     * @throws SecurityException
     * @throws IllegalArgumentException
     * @throws UnsupportedOperatingSystemException
     */
    public static Process execute( String cmds[] )
        throws IOException, 
               NullPointerException,
               SecurityException,
               IllegalArgumentException,
               UnsupportedOperatingSystemException{

        //Process p = null;
//        try{
            Process p = Runtime.getRuntime().exec( cmds );
//        } catch( IOException ioe ){
//            
//        }

        return p;
    }

    /**
     *
     * @return Determines if the Operating System is Windows
     */
    public static boolean isWindows() {
        String os = getOSName().toLowerCase();
        return os.indexOf("windows") != -1 || os.indexOf("nt") != -1;
    }
    /**
     *
     * @return Determines if the Operating System is MAC/OSX
     */
    public static boolean isMac() {
        String os = getOSName().toLowerCase();
        return os.indexOf("mac") != -1;
    }
    /**
     *
     * @return Determines if the Operating System is Linux
     */
    public static boolean isLinux() {
        String os = getOSName().toLowerCase();
        return os.indexOf("linux") != -1;
    }
    
//    public static boolean isWindows9X() {
//        String os = getOSName().toLowerCase();
//        return os.equals("windows 95") || os.equals("windows 98");
//    }
}
