package jus.poc.prodcons.v1;

import java.util.ArrayList;

import jus.poc.prodcons.Acteur;
import jus.poc.prodcons.Aleatoire;
import jus.poc.prodcons.ControlException;
import jus.poc.prodcons.Observateur;
import jus.poc.prodcons._Producteur;

public class Producteur extends Acteur implements _Producteur {
	
	private int nombreDeMessages;
	private ArrayList<Message1> messages;
	/*
	 * type : 
	 * 1 => typeProducteur
	 * 2 => typeConsommateur
	 * deviationTempsDeTraitement = l'écart type au temps moyen de traitement par cet acteur d'un message
	 * moyenneTempsDeTraitement = le temps moyen de traitement par cet acteur d'un message
	 */
	protected Producteur(int type, Observateur observateur, int moyenneTempsDeTraitement,
			int deviationTempsDeTraitement, Aleatoire alea) throws ControlException {
		super(type, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);		
		nombreDeMessages = alea.next();
		messages = new ArrayList<>();
		genererMessages();
		// TODO Auto-generated constructor stub
	}

	@Override
	//renvoie le nombre de messages (à) traités(er)
	public int nombreDeMessages() {
		// TODO Auto-generated method stub		
		return nombreDeMessages;
	}
	@Override
	public String toString() {
		return "Producteur [nombreDeMessages=" + nombreDeMessages + "]";
	}
		
	private void genererMessages(){
		for(int i = 0; i<nombreDeMessages;i++){
			messages.add(new Message1(i, this.identification(), "message"));
		}
	}
	
	public void afficherMessages(){
		for(Message1 m : messages){
			System.out.println(m.toString());
		}
	}
}
