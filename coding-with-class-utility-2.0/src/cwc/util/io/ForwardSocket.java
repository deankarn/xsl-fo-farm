/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cwc.util.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author karnd
 */
public class ForwardSocket implements Runnable {

    private int BUFFER_SIZE;
    private InputStream in;
    private OutputStream out;
    private String name;
    
    public ForwardSocket(int BUFFER_SIZE, InputStream in, OutputStream out){
        this(BUFFER_SIZE, in, out, "");
    }
    
    public ForwardSocket(int BUFFER_SIZE, InputStream in, OutputStream out, String name){
        this.BUFFER_SIZE = BUFFER_SIZE;
        this.in = in;
        this.out = out;
        this.name = name;
    }
    
    @Override
    public void run() {
        byte b[] = new byte[this.BUFFER_SIZE];
        
        try{
            System.out.println(this.name + " Ready to read Data");
            
            for(int len; (len=this.in.read(b)) != -1;)
            {
                //System.out.println(this.name + ":Read Data, writing to Fop Processor " + len);
                this.out.write(b, 0, len);
                //System.out.println(this.name + ":Wrote " + len + " Bytes to Fop Processor");
            }
            
            //System.out.println(this.name + ":Flushing + Closing Stream");
            this.out.flush();
            this.out.close();
        }
        catch(IOException ioe){
            if(in != null){
                try{
                    in.close();
                }catch( IOException ex){}
            }
            
            if(this.out != null){
                try{
                    this.out.close();
                }catch( IOException ex){}
            }
        }
    }
}
