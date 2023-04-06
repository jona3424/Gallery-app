/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.StefanWS.services;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.util.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author petri
 */

class Info{
    public byte[] fajl;
    public int mod;
    public String time;
    public Info(byte[] f, int m,String time){
        fajl=f;
        mod = m;
        this.time=time;
    }
}
@RestController
@RequestMapping("/serv")
public class OMRC {
    
    static 
        DecimalFormat dnf = new DecimalFormat("00000");
    static int ommm = 0;
      @GetMapping("/folderLen")
    public int notPrint() throws FileNotFoundException, IOException{
        File veliki = new File("C:\\Users\\petri\\Desktop\\tempSingleton.txt");
        FileInputStream fis = new FileInputStream(veliki);
        Scanner citaonica = new Scanner(fis);
        int folderNel = citaonica.nextInt();
        citaonica.close();
        fis.close();
        return folderNel;
    }
    @GetMapping("/gemmeThisSlika/{id}")
    public Info print(@PathVariable int id) throws FileNotFoundException, IOException{
        //neki stari code idk....
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
     
//        File files =new File("/Users/imac/Desktop/apk folder");
//        File[] sveSlike = files.listFiles();
        //File f = sveSlike[(id+1)%sveSlike.length];
      
        
       /* 
        
        vrijeme na pun sat read integera
        
        File time = new File("/Users/imac/Desktop/apk folder txt/"+ dnf.format(id)+".txt");
        FileInputStream fis = new FileInputStream(time);
        Scanner citaonica = new Scanner(fis);
        int timesend = citaonica.nextInt();
        citaonica.close();
        fis.close();
        */
        
        FileInputStream fis=new FileInputStream("C:\\Users\\petri\\Desktop\\time\\"+ dnf.format(id)+".txt");       
        Scanner sc=new Scanner(fis);
        String timesend =sc.nextLine();
        sc.close();
       
        
        
        
        File pic= new File("C:\\Users\\petri\\Desktop\\apk\\"+ dnf.format(id)+".jpg");
        InputStream inputStream = new FileInputStream(pic);
        long fileSize = pic.length(); 
        byte[] allBytes = new byte[(int) fileSize];
        int bytesRead = inputStream.read(allBytes);
        Info jj = new Info(allBytes,id,timesend);
            
        inputStream.close();

        
        return jj;
       
    }
    

    
}
