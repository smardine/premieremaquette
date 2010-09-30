package Thread;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import fr.smardine.android.premiereMaquette.R;

import android.widget.ProgressBar;
import android.widget.RemoteViews;

public class ThreadDownload extends Thread {
	
//tte les decalration necessaire...
	
	
	
	protected String adresse;
	protected ProgressBar Barreprogression;
	//protected JLabel TexteOperation,TexteMessageUtilisateur,TexteVitesse;
	
	
	
	/**
     * Passe  une requette HTTP et affiche le resultat dans des JTextField
     * @param url -String  url d'interrogation sur le site de maj
     * @param donneCaisseLocale -JTextField message pour l'utilisateur 
     * @param donneeCaisseSite -JTextField message pour l'utilisateur
     * @param donneeConvLocale -JTextField message pour l'utilisateur
     * @param donneeConvSite -JTextField message pour l'utilisateur
     * @param donneeMicrocLocale -JTextField message pour l'utilisateur
     * @param donneeMicrocSite -JTextField message pour l'utilisateur
     * @param text -JTextPane message pour l'utilisateur
     *  
     */
	
	public ThreadDownload (String url,ProgressBar progress)
			
			{
		adresse=url;
		Barreprogression=progress;
		
		
		
		}
	public void run(){
		//Barreprogression.setVisibility(VISIBLE);
		
		//TexteOperation.setText(" Opération (s) en cours: ");
		//TexteMessageUtilisateur.setText("Téléchargement de Pc Helpware");
		//TexteVitesse.setText("Vitesse Actuelle : 0 Ko/s");
		
		InputStream input = null;
		FileOutputStream writeFile = null;
		String fileName = null;
		String repTemp = null;
		long HeureDebut;
		long HeureActuelle;
		
		HeureDebut = System.currentTimeMillis();
		
		try
		{
			URL url = new URL(adresse);
			URLConnection connection = url.openConnection();
			
			
			final int fileLength = connection.getContentLength();

			if (fileLength == -1)
			{
				System.out.println("Invalide URL or file.");
				return;
			}
			
			//TexteTailleTotale.setText("Taille Totale : " +fileLength + "Octet(s)");

			input = connection.getInputStream();
			fileName = url.getFile().substring(url.getFile().lastIndexOf('/') + 1);
			if (fileName.contains("%20")==true){
				fileName=fileName.replaceAll("%20", " ");
			}
			if (fileName.contains("&amp;")==true){
				fileName=fileName.replaceAll("&amp;", " and ");
			}
			//VariableEnvironement.VarEnvSystemTotal();
			//repTemp = VariableEnvironement.VarEnvSystem("TMP");
			String PATH = "/mnt/sdcard/dd/";
			File fichier = new File (PATH+fileName);
			writeFile = new FileOutputStream(PATH+fileName);
			//lecture par segment de 4Ko
			byte[] buffer = new byte[4096*1024];
			int read;

			while ((read = input.read(buffer)) > 0){
				writeFile.write(buffer, 0, read);
				long TailleEncours = fichier.length();
				long progressionEnCours = (100*(TailleEncours+1))/fileLength;
				final int Pourcent=(int) progressionEnCours;
				
				HeureActuelle = System.currentTimeMillis();
				
				long Vitesse = (long) (TailleEncours / (HeureActuelle-HeureDebut));
						
				//TexteVitesse.setText("Vitesse Actuelle : "+ Vitesse + " Ko/s");
				System.out.println("Vitesse Actuelle : "+ Vitesse + " Ko/s");
				
				Barreprogression.setProgress(Pourcent);

			}
		
				
			
			writeFile.flush();
		}
		catch (IOException e)
		{
			System.out.println("Error while trying to download the file.");
			e.printStackTrace();
		}
		finally
		{
			try
			{
				writeFile.close();
				input.close();
				
				System.out.println("DL terminé");
				Barreprogression.setProgress(0);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		
		//dl terminé, execution du programme
		
		//OpenWithDefaultViewer.open(repTemp+"\\"+fileName);
	/*	Runtime r1 = Runtime.getRuntime();
		//Process p = null;
		
		String cmdExecuteSetup = ("\""+repTemp+"\\"+fileName+"\" ");//le parametre /silent permet de lancer le setup en automatique
		try {
		r1.exec(cmdExecuteSetup);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//JOptionPane.showMessageDialog(null,e,
				//	"Erreur au lancement du setup", JOptionPane.WARNING_MESSAGE);
			
			e.printStackTrace();
		}
		
		
		System.exit(0);*/
}
	
	

	
	public static double floor(double a, int decimales, double plus)
    {
       double p = Math.pow(10.0, decimales);
       //return Math.floor((a*p) + 0.5) / p; // avec arrondi éventuel (sans arrondi >>>> + 0.0
       return Math.floor((a*p) + plus) / p;
    }

}
