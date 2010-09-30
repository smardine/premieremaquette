package fr.smardine.android.premiereMaquette;

import Thread.ThreadDownload;
import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;

public class Preferences extends Activity implements OnClickListener{
	
	Button BoutonSynchro;
	ProgressBar Progress;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//addPreferencesFromResource(R.xml.preferences);
		 setContentView(R.layout.parametsynchro);
	        //definition du titre
	        this.setTitle("parametres");
	        BoutonSynchro = (Button)findViewById(R.id.Button01);
	        BoutonSynchro.setOnClickListener(this);
	        Progress=(ProgressBar) findViewById(R.id.ProgressBar01);
	}
	
	public void onClick(View v) {
		// TODO Auto-generated method stub
		ThreadDownload dl = new ThreadDownload ("http://downloads.sourceforge.net/project/carnetclient/setup/setupCarnetClient.exe",Progress);
		dl.start();
		
	}

}
