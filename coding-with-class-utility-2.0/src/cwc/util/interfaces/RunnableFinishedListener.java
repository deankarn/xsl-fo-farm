/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cwc.util.interfaces;

import cwc.util.objects.RunnableFinishedEvent;
import java.util.EventListener;

/**
 *
 * @author karnd01
 */
public interface RunnableFinishedListener extends EventListener{

    public void runnableFinished( RunnableFinishedEvent event );
}
