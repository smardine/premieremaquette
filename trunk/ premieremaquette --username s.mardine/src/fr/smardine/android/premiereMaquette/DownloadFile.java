package fr.smardine.android.premiereMaquette;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;


import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.RemoteViews;
import android.widget.TextView;

public class DownloadFile extends Activity implements OnClickListener{
	
	
	//definition des valuers a "rafraichir" ici afin qu'elle soient visible par toute la class
	int Pourcent=0;
	long Vitesse=0;
	String Status="";
	NotificationManager notificationManager;
	boolean NotifDejaLancée=false;
	

	   

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		 setContentView(R.layout.telechargement);
	        //definition du titre
	        this.setTitle("Synchronisation");
	      
	       // creation du thread qui va rafraichir les valeur de progression et de vitesse
	        updateUI();
	        handler.removeCallbacks(updateTimeTask);
			handler.postDelayed(updateTimeTask, 1000);	
			
			//Notification est un service qui tourne en background et sert à interagir avec les notifications
			notificationManager=(NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);
		        //annuler toute notification précédente
		    notificationManager.cancelAll();
	       
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
			if (Status.equals("Télèchargement terminé!!!")&&NotifDejaLancée==false){
				lanceNotification();
				NotifDejaLancée=true;
			}
			}
		
	
	private void lanceNotification() {
		// Créer un notification à l'action clic sur le bouton de l'activité Main
		Notification nf = new Notification(R.drawable.calendrie_96,"Synchronisation terminée",System.currentTimeMillis());
		// pour exécuter la notification il faut:
		nf.contentView = new RemoteViews(getPackageName(), R.layout.note);
		//contentView sera le contenu du message qui sera affiché dans la notification
		//RemoteViews sont des vues qui ne sont pas rattachées à l'activité en cours
		nf.icon = R.drawable.calendrier_48;
		nf.vibrate = new long[]{0,100,25,100};
		//indiquer quel fichier de son à jouer à réception de la notification
		// le fichier son doit être de format .ogg
		nf.sound = Uri.parse("android.resource://"+this.getPackageName()+"/"+R.raw.droid);  
		nf.ledOffMS = 25; //en milliseconde quand la led du téléphone s'éteind
		nf.ledOnMS = 100; // quand la led du téléphone va s'allumer
		nf.ledARGB = Color.CYAN; // définir la couleur de la LED
		nf.flags = nf.flags | Notification.FLAG_SHOW_LIGHTS;
		
		//contentIntent est une activité qui va se lancer lorsqu'on clic sur la notification qui apparait dans la barre de statut
		//obligatoire à toute création de notification
		Intent returnActivity = new Intent (this,Main.class);
		Intent activity = new Intent(this, Note.class);
		activity.putExtra("param", 1);
		Log.i("", ">> Main, affecter l'activity à la visualisation de la notification");
		PendingIntent pendingintent = PendingIntent.getActivity(this, 0, returnActivity, 0);
		nf.contentIntent = pendingintent;
		
		activity = new Intent(this, Note.class);
		activity.putExtra("param", 2);
		Log.i("", ">> Main, affecter l'activity à la suppression de la notification");
		//il est possible d'appeler une activité lorsque la notification est supprimée
		// lors de la supression de la notification, on peut definir une activité "par defaut" sur laquelle 
		//on va revenir une fois que l'on a cliqué sur "supprimer" dans les notifications
		//ici on defini l'activité "preferences" comme point de retour.
		
		nf.deleteIntent = PendingIntent.getActivity(this, 0, returnActivity, 0);
		
		//pour lancer la notification
		notificationManager.notify(-1, nf);
		
	}


	public void onClick(View v) {
		// TODO Auto-generated method stub
	}
	protected void onStop() {
		super.onStop();
		handler.removeCallbacks(updateTimeTask);
	}
	
    @SuppressWarnings("unchecked")
	@Override
    protected void onResume() {
        super.onResume();
        handler.removeCallbacks(updateTimeTask);
        handler.postDelayed(updateTimeTask, 1000);
        new DownloadFileTask().execute("http://support.microconcept.fr/Outils%5CTeleMaintenance/PcHelpWare.exe");
       
    }
    
	@Override
	protected void onDestroy() {
		super.onDestroy();
    	if ( handler != null )
    		handler.removeCallbacks(updateTimeTask);
    	handler = null;		
	}
    

    
@SuppressWarnings("rawtypes")
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
				
				//String PATH = "/data/data/fr.smardine.android.premiereMaquette/dl/";
				String PATH = "/sdcard/dl/";
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
					Status="Télèchargement terminé!!!";
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