/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cwc.util.filters;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author karnd01
 */
public final class FileFilters {

    public static void addAllImageFileFilters( JFileChooser jfc ){
        
        FileFilter all = new ALLIMAGESFileFilter();
        jfc.addChoosableFileFilter(all);
        jfc.addChoosableFileFilter(new JPGFileFilter());
        jfc.addChoosableFileFilter(new JPEGFileFilter());
        jfc.addChoosableFileFilter(new PNGFileFilter());
        jfc.addChoosableFileFilter(new GIFFileFilter());
        jfc.addChoosableFileFilter(new BMPFileFilter());
        jfc.addChoosableFileFilter(new TIFFFileFilter());
        jfc.addChoosableFileFilter(new FLASHPIXFileFilter());
        jfc.addChoosableFileFilter(new WBMPFileFilter());
        jfc.addChoosableFileFilter(new PNMFileFilter());
        jfc.setFileFilter(all);
    }

    public static class JPGFileFilter extends FileFilter {

        public boolean accept(File f) {
            return f.isDirectory() ||
                    f.getName().toLowerCase().endsWith(".jpg");
        }

        public String getDescription() {
            return ".jpg files";
        }
    }

    public static class JPEGFileFilter extends FileFilter {

        public boolean accept(File f) {
            return f.isDirectory() ||
                    f.getName().toLowerCase().endsWith(".jpeg");
        }

        public String getDescription() {
            return ".jpeg files";
        }
    }

    public static class PNGFileFilter extends FileFilter {

        public boolean accept(File f) {
            return f.isDirectory() ||
                    f.getName().toLowerCase().endsWith(".png");
        }

        public String getDescription() {
            return ".png files";
        }
    }

    public static class PNMFileFilter extends FileFilter {

        public boolean accept(File f) {
            return f.isDirectory() ||
                    f.getName().toLowerCase().endsWith(".pnm");
        }

        public String getDescription() {
            return ".pnm files";
        }
    }

    public static class GIFFileFilter extends FileFilter {

        public boolean accept(File f) {
            return f.isDirectory() ||
                    f.getName().toLowerCase().endsWith(".gif");
        }

        public String getDescription() {
            return ".gif files";
        }
    }

    public static class BMPFileFilter extends FileFilter {

        public boolean accept(File f) {
            return f.isDirectory() ||
                    f.getName().toLowerCase().endsWith(".bmp");
        }

        public String getDescription() {
            return ".bmp files";
        }
    }

    public static class FLASHPIXFileFilter extends FileFilter {

        public boolean accept(File f) {
            return f.isDirectory() ||
                    f.getName().toLowerCase().endsWith(".bmp");
        }

        public String getDescription() {
            return ".bmp files";
        }
    }

    public static class TIFFFileFilter extends FileFilter {

        public boolean accept(File f) {
            return f.isDirectory() ||
                    f.getName().toLowerCase().endsWith(".tiff");
        }

        public String getDescription() {
            return ".tiff files";
        }
    }

    public static class WBMPFileFilter extends FileFilter {

        public boolean accept(File f) {
            return f.isDirectory() ||
                    f.getName().toLowerCase().endsWith(".wbmp");
        }

        public String getDescription() {
            return ".wbmp files";
        }
    }

    public static class ALLIMAGESFileFilter extends FileFilter {

        public boolean accept(File f) {
            return f.isDirectory()
                    || f.getName().toLowerCase().endsWith(".jpg")
                    || f.getName().toLowerCase().endsWith(".jpeg")
                    || f.getName().toLowerCase().endsWith(".png")
                    || f.getName().toLowerCase().endsWith(".pnm")
                    || f.getName().toLowerCase().endsWith(".gif")
                    || f.getName().toLowerCase().endsWith(".bmp")
                    || f.getName().toLowerCase().endsWith(".tiff")
                    || f.getName().toLowerCase().endsWith(".wbmp")
                    || f.getName().toLowerCase().endsWith(".wbmp");
        }

        public String getDescription() {
            return "Image Files";
        }
    }
}
