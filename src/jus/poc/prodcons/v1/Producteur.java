package jus.poc.prodcons.v1;

import jus.poc.prodcons.Acteur;
import jus.poc.prodcons.Aleatoire;
import jus.poc.prodcons.ControlException;
import jus.poc.prodcons.Message;
import jus.poc.prodcons.Observateur;
import jus.poc.prodcons._Producteur;

public class Producteur extends Acteur implements _Producteur {
	
	// Le nombre de messages que le producteur doit produire
	private int nombreDeMessagesAproduire;
	
	// Le buffer dans lequel le producteur devra déposer les messages qu'il aura produit
	private ProdCons buffer;
	
	protected Producteur(Observateur observateur, ProdCons buffer, int moyenneTempsDeTraitement,
			int deviationTempsDeTraitement, Aleatoire alea) throws ControlException {
		super(Acteur.typeProducteur, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		
		// Le nombre de messages à produire est défini aléatoirement;
		nombreDeMessagesAproduire = alea.next();
		this.buffer = buffer;
	}

	@Override
	//renvoie le nombre de messages (à) traités(er)
	public int nombreDeMessages() {
		// TODO Auto-generated method stub		
		return nombreDeMessagesAproduire;
	}
	@Override
	public String toString() {
		return "Producteur [nombreDeMessages=" + nombreDeMessagesAproduire + "]";
	}
	
	
	
	
	// Run un thread de type producteur. => Production de nombreDeMessagesAproduire et placement dans le buffer
	public void run(){
		int idMessage = 1;
		int tempsProd;
		while(nombreDeMessages() >0 ){
			Message message = new Message1(idMessage,this.identification());
			
			//Calcul du temps de production
			tempsProd = Aleatoire.valeur(moyenneTempsDeTraitement(),deviationTempsDeTraitement());
			
			// Le thread attend tempsProd pour simuler le temps que la ressource soit produite
			try {
				Thread.sleep(tempsProd);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			// On dépose le message produit dans le buffer
			try {
				this.buffer.put(this, message);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

			// Le nombre de messages restant à produire est décrémenté
			this.nombreDeMessagesAproduire--;
			
			// On incrémente l'id du message
			idMessage++;
			
		}
	}
}
