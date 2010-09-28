package fr.smardine.android.premiereMaquette;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import fr.smardine.android.premiereMaquette.R;

public class Main extends Activity implements OnClickListener {
	private  Button BoutonClient,BoutonTournée;
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
        //finish();
       
        
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
			popUp("bouton tournée");
			
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
 
        popUp("onRestart()");
    }
 
    /**
     * Exécuté lorsque l'activité devient visible à l'utilisateur.
     *
     * La fonction onStart() est suivie de la fonction onResume().
     */
    @Override
    protected void onStart() {
        super.onStart();
 
        popUp("onStart()");
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
 
        popUp("onResume()");
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
 
        if (isFinishing()) {
            popUp("onPause, l'utilisateur à demandé la fermeture via un finish()");
            finish();
			onStop();
			onDestroy();
			//a faire avant  System.exit pour supprimer correctement toute les données presentes en memoire
			System.runFinalizersOnExit(true); 
			System.exit(0);
        } else {
            popUp("onPause, l'utilisateur n'a pas demandé la fermeture via un finish()");
        }
    }
	public void OnDestroy() {
		super.onDestroy();
		// popUp("onDestroy()");
		//System.out.println ("extinction");
	}

	public void popUp(String message) {
        Toast.makeText(this, message, 1).show();
    }
}