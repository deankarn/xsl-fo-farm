/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cwc.util.threading;



import cwc.util.events.RunnableFinished;
import cwc.util.exceptions.Exceptionx;
import cwc.util.interfaces.RunnableFinishedListener;
import cwc.util.objects.RunnableFinishedEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author karnd01
 */
public class MonitorableRunnable implements Runnable{

    private List<RunnableFinishedListener> _runnableFinishedListenerx =
            new ArrayList<>();

    public synchronized void addRunnableFinishedListener( RunnableFinishedListener l ) {
        _runnableFinishedListenerx.add( l );
    }

    public synchronized void removeRunnableFinishedListener( RunnableFinishedListener l ) {
        _runnableFinishedListenerx.remove( l );
    }

    public synchronized List<RunnableFinishedListener> getRunnableFinishedListeners(){
        return this._runnableFinishedListenerx;
    }

    public synchronized void _fireRunnableFinished( Object results, Exceptionx e){

        RunnableFinished tc = new RunnableFinished(results, e);
        RunnableFinishedEvent changedEvent = new RunnableFinishedEvent(this, tc);
        Iterator listeners = _runnableFinishedListenerx.iterator();
        while( listeners.hasNext() ) {
            ( (RunnableFinishedListener) listeners.next() ).runnableFinished( changedEvent );
        }
    }

    @Override
    public void run(){
        _fireRunnableFinished(null, null);
    }
}
