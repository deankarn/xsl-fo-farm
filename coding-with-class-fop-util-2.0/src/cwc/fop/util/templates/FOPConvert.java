/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cwc.fop.util.templates;

import cwc.fop.util.utility.Results;
import cwc.util.exceptions.EX;
import cwc.util.os.OS;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import org.apache.avalon.framework.configuration.Configuration;
import org.apache.avalon.framework.configuration.ConfigurationException;
import org.apache.avalon.framework.configuration.DefaultConfigurationBuilder;
import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.xml.sax.SAXException;

/**
 * @version 2.0
 * @author Dean Karn
 */
public abstract class FOPConvert {

    private static final FopFactory FOP_FACTORY = FopFactory.newInstance();
    private static boolean CONFIG_LOADED = false;
    
    /**
     * @see    FopFactory
     * @return A new FopFactory instance
     */
    public static FopFactory getFopFactory(){
        
        // Not 100% sure that the initial Load of the Configuration is
        // Thread Safe, afterwards the initial load FopFactory is though.
        if(!CONFIG_LOADED){
            DefaultConfigurationBuilder cfgBuilder = new DefaultConfigurationBuilder();
            try{
                Configuration cfg = cfgBuilder.buildFromFile(new File("./lib/fop.xconf"));
                FOP_FACTORY.setUserConfig(cfg);
            }
            catch(IOException | SAXException | ConfigurationException e)
            {
                System.out.println(e.getMessage());
            }
            
            CONFIG_LOADED = true;
        }
        return FOP_FACTORY;
        /*
        FopFactory ff = FopFactory.newInstance();
        DefaultConfigurationBuilder cfgBuilder = new DefaultConfigurationBuilder();
        
        try{
            Configuration cfg = cfgBuilder.buildFromFile(new File("./lib/fop.xconf"));
            ff.setUserConfig(cfg);
        }
        catch(IOException | SAXException | ConfigurationException e)
        {
            System.out.println(e.getMessage());
        }
        
        return ff;*/
    }

    /**
     * @see    FOUserAgent
     * @return A new FOUserAgent instance from a default
     *         FopFactory.
     */
    public static FOUserAgent getFopUserAgent(){
        return getFopUserAgent(getFopFactory());
    }

    /**
     * @see    FOUserAgent
     * @param  ff The FopFactory used to create the FOUserAgent from.
     * @return A new FOUserAgent instance from the provided
     *         FopFactory.
     */
    public static FOUserAgent getFopUserAgent( FopFactory ff ){
        return ff.newFOUserAgent();
    }

    /**
     * @see    Map HashMap
     * @return A new empty Map for use with FOUserAgent
     */
    public static Map<String, Object> getFOUserAgentMap(){
        return new HashMap<>();
    }

    /**
     * @see    Results
     * @param  MIMEType   MimeConstants to convert to.
     * @param  fua        FOUserAgent specific settings for conversion
     * @param  xmlSource  XML file to be used in conversion.
     * @param  xsltSource XSLT file to be used in translation.
     * @return A Result Class containing the conversion results
     */
    public static Results translate( String MIMEType,
                                        FOUserAgent fua,
                                            Source xmlSource,
                                                Source xsltSource ){

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try{
            // Construct fop with desired output format
            Fop fop = fua.getFactory().newFop(MIMEType, fua, out);
            //Map<String, Object> m = fua.getRendererOptions();
            
            //for (Map.Entry<String, Object> e : m.entrySet())
            //    System.out.println(e.getKey() + ": " + e.getValue());
            
            //fua.setRendererOverride(null)
            // Setup JAXP using identity transformer
            //TransformerFactory tf = TransformerFactory.newInstance();
            TransformerFactory tf = new net.sf.saxon.TransformerFactoryImpl();
            Transformer t = tf.newTransformer(xsltSource);
            t.setOutputProperty("encoding","UTF-8");

            // Resulting SAX events (the generated FO) must be piped through
            // to FOP
            javax.xml.transform.sax.SAXResult res =
                                        new SAXResult(fop.getDefaultHandler());

            // Start XSLT transformation and FOP processing
            t.transform(xmlSource, res);

        } catch( TransformerException | FOPException ex ){
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
