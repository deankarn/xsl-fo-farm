/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cwc.util.printing;

import java.awt.Component;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Locale;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.ServiceUI;
import javax.print.SimpleDoc;
import javax.print.attribute.Attribute;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.JobName;
import javax.swing.JOptionPane;

/**
 *
 * @author karnd01
 */
public class Print {

    private PrintRequestAttributeSet _aset;
    private DocFlavor _flavor;

    private Component _parent;

    public Print(){

        this._parent = null;
        this._aset = new HashPrintRequestAttributeSet();
        this._flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
    }

    public Print( Component parent ){

        this._parent = parent;
        this._aset = new HashPrintRequestAttributeSet();
        this._flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
    }

    public boolean printFile( File f ){

        if( !f.exists()){
            JOptionPane.showMessageDialog(this._parent,
                    "The File Specified Does not exist");
            return false;
        }

//        PrintService default = PrinterServiceLookup.lookupDefaultPrintService();
//PrintRequestAttributeSet attr = new HashPrintRequestAttributes();
//attr.add(new Copies(2));
//attr.add(MediaSizeName.ISO_A4);
//DocFlavor flavor = DocFlavor.INPUT_STREAM.POSTSCRIPT;
//PrintService [] all = PrinterServiceLookup.lookupPrintServices(flavor, attr);
//PrintService printer = ServiceUI.printDialog(null, 200, 200, all, default, flavor, attr);
        
//        PrinterJob printJob = PrinterJob.getPrinterJob();
//        if(printJob.printDialog(this._aset)){
//
//            FileInputStream textStream = null;
//
//            try{
//                textStream = new FileInputStream(f);
//                Doc mydoc = new SimpleDoc(textStream, this._flavor, null);
//
//                PrintService ps = printJob.getPrintService();
//                DocPrintJob job = ps.createPrintJob();
//
//                job.print(mydoc, this._aset);
////                PrintJobWatcher pjDone = new PrintJobWatcher(job);
////
////                pjDone.waitForDone();
//
//            } catch( FileNotFoundException fnfe ){
//                JOptionPane.showMessageDialog(this._parent, fnfe.getMessage());
//                return false;
//            } catch( PrintException pe ){
//                JOptionPane.showMessageDialog(this._parent, pe.getMessage());
//                return false;
//            } finally{
//                if( textStream != null)
//                    try{
//                        textStream.close();
//                    } catch( IOException ioe ){}
//            }
//        }
//        else
//            return false;

        PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
//        aset.add(OrientationRequested.PORTRAIT);
//        aset.add(new JobName("Order Confirmation", null));

//        Doc pd = new PrintDoc("");
        DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
//        DocFlavor flavor = DocFlavor.INPUT_STREAM.POSTSCRIPT;
//        this._aset.add(MediaSizeName);

//        PrintService[] services =
//                PrintServiceLookup.lookupPrintServices(flavor, this._aset);
        PrintService[] services =
                PrintServiceLookup.lookupPrintServices(flavor, null);
        PrintService defaultService =
                PrintServiceLookup.lookupDefaultPrintService();

//        DocFlavor[] df = defaultService.getSupportedDocFlavors();
//
//        for(int i = 0; i < df.length; i++ ) {
//            JOptionPane.showMessageDialog(_parent, df[i].toString());
//        }
        
//        this._aset.add(MediaSizeName.ISO_A4);
        this._aset.add(new JobName("Order Confirmation", null));
//        this._aset.add(new Copies(1));

//        JOptionPane.showMessageDialog(_parent, services.length);

        PrintService service =
                ServiceUI.printDialog(null, 200, 200,
                services, defaultService, flavor, this._aset);

//        JOptionPane.showMessageDialog(_parent,
//                service.getAttribute(JobName.class));

        if(service != null){

            FileInputStream textStream = null;

            try{
                textStream = new FileInputStream(f);
                Doc mydoc = new SimpleDoc(textStream, flavor, null);

                DocPrintJob job = service.createPrintJob();
//                JOptionPane.showMessageDialog(_parent,
//                    job.getAttributes().toString());

                job.print(mydoc, this._aset);
            } catch( FileNotFoundException fnfe ){
                JOptionPane.showMessageDialog(this._parent, fnfe.getMessage());
            } catch( PrintException pe ){
                JOptionPane.showMessageDialog(this._parent, pe.getMessage());
            } finally{
                if( textStream != null)
                    try{
                        textStream.close();
                    } catch( IOException ioe ){}
            }
        }
        else
            return false;

        return true;
    }

    public void addPrintRequestAttribute( Attribute a ){
        this._aset.add(a);
    }

    public void setJobName( String name ){
        this.setJobName(name, null);
    }

    public void setJobName( String name, Locale l ){
        this.addPrintRequestAttribute(new JobName(name, l));
    }
}
