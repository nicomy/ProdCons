package jus.poc.prodcons.v1;

import java.util.ArrayList;
import jus.poc.prodcons.Aleatoire;
import jus.poc.prodcons.ControlException;
import jus.poc.prodcons.Observateur;
import jus.poc.prodcons.Simulateur;

public class TestProdCons extends Simulateur {

	private ArrayList<Producteur> producteurs;
	private ArrayList<Consommateur> consommateurs;
	private Observateur obs = new Observateur();
	protected static int nbProd;
	protected static int nbCons;
	protected static int nbBuffer;
	protected static int tempsMoyenProduction;
	protected static int deviationTempsMoyenProduction;
	protected static int tempsMoyenConsommation;
	protected static int deviationTempsMoyenConsommation;
	protected static int nombreMoyenDeProduction;
	protected static int deviationNombreMoyenDeProduction;
	protected static int nombreMoyenNbExemplaire;
	protected static int deviationNombreMoyenNbExemplaire;
	private ProdCons buffer;
	
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
		nbProd = o.get("nbProd");
		nbCons = o.get("nbCons");
		nbBuffer = o.get("nbBuffer");
		tempsMoyenProduction = o.get("tempsMoyenProduction");
		deviationTempsMoyenProduction = o.get("deviationTempsMoyenProduction");
		tempsMoyenConsommation = o.get("tempsMoyenConsommation");
		deviationTempsMoyenConsommation = o.get("deviationTempsMoyenConsommation");
		nombreMoyenDeProduction = o.get("nombreMoyenDeProduction");
		deviationNombreMoyenDeProduction = o.get("deviationNombreMoyenDeProduction");
		nombreMoyenNbExemplaire = o.get("nombreMoyenNbExemplaire");
		deviationNombreMoyenNbExemplaire = o.get("deviationNombreMoyenNbExemplaire");
	}
	
	public static void main(String[] args){
		new TestProdCons(new Observateur()).start();		
	}
	
	public void displayConfiguration(){
		System.out.println("nbProd = "+nbProd);
		System.out.println("nbCons = "+nbCons);
		System.out.println("nbBuffer = "+nbBuffer);
		System.out.println("tempsMoyenProduction = "+tempsMoyenProduction);
		System.out.println("deviationTempsMoyenProduction = "+deviationTempsMoyenProduction);
		System.out.println("tempsMoyenConsommation = "+tempsMoyenConsommation);
		System.out.println("deviationTempsMoyenConsommation = "+deviationTempsMoyenConsommation);
		System.out.println("nombreMoyenDeProduction = "+nombreMoyenDeProduction);
		System.out.println("deviationNombreMoyenDeProduction = "+deviationNombreMoyenDeProduction);
		System.out.println("nombreMoyenNbExemplaire = "+nombreMoyenNbExemplaire);
		System.out.println("deviationNombreMoyenNbExemplaire = "+deviationNombreMoyenNbExemplaire);		
	}

	@Override
	protected void run() throws Exception {
		
		//Affichage de la configuration 
		//displayConfiguration();
		
		// Création du buffer
		buffer = new ProdCons(nbBuffer);
		
		// Création des producteurs et des consommateurs
		creerProducteurs();
		System.out.println("Création des producteurs terminée");
		
		creerConsommateurs();
		System.out.println("Création des consommateurs terminée");
		
		// A cet instant du programme, il faut attendre que tout les threads producteurs aient finis de produire:
		// join : met le thread courant en attente en attendant que le thread appelant soit mort
		for(Producteur p : producteurs){
			p.join();
		}
		System.out.println("Les messages ont tous été produits");
		
		// Ici, toutes les productions sont terminées, il faut alors attendre que le buffer soit vide avant de les interrompre
		do{
			Thread.yield();
		}while(buffer.enAttente()>0);
		
		// Tous les messages ont été lus
		System.out.println("Les messages ont tous été lus");
		
		// Interruption de tous les consommateurs
		for(Consommateur c : consommateurs){
			c.interrupt();
		}

	}

	
	// Méthode de création des producteurs
	public void creerProducteurs(){
		producteurs = new ArrayList<>();
		
		System.out.println(nbProd+" producteurs à créer");
		
		// alea va servir à donner un nombre aléatoire de messages à produire pour chaque producteur
		Aleatoire alea = new Aleatoire(nombreMoyenDeProduction,deviationNombreMoyenDeProduction);
		for(int i = 0;i<nbProd;i++){			
			try {	
				System.out.println("Création du producteur "+(i+1));
				Producteur p = new Producteur(obs, buffer, tempsMoyenProduction,deviationTempsMoyenProduction, alea);
				producteurs.add(p);
				p.start();
			} catch (ControlException e) {
				System.out.println("Erreur lors de la création des producteurs");
				e.printStackTrace();
			}		
		}
	}
	
	
	// Méthode de création des consommateurs
	public void creerConsommateurs(){
		consommateurs = new ArrayList<>();
		System.out.println(nbCons + " consommateurs à créer");
		for(int i = 0;i<nbCons;i++){			
			try {	
				System.out.println("Création du consommateur "+(i+1));
				Consommateur c = new Consommateur(obs,buffer,tempsMoyenConsommation,deviationTempsMoyenConsommation);
				consommateurs.add(c);
				c.start();
			} catch (ControlException e) {
				// TODO Auto-generated catch block
				System.out.println("Erreur lors de la création des consommateurs");
				e.printStackTrace();
			}		
		}
	}
}
