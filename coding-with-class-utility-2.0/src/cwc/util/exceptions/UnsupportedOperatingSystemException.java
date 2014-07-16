/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cwc.util.exceptions;

/**
 *
 * @author karnd01
 */
public class UnsupportedOperatingSystemException extends Exception {
    private static final long serialVersionUID = 1L;

    public UnsupportedOperatingSystemException(){
        super();
    }
    public UnsupportedOperatingSystemException( String message ){
        super(message);
    }
}
