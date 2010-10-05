package fr.smardine.android.premiereMaquette;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.TextView;

public class DownloadFile extends Activity implements OnClickListener{
	
	
	//definition des valuers a "rafraichir" ici afin qu'elle soient visible par toute la class
	int Pourcent=0;
	long Vitesse=0;
	String Status="";
	

	   

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		 setContentView(R.layout.telechargement);
	        //definition du titre
	        this.setTitle("Synchronisation");
	      
	       // creation du thread qui va rafraichir les valeur de progression et de vitesse
	        updateUI();
	        handler.removeCallbacks(updateTimeTask);
			handler.postDelayed(updateTimeTask, 1000);	 
	       
	}
	 private Handler handler = new Handler();
	
	private Runnable updateTimeTask = new Runnable() {
		public void run() {
			updateUI();
			handler.postDelayed(this, 1000);
		}
	};
	
	private void updateUI() {
			//on commence par recuperer les id des composants
			final TextView MessageVitesse = (TextView) findViewById(R.id.TextView01);
			final ProgressBar Progress = (ProgressBar) findViewById (R.id.ProgressBar01);
			final TextView MessageStatus = (TextView)findViewById (R.id.TextView02);
					
			//on affecte des valeurs aux composant
			MessageVitesse.setText("Vitesse Actuelle : "+ Vitesse + " Ko/s");
			Progress.setProgress(Pourcent);
			MessageStatus.setText (Status);

			}
		
	
	public void onClick(View v) {
		// TODO Auto-generated method stub
	}
	protected void onStop() {
		super.onStop();
		handler.removeCallbacks(updateTimeTask);
	}
	
    @Override
    protected void onResume() {
        super.onResume();
        handler.removeCallbacks(updateTimeTask);
        handler.postDelayed(updateTimeTask, 1000);
        new DownloadFileTask().execute("http://downloads.sourceforge.net/project/carnetclient/setup/setupCarnetClient.exe");
       
    }
    
	@Override
	protected void onDestroy() {
		super.onDestroy();
    	if ( handler != null )
    		handler.removeCallbacks(updateTimeTask);
    	handler = null;		
	}
    

    
private class DownloadFileTask extends AsyncTask {
	   
		protected Object doInBackground(Object... params) {
			InputStream input = null;
			FileOutputStream writeFile = null;
			String fileName = null;
			long HeureDebut;
			long HeureActuelle;
			
			HeureDebut = System.currentTimeMillis();
			
			try
			{
				URL url = new URL(params[0].toString());//le param[0] est l'url passé dans la commande "execute"
				URLConnection connection = url.openConnection();
				
				final int fileLength = connection.getContentLength();

				if (fileLength == -1)
				{
					System.out.println("Invalide URL or file.");
					return fileLength;
				}
				Status="Telechargement en cours, veuillez patienter...";
				input = connection.getInputStream();
				fileName = url.getFile().substring(url.getFile().lastIndexOf('/') + 1);
				if (fileName.contains("%20")==true){
					fileName=fileName.replaceAll("%20", " ");
				}
				if (fileName.contains("&amp;")==true){
					fileName=fileName.replaceAll("&amp;", " and ");
				}
				
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

				while ((read = input.read(buffer)) > 0){
					writeFile.write(buffer, 0, read);
					long TailleEncours = fichier.length();
					long progressionEnCours = (100*(TailleEncours+1))/fileLength;
					Pourcent=(int) progressionEnCours;
					
					HeureActuelle = System.currentTimeMillis();
					
					 Vitesse = (long) (TailleEncours / (HeureActuelle-HeureDebut));
							
					
					System.out.println("Vitesse Actuelle : "+ Vitesse + " Ko/s");
					
				}
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
					Pourcent=0;
					Vitesse=0;
					Status="Telechargement terminé!!!";
					writeFile.close();
					input.close();
				}
				catch (IOException e)
				{
					System.out.println(e);
					e.printStackTrace();
				}
			}
			return fileName;
		}
	
	}
}