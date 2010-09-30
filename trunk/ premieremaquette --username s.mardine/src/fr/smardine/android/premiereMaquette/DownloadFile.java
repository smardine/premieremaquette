package fr.smardine.android.premiereMaquette;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import android.os.AsyncTask;
import android.widget.ProgressBar;


//as you said you know how to use mProgressBar
//I'm not going to explain how to initialize it, etc.
DownloadFile DownloadFile = new DownloadFile();


public class DownloadFile extends AsyncTask<String, String, String>{

 protected String doInBackground(String... url) {
     int count;
     ProgressBar mProgressBar;
     try {
         URL url1 = new URL(url1[0]);
         URLConnection conexion = url1.openConnection();
         conexion.connect();

         // this will be useful so that you can show a tipical 0-100% progress bar
         int lenghtOfFile = conexion.getContentLength();

         // downlod the file
         InputStream input = new BufferedInputStream(url1.openStream());
         OutputStream output = new FileOutputStream("/sdcard/somewhere/nameofthefile.ext");

         byte data[] = new byte[1024];

         long total = 0;

         while ((count = input.read(data)) != -1) {
             total += count;
             // publishing the progress....
             publishProgress(""+(int)total*100/lenghtOfFile);
             output.write(data, 0, count);
         }

         output.flush();
         output.close();
         input.close();
     } catch (Exception e) {}
     return null;
 }

 public void onProgressUpdate(String... args){
     // here you will have to update the progressbar
     // with something like mProgressBar.setProgress(Float.parseFloat(args[0]))
 }
}
