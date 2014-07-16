/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cwc.fop.loadbalancer;

import cwc.fop.loadbalancer.interfaces.SocketInterface;
import cwc.fop.loadbalancer.threading.LoadBalancerTurboCoreWorkQueue;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author karnd
 */
public class CodingWithClassFopLoadBalancer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        int port = 0;
        int serverRegistrationPort = 0;
        
        for(int i=0;i<args.length;){
            switch(args[i]){
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
                case "-srp":
                case "-server-registration-port":
                    i++;
                    try{
                        serverRegistrationPort = Integer.parseInt(args[i]);
                    } catch( NumberFormatException ex ){
                        // NumberFormatException
                        // ArrayIdexOutOfBoundsException
                        System.err.println(
                                "Invalid Server Registration Port, must be an integer");
                        System.exit(-1);
                    }
                    break;
            }
            i++;
        }
        
        if(port <= 0){
            System.err.println("Invalid Port");
            System.err.println("-p <port>");
            System.err.println("-port <port>");
            System.exit(-1);
        }
        
        if(serverRegistrationPort <= 0){
            System.err.println("Invalid Server Registration Port");
            System.err.println("-srp <port>");
            System.err.println("-server-registration-port <port>");
            System.exit(-1);
        }
        
        LoadBalancerTurboCoreWorkQueue q = new LoadBalancerTurboCoreWorkQueue(Runtime.getRuntime().availableProcessors(), Integer.MAX_VALUE, 15*1000, TimeUnit.MILLISECONDS);
        
        SocketInterface si = new SocketInterface(q, port, serverRegistrationPort);
    }
}
