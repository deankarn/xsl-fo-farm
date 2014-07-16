/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cwc.fop.server.work;


import cwc.fop.util.conversion.XML2OTHER;
import cwc.fop.util.conversion.XML2PCL;
import cwc.fop.util.conversion.XML2PDF;
import cwc.fop.util.conversion.XML2PNG;
import cwc.fop.util.conversion.XML2POSTSCRIPT;
import cwc.fop.util.utility.Results;
import cwc.util.conversion.Base64Coder;
import cwc.util.threading.MonitorableRunnable;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.Socket;
import java.net.SocketException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

/**
 *
 * @author karnd01
 */
public class WorkerRunnable extends MonitorableRunnable {

    public static enum RunType{
        PDF,
        PNG,
        PCL,
        POSTSCRIPT,
        HTML,
        EXCEL,
        CSV,
        TEXT,
        XML
    }

    public static RunType getRunType( String type ){

        String t = type.toLowerCase();
        
        switch(t){
            case "pdf":
                return RunType.PDF;
            case "png":
                return RunType.PNG;
            case "pcl":
                return RunType.PCL;
            case "postscript":
                return RunType.POSTSCRIPT;
            case "html":
                return RunType.HTML;
            case "excel":
                return RunType.EXCEL;
            case "csv":
                return RunType.CSV;
            case "text":
                return RunType.TEXT;
            case "xml":
                return RunType.XML;
            default:
                return RunType.PDF;
        }
    }

    private Socket s;

    public WorkerRunnable( Socket s ){
        this.s = s;
    }

    @Override
    public void run(){

        try{
            s.setSoTimeout(120*1000);
        } catch( SocketException se ){
            System.err.println(se.getMessage());
        }
        
        BufferedOutputStream out = null;
        
        try(BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream())))
        {
            out = new BufferedOutputStream(s.getOutputStream());
            
            //System.out.println("reading base64Args");
            String base64Args = in.readLine();
            //System.out.println("reading base64Xml");
            String base64Xml = in.readLine();
            //System.out.println("reading base64Xslt");
            String base64Xslt = in.readLine();
            
            //System.out.println("Decoding base64Args");
            String convertArgs = Base64Coder.decodeString(base64Args);
            String[] args = convertArgs.split(" ");
            String convertTo = args[0];
            
            //System.out.println("Decoding base64Xml");
            String xml = Base64Coder.decodeString(base64Xml);
            //System.out.println("Decoding base64Xslt");
            String xslt = Base64Coder.decodeString(base64Xslt);
            
            //System.out.println("Determining Runtype");
            RunType runType = WorkerRunnable.getRunType(convertTo);
            
            Source srcXml = new StreamSource(new StringReader(xml));
            Source srcXslt = new StreamSource(new StringReader(xslt));

            Results r = null;
            
            switch(runType){
                case PDF:
                    Boolean securePDF = false;
                    Boolean allowPrinting = true;
                    Boolean allowCopyingContent = true;
                    Boolean allowEditingContent = false;
                    Boolean allowEditingAnnotations = false;
                    String password = "";

                    if(args.length > 1)
                    {
                        securePDF = Boolean.valueOf(args[1]);
                        allowPrinting = Boolean.valueOf(args[2]);
                        allowCopyingContent = Boolean.valueOf(args[3]);
                        allowEditingContent = Boolean.valueOf(args[4]);
                        allowEditingAnnotations = Boolean.valueOf(args[5]);
                    }
                    
                    if(securePDF)
                    {
                        password = args[6];
                        if(password.isEmpty())
                        {
                            password = "myPassword";
                        }
                    }

                    if(securePDF){
                        r = XML2PDF.translate(srcXml, srcXslt,
                                                password, 
                                                allowPrinting, 
                                                allowCopyingContent, 
                                                allowEditingContent, 
                                                allowEditingAnnotations);
                    }
                    else{
                        r = XML2PDF.translate(srcXml, srcXslt);
                    }
                    break;
                case PCL:
                    r = XML2PCL.translate(srcXml, srcXslt);
                    break;
                case PNG:
                    r = XML2PNG.translate(srcXml, srcXslt);
                    break;
                case POSTSCRIPT:
                    r = XML2POSTSCRIPT.translate(srcXml, srcXslt);
                    break;
                case HTML:
                    r = XML2OTHER.translate(srcXml, srcXslt);
                    break;
                case EXCEL:
                    r = XML2OTHER.translate(srcXml, srcXslt);
                    break;
                case CSV:
                    r = XML2OTHER.translate(srcXml, srcXslt);
                    break;
                case TEXT:
                    r = XML2OTHER.translate(srcXml, srcXslt);
                    break;
                case XML:
                    r = XML2OTHER.translate(srcXml, srcXslt);
                    break;
            }
            
            if( r != null && r.getSuccess() ){
                out.write(1);
            }
            else{
                out.write(0);
            }
            
            out.flush();

            if(r == null){
                out.write("Translation Error".getBytes("UTF-8"));
            }
            else if( !r.getSuccess()){
                out.write(r.getError().getBytes("UTF-8"));
            }
            else{
                r.getOutStream().writeTo(out);
                r.getOutStream().flush();
                r.getOutStream().close();
            }
            
            out.flush();
        }
        catch(RuntimeException | IOException ex)
        {
            this.ReturnError(out, ex.getMessage());
        }
        finally
        {
            if(out != null)
            {
                try{
                    out.close();
                } catch( IOException ioe ){}
            }
            try{
                s.close();
            } catch( IOException ioe ){}
        }
    }
    
    private void ReturnError(BufferedOutputStream out, String message)
    {
        System.err.println(message);
        
        try{
            out.write(message.getBytes("UTF-8"));
            out.flush();
        }
        catch(IOException ex){}
    }
}
