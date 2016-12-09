package jus.poc.prodcons.v1;

import jus.poc.prodcons.Message;

public class Message1 implements Message{
	private int rang;
	private int idProducteur;
	private String message;
	
	public Message1(int rang, int idProducteur, String message) {
		super();
		this.rang = rang;
		this.idProducteur = idProducteur;
		this.message = message;
	}

	@Override
	public String toString() {
		return "Message1 [rang=" + rang + ", idProducteur=" + idProducteur + ", message=" + message + "]";
	}	
}
