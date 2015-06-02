package RB;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

/**
 * @author Mihail Weiland (mihail.weiland@haw-hamburg.de) <br>
 * @author Edmund Schauer (edmund.schauer@haw-hamburg.de) <br>
 * 
 * @version 1.0<br>
 * 
 *          Praktikum Rechnernetze und Betriebssysteme, SS2015WI <br>
 *          Praktikumsgruppe 4 <br>
 *          Aufgabe 2 - "Fastfood"<br>
 *          Verwendete Quellen: Skript, 
 *          
 * @description Die Klasse KundenGenerator produziert eine Reihe von Kunden.
 * 			Die Anzahl der Kunden wird von der Klasse View bestimmt. 
 * 			Als Observable wird KundenGenerator von der Klasse
 * 			ServiceKraft beobachtet.
 */
public class KundenGenerator extends Observable {
	private List<Kunde> queue; 
	private int kdNr;

	/**
	 * Konstruktor. Die ersten Initialisierungen.
	 */
	public KundenGenerator(){
		super();
		this.queue = new LinkedList<Kunde>();
		this.kdNr = 1;
	}  

	/**
	 * @return eine Liste mit den Kunden
	 */
	public List<Kunde> getQueue() {
		return queue;
	}

	/**
	 * @param Anzahl der Kunden, die erzeugt werden muss
	 */
	public void esWerdeKunde(int kunden) {
		if(this.queue.size() > 0)
			this.queue.clear(); // Die Liste ist temporaer.
		
		for(int i = 0; i < kunden; i++){
			Kunde customer = new Kunde();
			int tmp = (int)(Math.random()*8+1);
			customer.setKdNr(kdNr++);
			customer.setMenge(tmp);
			queue.add(customer);
			System.out.println("  Neuer Kunde Nr: "+ customer.getKdNr());
			
		}
		setChanged();
		notifyObservers(true);
	}

}
