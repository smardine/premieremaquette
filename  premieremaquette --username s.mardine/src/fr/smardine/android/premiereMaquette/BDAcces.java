package fr.smardine.android.premiereMaquette;



import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

public class BDAcces {

	
	private static final String CLIENT_TABLE = "client_LesClients";
	
	//private static final String INGREDIENT_RECETTE_TABLE = "recette_contenu";
//
	//private static final String INGREDIENT_TABLE = "recette_condiment";
	
	private static final String DATABASE_NAME = "client_base";
	
	private final Context mCtx;

	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;
	private SQLiteQueryBuilder mbbuilder;
	
	private static final String TAG = "BDAcces";
	private static final int DATABASE_VERSION = 1;
	//les differentes requete pour le creation des tables
	private static final String CREATE_CLIENT_TABLE ="CREATE TABLE client_LesClients ("
								  +"id_client INTEGER PRIMARY KEY  AUTOINCREMENT,"
								  +"nom_client VARCHAR(250)  NULL,"
								  +"prenom_client VARCHAR(250)  NULL,"
								  +"date_anniversaire DATETIME  NULL"+")";
	
/*	private static final String CREATE_RECETTE_CONTENU ="CREATE TABLE recette_contenu ("
								  +"id_contenu INTEGER PRIMARY KEY AUTOINCREMENT,"
								  +"id_unitemesure INT NOT NULL,"
								  +"id_larecette INTEGER NOT NULL,"
								  +"id_condiment INTEGER NOT NULL,"
								  +"nombre_contenu DECIMAL NOT NULL,"
								  +"unitemesure_contenu VARCHAR(20) NULL"
								  +")";
	
	private static final String CREATE_RECETTE_CONDIMENT ="CREATE TABLE recette_condiment ("
								+"id_condiment INTEGER PRIMARY KEY AUTOINCREMENT,"
								+"id_typealiment INTEGER UNSIGNED NOT NULL,"
								+"nom_condiment VARCHAR(250) NOT NULL,"
								+"mesure_condiment VARCHAR(100) NOT NULL,"
								+"supplogique_condiment VARCHAR(10) NOT NULL"
								+")";
	*/
	
	
	public BDAcces(Context ctx) {
	 
		 this.mCtx = ctx;
	 }
 
	 
private static class DatabaseHelper extends SQLiteOpenHelper {
		
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		
		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(CREATE_CLIENT_TABLE);
			//db.execSQL(CREATE_RECETTE_CONTENU);
			//db.execSQL(CREATE_RECETTE_CONDIMENT);
	    }
		
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
			+ newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS "+CLIENT_TABLE);
			//db.execSQL("DROP TABLE IF EXISTS "+RECETTE_TABLE);
			//db.execSQL("DROP TABLE IF EXISTS "+INGREDIENT_RECETTE_TABLE);
			onCreate(db);
			}
		}
    

    //---opens the database---
      public void open() throws SQLException 
      {
    	  	mDbHelper = new DatabaseHelper(mCtx);
  			mDb = mDbHelper.getWritableDatabase();
  			//mbbuilder = 
  			
      }

      //---closes the database---    
      public void close() 
      {
    	  mDbHelper.close();
      }
      public String renvoi_requete(){
  		return CREATE_CLIENT_TABLE;
  		}
      public String Liste_table(){

    	  return mDb.getPath();
    	}
      
      public long insertClient(String sNomClient, String sPrenomClient,String DateAnniv) 
      {
          //Enregistrement de la recette dans ma base de donnée le nom et la préparation 
    	  
    	  ContentValues initialValues = new ContentValues();
          initialValues.put("nom_client", sNomClient);
          initialValues.put("prenom_client", sPrenomClient); 
          initialValues.put("date_anniversaire", DateAnniv);
          long idrecette = mDb.insert(CLIENT_TABLE, null, initialValues);
          String[] colonne= new String[]{"id_client"};
          String condition = "nom_client='"+sNomClient+"' and prenom_client='" +sPrenomClient+"'";
          String[] conditionArgs = null;
          String groupby="";
          String having="";
          String orderby="";
          
          	Cursor tmpcursor = mDb.query(CLIENT_TABLE, colonne, condition, conditionArgs, groupby, having, orderby);
          	int id_client=tmpcursor.getCount();
         
          		return id_client;
        	 }
         
      
    
      
      //renvoi liste recette ArrayList<String> 
      public ArrayList[] renvoi_liste_client(){
    	  
    	  String[] aTableCollonne=new String[] {"nom_client","prenom_client","date_anniversaire"};
		  Cursor objCursor = mDb.query(CLIENT_TABLE,aTableCollonne,null,null,null,null,null,null);
		  int iPostNomchaine = objCursor.getColumnIndex("nom_client");
		  int iPostPrenomChaine = objCursor.getColumnIndex("prenom_client");
		  int iPostDateAnniv = objCursor.getColumnIndex("date_anniversaire");
		  int itotal = objCursor.getCount();
		  ArrayList<String> aTableRetourNom = new ArrayList<String>();
		  ArrayList<String> aTableRetourPrenom = new ArrayList<String>();
		  ArrayList<String> aTableRetourAnniv = new ArrayList<String>();
		  
		  objCursor.moveToFirst();
		  ArrayList[] aTableRetour = new ArrayList[3];
		  
		/* Check if our result was valid. */
             if (objCursor != null) {            	 
            	 if (objCursor.isFirst()) { 
            		 int i = 0; 
            		 do {
                         i++;
                         String resultnom_client = objCursor.getString(iPostNomchaine);
                         String resultprenom_client = objCursor.getString(iPostPrenomChaine);
                         String resultAnniv_client = objCursor.getString(iPostDateAnniv);
                         aTableRetourNom.add(resultnom_client);
                         aTableRetourPrenom.add(resultprenom_client);
                         aTableRetourAnniv.add(resultAnniv_client);
                         objCursor.moveToNext();
            	     }while(!objCursor.isLast());
             }
             }
			
          aTableRetour[0]=aTableRetourNom;
          aTableRetour[1]=aTableRetourPrenom;
          aTableRetour[2]=aTableRetourAnniv;
		  return aTableRetour;
      }
      
      public void update_bdd(){
  		int newversion = mDb.getVersion()+1;
  		mDbHelper.onUpgrade(mDb, mDb.getVersion(), newversion);	
  		//mDbHelper.onCreate(mDb);
  	  }
      
    //renvoi info recette ArrayList<String> 
    /*  public String[] info_recette(String id_recette){
    	  
    	  String[] aTableauRetour = new String[2];
    	  
    	  String[] aTableCollonne=new String[] {"nom_larecette","id_larecette","recette_larecette"};
    	  String sFiltre="id_larecette='"+id_recette+"'";
    	  Cursor objCursor = mDb.query(RECETTE_TABLE,aTableCollonne,sFiltre,null,null,null,null,null);
    	  int iPostNomchaine = objCursor.getColumnIndex("nom_larecette");
		  int iPostRecette = objCursor.getColumnIndex("recette_larecette");
		  objCursor.moveToFirst();
		  String resultNomRecette = objCursor.getString(iPostNomchaine);
          String resultLaRecette = objCursor.getString(iPostRecette);
          aTableauRetour[0]=resultNomRecette;
          aTableauRetour[1]=resultLaRecette;
		  return aTableauRetour;
      }*/
      
      //fonction renvoi liste recette
      public String[] liste_recherche(){
    	  
    	   String[] COUNTRIES = new String[] {
    			"Afghanistan", "Albania", "Algeria", "American Samoa", "Andorra",
    			"Angola", "Anguilla", "Antarctica", "Antigua and Barbuda", "Argentina",
    			"Armenia", "Aruba", "Australia", "Austria", "Azerbaijan",
    			"Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium",
    			"Belize", "Benin", "Bermuda", "Bhutan", "Bolivia"};
    	   return COUNTRIES;
      }
    //fonction remplissage automatique de ma base de recette
      public ArrayList[]  tableau_tmp(){
    	  ArrayList[] aTableauTmp = new ArrayList[4];
    	  return aTableauTmp;
      }
      //fonction remplissage automatique de ma base de recette
      public  ArrayList<ArrayList> renvoi_liste_recette_xml() throws Exception{
    	 
    	  //***********************création de notre tableau dinamique   	  
    	  ArrayList<ArrayList> aTableRetour = new ArrayList<ArrayList>();
    	  
    	  //************************récupération du flux wml
    	  URL myURL = new URL("http://www.dgentreprises.com/recette/listerecette.php");
    	  DocumentBuilderFactory fabrique = DocumentBuilderFactory.newInstance();
          // création d'un constructeur de documents
          DocumentBuilder constructeur = fabrique.newDocumentBuilder();
          //*****************lecture du flux xml**************
          Document document = constructeur.parse(myURL.openStream());
          Element racine = document.getDocumentElement();
          NodeList liste = racine.getElementsByTagName("recette");
          //remplissage de mon tableau
          for(int i=0; i<liste.getLength(); i++){
        	  ArrayList<String> aTableauTmp =  new ArrayList<String>(); 
        	  Element E1= (Element) liste.item(i);
        	  //aTableRetour[i]= "";
        	  aTableauTmp.add(E1.getAttribute("nom"));
        	  aTableauTmp.add(E1.getAttribute("contenu"));
        	  aTableRetour.add(aTableauTmp);
          }
          
          return aTableRetour;
    	  
      }

      
      	
//**************************fin de la classe**********************************	
}
