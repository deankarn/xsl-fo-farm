/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cwc.fop.loadbalancer.threading;

import cwc.fop.loadbalancer.objects.FopServer;
import cwc.util.threading.TurboCoreWorkQueue;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author karnd
 */
public class LoadBalancerTurboCoreWorkQueue extends TurboCoreWorkQueue {
    
    private final LinkedList<FopServer> servers;
    
    public FopServer AddServer(FopServer s){
        synchronized (this.servers) {
            for( FopServer rs : this.servers)
            {
                if(rs.getHOSTNAME().equalsIgnoreCase(s.getHOSTNAME()) && rs.getPORT() == s.getPORT())
                {
                    //rs = s; // reset elements later to make more fault tolerant.
                    rs.setCPU(s.getCPU());
                    rs.setTHREADS(s.getTHREADS());
                    rs.setMAX_THREADS(s.getMAX_THREADS());
                    rs.setMEMORY(s.getMEMORY());
                    rs.setPORT(s.getPORT());
                    rs.setRESPONSE(s.getRESPONSE());
                    
                    System.out.println("Updating Server HOSTNAME:" + s.getHOSTNAME() + " PORT:" + s.getPORT() + " THREADS:" + s.getTHREADS() + " Max THREADS:" + s.getMAX_THREADS() + " Memory:" + s.getMEMORY() + " CPU SPEED:" + s.getCPU());
                    
                    return rs;
                }
            }
            
            this.servers.add(s);
            System.out.println("Added Server HOSTNAME:" + s.getHOSTNAME() + " PORT:" + s.getPORT() + " THREADS:" + s.getTHREADS() + " Max THREADS:" + s.getMAX_THREADS() + " Memory:" + s.getMEMORY() + " CPU SPEED:" + s.getCPU());
            return s;
        }
    }
    
    public synchronized FopServer GetServer(){
        synchronized (this.servers) {
            
            //System.out.println("Checking Servers Available for processing");
            
            FopServer fs;
            int exec;
            List<FopServer> available = new ArrayList<>();
            List<FopServer> core = new ArrayList<>();
            List<FopServer> max = new ArrayList<>();
            // Algorithm starting ppint to determine best fop server to use 
            // for processing.
            for( FopServer s : this.servers)
            {
                exec = s.getNumberExecuting();
                
                if(exec == 0){
                    available.add(s);
                }
                else if(exec < s.getTHREADS()){
                    core.add(s);
                }
                else if(exec < s.getMAX_THREADS()){
                    max.add(s);
                }
            }
            
            //System.out.println("# Available:" + available.size());
            //System.out.println("# Available:" + core.size());
            //System.out.println("# Available:" + max.size());
            
            if(available.size() > 0)
            {
                fs = this.DetermineBestServer(available);
            }
            else if(core.size() > 0)
            {
                fs = this.DetermineBestServer(core);
            }
            else if(max.size() > 0)
            {
                fs = this.DetermineBestServer(max);
            }
            else{
                fs = this.DetermineBestServer(this.servers);
            }
            
            return fs;
        }
    }
    
    private FopServer DetermineBestServer(List<FopServer> avail){
        
        // Algorithm could use some tweaking as it is not just the CPU or MEMORY
        // exclusively, but it's more than good enough for now.
        
        if(avail.isEmpty()){
            return null;
        }
        
        FopServer best = avail.get(0);
        
        for(FopServer s : avail)
        {   
            if(s.getCPU().ordinal() > best.getCPU().ordinal())
            {
                best = s;
            }
            else if(s.getCPU().ordinal() == best.getCPU().ordinal())
            {
                if(s.getMEMORY() > best.getMEMORY())
                {
                    best = s;
                }
                else if(s.getMEMORY() == best.getMEMORY()){
                    if(s.getRESPONSE().ordinal() > best.getRESPONSE().ordinal())
                    {
                        best = s;
                    }
                }
            }
        }
        
        return best;
    }
    
    public void RemoveServer(FopServer s){
        synchronized (this.servers) {
            
            //System.out.println("Removing Disconnected Server.");
            if(this.servers.contains(s)){
                this.servers.remove(s);
            }
            //Write some code to notify someone that a server is down, email?
        }
    }
    
    public LoadBalancerTurboCoreWorkQueue(int corePoolSize) {
        this(corePoolSize, corePoolSize, 0, TimeUnit.MILLISECONDS);
    }
    
    public LoadBalancerTurboCoreWorkQueue(int corePoolSize, int maxPoolSize, long timeUntilTimeout, TimeUnit timeoutTimeUnit) {
        super(corePoolSize, maxPoolSize, timeUntilTimeout, timeoutTimeUnit);
        
        this.servers = new LinkedList<>();
    }
}
