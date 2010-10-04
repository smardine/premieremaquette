package fr.smardine.android.premiereMaquette;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

public class Preferences extends Activity implements OnClickListener{
	
	Button BoutonSynchro;
	ProgressBar Progress;
	EditText idUtilisateur,motdepasse;
	SharedPreferences preferences;
	String Finess,Password;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//addPreferencesFromResource(R.xml.preferences);
		 	setContentView(R.layout.prefetsynchro);
	        //definition du titre
	        this.setTitle("Paramètres et Synchronisation");
	        BoutonSynchro = (Button)findViewById(R.id.BtGo);
	        BoutonSynchro.setOnClickListener(this);
	      //  Progress=(ProgressBar) findViewById(R.id.ProgressBar01);
	        preferences = PreferenceManager.getDefaultSharedPreferences(this);
	        
	        idUtilisateur = (EditText) findViewById(R.id.EditTextIdUtilisateur);
	        motdepasse = (EditText) findViewById (R.id.EditTextPassword);
	        
	}
	
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v== BoutonSynchro){
			Intent intent = new Intent(Preferences.this, CopyOfDownloadFile.class);
			//on demarre la nouvelle activité
			startActivity(intent);
		}
		//ThreadDownload dl = new ThreadDownload ("http://downloads.sourceforge.net/project/carnetclient/setup/setupCarnetClient.exe",Progress);
		//dl.start();
		
	}
	protected void onPause(){
		super.onPause();
		//comme on quitte la vue, on sauvegarde ce que l'utilisateur a saisi
		Finess = idUtilisateur.getText().toString();
		Password = motdepasse.getText().toString();
		 SharedPreferences.Editor editor = preferences.edit();
         //on affecte la valeur "" à l'entrée "password" du fichier Preferences.xml
         editor.putString("password", Password);
         editor.putString("username", Finess);
         //on "commit" afin de valider les modifications, 
         //il pourrait y avoir d'autre insctruction avant le commit
         editor.commit();
	}
	protected void onResume(){
		super.onResume();
		Finess = preferences.getString("username", "numéro finess");
        Password = preferences.getString ("password","mot de passe");
        idUtilisateur.setText(Finess);
        motdepasse.setText(Password);
        
       
        
	}

}
