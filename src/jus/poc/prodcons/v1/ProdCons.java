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
		
		// On récupère le message
		Message message = buffer[read_index];
		
		// le nombre de messages dans le buffer est décrémenté
		nbMessagesDansBuffer--;
		
		// On met à jour l'indice de la prochaine case dans le buffer où un consommateur pourra lire le message qui y est contenu.
		// Attention c'est un buffer circulaire d'ou la présence du modulo.
		read_index = (read_index+1) % taille();
		
		
		// Affichage
		System.out.println("Le message "+message+" est lu par le consommateur "+c.identification());
		
		// Réveille les autres threads pour qu'un autre puisse consommer à son tour
		notifyAll(); 
		
		return message;
	}

	@Override
	public synchronized void put(_Producteur p, Message m) throws Exception, InterruptedException {
		// Tant que le buffer est plein il faut attendre qu'une place se libère pour placer un message
		while(nbMessagesDansBuffer >= taille()){
			wait();
		}
		// Enregistrement du message dans le buffer
		buffer[write_index] = m;
		
		// Le nombre de messages dans le bufer s'incrémente
		nbMessagesDansBuffer++;
		
		//Mise à jour de l'indice de la prochaine case dans le buffer où on va pouvoir écrire
		// Attention c'est un buffer circulaire d'où la présence du modulo
		write_index = (write_index+1) % taille();
			
		
		//Affichage
		System.out.println("Le message "+m+" est déposé dans le buffer");
		
		// On réveille les autres threads pour que le prochain producteur puisse à son tour déposer un message
		notifyAll();
	}

	
	// Retourne la taille du buffer
	@Override
	public int taille() {
		return buffer.length;
	}

}
