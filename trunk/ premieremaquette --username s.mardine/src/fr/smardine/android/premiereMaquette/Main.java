package fr.smardine.android.premiereMaquette;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import fr.smardine.android.premiereMaquette.R;

public class Main extends Activity implements OnClickListener {
	private  Button BoutonClient;
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
       
        
    }

	@Override
	public void onClick(View v) {
		
		
		if (v == BoutonClient) {//si le bouton cliqué est le "boutonClient"
			//on créer une nouvelle activité avec comme point de depart "Main" et comme destination "FicheClient"
			Intent intent = new Intent(Main.this, FicheClient.class);
			//on demarre la nouvelle activité
			startActivity(intent);
		}

		
	}
}