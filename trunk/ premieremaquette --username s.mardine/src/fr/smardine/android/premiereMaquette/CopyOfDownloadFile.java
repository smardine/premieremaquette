package fr.smardine.android.premiereMaquette;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import Thread.ThreadDownload;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class CopyOfDownloadFile extends Activity implements OnClickListener{
	
	
	ProgressBar Progress;
	TextView MessageVitesse;
	int Pourcent=0;
	 // Need handler for callbacks to the UI thread

	 final Handler mHandler = new Handler();

	    // Create runnable for posting
	    final Runnable mUpdateResults = new Runnable() {
	        public void run() {
	            updateResultsInUi(0, "Vitesse Actuelle : 0 Ko/s");
	        }
	    };

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		 setContentView(R.layout.telechargement);
	        //definition du titre
	        this.setTitle("Synchronisation");
	      Progress=(ProgressBar) findViewById(R.id.ProgressBar01);
	      MessageVitesse=(TextView) findViewById (R.id.TextView01);
	      
	       
	}
	
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		
		
		
	}
	protected void onStart(){
		super.onStart();
	}
	protected void onResume(){
		super.onResume();
		//ThreadDownload dl = new ThreadDownload ("http://downloads.sourceforge.net/project/carnetclient/setup/setupCarnetClient.exe",
		//				Progress,MessageVitesse);
		//	dl.start();
		startLongRunningOperation();
	}
	
	 protected void startLongRunningOperation() {

	        // Fire off a thread to do some work that we shouldn't do directly in the UI thread
	        Thread t = new Thread() {
	            public void run() {
	            Object mresult= LanceTelechargement("http://downloads.sourceforge.net/project/carnetclient/setup/setupCarnetClient.exe",MessageVitesse);
	                mHandler.post(mUpdateResults);
	            }
	        };
	        t.start();
	    }

	    protected <updateResultsInUi> Object LanceTelechargement(String adresse,TextView MessageVitesses) {
	    	MessageVitesses.setText("Vitesse Actuelle : 0 Ko/s");
			
			InputStream input = null;
			FileOutputStream writeFile = null;
			String fileName = null;
			long HeureDebut;
			long HeureActuelle;
			
			HeureDebut = System.currentTimeMillis();
			
			try
			{
				URL url = new URL(adresse);
				URLConnection connection = url.openConnection();
				//String message = "Téléchargement en cours";
			//	MessageStatus.setText(message);
				final int fileLength = connection.getContentLength();

				if (fileLength == -1)
				{
					System.out.println("Invalide URL or file.");
					return null ;
				}
				
				//TexteTailleTotale.setText("Taille Totale : " +fileLength + "Octet(s)");

				input = connection.getInputStream();
				fileName = url.getFile().substring(url.getFile().lastIndexOf('/') + 1);
				if (fileName.contains("%20")==true){
					fileName=fileName.replaceAll("%20", " ");
				}
				if (fileName.contains("&amp;")==true){
					fileName=fileName.replaceAll("&amp;", " and ");
				}
				//VariableEnvironement.VarEnvSystemTotal();
				//repTemp = VariableEnvironement.VarEnvSystem("TMP");
				String PATH = "/data/data/fr.smardine.android.premiereMaquette/dl/";
				//String PATH = "/sdcard/dl/";
				File path = new File (PATH);
				if (!path.exists()){
					path.mkdirs();
				}
				File fichier = new File (PATH+fileName);
				writeFile = new FileOutputStream(PATH+fileName);
				//lecture par segment de 4Ko
				byte[] buffer = new byte[4096*1024];
				int read;
				long Vitesse = 0;
				while ((read = input.read(buffer)) > 0){
					writeFile.write(buffer, 0, read);
					long TailleEncours = fichier.length();
					long progressionEnCours = (100*(TailleEncours+1))/fileLength;
					Pourcent=(int) progressionEnCours;
					
					HeureActuelle = System.currentTimeMillis();
					
					 Vitesse = (long) (TailleEncours / (HeureActuelle-HeureDebut));
							
					//TexteVitesse.setText("Vitesse Actuelle : "+ Vitesse + " Ko/s");
					System.out.println("Vitesse Actuelle : "+ Vitesse + " Ko/s");
					//updateResultsInUi (Pourcent,"Vitesse Actuelle : "+ Vitesse + " Ko/s");
					 Progress.setProgress(Pourcent);
					 CharSequence vitesse = ("Vitesse Actuelle : "+ Vitesse + " Ko/s");
					 MessageVitesses.setText(vitesse);
					
					//return Pourcent;

				}
				MessageVitesses.setText("Vitesse Actuelle : "+ Vitesse + " Ko/s");
				writeFile.flush();
			}
			catch (IOException e)
			{
				System.out.println("Error while trying to download the file.");
				System.out.println(e);
				e.printStackTrace();
			}
			finally
			{
				try
				{
					writeFile.close();
					input.close();
					
				//	System.out.println("DL terminé");
					//Barreprogression.setProgress(0);
					//MessageStatus.setText("Telechargement terminé");
				}
				catch (IOException e)
				{
					
					System.out.println(e);
					e.printStackTrace();
				}
			}
			return null;
	}

		private void updateResultsInUi(int progression, String vitesse) {

	        // Back in the UI thread -- update our UI elements based on the data in mResults
	       Progress.setProgress(progression);
	       MessageVitesse.setText(vitesse);
	    }


	

}
