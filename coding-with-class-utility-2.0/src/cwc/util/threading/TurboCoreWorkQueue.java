/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cwc.util.threading;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author karnd01
 */
public class TurboCoreWorkQueue {

    private final LinkedBlockingQueue<Runnable> queue;
    private final int corePoolSize;
    private final int maxPoolSizeDiff;
    private final PoolWorker[] coreThreads;
    private final LinkedBlockingQueue<PoolWorkerTimeout> additionalThreads;
    private long timeUntilTimeout;
    private TimeUnit timeoutTimeUnit;
    private boolean isAdditionalThreads = false;

    public TurboCoreWorkQueue(int corePoolSize) {
        this(corePoolSize, corePoolSize, 0, TimeUnit.MILLISECONDS);
    }
    
    public TurboCoreWorkQueue(int corePoolSize, int maxPoolSize, long timeUntilTimeout, TimeUnit timeoutTimeUnit) {

        this.corePoolSize = Math.max(1, corePoolSize);
        this.maxPoolSizeDiff = Math.max(this.corePoolSize, maxPoolSize) - this.corePoolSize;
        this.timeUntilTimeout = timeUntilTimeout;
        this.timeoutTimeUnit = timeoutTimeUnit;
        this.queue = new LinkedBlockingQueue<>();
        this.coreThreads = new PoolWorker[this.corePoolSize];
        
        if(this.maxPoolSizeDiff == 0){
            this.additionalThreads = null;
            this.isAdditionalThreads = false;
        }
        else{
            this.additionalThreads = new LinkedBlockingQueue<>(this.maxPoolSizeDiff);
            this.isAdditionalThreads = true;
        }
        this.startThreads();
    }
    
    public final void startThreads(){
        for (int i = 0; i < this.corePoolSize; i++) {
            this.coreThreads[i] = new PoolWorker();
            this.coreThreads[i].start();
        }
    }

    public void execute(Runnable r) {
        try{
            
            // ***NOTE: If ever make this into a fixed queue, this should go below the check for 
            //          additional threads, because .put can block; moreover will block 
            queue.put(r);
            
            // if we're overextended... lets try to spawn
            // some additional Threads..but only to the specified amount           
            // If there are jobs in the queue, then all threads ar busy and have
            // yet to take more from the queue
            if(this.isAdditionalThreads && this.additionalThreads.remainingCapacity() > 0 && queue.size() > 0){
                
                System.out.println("Spawning Additional Thread");
                PoolWorkerTimeout pw = new PoolWorkerTimeout(this.timeUntilTimeout, this.timeoutTimeUnit);
                
                if(this.additionalThreads.add(pw)){
                    pw.start();
                }
                else{
                    // shouldn't get here but just in case thread is not added 
                    // to the queue, be sure to clean it up.
                    pw.interrupt();
                }
            }
        }catch(InterruptedException | NullPointerException | IllegalStateException ignored){
            // Ignored, the runnable class itself should return 
            // success or in this case failure.
            
            // IllegalStateException can be thrown by the this.additionalThreads.add if
            // exceeding the pool size, even though a check was added to prevent the error from
            // ever occurring.
        }
    }

    private class PoolWorker extends Thread {
        
        @Override
        public void run() {
            Runnable r;

            while (true) {
                
                try {
                    r = (Runnable)queue.take();
                    System.out.println("Running Job in Core Thread");
                    r.run();
                } catch (InterruptedException ignored) {
                }
                // If we don't catch RuntimeException,
                // the pool could leak threads
                catch (RuntimeException ex) {
                    // You might want to log something here
                    System.out.println("RuntimeException: " + ex.getMessage());
                }
            }
        }
    }
    
    private class PoolWorkerTimeout extends PoolWorker {

        private final long timeUntilTimeout;
        private final TimeUnit timeUnit;
        
        public PoolWorkerTimeout(long secondsUntilTimeout, TimeUnit timeUnit){
            super();
            this.timeUntilTimeout = secondsUntilTimeout;
            this.timeUnit = timeUnit;
        }
        
        @Override
        public void run() {
            Runnable r;

            try {
                while ((r = queue.poll(this.timeUntilTimeout, this.timeUnit)) != null) {
                        System.out.println("Running Job in Additional Thread");
                        r.run();
                }
                
                System.out.println("Killing Additional Thread");
                additionalThreads.remove(this);
            }
            catch (InterruptedException ignored) {
            }
            // If we don't catch RuntimeException,
            // the pool could leak threads
            catch (RuntimeException ex) {
                // You might want to log something here
                System.out.println("RuntimeException: " + ex.getMessage());
            }
        }
    }
}
