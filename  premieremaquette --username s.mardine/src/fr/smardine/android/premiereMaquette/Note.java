package fr.smardine.android.premiereMaquette;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class Note extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//associer l'activit� note.java au layout note.xml sans �a la r�cup de 
		//TextView renvoie null ! 
		setContentView(R.layout.note);
		Log.i("", "activite Note : entree");
		//r�cup�rer les indications pass�es au lancement de la notification
		Bundle extra = this.getIntent().getExtras();
		if (extra != null){
			Log.i("", "activite Note recup param : "+extra.getInt("param", 0));
		}
		//attraper une instance de la vue note.xml
		TextView view = ((TextView)this.findViewById(R.id.text));
		if (view == null){
			Log.i("", ">>Note : recuperation view null !");
		}else {
			Log.i("", "activit� Note : "+view.getText());
			if (extra != null){
				switch (extra.getInt("param", 0)){
					case 1:view.setText("Cliqu�"); break;
					case 2:view.setText("Supprim�"); break;
				}
			}
		}
	}

}
