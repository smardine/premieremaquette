package Thread;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import android.graphics.Bitmap;
import android.os.AsyncTask;

@SuppressWarnings("rawtypes")
public class DownloadFileTask extends AsyncTask {
    

    protected void onPostExecute(Bitmap result) {
       // mImageView.setImageBitmap(result);
    }

	protected Object doInBackground(Object... params) {
		InputStream input = null;
		FileOutputStream writeFile = null;
		String fileName = null;
		long HeureDebut;
		long HeureActuelle;
		
		HeureDebut = System.currentTimeMillis();
		
		try
		{
			URL url = new URL(params[0].toString());
			URLConnection connection = url.openConnection();
			//String message = "Téléchargement en cours";
		//	MessageStatus.setText(message);
			final int fileLength = connection.getContentLength();

			if (fileLength == -1)
			{
				System.out.println("Invalide URL or file.");
				return fileLength;
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
			String PATH = "/data/data/fr.smardine.android.premiereMaquette/dl/";
			//String PATH = "/sdcard/dl/";
			File path = new File (PATH);
			if (!path.exists()){
				path.mkdirs();
			}
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
				//MessageVitesse.setText("Vitesse Actuelle : "+ Vitesse + " Ko/s");
				
				//Barreprogression.setProgress(Pourcent);
				publishProgress();

			}
			writeFile.flush();
		}
		catch (IOException e)
		{
			System.out.println("Error while trying to download the file.");
			System.out.println(e);
			e.printStackTrace();
		}
		finally
		{
			try
			{
				writeFile.close();
				input.close();
				
			//	System.out.println("DL terminé");
				//Barreprogression.setProgress(0);
				//MessageStatus.setText("Telechargement terminé");
			}
			catch (IOException e)
			{
				
				System.out.println(e);
				e.printStackTrace();
			}
		}
		return fileName;
	}

	
	
}