/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package FolderWatch;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.*;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import org.imgscalr.Scalr;

/**
 *
 * @author petri
 */
public class FolderWatch2 {

    static boolean radi = true;static boolean pristigao = false;
    static File files;
    static File[] sveSlike;
    static 
        DecimalFormat dnf = new DecimalFormat("00000");
     static int tmp;
     static int prevval;
     static Thread t;
     
      static  WatchKey key;
    
    
   // static boolean radi = true;
    
 static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws Exception {
    BufferedImage scaled =Scalr.resize(originalImage, Scalr.Method.AUTOMATIC, Scalr.Mode.AUTOMATIC, targetWidth, targetHeight, Scalr.OP_ANTIALIAS);

    return scaled;
}
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InterruptedException {
         
        files = new File("C:\\Users\\petri\\Desktop\\reducedimage");
        sveSlike = files.listFiles();
      tmp =sveSlike.length;
        prevval=tmp;
         WatchService watchService
          = FileSystems.getDefault().newWatchService();

        Path path = Paths.get("C:\\Users\\petri\\Desktop\\ovamouda");

        path.register(
          watchService, 
            StandardWatchEventKinds.ENTRY_CREATE, 
              StandardWatchEventKinds.ENTRY_DELETE, 
                StandardWatchEventKinds.ENTRY_MODIFY);

        while ((key = watchService.take()) != null) {
            
            for (WatchEvent<?> event : key.pollEvents()) {
                //proces
            if(!radi){
             pristigao = true;
            }else{
                
        radi = false;
           t = new Thread() {
               @Override
               public void run() {  try {
                   // override the run() to specify the running behavior
                   reku();
                   

                   } catch (IOException ex) {
                       Logger.getLogger(FolderWatch2.class.getName()).log(Level.SEVERE, null, ex);
                   }
               }
            };
            Thread.sleep(200);
            t.start();  // call back run()
            }
            }
            
                 key.reset();
            
        }
    }
    
    
    static void reku() throws IOException{
        
          files = new File("C:\\Users\\petri\\Desktop\\ovamouda");

            sveSlike = files.listFiles();
           
            int nigger = sveSlike.length;
            while(prevval<nigger){  


             File f = sveSlike[tmp];
            
             InputStream inputStream = new FileInputStream(f);
           
             OutputStream outputStream = new FileOutputStream(new File("C:\\Users\\petri\\Desktop\\reducedimage\\"+dnf.format(tmp)+".jpg"));
               

             float quality = 0.5f;

             // create a BufferedImage as the result of decoding the supplied InputStream
             BufferedImage image = ImageIO.read(inputStream);

             // get all image writers for JPG format
             Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");

             if (!writers.hasNext())
                 throw new IllegalStateException("No writers found");

             ImageWriter writer = (ImageWriter) writers.next();
             ImageOutputStream ios = ImageIO.createImageOutputStream(outputStream);
             writer.setOutput(ios);

             ImageWriteParam param = writer.getDefaultWriteParam();
//
//             // compress to a given quality
//             param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
//             param.setCompressionQuality(quality);

                try {
                    image=resizeImage(image,800,800);
                } catch (Exception ex) {
                    Logger.getLogger(FolderWatch2.class.getName()).log(Level.SEVERE, null, ex);
                }
             // appends a complete image stream containing a single image and
             //associated stream and image metadata and thumbnails to the output
             writer.write(null, new IIOImage(image, null, null), param);

           
            
            
             // close all streams
             inputStream.close();
             outputStream.close(); 
             writer.dispose();
             ios.close();
            
             
              ///rename picture
 
            Path old = Paths.get(f.getAbsolutePath());
            Path  nw= Paths.get("C:\\Users\\petri\\Desktop\\ovamouda\\"+dnf.format(tmp)+".jpg");
        try {
            Files.move(old, nw, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File was successfully renamed");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error: Unable to rename file");
        }   
             
             
              tmp++; 
             prevval+=1;

           
                 }
            radi = true;
            if(pristigao){
            pristigao = false;
            radi = false;
              t.run();
              
  // call back run()
            }
            
            
    }
    
}
