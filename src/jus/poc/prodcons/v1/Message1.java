package jus.poc.prodcons.v1;

import jus.poc.prodcons.Message;

public class Message1 implements Message{
	private int idMessage;
	private int idProducteur;
	
	public Message1(int rang, int idProducteur) {
		super();
		this.idMessage = rang;
		this.idProducteur = idProducteur;
	}

	@Override
	public String toString() {
		return "Message1 [rang=" + idMessage + ", idProducteur=" + idProducteur+"]";
	}	
}
