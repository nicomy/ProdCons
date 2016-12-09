package jus.poc.prodcons.v1;

import jus.poc.prodcons.Message;
import jus.poc.prodcons.Tampon;
import jus.poc.prodcons._Consommateur;
import jus.poc.prodcons._Producteur;

public class ProdCons implements Tampon{

	// Tableau de messages symbolisant le buffer
	private Message[] buffer;
	
	private int read_index;
	private int write_index;
	
	private int nbMessagesDansBuffer;
	
	public ProdCons(int tailleBuffer){
		buffer = new Message[tailleBuffer];
	}
	
	
	
	// Retourne le nombre de messages contenus dans le buffer
	@Override
	public synchronized int enAttente() {
		return nbMessagesDansBuffer;
	}

	
	
	// Consomme un message dans le buffer
	@Override
	public synchronized Message get(_Consommateur c) throws InterruptedException {
		// tant que le buffer est vide, on attend qu'il se remplisse
		while(nbMessagesDansBuffer <= 0){
			wait();
		}
		
		// On r�cup�re le message
		Message message = buffer[read_index];
		
		// le nombre de messages dans le buffer est d�cr�ment�
		nbMessagesDansBuffer--;
		
		// On met � jour l'indice de la prochaine case dans le buffer o� un consommateur pourra lire le message qui y est contenu.
		// Attention c'est un buffer circulaire d'ou la pr�sence du modulo.
		read_index = (read_index+1) % taille();
		
		
		// Affichage
		System.out.println("Le message "+message+" est lu par le consommateur "+c.identification());
		
		// R�veille les autres threads pour qu'un autre puisse consommer � son tour
		notifyAll(); 
		
		return message;
	}

	@Override
	public synchronized void put(_Producteur p, Message m) throws Exception, InterruptedException {
		// Tant que le buffer est plein il faut attendre qu'une place se lib�re pour placer un message
		while(nbMessagesDansBuffer >= taille()){
			wait();
		}
		// Enregistrement du message dans le buffer
		buffer[write_index] = m;
		
		// Le nombre de messages dans le bufer s'incr�mente
		nbMessagesDansBuffer++;
		
		//Mise � jour de l'indice de la prochaine case dans le buffer o� on va pouvoir �crire
		// Attention c'est un buffer circulaire d'o� la pr�sence du modulo
		write_index = (write_index+1) % taille();
			
		
		//Affichage
		System.out.println("Le message "+m+" est d�pos� dans le buffer");
		
		// On r�veille les autres threads pour que le prochain producteur puisse � son tour d�poser un message
		notifyAll();
	}

	
	// Retourne la taille du buffer
	@Override
	public int taille() {
		return buffer.length;
	}

}
