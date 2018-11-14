import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.omg.Messaging.SyncScopeHelper;

import fr.enseeiht.danck.voice_analyzer.DTWHelper;
import fr.enseeiht.danck.voice_analyzer.Extractor;
import fr.enseeiht.danck.voice_analyzer.Field;
import fr.enseeiht.danck.voice_analyzer.MFCC;
import fr.enseeiht.danck.voice_analyzer.WindowMaker;
import fr.enseeiht.danck.voice_analyzer.defaults.DTWHelperDefault;

//A faire dans le dossier possible : 
	//-gérer le ncontexte de bruit des hélices : on va donc creer des contexte de bruits en salle de tp, dehors, et d'enregistrer 
		//ses ordres avec ces bruits
	//- on part sur une base d'apprentissage qui se rapproche le plus avec les tests BA : enregisrement niquel/pur 
	// et les tests : des enregistremetns avec des bruits de fonds

//on prend le nouveau fichier et on fait une DTW distance avec chaque BA pour le score minimum: si ça match on met 2 dans la matrice

//pour la matrice de diagonisation , on met 2 quand ça match et 1 sinon, et on compte le nb d'elem en dehors de la martice et on divise par le nb d'ordre(enreg)
//on va se rendre compte que la matrice pue
//du coup on va partir sur des fichiers de nous meme avec des bruits de fond et tout 

//compariason voix fille / voix homme
//comparaison bruit/pur
public class myDTWtest {

		//protected static final int MFCCLength = 13;
		
		static Integer[][] matriceConfusion;
		
		// Fonction permettant de calculer la taille des Fields 
				//c'est-à-dire le nombre de MFCC du Field 
					static int FieldLength(String fileName) throws IOException {
					int counter= 0;
					File file = new File(System.getProperty("user.dir") + fileName);
		            for (String line : Files.readAllLines(file.toPath(), Charset.defaultCharset())) {
		            	counter++;
		            }
		            return 2*Math.floorDiv(counter, 512);
				}

