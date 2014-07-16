/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cwc.fop.util.conversion;

import cwc.fop.util.templates.FOPConvert;
import cwc.fop.util.utility.Results;
import java.util.Map;
import javax.xml.transform.Source;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.pdf.PDFEncryptionParams;
import org.apache.xmlgraphics.util.MimeConstants;

/**
 *
 * @author Dean Karn
 */
public class XML2PDF extends FOPConvert {
    
    private static final String DEFAULT_PDF_PASSWORD = "myCrazyPasswd45%!@#4";
    
    /**
     * @see   PDFEncryptionParams
     * @return Creates a Default instance of PDFEncryptionParams to be applied 
     *         to the converted PDF, which has a Default User and Owner password
     *         , allows printing, allows copying of content, disallows Editing
     *         content and disallows editing annotations.
     */
    private static PDFEncryptionParams createDefaultEncryptionParams(){

        return createEncryptionParams(
                null, DEFAULT_PDF_PASSWORD,
                    true, true, false, false);
    }

    /**
     * @see   PDFEncryptionParams
     * @param userPassword         PDf User Password
     * @param ownerPassword        PDF Owner Password
     * @param allowPrint           PDF Allowing Printing of PDF
     * @param allowCopyContent     PDF Allow Copying of Content
     * @param allowEditContent     PDF Allow Editing of Content
     * @param allowEditAnnotations PDF Allow Editing of Annotations
     * @return PDFEncryptionParams to be applied to the converted PDF.
     */
    public static PDFEncryptionParams createEncryptionParams(
            String userPassword, String ownerPassword, Boolean allowPrint,
                Boolean allowCopyContent, Boolean allowEditContent,
                    Boolean allowEditAnnotations){
        
        return new PDFEncryptionParams( userPassword, ownerPassword,
                                            allowPrint, allowCopyContent,
                                                allowEditContent,
                                                    allowEditAnnotations);
    }

    /**
     * @see    Results
     * @param  xmlSource  XML file to be used in conversion.
     * @param  xsltSource XSLT file to be used in translation.
     * @return A Result Class containing the conversion results
     */
    public static Results translate( Source xmlSource, Source xsltSource ){

        return translate( getFopFactory(), xmlSource, xsltSource);
    }
    
    public static Results translate( Source xmlSource, Source xsltSource, 
                                       String password,
                                       Boolean allowPrint, 
                                       Boolean allowCopyContent,
                                       Boolean allowEditContent,
                                       Boolean allowEditAnnotations){

        return translate( xmlSource, xsltSource, 
                            createEncryptionParams(
                                null, password, allowPrint, allowCopyContent, 
                                    allowEditContent, allowEditAnnotations)
                            );
    }

    /**
     * @see    Results PDFEncryptionParams Source
     * @param  xmlSource    XML file to be used in conversion.
     * @param  xsltSource   XSLT file to be used in translation.
     * @param  ecryptParams PDFEncryptionParams applied to the converted PDF.
     * @return A Result Class containing the conversion results
     */
    public static Results translate( Source xmlSource,
                                        Source xsltSource,
                                            PDFEncryptionParams ecryptParams ){

        return translate( getFopFactory(), xmlSource, xsltSource,
                            ecryptParams);
    }

    /**
     * @see    Results FopFactory PDFEncryptionParams Source
     * @param  ff           The FopFactory used to create the FOUserAgent used to
     *                      convert.
     * @param  xmlSource    XML file to be used in conversion.
     * @param  xsltSource   XSLT file to be used in translation.
     * @return A Result Class containing the conversion results
     */
    @SuppressWarnings("unchecked")
    public static Results translate( FopFactory ff,
                                        Source xmlSource,
                                            Source xsltSource
                                                    ){

        FOUserAgent fua = getFopUserAgent(ff);

        return translate( fua, xmlSource, xsltSource);
    }
    
    /**
     * @see    Results FopFactory PDFEncryptionParams Source
     * @param  ff           The FopFactory used to create the FOUserAgent used to
     *                      convert.
     * @param  xmlSource    XML file to be used in conversion.
     * @param  xsltSource   XSLT file to be used in translation.
     * @param  ecryptParams PDFEncryptionParams applied to the converted PDF.
     * @return A Result Class containing the conversion results
     */
    @SuppressWarnings("unchecked")
    public static Results translate( FopFactory ff,
                                        Source xmlSource,
                                            Source xsltSource,
                                                PDFEncryptionParams ecryptParams
                                                    ){

        FOUserAgent fua = getFopUserAgent(ff);
        Map<String, Object> m = getFOUserAgentMap();
        m.put( "encryption-params", ecryptParams );
        fua.getRendererOptions().putAll( m );

        return translate( fua, xmlSource, xsltSource);
    }

    /**
     * @see    Results FOUserAgent Source
     * @param  fua        FOUserAgent specific settings for conversion
     * @param  xmlSource  XML file to be used in conversion.
     * @param  xsltSource XSLT file to be used in translation.
     * @return A Result Class containing the conversion results
     */
    public static Results translate( FOUserAgent fua,
                                        Source xmlSource,
                                            Source xsltSource ){
        return translate( MimeConstants.MIME_PDF, fua, xmlSource, xsltSource);
    }
}
