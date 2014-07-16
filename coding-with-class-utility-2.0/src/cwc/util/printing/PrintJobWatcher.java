/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cwc.util.printing;

import javax.print.DocPrintJob;
import javax.print.event.PrintJobAdapter;
import javax.print.event.PrintJobEvent;

/**
 *
 * @author karnd01
 */
public class PrintJobWatcher {

    boolean done = false;

        PrintJobWatcher(DocPrintJob job) {
            // Add a listener to the print job
            job.addPrintJobListener(new PrintJobAdapter() {
                @Override
                public void printJobCanceled(PrintJobEvent pje) {
                    allDone();
                }

                @Override
                public void printJobCompleted(PrintJobEvent pje) {
                    allDone();
                }

                @Override
                public void printJobFailed(PrintJobEvent pje) {
                    allDone();
                }
                @Override
                public void printJobNoMoreEvents(PrintJobEvent pje) {
                    allDone();
                }
                void allDone() {
                    synchronized (PrintJobWatcher.this) {
                        done = true;
                        this.notify();
                    }
                }
            });
        }
        public synchronized void waitForDone() {
            try {
                while (!done) {
                    this.wait();
                }
            } catch (InterruptedException e) {
            }
        }
}
