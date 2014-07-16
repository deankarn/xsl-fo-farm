/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cwc.fop.loadbalancer.interfaces;

import cwc.fop.loadbalancer.objects.BalancedExecutor;
import cwc.fop.loadbalancer.objects.ServerRegister;
import cwc.fop.loadbalancer.threading.LoadBalancerTurboCoreWorkQueue;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author karnd
 */
public class SocketInterface {
    
    private final Thread register;
    
    public SocketInterface( LoadBalancerTurboCoreWorkQueue q, int port, int serverRegistrationPort ){

        register = new Thread(new ServerRegister(q, serverRegistrationPort));
        this.StartThreads();
        
        System.out.println("Openning Connection: " + port);
        
        try(ServerSocket ss = new ServerSocket(port))
        {
            while(true){
                System.out.println("Accepting Socket on PORT: " + port);
                Socket s = ss.accept();
                
                BalancedExecutor be = new BalancedExecutor(q, s);
                
                q.execute(be);
            }
        } catch( IOException ioe ){
            System.err.println(ioe.getMessage());
            System.exit(-1);
        }
    }
    
    private void StartThreads(){
        this.register.start();
    }
}
