/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cwc.util.os;

import cwc.util.exceptions.UnsupportedOperatingSystemException;
import java.awt.Component;
import java.awt.Cursor;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author karnd01
 */
public final class WM {

    private static Cursor _WaitCursor = null;
    private static Cursor _DefaultCursor = null;
    private static Cursor _CrosshairCursor = null;
    private static Cursor _EastResizeCursor = null;
    private static Cursor _HandCursor = null;
    private static Cursor _MoveCursor = null;
    private static Cursor _NorthEastResizeCursor = null;
    private static Cursor _NorthWestResizeCursor = null;
    private static Cursor _NorthResizeCursor = null;
    private static Cursor _SouthEastResizeCursor = null;
    private static Cursor _SouthWestResizeCursor = null;
    private static Cursor _SouthResizeCursor = null;
    private static Cursor _TextCursor = null;
    private static Cursor _WestResizeCursor = null;

    /**
     *
     * @return The Operating Systems West Resize cursor.
     */
    public static Cursor getWestResizeCursor() {
        if( _WestResizeCursor == null ){
            _WestResizeCursor = Cursor.getPredefinedCursor(
                                    Cursor.W_RESIZE_CURSOR);
        }
        return _WestResizeCursor;
    }

    /**
     *
     * @return The Operating Systems Text cursor.
     */
    public static Cursor getTextCursor() {
        if( _TextCursor == null ){
            _TextCursor = Cursor.getPredefinedCursor(
                                    Cursor.TEXT_CURSOR);
        }
        return _TextCursor;
    }

    /**
     *
     * @return The Operating Systems South Resize cursor.
     */
    public static Cursor getSouthResizeCursor() {
        if( _SouthResizeCursor == null ){
            _SouthResizeCursor = Cursor.getPredefinedCursor(
                                    Cursor.S_RESIZE_CURSOR);
        }
        return _SouthResizeCursor;
    }

    /**
     *
     * @return The Operating Systems SouthWest Resize cursor.
     */
    public static Cursor getSouthWestResizeCursor() {
        if( _SouthWestResizeCursor == null ){
            _SouthWestResizeCursor = Cursor.getPredefinedCursor(
                                    Cursor.SW_RESIZE_CURSOR);
        }
        return _SouthWestResizeCursor;
    }

    /**
     *
     * @return The Operating Systems SouthEast Resize cursor.
     */
    public static Cursor getSouthEastResizeCursor() {
        if( _SouthEastResizeCursor == null ){
            _SouthEastResizeCursor = Cursor.getPredefinedCursor(
                                    Cursor.SE_RESIZE_CURSOR);
        }
        return _SouthEastResizeCursor;
    }

    /**
     *
     * @return The Operating Systems North Resize cursor.
     */
    public static Cursor getNorthResizeCursor() {
        if( _NorthResizeCursor == null )
        {
            _NorthResizeCursor = Cursor.getPredefinedCursor(
                                    Cursor.N_RESIZE_CURSOR);
        }
        return _NorthResizeCursor;
    }

    /**
     *
     * @return The Operating Systems NorthWest Resize cursor.
     */
    public static Cursor getNorthWestResizeCursor() {
        if( _NorthWestResizeCursor == null ){
            _NorthWestResizeCursor = Cursor.getPredefinedCursor(
                                    Cursor.NW_RESIZE_CURSOR);
        }
        return _NorthWestResizeCursor;
    }

    /**
     *
     * @return The Operating Systems NorthEast Resize cursor.
     */
    public static Cursor getNorthEastResizeCursor() {
        if( _NorthEastResizeCursor == null ){
            _NorthEastResizeCursor = Cursor.getPredefinedCursor(
                                    Cursor.NE_RESIZE_CURSOR);
        }
        return _NorthEastResizeCursor;
    }

    /**
     *
     * @return The Operating Systems Move cursor.
     */
    public static Cursor getMoveCursor() {
        if( _MoveCursor == null )
        {
            _MoveCursor = Cursor.getPredefinedCursor(
                                    Cursor.MOVE_CURSOR);
        }
        return _MoveCursor;
    }

    /**
     *
     * @return The Operating Systems Hand cursor.
     */
    public static Cursor getHandCursor() {
        if( _HandCursor == null )
        {
            _HandCursor = Cursor.getPredefinedCursor(
                                    Cursor.HAND_CURSOR);
        }
        return _HandCursor;
    }

    /**
     *
     * @return The Operating Systems East Resize cursor.
     */
    public static Cursor getEastResizeCursor() {
        if( _EastResizeCursor == null ){
            _EastResizeCursor = Cursor.getPredefinedCursor(
                                    Cursor.E_RESIZE_CURSOR);
        }
        return _EastResizeCursor;
    }

    /**
     *
     * @return The Operating Systems Cross hair cursor.
     */
    public static Cursor getCrosshairCursor() {
        if( _CrosshairCursor == null ){
            _CrosshairCursor = Cursor.getPredefinedCursor(
                                    Cursor.CROSSHAIR_CURSOR);
        }
        return _CrosshairCursor;
    }

