/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cwc.fop.loadbalancer.objects;

import cwc.fop.loadbalancer.threading.LoadBalancerTurboCoreWorkQueue;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author karnd
 */
public class ServerRegister implements Runnable {
    
    private final LoadBalancerTurboCoreWorkQueue WORK_QUEUE;
    private final int PORT;
            
    public ServerRegister(LoadBalancerTurboCoreWorkQueue q, int port){
        this.WORK_QUEUE = q;
        this.PORT = port;
    }
    
    @Override
    public void run(){
        System.out.println("Openning Server Registration Connection: " + this.PORT);
        
        try(ServerSocket ss = new ServerSocket(this.PORT))
        {
            while(true){
                System.out.println("Ready To Accept Server on Socket on PORT: " + this.PORT);
                
                Socket s = ss.accept();
                
                System.out.println("Accepting & Registering Server");
                
                try(BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                        BufferedOutputStream out = new BufferedOutputStream(s.getOutputStream());)
                {
                
                    //System.out.println("Reading Server Registration Data");
                    
                    String[] args = in.readLine().split(" ");
                
                    if(args == null || args.length != 6){
                        System.out.println("Failed to Registering Server, argument length is incorrect.");
                        continue;
                    }

                    String hostname = args[0];
                    int threads = Integer.parseInt(args[1]);
                    int maxThreads = Integer.parseInt(args[2]);
                    int memory = Integer.parseInt(args[3]);
                    int sPort = Integer.parseInt(args[4]);
                    int cpuSpeed = Integer.parseInt(args[5]);
                    FopServer.CPUSpeedClass cpu = FopServer.CPUSpeedClass.SlowerThanMolassesInJanuary;
                    if(cpuSpeed == 0){ //SlowerThanMolassesInJanuary
                        cpu = FopServer.CPUSpeedClass.SlowerThanMolassesInJanuary;
                    }
                    else if( cpuSpeed == 1) // Low
                    {
                        cpu = FopServer.CPUSpeedClass.Low;
                    }
                    else if( cpuSpeed == 2) // Medium
                    {
                        cpu = FopServer.CPUSpeedClass.Medium;
                    }
                    else if( cpuSpeed == 3) // High
                    {
                        cpu = FopServer.CPUSpeedClass.High;
                    }
                    else if( cpuSpeed >= 4) // FasterThanLightning
                    {
                        cpu = FopServer.CPUSpeedClass.FasterThanLightning;
                    }

                    //System.out.println(hostname + "|" + threads + "|" + maxThreads + "|" + memory + "|" + sPort + "|" + cpuSpeed);
                    
                    FopServer server = new FopServer(hostname, threads, maxThreads, memory, sPort, cpu);
                    
                    this.WORK_QUEUE.AddServer(server);
                    
                    //System.out.println("Added Server HOSTNAME:" + hostname + " PORT:" + sPort + " THREADS:" + threads + " Max THREADS:" + maxThreads + " Memory:" + memory + " CPU SPEED:" + cpuSpeed);
                    
                    out.write(1);
                    out.flush();
                    
                }catch(NumberFormatException nfe){
                    //out.write(0);
                    continue; // closes streams without success return;
                }
                catch(IOException ioe){
                    System.err.println(ioe.getMessage());
                    continue;
                }
            }
        } catch( IOException ioe ){
            System.err.println(ioe.getMessage());
            System.exit(-1);
        }
    }
}
