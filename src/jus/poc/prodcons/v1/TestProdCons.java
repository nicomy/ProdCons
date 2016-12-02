package jus.poc.prodcons.v1;

import java.util.ArrayList;

import jus.poc.prodcons.Acteur;
import jus.poc.prodcons.Aleatoire;
import jus.poc.prodcons.ControlException;
import jus.poc.prodcons.Observateur;
import jus.poc.prodcons.Simulateur;

public class TestProdCons extends Simulateur {

	private ArrayList<Producteur> producteurs;
	private Observateur obs = new Observateur();
	protected static String nbProd = "";
	protected static String nbCons = "";
	protected static String nbBuffer = "";
	
	public TestProdCons(Observateur observateur){
		super(observateur);
		init("options.xml");
					
	}
	

	protected static void init(String file) {
		
		final class Properties extends java.util.Properties {			
			private static final long serialVersionUID = 1L;		
			public int get(String key){
				return Integer.parseInt(getProperty(key));
		}
			
			public Properties(String file) {
				try{
					loadFromXML(ClassLoader.getSystemResourceAsStream(file));
				}catch(Exception e){e.printStackTrace();}
			}
		}
		
		// Récupération des paramètres 
		Properties o = new Properties("jus/poc/prodcons/options/"+file);
		nbProd = o.getProperty("nbProd");
		nbCons = o.getProperty("nbCons");
		nbBuffer = o.getProperty("nbBuffer");
	}

	@Override
	protected void run() throws Exception {
		producteurs = new ArrayList<>();	
		creerProducteurs();
		// Corps du programme principal
		
		// Affichage des paramètres 
		/*System.out.println("Nombre de producteurs = "+this.nbProd+'\n');
		System.out.println("Nombre de consommateurs = "+this.nbCons+'\n');
		System.out.println("Taille buffer = "+this.nbBuffer+'\n');
		*/
		for(Producteur p : producteurs){			
			System.out.println(p.toString());
			p.afficherMessages();
		}
	}
	public static void main(String[] args){
		new TestProdCons(new Observateur()).start();		
	}
	
	public void creerProducteurs(){
		Aleatoire alea = new Aleatoire(10, 5);
		for(int i = 0;i<Integer.parseInt(nbProd);i++){			
			try {				
				producteurs.add(new Producteur(1, obs, 1000, 100, alea));
			} catch (ControlException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		}
	}
}