    /**
     *
     * @return The Operating Systems Busy cursor.
     */
    public static Cursor getWaitCursor() {
        if( _WaitCursor == null ){
            _WaitCursor = Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR);
        }
        return _WaitCursor;
    }

    /**
     *
     * @return The Operating Systems Default cursor.
     */
    public static Cursor getDefaultCursor() {
        if( _DefaultCursor == null ){
            _DefaultCursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
        }

        return _DefaultCursor;
    }

    /**
     *
     * @param c Component to set cursor for.
     * @param cursor Cursor to set on the Component.
     */
    public static void setCursor( Component c, Cursor cursor ){
        if(c == null || cursor == null){
            return;
        }

        c.setCursor(cursor);
    }

    /**
     *
     * @param c Component to set cursor for.
     */
    public static void setWaitCursor( Component c ){
        setCursor( c, getWaitCursor() );
    }

    /**
     *
     * @param c Component to set cursor for.
     */
    public static void setDefaultCursor( Component c ){
        setCursor( c, getDefaultCursor() );
    }

    /**
     *
     * @param c Component to set cursor for.
     */
    public static void setWestResizeCursor( Component c ){
        setCursor( c, getWestResizeCursor() );
    }

    /**
     *
     * @param c Component to set cursor for.
     */
    public static void setTextCursor( Component c ){
        setCursor( c, getTextCursor() );
    }

    /**
     *
     * @param c Component to set cursor for.
     */
    public static void setSouthResizeCursor( Component c ){
        setCursor( c, getSouthResizeCursor() );
    }

    /**
     *
     * @param c Component to set cursor for.
     */
    public static void setSouthWestResizeCursor( Component c ){
        setCursor( c, getSouthWestResizeCursor() );
    }

    /**
     *
     * @param c Component to set cursor for.
     */
    public static void setSouthEastResizeCursor( Component c ){
        setCursor( c, getSouthEastResizeCursor() );
    }

    /**
     *
     * @param c Component to set cursor for.
     */
    public static void setNorthResizeCursor( Component c ){
        setCursor( c, getNorthResizeCursor() );
    }

    /**
     *
     * @param c Component to set cursor for.
     */
    public static void setNorthWestResizeCursor( Component c ){
        setCursor( c, getNorthWestResizeCursor() );
    }

    /**
     *
     * @param c Component to set cursor for.
     */
    public static void setNorthEastResizeCursor( Component c ){
        setCursor( c, getNorthEastResizeCursor() );
    }

    /**
     *
     * @param c Component to set cursor for.
     */
    public static void setMoveCursor( Component c ){
        setCursor( c, getMoveCursor() );
    }

    /**
     *
     * @param c Component to set cursor for.
     */
    public static void setHandCursor( Component c ){
        setCursor( c, getHandCursor() );
    }

    /**
     *
     * @param c Component to set cursor for.
     */
    public static void setEastResizeCursor( Component c ){
        setCursor( c, getEastResizeCursor() );
    }

    /**
     *
     * @param c Component to set cursor for.
     */
    public static void setCrosshairCursor( Component c ){
        setCursor( c, getCrosshairCursor() );
    }

    /**
     *
     * @param f The file to be opened via the OS's Default application.
     * @return The Process associated to the Runtime command.
     * @throws IOException
     * @throws NullPointerException
     * @throws SecurityException
     * @throws IllegalArgumentException
     * @throws UnsupportedOperatingSystemException
     */
    public static Process open( File f )
        throws IOException,
               NullPointerException,
               SecurityException,
               IllegalArgumentException,
               UnsupportedOperatingSystemException{

        String cmd = WM.getRuntimeExecutionCommand();
        //Process p = null;
//        try{
            Process p = Runtime.getRuntime().exec( cmd + f.getAbsolutePath() );
//        } catch( IOException ioe ){
//
//        }

        return p;
    }

    /**
     * @see "Supported Operated Systems."
     * @return The command for the determined Operating System to execute a
     *         file
     * @throws UnsupportedOperatingSystemException
     */
    public static String getRuntimeExecutionCommand()
    throws UnsupportedOperatingSystemException{
        String command = "UNKNOWN";

        if(OS.isLinux()){
            if(isGNOME()){
                command = String.format("gnome-open ");
            }
            else if (isKDE()){
                command = String.format("kde-open ");
            }

        }
        else if (OS.isMac()){
            command = String.format("open ");
        }
        else if (OS.isWindows()){
            command = String.format("rundll32 SHELL32.DLL,ShellExec_RunDLL ");
//            command = String.format("rundll32 url.dll,FileProtocolHandler ");
//            command = String.format("cmd /c start %s");
//            command = String.format("cmd /c ");
        }
//        else if (isWindows() && isWindows9X()){
//            if( debugmode )
//                System.out.println("Windows 9x");
//
//            command = String.format("command.com /C start %s");
//        }

        if(command.equalsIgnoreCase("UNKNOWN")){
            // NO OS Determined, must be Unsupported OS.
            throw new UnsupportedOperatingSystemException(
                                      "Unsupported Operating System Exception");
        }
        else{
            return command;
        }
    }

    /**
     *
     * @return Determines if the Operating System is GNOME
     */
    public static boolean isGNOME() {
        try{
            String wm = OS.getEnvironmentVariable("WINDOWMANAGER");
            return wm.indexOf("gnome") != -1;
        } catch( NullPointerException | SecurityException ex ){
            return false;
        }
    }
    /**
     *
     * @return Determines if the Operating System is KDE
     */
    public static boolean isKDE() {
        try{
            String wm = OS.getEnvironmentVariable("WINDOWMANAGER");
            return wm.indexOf("kde") != -1;
        } catch( NullPointerException | SecurityException ex ){
            return false;
        }
    }
}
