/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cwc.fop.loadbalancer.objects;

/**
 *
 * @author karnd
 */
public class FopServer {
    
    public static enum CPUSpeedClass{
        SlowerThanMolassesInJanuary,
        Low,
        Medium,
        High,
        FasterThanLightning
    }
    
    public static enum ResponseSpeedClass{
        ComeOutComeOutWhereverYouAre,
        Good,
        Better,
        Great,
        ThatWasALittleTooFast
    }
    
    private String HOSTNAME = "";
    
    private int NumberExecuting = 0;

    public int getNumberExecuting() {
        return NumberExecuting;
    }

    public void setNumberExecuting(int NumberExecuting) {
        this.NumberExecuting = NumberExecuting;
    }
    
    private int THREADS = 0; // Threads the FopServer's core pool can handle

    public String getHOSTNAME() {
        return HOSTNAME;
    }

    public int getTHREADS() {
        return THREADS;
    }

    public int getMAX_THREADS() {
        return MAX_THREADS;
    }

    public int getMEMORY() {
        return MEMORY;
    }

    public int getPORT() {
        return PORT;
    }

    public CPUSpeedClass getCPU() {
        return CPU;
    }

    public ResponseSpeedClass getRESPONSE() {
        return RESPONSE;
    }
    private int MAX_THREADS = 0; // Maximum # of threads the server can handle before queueing occures

    public void setHOSTNAME(String HOSTNAME) {
        this.HOSTNAME = HOSTNAME;
    }

    public void setTHREADS(int THREADS) {
        this.THREADS = THREADS;
    }

    public void setMAX_THREADS(int MAX_THREADS) {
        this.MAX_THREADS = MAX_THREADS;
    }

    public void setMEMORY(int MEMORY) {
        this.MEMORY = MEMORY;
    }

    public void setPORT(int PORT) {
        this.PORT = PORT;
    }

    public void setCPU(CPUSpeedClass CPU) {
        this.CPU = CPU;
    }

    public void setRESPONSE(ResponseSpeedClass RESPONSE) {
        this.RESPONSE = RESPONSE;
    }
    private int MEMORY = 0; // Memory, in Megabytes that the FopServer is configured to run with.
    private int PORT = 0;
    
    private CPUSpeedClass CPU = CPUSpeedClass.SlowerThanMolassesInJanuary;
    
    private ResponseSpeedClass RESPONSE = ResponseSpeedClass.ComeOutComeOutWhereverYouAre;
    
    public FopServer(String hostname, int threads, int maxThreads, int memory, int port, CPUSpeedClass cpu){
        this.HOSTNAME = hostname;
        this.THREADS = threads;
        this.MAX_THREADS = maxThreads;
        this.MEMORY = memory;
        this.PORT = port;
        this.CPU = cpu;
    }
}