		public static void main(String[] args) throws IOException, InterruptedException {
			
			int MFCCLength;
			DTWHelper myDTWHelper= new myDTW();
			DTWHelper DTWHelperDefault= new DTWHelperDefault();
			 
			// Chemin de recherche des fichiers sons
		    String base = "/test_res/audio/";
		    
		    // Appel a l'extracteur par defaut (calcul des MFCC)
		    Extractor extractor = Extractor.getExtractor();
		    
		    //Lecture des fichiers audios
		    //De base
		    
		    //Alpha
			// Etape 1. Lecture de Alpha
		    List<String> files = new ArrayList<>();
		    files.add(base + "Alpha.csv");
		    WindowMaker windowMaker = new MultipleFileWindowMaker(files);
		    
		    // Etape 2. Recuperation des MFCC du mot Alpha
		    MFCCLength = FieldLength(base + "Alpha.csv");
		    MFCC[] mfccsAlpha = new MFCC[MFCCLength];
	        for (int i = 0; i < mfccsAlpha.length; i++) {
	            mfccsAlpha[i] = extractor.nextMFCC(windowMaker);
	        }
	        
	        // Etape 3. Construction du Field (ensemble de MFCC) de alpha
	        Field alphaField= new Field(mfccsAlpha);
	        
	        			 //Bravo
					     // Etape 1. Lecture de Bravo
					        files= new ArrayList<>();
						    files.add(base + "Bravo.csv");
						    windowMaker = new MultipleFileWindowMaker(files);
						    
						 // Etape 2. Recuperation des MFCC du mot Bravo
						    MFCCLength = FieldLength(base + "Bravo.csv");
						    MFCC[] mfccsBravo= new MFCC[MFCCLength];
					        for (int i = 0; i < mfccsBravo.length; i++) {
					            mfccsBravo[i] = extractor.nextMFCC(windowMaker);
					       
					        }
					        
					        // Etape 3. Construction du Field (ensemble de MFCC) de Bravo
					        Field bravoField= new Field(mfccsBravo);
	        
	       
			//Charlie
            // Etape 1. Lecture de Charlie
	        files= new ArrayList<>();
		    files.add(base + "Charlie.csv");
		    windowMaker = new MultipleFileWindowMaker(files);
		    
		    // Etape 2. Recuperation des MFCC du mot Charlie
		    MFCCLength = FieldLength(base + "Charlie.csv");
		    MFCC[] mfccsCharlie= new MFCC[MFCCLength];
	        for (int i = 0; i < mfccsCharlie.length; i++) {
	        	mfccsCharlie[i] = extractor.nextMFCC(windowMaker); 
	        }
	        
	        // Etape 3. Construction du Field (ensemble de MFCC) de Charlie
	        Field charlieField= new Field(mfccsCharlie);
	        // Si on veut rajouter de nouveaux mots, il suffit de repeter les etapes 1 a 3
	        
	        				//Delta
					        // Etape 1. Lecture de Delta
					        files= new ArrayList<>();
						    files.add(base + "Delta.csv");
						    windowMaker = new MultipleFileWindowMaker(files);
						    
						    // Etape 2. Recuperation des MFCC du mot Delta
						    MFCCLength = FieldLength(base + "Delta.csv");
						    MFCC[] mfccsDelta= new MFCC[MFCCLength];
					        for (int i = 0; i < mfccsDelta.length; i++) {
					        	mfccsDelta[i] = extractor.nextMFCC(windowMaker); 
					        }
					        
					        // Etape 3. Construction du Field (ensemble de MFCC) de Delta
					        Field deltaField= new Field(mfccsDelta);
					        
			//Dylan Avance
	        // Etape 1. Lecture de Delta
	        files= new ArrayList<>();
		    files.add(base + "dylan01.csv");
		    windowMaker = new MultipleFileWindowMaker(files);
		    
		    // Etape 2. Recuperation des MFCC du mot Delta
		    MFCCLength = FieldLength(base + "dylan01.csv");
		    MFCC[] mfccsDavance= new MFCC[MFCCLength];
	        for (int i = 0; i < mfccsDavance.length; i++) {
	        	mfccsDavance[i] = extractor.nextMFCC(windowMaker); 
	        }
	        
	        // Etape 3. Construction du Field (ensemble de MFCC) de Delta
	        Field dAvanceField= new Field(mfccsDavance);
	        
	        
					      	//Avance
					        // Etape 1. Lecture de Delta
					        files= new ArrayList<>();
						    files.add(base + "F01_avance.csv");
						    windowMaker = new MultipleFileWindowMaker(files);
						    
						    // Etape 2. Recuperation des MFCC du mot Delta
						    MFCCLength = FieldLength(base + "F01_avance.csv");
						    MFCC[] mfccsF01Avance= new MFCC[MFCCLength];
					        for (int i = 0; i < mfccsF01Avance.length; i++) {
					        	mfccsF01Avance[i] = extractor.nextMFCC(windowMaker); 
					        }
					        
					        // Etape 3. Construction du Field (ensemble de MFCC) de Delta
					        Field f01AvanceField= new Field(mfccsF01Avance);
					        
	        // Par ex., on peut tester que la distance entre alpha et alpha c'est 0
	        float mydistanceAlphaAlpha= myDTWHelper.DTWDistance(alphaField, alphaField);
	        float distanceAlphaAlphadefault= DTWHelperDefault.DTWDistance(alphaField, alphaField);
	        
	        System.out.println("myDTW - valeur distance Alpha-Alpha calculee : "+mydistanceAlphaAlpha);
	        System.out.println("DTWHelperDefault - valeur distance Alpha-Alpha calculee : "+distanceAlphaAlphadefault);
		
	        // Calcul de la distance entre Alpha et Bravo	        
	        float mydistanceAlphaBravo= myDTWHelper.DTWDistance(alphaField, bravoField);
	        float distanceAlphaBravodefault= DTWHelperDefault.DTWDistance(alphaField, bravoField);
	        
	        System.out.println("myDTW - valeur distance Alpha-Bravo calculee : "+mydistanceAlphaBravo);
	        System.out.println("DTWHelperDefault - valeur distance Alpha-Bravo calculee : "+distanceAlphaBravodefault);
	        	        
	        
	        // Calcul de la distance entre Alpha et Charlie	        
	        float mydistanceAlphaCharlie= myDTWHelper.DTWDistance(alphaField, charlieField);
	        float distanceAlphaCharliedefault= DTWHelperDefault.DTWDistance(alphaField, charlieField);
	        
	        System.out.println("myDTW - valeur distance Alpha-Charlie calculee : "+mydistanceAlphaCharlie);
	        System.out.println("DTWHelperDefault - valeur distance Alpha-Charlie calculee : "+distanceAlphaCharliedefault);
	        
	        // Calcul de la distance entre Alpha et Delta	        
	        float mydistanceAlphaDelta= myDTWHelper.DTWDistance(alphaField, deltaField);
	        float distanceAlphaDeltadefault= DTWHelperDefault.DTWDistance(alphaField, deltaField);
	        
	        System.out.println("myDTW - valeur distance Alpha-Delta calculee : "+mydistanceAlphaDelta);
	        System.out.println("DTWHelperDefault - valeur distance Alpha-Delta calculee : "+distanceAlphaDeltadefault);
	        	        
	        
	        // Calcul de la distance entre Bravo et Charlie	        
	        float mydistanceBravoCharlie= myDTWHelper.DTWDistance(bravoField,charlieField);
	        float distanceBravoCharliedefault= DTWHelperDefault.DTWDistance(bravoField,charlieField);
	        
	        System.out.println("myDTW - valeur distance Bravo-Charlie calculee : "+mydistanceBravoCharlie);
	        System.out.println("DTWHelperDefault - valeur distance Bravo-Charlie calculee : "+distanceBravoCharliedefault);
	        
	        
	        // Calcul de la distance entre Bravo et Delta   
	        float mydistanceBravoDelta= myDTWHelper.DTWDistance(bravoField,deltaField);
	        float distanceBravoDeltadefault= DTWHelperDefault.DTWDistance(bravoField,deltaField);
	        
	        System.out.println("myDTW - valeur distance Bravo-Delta calculee : "+mydistanceBravoDelta);
	        System.out.println("DTWHelperDefault - valeur distance Bravo-Delta calculee : "+distanceBravoDeltadefault);
	        
	        
	        // Calcul de la distance entre Charlie et Delta 
	        float mydistanceCharlieDelta= myDTWHelper.DTWDistance(charlieField,deltaField);
	        float distanceCharlieDeltadefault= DTWHelperDefault.DTWDistance(charlieField,deltaField);
	        
	        System.out.println("myDTW - valeur distance Charlie-Delta calculee : "+mydistanceCharlieDelta);
	        System.out.println("DTWHelperDefault - valeur distance Charlie-Delta calculee : "+distanceCharlieDeltadefault);
	        
	        // Calcul de la distance entre Alpha et Dylan avance 
	        float mydistanceAlphaDavance= myDTWHelper.DTWDistance(alphaField,dAvanceField);
	        float distanceAlphaDavancedefault= DTWHelperDefault.DTWDistance(alphaField,dAvanceField);
	        
	        System.out.println("myDTW - valeur distance Alpha et Dylan Avance calculee : "+mydistanceAlphaDavance);
	        System.out.println("DTWHelperDefault - valeur distance Alpha et Dylan Avance calculee : "+distanceAlphaDavancedefault);
	        
	        // Calcul de la distance entre Bravo et Dylan avance 
	        float mydistanceBravoDavance= myDTWHelper.DTWDistance(bravoField,dAvanceField);
	        float distanceBravoDavancedefault= DTWHelperDefault.DTWDistance(bravoField,dAvanceField);
	        
	        System.out.println("myDTW - valeur distance Bravo et Dylan Avance calculee : "+mydistanceBravoDavance);
	        System.out.println("DTWHelperDefault - valeur distance Bravo et Dylan Avance calculee : "+distanceBravoDavancedefault);
		
	        // Calcul de la distance entre Bravo et Dylan avance 
	        float mydistanceDavanceF01Avance= myDTWHelper.DTWDistance(dAvanceField,f01AvanceField);
	        float distanceDavanceF01Avancedefault= DTWHelperDefault.DTWDistance(dAvanceField,f01AvanceField);
	        
	        System.out.println("myDTW - valeur distance Dylan Avance et F01 Avance calculee : "+mydistanceDavanceF01Avance);
	        System.out.println("DTWHelperDefault - valeur distance Dylan Avance et F01 Avance calculee : "+distanceDavanceF01Avancedefault);
	        
	       //System.out.println(testBaseApprentissage(dAvanceField,0));
	       construction();
	        
		}
		
		
	/*	public static String testBaseApprentissage(Field test, int indiceTest) throws IOException, InterruptedException {
			
			HashMap retour = new HashMap<Float,String>();
			int MFCCLength;
			float min = 1000.0f;
			DTWHelper myDTWHelper= new myDTW();
			//DTWHelper DTWHelperDefault= new DTWHelperDefault();
			 
		    String base = "D:/M1 DL/Semestre 7/WorkspaceM1S7/TestDTWStudent/test_res/audio/testBA/";
		    File rep = new File(base);
		    String nomsFichiersX[] = rep.list();
		    //System.out.println(nomsFichiersX.length);
		    /*try {
		    nomsFichiersX = rep.list();
		    System.out.println(nomsFichiersX[0]);
		    System.out.println(nomsFichiersX[1]);
		    }catch (Exception e) {
		    	e.printStackTrace();
		    }
		    
		    Extractor extractor = Extractor.getExtractor();
		    
		    //List<String> nomsFichiers = new ArrayList<>();
		    //nomsFichiers.add("F01_avance.csv");
		    //nomsFichiers.add("M01_avance.csv");
		    String dossier = "/test_res/audio/testBA/";
		    for(String s : nomsFichiersX) {
				// Etape 1. Lecture
			    List<String> files = new ArrayList<>();
			    files.add(dossier + s);
			    //System.out.println(base);
			    //System.out.println(s);
			    System.out.print(s + " : ");
			    WindowMaker windowMaker = new MultipleFileWindowMaker(files);
			    
			    // Etape 2. Recuperation des MFCC
			    MFCC[] mfccs = new MFCC[MFCCLength];
		        for (int i = 0; i < mfccs.length; i++) {
		            mfccs[i] = extractor.nextMFCC(windowMaker);
		        }
		        
		        // Etape 3. Construction du Field (ensemble de MFCC)
		        Field field= new Field(mfccs);			      
		        float mydistance = myDTWHelper.DTWDistance(field,test);
		        System.out.println(mydistance);
		        retour.put(mydistance,s);
		        if (mydistance < min ) {
		        	min = mydistance;
		        }
		    }			    
			return (String) retour.get(min) + min;
		}
*/
		public static void creerMatriceDeConfusion(int i, int j) {
			matriceConfusion = new Integer[i][j];
			for(int x = 0 ; x < i ; x++ ) {
				for(int y = 0 ; y < j ; y++ ) {
					matriceConfusion[x][y] = 0;
				}
			}
		}
		
