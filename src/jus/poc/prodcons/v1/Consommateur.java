package jus.poc.prodcons.v1;

import jus.poc.prodcons.Acteur;
import jus.poc.prodcons.ControlException;
import jus.poc.prodcons.Observateur;
import jus.poc.prodcons._Consommateur;

public class Consommateur extends Acteur implements _Consommateur {
	
	/*
	 * type : 
	 * 1 => typeProducteur
	 * 2 => typeConsommateur
	 * deviationTempsDeTraitement = l'écart type au temps moyen de traitement par cet acteur d'un message
	 * moyenneTempsDeTraitement = le temps moyen de traitement par cet acteur d'un message
	 */
	protected Consommateur(int type, Observateur observateur, int moyenneTempsDeTraitement,
			int deviationTempsDeTraitement) throws ControlException {
		super(type, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		// TODO Auto-generated constructor stub
	}

	@Override
	//renvoie le nombre de messages (à) traités(er)
	public int nombreDeMessages() {
		// TODO Auto-generated method stub
		return 0;
	}
}
