/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cwc.fop.server.interfaces;

import cwc.util.microbenchmarks.Pi;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.TimerTask;

/**
 *
 * @author Dean Karn
 */
public final class RegisterServer extends TimerTask {
    
    private String HOSTNAME= "";
    
    private int LOAD_BALANCER_PORT = 0,
                CORE_THREADS = 0,
                MAX_THREADS = 0,
                PORT = 0,
                CPUClass = -1;
    
    public RegisterServer(String hostname, int loadBalancerPort, int coreThreads, int maxThreads, int port){
        this.HOSTNAME = hostname;
        this.LOAD_BALANCER_PORT = loadBalancerPort;
        this.CORE_THREADS = coreThreads;
        this.MAX_THREADS = maxThreads;
        this.PORT = port;
    }
    
    public int RegisterServer(){
        
        //System.out.println("Registering Server...");
        if(this.CPUClass == -1){
            
            System.out.println("Running Short CPU Test to Determine CPU Speed");
            long start = System.currentTimeMillis();
            //BigDecimal pi = Pi.pi(50000);
            Pi.pi(50000);
            long end = System.currentTimeMillis();
            long diff = (end - start) / 1000;
            
            if(diff > 25){
                this.CPUClass = 0; // SlowerThanMollassesInJanuary
            }
            else if(diff > 15){
                this.CPUClass = 1; // Low
            }
            else if(diff > 10){
                this.CPUClass = 2; // Medium
            }
            else if(diff > 5){
                this.CPUClass = 3; // High
            }
            else if(diff <= 5){
                this.CPUClass = 4; // FasterThanLightening
            }
        }
        
        System.out.println("Connecting To:" + this.HOSTNAME + " PORT:" + this.LOAD_BALANCER_PORT);
        
        int serverRegistered = 0;
        try(Socket s = new Socket(this.HOSTNAME, this.LOAD_BALANCER_PORT);
                BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                    PrintWriter out = new PrintWriter(s.getOutputStream());){
                    
            out.println(InetAddress.getLocalHost().getHostName() + " " + this.CORE_THREADS + " " + this.MAX_THREADS + " " + Runtime.getRuntime().maxMemory() + " " + this.PORT + " " + this.CPUClass);
            out.flush();
            
            serverRegistered = in.read();
        } catch( IOException ioe){
            
            System.out.println("Error:" + ioe.getMessage());
            // Ignore and keep trying to re-register
            //ioe.printStackTrace();
        }
        
        System.out.println("Server Registerd: " + serverRegistered);
        
        return serverRegistered;
    }
    
    @Override
    public void run(){
        System.out.println("Registering/Re-Registering Server...");
        this.RegisterServer();
    }
}
