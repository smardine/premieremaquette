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
	private  Button BoutonClient,BoutonTourn�e,BoutonParametres;
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
        BoutonTourn�e = (Button)findViewById (R.id.BtTournee);
        BoutonTourn�e.setOnClickListener (this);
        
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
		
		
		if (v == BoutonClient) {//si le bouton cliqu� est le "boutonClient"
			//on cr�er une nouvelle activit� avec comme point de depart "Main" et comme destination "FicheClient"
			Intent intent = new Intent(Main.this, FicheClient.class);
			//on demarre la nouvelle activit�
			startActivity(intent);
		}
		if (v== BoutonTourn�e){
			//popUp("telechargement");
			
			//Intent intent = new Intent(Main.this, Preferences.class);
			//on demarre la nouvelle activit�
			//startActivity(intent);
			
			
			
			
		}
		if (v==BoutonParametres){
			Intent intent = new Intent(Main.this, Preferences.class);
			//on demarre la nouvelle activit�
			startActivity(intent);
    		
		}
		
		
	}
	 /**
     * Ex�cut� que l'activit� arr�t�e via un "stop" red�marre.
     *
     * La fonction onRestart() est suivie de la fonction onStart().
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        //popUp("onRestart()");
    }
 
    /**
     * Ex�cut� lorsque l'activit� devient visible � l'utilisateur.
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
     * Ex�cut�e a chaque passage en premier plan de l'activit�.
     * Ou bien, si l'activit� passe � nouveau en premier (si une autre activit� �tait pass� en premier plan entre temps).
     *
     * La fonction onResume() est suivie de l'ex�cution de l'activit�.
     */
    @Override
    protected void onResume() {
        super.onResume();
        handler.removeCallbacks(updateTimeTask);
        handler.postDelayed(updateTimeTask, 1000);
      //  popUp("onResume()");
    }
	/**
     * La fonction onStop() est ex�cut�e :
     * - lorsque l'activit� n'est plus en premier plan
     * - ou bien lorsque l'activit� va �tre d�truite
     *
     * Cette fonction est suivie :
     * - de la fonction onRestart() si l'activit� passe � nouveau en premier plan
     * - de la fonction onDestroy() lorsque l'activit� se termine ou bien lorsque le syst�me d�cide de l'arr�ter
     */
    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacks(updateTimeTask);
  }
    /**
     * La fonction onPause() est suivie :
     * - d'un onResume() si l'activit� passe � nouveau en premier plan
     * - d'un onStop() si elle devient invisible � l'utilisateur
     *
     * L'ex�cution de la fonction onPause() doit �tre rapide,
     * car la prochaine activit� ne d�marrera pas tant que l'ex�cution
     * de la fonction onPause() n'est pas termin�e.
     */
    @Override
    protected void onPause() {
        super.onPause();
 
        if (isFinishing()) {//si le SYSTEME detecte que l'on sort de l'application 
          //  popUp("onPause, l'utilisateur � demand� la fermeture via un finish()");
           
            //d'apres le cahier des charges, seul le nom d'utilisateur doit etre conserv�, 
            //le mot de passe doit etre effac� lorsqu'on quitte l'application
            //definition d'un editeur de preferences
            SharedPreferences.Editor editor = preferences.edit();
            //on affecte la valeur "" � l'entr�e "password" du fichier Preferences.xml
            editor.putString("password", "");
            //on "commit" afin de valider les modifications, 
            //il pourrait y avoir d'autre insctruction avant le commit
            editor.commit();
            finish();
			onStop();
			onDestroy();
			//a faire avant  System.exit pour supprimer correctement toute les donn�es presentes en memoire
			System.runFinalizersOnExit(true); 
			System.exit(0);
        } else {//sinon, on pert juste le "focus sur l'appli (lors d'un appel telephonique entrant par exemple)
          //  popUp("onPause, l'utilisateur n'a pas demand� la fermeture via un finish()");
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