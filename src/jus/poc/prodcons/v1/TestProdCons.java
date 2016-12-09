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
		nbProd = Integer.parseInt(o.getProperty("nbProd"));
		nbCons = Integer.parseInt(o.getProperty("nbCons"));
		nbBuffer = Integer.parseInt(o.getProperty("nbBuffer"));
		tempsMoyenProduction = Integer.parseInt(o.getProperty("tempsMoyenProduction"));
		deviationTempsMoyenConsommation = Integer.parseInt(o.getProperty("deviationTempsMoyenConsommation"));
		nombreMoyenDeProduction = Integer.parseInt(o.getProperty("nombreMoyenDeProduction"));
		deviationNombreMoyenDeProduction = Integer.parseInt(o.getProperty("deviationNombreMoyenDeProduction"));
		nombreMoyenNbExemplaire = Integer.parseInt(o.getProperty("nombreMoyenNbExemplaire"));
		deviationNombreMoyenNbExemplaire = Integer.parseInt(o.getProperty("deviationNombreMoyenNbExemplaire"));
	}

	@Override
	protected void run() throws Exception {
		// Corps du programme principal
		creerProducteurs();
		
		// Affichage des paramètres 
		/*System.out.println("Nombre de producteurs = "+this.nbProd+'\n');
		System.out.println("Nombre de consommateurs = "+this.nbCons+'\n');
		System.out.println("Taille buffer = "+this.nbBuffer+'\n');
		*/
		
		// Affichage des messages produits par les producteurs
		for(Producteur p : producteurs){			
			System.out.println(p.toString());
			p.afficherMessages();
		}
	}
	public static void main(String[] args){
		new TestProdCons(new Observateur()).start();		
	}
	
	public void creerProducteurs(){
		producteurs = new ArrayList<>();
		//TODO			
		Aleatoire alea = new Aleatoire(nombreMoyenDeProduction,deviationNombreMoyenDeProduction);
		for(int i = 0;i<nbProd;i++){			
			try {	
				producteurs.add(new Producteur(1, obs, tempsMoyenProduction,deviationTempsMoyenProduction, alea));
			} catch (ControlException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		}
	}
}
