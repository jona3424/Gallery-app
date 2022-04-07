/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.StefanWS.services;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Scanner;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author petri
 */

class Jaja{
    public byte[] fajl;
    public int mod;
    public Jaja(byte[] f, int m){
        fajl=f;
        mod = m;
    }
}
@RestController
@RequestMapping("/ovaMouda")
public class OMRC {
      @GetMapping("/folderLen")
    public int notPrint() throws FileNotFoundException, IOException{
        return new File("C:\\Users\\petri\\Desktop\\reducedimage").listFiles().length;
    }
    @GetMapping("/gemmeThisSlika/{id}")
    public Jaja print(@PathVariable int id) throws FileNotFoundException, IOException{
     /*   File files = new File("C:\\Users\\petri\\Desktop\\ovamouda");
        File[] sveSlike = files.listFiles();
        File f = sveSlike[id%sveSlike.length];

           // OutputStream outputStream = new FileOutputStream(new File("C:\\Users\\petri\\Desktop\\ovamouda\\"+"Luka"+sveSlike.length+1+".jpg"));
 
            long fileSize = f.length();
            
            byte[] allBytes = new byte[(int) fileSize];
 
            int bytesRead = inputStream.read(allBytes);
            
            Jaja jj = new Jaja(allBytes,sveSlike.length);
            
            
         //  outputStream.write(allBytes, 0, bytesRead);
 

        return jj;*/
     
        File files =new File("C:\\Users\\petri\\Desktop\\reducedimage");
        File[] sveSlike = files.listFiles();
       
        File f = sveSlike[id%sveSlike.length];
        System.out.println(id);
        System.out.println("\n\n\n" + sveSlike.length);
        InputStream inputStream = new FileInputStream(f);
        inputStream = new FileInputStream(f);
        long fileSize = f.length(); 
        byte[] allBytes = new byte[(int) fileSize];
        int bytesRead = inputStream.read(allBytes);
        Jaja jj = new Jaja(allBytes,sveSlike.length);
            
         inputStream.close();

        
        return jj;
       
    }
    
    @GetMapping("/copy")
    public void copy(@RequestBody Jaja kurac) throws FileNotFoundException, IOException{
        File files = new File("C:\\Users\\petri\\Desktop\\ovamouda");
        File[] sveSlike = files.listFiles();


            OutputStream outputStream = new FileOutputStream(new File("C:\\Users\\petri\\Desktop\\ovamouda\\"+sveSlike.length+".jpg"));

            ObjectMapper om = new ObjectMapper();
            //om.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
            Jaja nig = kurac;
            outputStream.write(nig.fajl, 0, nig.fajl.length);
 

    }
    
}
