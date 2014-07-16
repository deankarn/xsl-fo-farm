/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cwc.fop.util.conversion;


import cwc.fop.util.templates.FOPConvert;
import cwc.fop.util.utility.Results;
import javax.xml.transform.Source;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;

/**
 * @version 1.0
 * @author Dean Karn
 */
public class XML2POSTSCRIPT extends FOPConvert {

    /**
     * @see    Results
     * @param  xmlSource  XML file to be used in conversion.
     * @param  xsltSource XSLT file to be used in translation.
     * @return A Result Class containing the conversion results
     */
    public static Results translate( Source xmlSource, Source xsltSource ){

        return translate( getFopFactory(), xmlSource, xsltSource );
    }

    /**
     * @see    Results
     * @param  ff         The FopFactory used to create the FOUserAgent used to
     *                    convert.
     * @param  xmlSource  XML file to be used in conversion.
     * @param  xsltSource XSLT file to be used in translation.
     * @return A Result Class containing the conversion results
     */
    public static Results translate( FopFactory ff,
                                        Source xmlSource,
                                            Source xsltSource ){

        return translate( 600, ff, xmlSource, xsltSource);
    }

    /**
     * @see    Results
     * @param  targetResolution The Target Output Resolution.
     * @param  ff               The FopFactory used to create the FOUserAgent
     *                          used to convert.
     * @param  xmlSource        XML file to be used in conversion.
     * @param  xsltSource       XSLT file to be used in translation.
     * @return A Result Class containing the conversion results
     */
    public static Results translate( int targetResolution,
                                        FopFactory ff,
                                            Source xmlSource,
                                                Source xsltSource ){

        FOUserAgent fua = getFopUserAgent(ff);
        fua.setTargetResolution(targetResolution);

        return translate( fua, xmlSource, xsltSource);
    }

    /**
     * @see    Results
     * @param  fua        FOUserAgent specific settings for conversion
     * @param  xmlSource  XML file to be used in conversion.
     * @param  xsltSource XSLT file to be used in translation.
     * @return A Result Class containing the conversion results
     */
    public static Results translate( FOUserAgent fua,
                                        Source xmlSource,
                                            Source xsltSource ){
        return translate( MimeConstants.MIME_POSTSCRIPT, fua, xmlSource, xsltSource);
    }
}