		public static void afficherMatriceDeConfusion(String[] ba,String[] fichiersTest) {
			
			for(int i = 0 ; i < ba.length ; i++) {
				String s = "[ ";
				for(int j = 0; j < ba.length; j++) {
					s += matriceConfusion[i][j] +" ";
				}
				s+= "] ";
				System.out.println(s + ba[i]);
			}
			
			for(int i = 0 ; i <fichiersTest.length ; i++) {
				System.out.println( fichiersTest[i]);
			}
			
		}
		public static void addMatriceDeConfusion(int i, int j, int valeur) {
			matriceConfusion[i][j] = valeur;
		}
		
		public static void construction() throws IOException, InterruptedException {
			 creerMatriceDeConfusion(9,9);
			 DTWHelper myDTWHelper= new myDTW();
			 int MFCCLength;
			 String test = "D:/M1 DL/Semestre 7/WorkspaceM1S7/TestDTWStudent/test_res/audio/test/";
			 File rep = new File(test);
			 String nomsFichiersX[] = rep.list();
			 
			 String base = "D:/M1 DL/Semestre 7/WorkspaceM1S7/TestDTWStudent/test_res/audio/testBA/";
			 File repBase = new File(base);
			 String nomsFichiersBase[] = repBase.list();			    
			    
			 String dossierTest = "/test_res/audio/test/";
			 String dossierBase = "/test_res/audio/testBA/";
			 int indiceI = 0;
			 
			 for(int j = 0 ; j < nomsFichiersX.length ; j++) {
					// Etape 1. Lecture
				 	Extractor extractorTest = Extractor.getExtractor();
				    List<String> files = new ArrayList<>();
				    files.add(dossierTest + nomsFichiersX[j]);
				    System.out.println("---------Nom fichier : " + nomsFichiersX[j]+ "-------");
				    WindowMaker windowMaker = new MultipleFileWindowMaker(files);
				    
				    // Etape 2. Recuperation des MFCC
				    MFCCLength = FieldLength(dossierTest + nomsFichiersX[j]);
				    MFCC[] mfccs = new MFCC[MFCCLength];
			        for (int y = 0; y < mfccs.length; y++) {
			            mfccs[y] = extractorTest.nextMFCC(windowMaker);
			        }
			        
			        // Etape 3. Construction du Field (ensemble de MFCC)
			        Field field= new Field(mfccs);
			        		        
			        //test avec la base d'apprentissage 			        
			        float min = 1000.0f;
				    for(int i = 0; i < nomsFichiersBase.length ; i++) {
						// Etape 1. Lecture
				    	Extractor extractorBase = Extractor.getExtractor();
					    List<String> filesBase = new ArrayList<>();
					    filesBase.add(dossierBase + nomsFichiersBase[i]);
					    windowMaker = new MultipleFileWindowMaker(filesBase);
					    
					    // Etape 2. Recuperation des MFCC
					    MFCCLength = FieldLength(dossierBase + nomsFichiersBase[i]);
					    MFCC[] mfccsBase = new MFCC[MFCCLength];
				        for (int x = 0; x < mfccsBase.length; x++) {
				        	mfccsBase[x] = extractorBase.nextMFCC(windowMaker);
				        }
				        
				        // Etape 3. Construction du Field (ensemble de MFCC)
				        Field fieldBase= new Field(mfccsBase);			      
				        float mydistance = myDTWHelper.DTWDistance(fieldBase,field);
				        System.out.println("Nom fichier base : " + nomsFichiersBase[i]+ " : " + mydistance);
				        if (mydistance < min ) {
				        	min = mydistance;
				        	indiceI = i;
				        }
				    }
				    System.out.println("----------------------------------------------------");
				    addMatriceDeConfusion(indiceI, j, 1);
			 }
			 
			 afficherMatriceDeConfusion(nomsFichiersBase,nomsFichiersX);
		}
		
		// Application de l'ACP
		public void baseToAcp(MFCC[] base) {

			float[] g = new float[base[0].getLength()];
			float[][] xc = new float[base.length][base[0].getLength()] ;
			float[][] xcT = new float[base.length][base[0].getLength()] ;

			for (int i = 0 ; i < xc.length ; i++)
				for (int j = 0 ; j < xc[0].length ; j++) {
					xc[i][j] = base[i].getCoef(j) ;
					g[j] += xc[i][j] ;
				}
	
			for (int i = 0 ; i < g.length ; i++)
				g[i] /= xc.length ;

			for (int i = 0 ; i < xc.length ; i++)
				for (int j = 0 ; j < xc[0].length ; j++)
					xc[i][j] -= g[j] ;
			
			for (int i = 0 ; i < xc.length ; i++)
				for (int j = 0 ; j < xc[0].length ; j++)
					xcT[j][i] = xc[i][j] ;

			
		}
}
