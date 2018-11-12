import fr.enseeiht.danck.voice_analyzer.DTWHelper;
import fr.enseeiht.danck.voice_analyzer.Field;

public class myDTW extends DTWHelper {

	@Override
	public float DTWDistance(Field unknown, Field known) {
		// Methode qui calcule le score de la DTW 
		// entre 2 ensembles de MFCC
		myMFCCdistance testeur = new myMFCCdistance();
		float[][] g = new float[unknown.getLength()+1][known.getLength()+1];
		g[0][0] = 0;
		
		int I = unknown.getLength();
		int J = known.getLength(); 
		
		for( int j = 1; j <= J ; j++) {
			g[0][j] = Float.POSITIVE_INFINITY;
		}
		
		for( int i = 1; i <= I ; i++) {
			g[i][0] = Float.POSITIVE_INFINITY;
			for( int j = 1; j <= J ; j++) {
				//recherche du chemin minimal
				g[i][j] = Math.min(g[i-1][j] + testeur.distance(unknown.getMFCC(i-1),known.getMFCC(j-1)),
						Math.min(g[i-1][j-1] + 2*testeur.distance(unknown.getMFCC(i-1),known.getMFCC(j-1)),g[i][j-1] + testeur.distance(unknown.getMFCC(i-1),known.getMFCC(j-1))));
			}
		}
	
		float D = g[I][J] / (I + J);
		return D;
	}

}
