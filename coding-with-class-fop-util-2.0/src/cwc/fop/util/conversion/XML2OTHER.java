/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cwc.fop.util.conversion;

import cwc.fop.util.utility.Results;
import cwc.util.exceptions.EX;
import cwc.util.os.OS;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

/**
 *
 * @author Dean Karn
 */
public class XML2OTHER {
    
    /**
     * @see    Results
     * @param  xmlSource  XML file to be used in conversion.
     * @param  xsltSource XSLT file to be used in translation.
     * @return A Result Class containing the conversion results
     */
    public static Results translate( Source xmlSource, Source xsltSource ){
        
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        //StringWriter sw;
        
        try{
            // Setup JAXP using identity transformer
            TransformerFactory tf = new net.sf.saxon.TransformerFactoryImpl(); 
            //TransformerFactory tf = TransformerFactory.newInstance();
            Transformer t = tf.newTransformer(xsltSource);
            //t.setParameter("versionParam", "2.0");
            t.setOutputProperty("encoding","UTF-8");

            StreamResult res = new StreamResult();
            res.setOutputStream(out); 
            
            t.transform(xmlSource, res);
            
        } catch( TransformerException ex ){
            System.err.println(ex.getMessage());
            return new Results( false, ex.getMessage() + OS.getLineSeparator() + EX.getStackTrace(ex), null );
        } finally{
            try{
            out.close();
            }catch(IOException ex){}
        }

        return new Results( true, null, out );
    }
    
}
