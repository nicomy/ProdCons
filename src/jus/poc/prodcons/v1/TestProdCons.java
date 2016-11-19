package jus.poc.prodcons.v1;

import jus.poc.prodcons.Observateur;
import jus.poc.prodcons.Simulateur;

public class TestProdCons extends Simulateur {

	public TestProdCons(Observateur observateur){
		super(observateur);
	}

	protected static String option = "";

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
		Properties o = new Properties("jus/poc/prodcons/options/"+file);
		option = o.getProperty("nbProd");
	}

	@Override
	protected void run() throws Exception {
		// Corps du programme principal
		init("options.xml");
		System.out.println("nbProd = "+option);
	}
	public static void main(String[] args){
		new TestProdCons(new Observateur()).start();		
	}
}
