package fr.smardine.android.premiereMaquette;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.ListView;


public class FicheClient extends Activity implements OnClickListener {
	ArrayList<produit> produits = new ArrayList<produit>(); 
	public void onCreate(Bundle savedInstanceState) {
	   	 	super.onCreate(savedInstanceState);
	   	 	//appel du fichier "ficheclient.xml"
	        setContentView(R.layout.fiche_client);
	        //definition du titre
	        this.setTitle("Fiches Clients");
	        produits.add(new produit("ESSAI Faust","05/04/1993"));
	        produits.add(new produit("CARTE Lucille","20/03/1961"));
	        produits.add(new produit("TREIZE Hugo","01/10/2007"));
	        produits.add(new produit("SPECIMEN CARTE Marie-Amelie","01/04/1964"));
	        produits.add(new produit("ESSAI Agathe","15/04/1982"));
	        produits.add(new produit("POLICE Jacques","07/19/1947"));
	        produits.add(new produit("RIVIERE Christine","12/19/1965"));
	        produits.add(new produit("SPECIMEN CARTE Simon","21/01/1965"));
	        produits.add(new produit("LE HAVRE Jean","01/19/1942"));
	        produits.add(new produit("ESSAI Alexis","04/09/1989"));
	        produits.add(new produit("MEDECIN Gladys","25/08/1963"));
	        produits.add(new produit("JEAN-CHARLES Lidiana","22/12/1984"));
	        produits.add(new produit("ECLANCHER Roselyne","03/08/1949"));
	        
	        //afficher le contenu de l'ArrayList "produits" dans une ListView
	        //- créer le ListAdapter dérivant d'un baseAdapter
	        ListView produitlistview = (ListView)this.findViewById(R.id.produitListView);
	        
	        //animation d'affichage cascade du haut vers le bas
	        AnimationSet set = new AnimationSet(true);
	        Animation animation = new AlphaAnimation(0.0f, 1.0f);
	        animation.setDuration(700);
	        set.addAnimation(animation);
	        animation = new TranslateAnimation(
	            Animation.RELATIVE_TO_SELF, 0.0f,Animation.RELATIVE_TO_SELF, 0.0f,
	            Animation.RELATIVE_TO_SELF, -1.0f,Animation.RELATIVE_TO_SELF, 0.0f
	        );
	        animation.setDuration(100);
	        set.addAnimation(animation);
	        LayoutAnimationController controller = new LayoutAnimationController(set, 0.5f);
	        produitlistview.setLayoutAnimation(controller);
	        
	        
	        //paramètrer l'adapteur correspondant
	        produitListAdapter adpt = new produitListAdapter(this, produits);
	        //paramèter l'adapter sur la listview
	        produitlistview.setAdapter(adpt);
	       
	        
	    }
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}
