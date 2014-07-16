/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cwc.util.exceptions;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 *
 * @author Dean Karn
 */
public final class EX {
    
    public static String getStackTrace(Exception ex){
        try(StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw)){
            
            ex.printStackTrace(pw);
            
            return sw.toString();
            
        } catch(IOException ioe){
            return "";
        }
    }
}
