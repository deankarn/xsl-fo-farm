/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cwc.fop.loadbalancer.objects;

import cwc.fop.loadbalancer.threading.LoadBalancerTurboCoreWorkQueue;
import cwc.util.io.ForwardSocket;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 *
 * @author karnd
 */
public class BalancedExecutor implements Runnable {
        
    private final Socket s;
    private final LoadBalancerTurboCoreWorkQueue queue;

    public BalancedExecutor(LoadBalancerTurboCoreWorkQueue queue, Socket s){
        this.queue = queue;
        this.s = s;
    }

    @Override
    public void run() {

        FopServer server = null;
        Socket ss = null;
        
        System.out.println("Running Balanced Executor..");
        
        while(ss == null || !ss.isConnected()){
            try{
                System.out.println("Balanced Executor Getting Server");
                server = this.queue.GetServer();
                
                if(server == null)
                    break;
                
                System.out.println("Balanced Executor Connecting To Server:" + server.getHOSTNAME() + " Port:" + server.getPORT());
                SocketAddress addy = new InetSocketAddress(server.getHOSTNAME(), server.getPORT());
                //ss = new Socket(server.getHOSTNAME(), server.getPORT());
                ss = new Socket();
                ss.connect(addy,2000);
                
            } catch(IOException ex ){
                System.out.println("Connecting to server failed...Removing Server");
                this.queue.RemoveServer(server); 
                // Cannot connect to server, remove from pool of servers
            }
        }
        
        if(server == null){
            System.out.println("Balanced Executor No Servers Available Returning");
            try{
                s.close();
            }
            catch(IOException ioe){ 
                //Ignore
            }
            return;
        }
        
        System.out.println("Balanced Executor Established Connection");
        
        server.setNumberExecuting(server.getNumberExecuting() + 1);
        
        try{
            System.out.println("Forwarding Data to Server...");
            
            ForwardSocket inOut = new ForwardSocket(8192, s.getInputStream(), ss.getOutputStream(), "Input - ");
            ForwardSocket outIn = new ForwardSocket(8192, ss.getInputStream(), s.getOutputStream(), "Output - ");

            Thread o = new Thread(outIn);
            Thread i = new Thread(inOut);
            o.start();
            i.start();
            
            System.out.println("Passing Data to Fop Processor");
            o.join();
            System.out.println("Done Forwarding Data to Server...");
            System.out.println("Reading Data from Server...");
            i.join();
            System.out.println("Done Reading Data from Server...");
            
            System.out.println("Data Forwarded and processed...");
            
            server.setNumberExecuting(server.getNumberExecuting() - 1);
            
            this.s.close();
            
        } catch(IOException | InterruptedException ex ){
            // Ignore
        }
    }
}