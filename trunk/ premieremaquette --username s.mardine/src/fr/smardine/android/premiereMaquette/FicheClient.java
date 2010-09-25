package fr.smardine.android.premiereMaquette;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import fr.smardine.android.premiereMaquette.R;

public class FicheClient extends Activity implements OnClickListener {

	public void onCreate(Bundle savedInstanceState) {
	   	 	super.onCreate(savedInstanceState);
	   	 	//appel du fichier "ficheclient.xml"
	        setContentView(R.layout.fiche_client);
	        //definition du titre
	        this.setTitle("Fiches Clients");
	       
	        
	    }
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}
