package fr.smardine.android.premiereMaquette;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class produitListAdapter extends BaseAdapter {
	
	private ArrayList<produit> produits;
	//créer un layoutinflater pour intégrer la listview dedans
	private LayoutInflater myInflater;
	

	public produitListAdapter(Context context, ArrayList<produit> _produits) {
		// paramètrer le layout en prenant celui du context
		this.myInflater = LayoutInflater.from(context);
		this.produits = _produits;
	}
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.produits.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return this.produits.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	
	/*
	 * astuce pour fluidifier au mieux l'affichage de la listview
	 * mémoriser le contenu des composants visuels qui sont présents dans
	 * une ligne de la listview
	 * La classe peut être déclarée dans un autre module pour être réutilisée
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	public static class ViewHolder {
		TextView text01;
		TextView text02;
	}
	
	
	
	/*
	 * cette méthode est appelée à chaque fois que la listview doit
	 * afficher une ligne
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		//convertView peut déjà être initialisé sinon alors l'initialiser
		if (convertView == null ) {
			//affecter un linearlayout propre à la ligne à afficher dans le listview
			convertView = myInflater.inflate(R.layout.produitlistitem, null);
			holder = new ViewHolder();
			holder.text01 = (TextView)convertView.findViewById(R.id.txtNom);
			holder.text02 = (TextView)convertView.findViewById(R.id.txtDetail);
			//tagger le convertView avec ce Holder créé pour que l'association se fasse
			convertView.setTag(holder);
		} else {
			//puisque déjà valorisé une fois alors récupérer le holder à partir du tag posé à la création
			holder = (ViewHolder)convertView.getTag();
		}
		holder.text01.setText(produits.get(position).nom);
		holder.text02.setText(produits.get(position).detail);
		
		return convertView;
	}

}
