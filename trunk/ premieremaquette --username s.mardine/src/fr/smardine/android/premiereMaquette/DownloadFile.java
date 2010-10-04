package fr.smardine.android.premiereMaquette;


import Thread.ThreadDownload;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class DownloadFile extends Activity implements OnClickListener{
	
	
	ProgressBar Progress;
	TextView MessageVitesse;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		 setContentView(R.layout.telechargement);
	        //definition du titre
	        this.setTitle("Synchronisation");
	      Progress=(ProgressBar) findViewById(R.id.ProgressBar01);
	      MessageVitesse=(TextView) findViewById (R.id.TextView01);
	      
	       
	}
	
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		
		
		
	}
	protected void onStart(){
		super.onStart();
	}
	protected void onResume(){
		super.onResume();
		ThreadDownload dl = new ThreadDownload ("http://downloads.sourceforge.net/project/carnetclient/setup/setupCarnetClient.exe",
						Progress,MessageVitesse);
			dl.start();
	}

	

}
