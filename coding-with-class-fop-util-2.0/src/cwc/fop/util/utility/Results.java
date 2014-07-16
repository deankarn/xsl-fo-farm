/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cwc.fop.util.utility;

import java.io.ByteArrayOutputStream;

/**
 * @version 1.0
 * @author Dean Karn
 */
public class Results {

    private Boolean _success = true;
    private String _error = "";
    private final ByteArrayOutputStream _outStream;

    /**
     *
     * @param success   Determines if the conversion was successful
     * @param error     The Error Message, if any.
     * @param outStream The ByteArrayOutputStream containing the converted
     *                  results.
     */
    public Results( Boolean success, String error,
                        ByteArrayOutputStream outStream){

        this._success = success;
        this._error = error;
        this._outStream = outStream;
    }

    /**
     * @return The Error String, Blank in getSuccess() is true.
     */
    public String getError() {
        return _error;
    }

    /**
     * @return Whether the Conversion was successful or not.
     */
    public Boolean getSuccess() {
        return _success;
    }

    /**
     * @return A ByteArrayOutputStream containing the converted results.
     */
    public ByteArrayOutputStream getOutStream() {
        return _outStream;
    }
}
