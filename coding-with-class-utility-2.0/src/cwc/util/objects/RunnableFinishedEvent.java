/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cwc.util.objects;

import cwc.util.events.RunnableFinished;
import java.util.EventObject;

/**
 *
 * @author karnd01
 */
public class RunnableFinishedEvent extends EventObject{

    private RunnableFinished _runnableFinished;

    public RunnableFinished getRunnableFinished() {
        return _runnableFinished;
    }

    public RunnableFinished changeClass() {
        return _runnableFinished;
    }

    public RunnableFinishedEvent( Object source, RunnableFinished runnableFinished ) {
        super( source );
        _runnableFinished = runnableFinished;
    }


}
