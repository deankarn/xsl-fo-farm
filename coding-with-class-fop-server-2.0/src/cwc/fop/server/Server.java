/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cwc.fop.server;

import cwc.fop.server.interfaces.RegisterServer;
import cwc.fop.server.interfaces.SocketInterface;
import cwc.util.threading.TurboCoreWorkQueue;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Dean Karn
 */
public class Server {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        boolean LOAD_BALANCED = false;
        
        String loadBalancerHostname = "";
        
        int coreThreads = 0,
            maxThreads = 0,
            port = 0,
            loadBalancerPort = 0;
        
        long additionalThreadtimeOut = 0,
             registerInterval = 30*60*1000;
        
        for(int i=0;i<args.length;){
            switch(args[i]){
                case "-ct":
                case "-core-threads":
                    i++;
                    try{
                        coreThreads = Integer.parseInt(args[i]);
                        
                        if(maxThreads == 0){
                            maxThreads = coreThreads;
                        }
                    } catch( NumberFormatException ex ){
                        // NumberFormatException
                        // ArrayIdexOutOfBoundsException
                        System.err.println(
                                "Invalid Argument, Core Threads must be an integer");
                        System.exit(-1);
                    }
                    break;
                case "-mt":
                case "-max-threads":
                    i++;
                    try{
                        maxThreads = Integer.parseInt(args[i]);
                    } catch( NumberFormatException ex ){
                        // NumberFormatException
                        // ArrayIdexOutOfBoundsException
                        System.err.println(
                                "Invalid Argument, Max Threads must be an integer");
                        System.exit(-1);
                    }
                    break;
                case "-p":
                case "-port":
                    i++;
                    try{
                        port = Integer.parseInt(args[i]);
                    } catch( NumberFormatException ex ){
                        // NumberFormatException
                        // ArrayIdexOutOfBoundsException
                        System.err.println(
                                "Invalid Argumanet, Port must be an integer");
                        System.exit(-1);
                    }
                    break;
                case "-lbp":
                case "-load-balancer-port":
                    LOAD_BALANCED = true;
                    i++;
                    try{
                        loadBalancerPort = Integer.parseInt(args[i]);
                    } catch( NumberFormatException ex ){
                        // NumberFormatException
                        // ArrayIdexOutOfBoundsException
                        System.err.println(
                                "Invalid Load Balancer Port, must be an integer");
                        System.exit(-1);
                    }
                    break;
                case "-lbh":
                case "-load-balancer-hostname":
                    LOAD_BALANCED = true;
                    i++;
                    loadBalancerHostname = args[i];
                    break;
                case "-lbri":
                case "-load-balancer-register-interval":
                    LOAD_BALANCED = true;
                    i++;
                    try{
                        registerInterval = Long.parseLong(args[i]);
                    } catch( NumberFormatException ex ){
                        // NumberFormatException
                        // ArrayIdexOutOfBoundsException
                        System.err.println(
                                "Invalid Server Registration Timeout, 7th argument must be an long/integer");
                        System.exit(-1);
                    }
                    break;
                case "-att":
                case "-additional-thread-timeout":
                    i++;
                    try{
                        additionalThreadtimeOut = Long.parseLong(args[i]);
                    } catch( NumberFormatException ex ){
                        // NumberFormatException
                        // ArrayIdexOutOfBoundsException
                        System.err.println(
                                "Invalid Additional Thread Timeout, 4th argument must be an long/integer");
                        System.exit(-1);
                    }
                    break;
            }
            i++;
        }
        
        if( coreThreads <= 0 ){
            System.err.println("You must run at least 1 thread:");
            System.err.println("-ct <threads>");
            System.err.println("-core-threads <threads>");
            System.exit(-1);
        }

        if( maxThreads < coreThreads ){
            System.err.println("Maximum Threads must be at least greater than or equal to the Core Thread count.");
            System.err.println("-mt <threads>");
            System.err.println("-max-threads <threads>");
            System.exit(-1);
        }
        
        if(maxThreads > coreThreads && additionalThreadtimeOut <= 0){
            System.err.println("Additional Thread Timeout must be set when greater number of max threads specified.");
            System.err.println("-att <timeout>");
            System.err.println("-additional-thread-timeout <timeout>");
            System.exit(-1);
        }
        
        if( port <= 0 ){
            System.err.println("Invalid Port specified.");
            System.err.println("-p <port>");
            System.err.println("-port <port>");
            System.exit(-1);
        }

        if(LOAD_BALANCED){

            if(loadBalancerHostname.length() == 0)
            {
                System.err.println(
                        "Invalid Load Balancer Hostname");
                System.err.println("-lbh <hostname or ip>");
                System.err.println("-load-balancer-hostname <hostname or ip>");
                System.exit(-1);
            }

            if(loadBalancerPort <= 0){
                System.err.println("Invalid Load Balancer Port");
                System.err.println("-lbp <port>");
                System.err.println("-load-balancer-port <port>");
                System.exit(-1);
            }
            
            if(registerInterval <= 0){
                System.err.println("Invalid Load Balancer Register Interval");
                System.err.println("-lbri <interval in milliseconds>");
                System.err.println("-load-balancer-register-interval <interval in milliseconds>");
                System.exit(-1);
            }
            
            // Register Server        
            RegisterServer rs = new RegisterServer(loadBalancerHostname, loadBalancerPort, coreThreads, maxThreads, port);

            new Timer().scheduleAtFixedRate(rs, 3000, registerInterval);
        }
        // Start Up Server, if registration is successfull
        System.out.println("Running with a core thread pool of:" + coreThreads);
        System.out.println("Running with a max thread pool of:" + maxThreads);
        
        TurboCoreWorkQueue q = new TurboCoreWorkQueue(coreThreads, maxThreads, additionalThreadtimeOut, TimeUnit.MILLISECONDS);
        
        SocketInterface si = new SocketInterface(q, port);
    }
}
