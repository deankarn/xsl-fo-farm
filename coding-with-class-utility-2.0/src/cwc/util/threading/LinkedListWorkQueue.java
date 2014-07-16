/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cwc.util.threading;

import java.util.LinkedList;

/**
 *
 * @author karnd01
 */
public class LinkedListWorkQueue {

    private final int nThreads;
    private final PoolWorker[] threads;
    private final LinkedList<Runnable> queue;

    public LinkedListWorkQueue(int nThreads) {

        this.nThreads = Math.max(1, nThreads);
        queue = new LinkedList<>();
        threads = new PoolWorker[nThreads];
        
        this.startThreads();
    }
    
    public final void startThreads(){
        for (int i = 0; i < this.nThreads; i++) {
            this.threads[i] = new PoolWorker();
            this.threads[i].start();
        }
    }

    public void execute(Runnable r) {
        synchronized (queue) {
            queue.addLast(r);
            queue.notify();
        }
    }

    private class PoolWorker extends Thread {

        @Override
        public void run() {
            Runnable r;

            while (true) {
                synchronized (queue) {
                    while (queue.isEmpty()) {
                        try {
                            queue.wait();
                        } catch (InterruptedException ignored) {
                        }
                    }

                    r = (Runnable)queue.removeFirst();
                }

                // If we don't catch RuntimeException,
                // the pool could leak threads
                try {
                    r.run();
                } catch (RuntimeException ex) {
                    // You might want to log something here
                    System.out.println("RuntimeException: " + ex.getMessage());
                }
            }
        }
    }
}
