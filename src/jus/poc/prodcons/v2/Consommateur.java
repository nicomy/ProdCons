package jus.poc.prodcons.v2;

import jus.poc.prodcons.Acteur;
import jus.poc.prodcons.Aleatoire;
import jus.poc.prodcons.ControlException;
import jus.poc.prodcons.Message;
import jus.poc.prodcons.Observateur;
import jus.poc.prodcons._Consommateur;

public class Consommateur extends Acteur implements _Consommateur {
	
	// Le nombre de messages que le consommateur a lu
	private int nbMessagesConsommes = 0;
	
	// Buffer dans lequel le consommateur va pouvoir aller chercher les messages � traiter
	private ProdCons buffer;
	
	protected Consommateur(Observateur observateur, ProdCons buffer,  int moyenneTempsDeTraitement,
			int deviationTempsDeTraitement) throws ControlException {
		super(Acteur.typeConsommateur, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		// TODO Auto-generated constructor stub
		this.buffer = buffer;
	}

	@Override
	//renvoie le nombre de messages consomm�s
	public int nombreDeMessages() {
		// TODO Auto-generated method stub
		return nbMessagesConsommes;
	}
	
	
	// Run un thread de type consommateur => un consommateur peut lire un message dans le buffer tant que c'est possible.
	public void run(){		
		try{
			// Les consommateurs sont constamment en train d'essayer de lire les messages contenus dans le buffer
			while(true){
				// on r�cup�re un message
				
				Message m = this.buffer.get(this);
				
				//Calcul du temps de consommation
				int tempsCons = Aleatoire.valeur(moyenneTempsDeTraitement(), deviationTempsDeTraitement());				
				// On simule l'attente 
				
				Thread.sleep(tempsCons);
							
				// On a lu 1 message, il faut donc incr�menter nbMessageConsommes
				nbMessagesConsommes++;
			}			
		}catch(InterruptedException e){
			
		}
	}
}
