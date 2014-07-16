/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cwc.fop.server.interfaces;


import cwc.fop.server.work.WorkerRunnable;
import cwc.util.threading.TurboCoreWorkQueue;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Dean Karn
 */
public class SocketInterface {

    public SocketInterface( TurboCoreWorkQueue q, int port ){

        System.out.println("Openning Connection: " + port);
        
        try(ServerSocket ss = new ServerSocket(port))
        {
            while(true){
                System.out.println("Accepting Socket on PORT: " + port);
                Socket s = ss.accept();
                
                WorkerRunnable wr = new WorkerRunnable( s );
                q.execute(wr);
            }

        } catch( IOException ioe ){
            System.err.println(ioe.getMessage());
            System.exit(-1);
        }         
    }
}
