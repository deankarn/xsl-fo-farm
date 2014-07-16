/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cwc.util.events;

import cwc.util.exceptions.Exceptionx;


/**
 *
 * @author karnd01
 */
public class RunnableFinished {

    private Exceptionx _Exception = null;
    private Object _results = null;

//    public RunnableFinishedx() {
//    }

    public RunnableFinished( Object results, Exceptionx e ) {
        this._results = results;
        this._Exception = e;
    }

    public Boolean getSuccess(){
        return ( this._Exception == null );
    }

    public Object getResults() {
        return _results;
    }

    public Exceptionx getException(){
        return this._Exception;
    }
}
