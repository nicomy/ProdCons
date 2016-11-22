package jus.poc.prodcons.v1;

import jus.poc.prodcons.Observateur;
import jus.poc.prodcons.Simulateur;

public class TestProdCons extends Simulateur {

	public TestProdCons(Observateur observateur){
		super(observateur);
		init("options.xml");
	}

	protected static String nbProd = "";
	protected static String nbCons = "";
	protected static String nbBuffer = "";

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
		// Corps du programme principal
		
		// Affichage des paramètres 
		System.out.println("Nombre de producteurs = "+this.nbProd+'\n');
		System.out.println("Nombre de consommateurs = "+this.nbCons+'\n');
		System.out.println("Taille buffer = "+this.nbBuffer+'\n');
		
		
	}
	public static void main(String[] args){
		new TestProdCons(new Observateur()).start();		
	}
}
