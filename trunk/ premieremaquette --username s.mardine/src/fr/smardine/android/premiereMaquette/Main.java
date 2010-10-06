package fr.smardine.android.premiereMaquette;




import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends Activity implements OnClickListener {
	
	//
	private  Button BoutonClient,BoutonTournée,BoutonParametres;
	SharedPreferences preferences;
	String Date="";
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
   	 super.onCreate(savedInstanceState);
   	 //definition du titre de la fenetre
   	 	this.setTitle("Acceuil");
   	 	//on appelle le fichier main.xml (definition de la fenetre)
        setContentView(R.layout.main);
        //on initialise le bouton en lui indiquant son ID 
        BoutonClient= (Button)findViewById(R.id.BtClient);
        //on dit a android de surveiller les click sur ce bouton
        BoutonClient.setOnClickListener(this);
        BoutonTournée = (Button)findViewById (R.id.BtTournee);
        BoutonTournée.setOnClickListener (this);
        
        BoutonParametres = (Button)findViewById(R.id.BtParam);
        BoutonParametres.setOnClickListener(this);
               
       // AnalogClock Horloge = (AnalogClock)findViewById(R.id.Horloge);
        
        //initialisation des preferences
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        
       
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
		final TextView AfficheDate=(TextView)findViewById(R.id.TexteDate);
					
			//on affecte des valeurs aux composant
			  Date actuelle = new Date();
				DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
				Date = dateFormat.format(actuelle);
							
				AfficheDate.setText(Date);
			
			
			}

	@Override
	public void onClick(View v) {
		
		
		if (v == BoutonClient) {//si le bouton cliqué est le "boutonClient"
			//on créer une nouvelle activité avec comme point de depart "Main" et comme destination "FicheClient"
			Intent intent = new Intent(Main.this, FicheClient.class);
			//on demarre la nouvelle activité
			startActivity(intent);
		}
		if (v== BoutonTournée){
			//popUp("telechargement");
			
			//Intent intent = new Intent(Main.this, Preferences.class);
			//on demarre la nouvelle activité
			//startActivity(intent);
			
			
			
			
		}
		if (v==BoutonParametres){
			Intent intent = new Intent(Main.this, Preferences.class);
			//on demarre la nouvelle activité
			startActivity(intent);
    		
		}
		
		
	}
	 /**
     * Exécuté que l'activité arrêtée via un "stop" redémarre.
     *
     * La fonction onRestart() est suivie de la fonction onStart().
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        //popUp("onRestart()");
    }
 
    /**
     * Exécuté lorsque l'activité devient visible à l'utilisateur.
     *
     * La fonction onStart() est suivie de la fonction onResume().
     */
    @Override
    protected void onStart() {
        super.onStart();
        updateUI();
       // popUp("onStart()");
    }
 
    /**
     * Exécutée a chaque passage en premier plan de l'activité.
     * Ou bien, si l'activité passe à nouveau en premier (si une autre activité était passé en premier plan entre temps).
     *
     * La fonction onResume() est suivie de l'exécution de l'activité.
     */
    @Override
    protected void onResume() {
        super.onResume();
        handler.removeCallbacks(updateTimeTask);
        handler.postDelayed(updateTimeTask, 1000);
      //  popUp("onResume()");
    }
	/**
     * La fonction onStop() est exécutée :
     * - lorsque l'activité n'est plus en premier plan
     * - ou bien lorsque l'activité va être détruite
     *
     * Cette fonction est suivie :
     * - de la fonction onRestart() si l'activité passe à nouveau en premier plan
     * - de la fonction onDestroy() lorsque l'activité se termine ou bien lorsque le système décide de l'arrêter
     */
    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacks(updateTimeTask);
  }
    /**
     * La fonction onPause() est suivie :
     * - d'un onResume() si l'activité passe à nouveau en premier plan
     * - d'un onStop() si elle devient invisible à l'utilisateur
     *
     * L'exécution de la fonction onPause() doit être rapide,
     * car la prochaine activité ne démarrera pas tant que l'exécution
     * de la fonction onPause() n'est pas terminée.
     */
    @Override
    protected void onPause() {
        super.onPause();
 
        if (isFinishing()) {//si le SYSTEME detecte que l'on sort de l'application 
          //  popUp("onPause, l'utilisateur à demandé la fermeture via un finish()");
           
            //d'apres le cahier des charges, seul le nom d'utilisateur doit etre conservé, 
            //le mot de passe doit etre effacé lorsqu'on quitte l'application
            //definition d'un editeur de preferences
            SharedPreferences.Editor editor = preferences.edit();
            //on affecte la valeur "" à l'entrée "password" du fichier Preferences.xml
            editor.putString("password", "");
            //on "commit" afin de valider les modifications, 
            //il pourrait y avoir d'autre insctruction avant le commit
            editor.commit();
            finish();
			onStop();
			onDestroy();
			//a faire avant  System.exit pour supprimer correctement toute les données presentes en memoire
			System.runFinalizersOnExit(true); 
			System.exit(0);
        } else {//sinon, on pert juste le "focus sur l'appli (lors d'un appel telephonique entrant par exemple)
          //  popUp("onPause, l'utilisateur n'a pas demandé la fermeture via un finish()");
        }
    }
	public void OnDestroy() {
		super.onDestroy();
		if ( handler != null )
    		handler.removeCallbacks(updateTimeTask);
    	handler = null;		
		
	}

	public void popUp(String message) {
        Toast.makeText(this, message, 1).show();
    }
}