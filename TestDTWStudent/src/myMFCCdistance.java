import fr.enseeiht.danck.voice_analyzer.MFCC;
import fr.enseeiht.danck.voice_analyzer.MFCCHelper;

public class myMFCCdistance extends MFCCHelper {

	@Override
	public float distance(MFCC mfcc1, MFCC mfcc2) {
		// calcule la distance entre 2 MFCC
		float retour = 0;
		int mfcclength = mfcc1.getLength();
		
		for(int i=0 ; i < mfcclength ; i++) {
			float x = (float) Math.pow(mfcc1.getCoef(i)-mfcc2.getCoef(i),2);
			retour += x;
		}		
		
		return (float) Math.sqrt(retour);
	}

	@Override
	public float norm(MFCC mfcc) {
		// retourne la valeur de mesure de la MFCC (coef d'indice 0 dans la MFCC) 
		// cette mesure permet de determiner s'il s'agit d'un mot ou d'un silence
		return mfcc.getCoef(0);
	}

	@Override
	public MFCC unnoise(MFCC mfcc, MFCC noise) {
		// supprime le bruit de la MFCC passee en parametre
		// soustrait chaque coef du bruit a chaque coef du la MFCC 
		// passee en parametre
		int mfcclength = mfcc.getLength();
		float[] coef = new float[mfcclength];
		
		for(int i=0 ; i <= mfcclength ; i++) {
			coef[i] = mfcc.getCoef(i) - noise.getCoef(i);
		}
		
		MFCC mfccRetour = new MFCC(coef, mfcc.getSignal());
		return mfccRetour;
	}

}
