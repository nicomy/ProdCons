/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jus.poc.prodcons.v2;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Antoine Gambro
 */
public class Semaphore {
    private int taille;

    public Semaphore(int taille) {
        this.taille = taille;
    }
    
    public synchronized void P() {
        if(taille <= 0) {
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Semaphore.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public synchronized void V() {
        taille++;
        notify();
    }
}
