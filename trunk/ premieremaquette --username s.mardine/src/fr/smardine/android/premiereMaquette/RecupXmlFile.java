package fr.smardine.android.premiereMaquette;

 
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.util.ByteArrayBuffer;

import android.util.Log;
 
 
 
public class RecupXmlFile {
 
 
 
    
		private final static String PATH = "/mnt/sdcard/dd/";  //put the downloaded file here
 
       
 
 
 
        public static void DownloadFromUrl(String imageURL, String fileName) {  //this is the downloader method
        	int tailleActu;
                try {
 
                        URL url = new URL("http://support.microconcept.fr/Outils%5CTeleMaintenance/" + imageURL); //you can write here any link
 
                        File file = new File(fileName);
 
 
 
                        long startTime = System.currentTimeMillis();
 
                        Log.d("ImageManager", "download begining");
 
                        Log.d("ImageManager", "download url:" + url);
 
                        Log.d("ImageManager", "downloaded file name:" + fileName);
 
                        /* Open a connection to that URL. */
 
                        URLConnection ucon = url.openConnection();
 
 
                    
						int contentLenght=ucon.getContentLength();
                        /*
 
                         * Define InputStreams to read from the URLConnection.
 
                         */
 
                        InputStream is = ucon.getInputStream();
 
                        BufferedInputStream bis = new BufferedInputStream(is);
 
 
 
                        /*
 
                         * Read bytes to the Buffer until there is nothing more to read(-1).
 
                         */
 
                        ByteArrayBuffer baf = new ByteArrayBuffer(500*1024);
 
                        int current = 0;
                      
 
                        while ((current = bis.read()) != -1) {
 
                                baf.append((byte) current);
                               
                              tailleActu=baf.length();  
                                
                             
								int progres=(100*tailleActu)/contentLenght;
                                System.out.println("Porgression: "+progres+","+tailleActu+" / "+contentLenght);
                                                               	
                               // Notif.notification(progression);
 
                        }
 
 
 
                        /* Convert the Bytes read to a String. */
 
                        FileOutputStream fos = new FileOutputStream(PATH+file);
 
                        fos.write(baf.toByteArray());
 
                        fos.close();
 
                        Log.d("ImageManager", "download ready in"
 
                                        + ((System.currentTimeMillis() - startTime) / 1000)
 
                                        + " sec");
 
 
 
                } catch (IOException e) {
 
                        Log.d("ImageManager", "Error: " + e);
 
                }
 
 
 
        }
 
        
        
        public static String load(String url, int bufferSize){

            try {
                URL myURL = new URL(url);
                URLConnection ucon = myURL.openConnection();
                ucon.setRequestProperty("Connection", "keep-alive");
                InputStream inputStream = ucon.getInputStream();
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                ByteArrayBuffer byteArrayBuffer = new ByteArrayBuffer(bufferSize);
                byte[] buf = new byte[bufferSize];
                int read;
                do {
                    read = bufferedInputStream.read(buf, 0, buf.length);
                    if (read > 0)
                        byteArrayBuffer.append(buf, 0, read);
                } while (read >= 0);
                return new String(byteArrayBuffer.toByteArray());
            } catch (Exception e) {
                Log.i("Error", e.toString());
            }
            return null;
        }
      
}
	
		


